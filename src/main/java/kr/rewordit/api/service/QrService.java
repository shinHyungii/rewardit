package kr.rewordit.api.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import kr.rewordit.api.dto.QrGenerateReq;
import kr.rewordit.api.dto.enumeration.QrHistoryStatus;
import kr.rewordit.api.exception.CustomException;
import kr.rewordit.api.model.Member;
import kr.rewordit.api.model.QrHistory;
import kr.rewordit.api.model.Shop;
import kr.rewordit.api.repository.MemberRepository;
import kr.rewordit.api.repository.QrHistoryRepository;
import kr.rewordit.api.repository.ShopRepository;
import kr.rewordit.api.security.CustomUserDetails;
import kr.rewordit.api.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class QrService {

    @Value("${rewardit.url}")
    private String rewarditUrl;

    private final MemberRepository memberRepository;

    private final QrHistoryRepository qrHistoryRepository;

    private final ShopRepository shopRepository;


    @Transactional
    public void generate(QrGenerateReq request, CustomUserDetails userDetails) {
        Member member = memberRepository.findById(Long.parseLong(userDetails.getUsername()))
            .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "가입되지 않은 회원입니다."));

        if (member.getRewardPoint() < request.getUsePoint()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "보유 포인트가 충분하지 않습니다.");
        }

        member.usePoint(request.getUsePoint());

        int width = 200;
        int height = 200;

        try {
            String qrId = UUID.randomUUID().toString();
            String url = String.format("%s/%s", rewarditUrl, qrId);

            BitMatrix encode = new MultiFormatWriter()
                .encode(url, BarcodeFormat.QR_CODE, width, height);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(encode, "PNG", out);

            String qrImage = new String(Base64.getEncoder().encode(out.toByteArray()), StandardCharsets.UTF_8);

            QrHistory qrHistory = QrHistory.builder()
                .member(member)
                .qrId(qrId)
                .qrImage(qrImage)
                .requestAt(DateUtils.now())
                .requestPoint(request.getUsePoint())
                .status(QrHistoryStatus.AVAILABLE.name())
                .build();

            qrHistoryRepository.save(qrHistory);

        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "잠시 후 다시 시도해주세요.");
        }

    }


    public byte[] show(String qrId, CustomUserDetails userDetails) {
        Member member = memberRepository.findById(Long.parseLong(userDetails.getUsername()))
            .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "가입되지 않은 회원입니다."));

        QrHistory qrHistory = qrHistoryRepository.findByQrId(qrId)
            .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "해당 QR을 찾을 수 없습니다."));

        if (qrHistory.getStatus().equals(QrHistoryStatus.USED.name())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 사용된 QR입니다.");
        }

        if (!Objects.equals(member.getId(), qrHistory.getMember().getId())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "타 회원의 QR은 사용할 수 없습니다.");
        }

        return Base64.getDecoder().decode(qrHistory.getQrImage());
    }


    @Transactional
    public void use(String qrId, CustomUserDetails userDetails) {
        Shop shop = shopRepository.findById(Long.parseLong(userDetails.getUsername()))
            .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "해당 가맹점 정보가 존재하지 않습니다."));

        QrHistory qrHistory = qrHistoryRepository.findByQrId(qrId)
            .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "해당 QR을 찾을 수 없습니다."));

        if (qrHistory.getStatus().equals(QrHistoryStatus.USED.name())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 사용된 QR입니다.");
        }

        qrHistory.usedAt(shop);
    }
}
