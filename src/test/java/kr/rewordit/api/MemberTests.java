package kr.rewordit.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.rewordit.api.client.google.GoogleLoginClient;
import kr.rewordit.api.client.google.message.SocialAccountProfile;
import kr.rewordit.api.dto.GoogleLoginReq;
import kr.rewordit.api.dto.MemberStoreReq;
import kr.rewordit.api.model.Member;
import kr.rewordit.api.model.RewarditEvent;
import kr.rewordit.api.repository.MemberRepository;
import kr.rewordit.api.repository.RewarditEventRepository;
import kr.rewordit.api.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class MemberTests {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RewarditEventRepository rewarditEventRepository;

    @MockitoBean
    GoogleLoginClient googleLoginClient;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    JwtProvider provider;


    @BeforeEach
    void setup() {
        log.info("setup");

        rewarditEventRepository.save(
            RewarditEvent.builder()
                .eventName("회원가입 적립")
                .eventPoint(2000)
                .memberLimit(2)
                .build()
        );
    }


    @Test
    void googleSignup_success() throws Exception {
        // given
        String email = "test@gmail.com";
        String code = "1234";
        MemberStoreReq request = MemberStoreReq.builder()
            .code(code)
            .email(email)
            .name("test")
            .phone("01039335727")
            .address("test")
            .build();

        // when
        Mockito
            .when(googleLoginClient.getProfile(code))
            .thenReturn(new SocialAccountProfile(email, email));

        // then
        mockMvc.perform(
                post("/member/google/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(request))
            )
            .andExpect(status().isOk());

        // then
        Optional<Member> member = memberRepository.findByEmail(email);
        assertTrue(member.isPresent());
        assertThat(member.get().getPassword()).isEqualTo(code);
        assertThat(member.get().getRewardPoint()).isEqualTo(2000);

        Optional<RewarditEvent> event = rewarditEventRepository.findById(1L);
        assertTrue(event.isPresent());
        assertThat(event.get().getMemberLimit()).isEqualTo(1);
    }


    @Test
    void googleLogin_success() throws Exception {
        // given
        String email = "test@gmail.com";
        String code = "1234";

        Member member = Member.builder()
            .loginId(email)
            .password(code)
            .email(email)
            .rewardPoint(0)
            .build();
        memberRepository.save(member);

        GoogleLoginReq request = GoogleLoginReq.builder()
            .code(code)
            .build();

        // when
        Mockito
            .when(googleLoginClient.getProfile(code))
            .thenReturn(new SocialAccountProfile(email, email));

        // then
        mockMvc.perform(
                post("/member/google/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(request))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.accessToken").exists())
            .andExpect(jsonPath("$.data.refreshToken").exists());
    }


    @Test
    void refresh_success() throws Exception {
        // given
        Member member = Member.builder()
            .loginId("TEST")
            .password("1234")
            .name("TEST")
            .email("TEST")
            .rewardPoint(0)
            .build();
        memberRepository.save(member);

        String token = provider.createAccessToken(member);

        mockMvc.perform(
                get("/member/refresh")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.accessToken").exists());
    }


    @Test
    void show_success() throws Exception {
        // given
        String name = "TEST";
        Member member = Member.builder()
            .loginId("TEST")
            .password("1234")
            .name(name)
            .email("TEST")
            .rewardPoint(0)
            .build();
        memberRepository.save(member);

        String token = provider.createAccessToken(member);

        mockMvc.perform(
                get("/member")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.name").value(name));
    }
}
