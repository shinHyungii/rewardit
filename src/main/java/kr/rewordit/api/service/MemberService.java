package kr.rewordit.api.service;

import kr.rewordit.api.client.google.GoogleLoginClient;
import kr.rewordit.api.client.google.message.SocialAccountProfile;
import kr.rewordit.api.dto.GoogleLoginReq;
import kr.rewordit.api.dto.MemberShowRes;
import kr.rewordit.api.dto.MemberStoreReq;
import kr.rewordit.api.dto.TokenInfo;
import kr.rewordit.api.exception.CustomException;
import kr.rewordit.api.model.Member;
import kr.rewordit.api.model.RewardHistory;
import kr.rewordit.api.model.RewarditEvent;
import kr.rewordit.api.repository.MemberRepository;
import kr.rewordit.api.repository.RewardHistoryRepository;
import kr.rewordit.api.repository.RewarditEventRepository;
import kr.rewordit.api.security.CustomUserDetails;
import kr.rewordit.api.security.JwtProvider;
import kr.rewordit.api.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final GoogleLoginClient googleClient;

    private final MemberRepository memberRepository;

    private final RewardHistoryRepository rewardHistoryRepository;

    private final RewarditEventRepository rewarditEventRepository;

    private final JwtProvider jwtProvider;


    @Transactional
    public boolean existsGoogleAccount(MemberStoreReq request) {
        SocialAccountProfile profile = googleClient.getProfile(request.getCode());

        if (profile == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Social user lookup failed");
        }

        return memberRepository.findByLoginIdOrEmail(profile.getEmail(), profile.getEmail()).isPresent();
    }


    @Transactional
    public TokenInfo googleLogin(GoogleLoginReq request) {
        SocialAccountProfile profile = googleClient.getProfile(request.getCode());

        if (profile == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Social user lookup failed");
        }

        Member member = memberRepository.findByLoginIdOrEmail(profile.getEmail(), profile.getEmail())
            .orElseGet(() -> {
                Member newMember = Member.builder()
                    .name(profile.getName())
                    .password(request.getCode())
                    .loginId(profile.getEmail())
                    .email(profile.getEmail())
                    .rewardPoint(0)
                    .build();

                memberRepository.save(newMember);

                // event 적립

                List<RewarditEvent> eventList = rewarditEventRepository.findAllEvent(DateUtils.now());
                log.debug("event exists : {}", !eventList.isEmpty());

                if (!eventList.isEmpty()) {

                    eventList.forEach(event -> {

                        if (event.getMemberLimit() == null || event.getMemberLimit() > 0) {
                            log.info("{} 이벤트 {} 포인트 적립", event.getEventName(), event.getEventPoint());

                            newMember.givePoint(2000);

                            RewardHistory rewardHistory = RewardHistory.builder()
                                .member(newMember)
                                .adsSubType(event.getEventName())
                                .eventName(event.getEventName())
                                .acceptedAt(DateUtils.now())
                                .remainReward(event.getEventPoint())
                                .build();

                            rewardHistoryRepository.save(rewardHistory);

                            // 회원 limit -1
                            event.decMemberLimit();
                        }
                    });
                }

                return newMember;
            });

        member.loggedIn();

        return TokenInfo.builder()
            .accessToken(jwtProvider.createAccessToken(member))
            .refreshToken(jwtProvider.createRefreshToken(member))
            .build();
    }


    public TokenInfo refresh(CustomUserDetails userDetails) {
        Member member = memberRepository.findById(Long.parseLong(userDetails.getUsername()))
            .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "가입되지 않은 회원입니다."));

        return TokenInfo.builder()
            .accessToken(jwtProvider.createAccessToken(member))
            .build();
    }


    public MemberShowRes show(CustomUserDetails userDetails) {
        Member member = memberRepository.findById(Long.parseLong(userDetails.getUsername()))
            .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "가입되지 않은 회원입니다."));

        return MemberShowRes.builder()
            .id(member.getId())
            .email(member.getEmail())
            .name(member.getName())
            .rewardPoint(member.getRewardPoint())
            .usedPoint(member.getUsedPoint())
            .build();
    }
}
