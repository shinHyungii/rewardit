package kr.rewordit.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.rewordit.api.dto.QrGenerateReq;
import kr.rewordit.api.dto.enumeration.QrHistoryStatus;
import kr.rewordit.api.model.Member;
import kr.rewordit.api.model.QrHistory;
import kr.rewordit.api.model.Shop;
import kr.rewordit.api.repository.MemberRepository;
import kr.rewordit.api.repository.QrHistoryRepository;
import kr.rewordit.api.repository.ShopRepository;
import kr.rewordit.api.security.JwtProvider;
import kr.rewordit.api.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
class QrTests {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    JwtProvider provider;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    QrHistoryRepository qrHistoryRepository;

    @Autowired
    ShopRepository shopRepository;


    @Test
    void generate_success() throws Exception {
        // given
        Member member = Member.builder()
            .loginId("TEST")
            .password("1234")
            .name("TEST")
            .email("TEST")
            .rewardPoint(1000)
            .build();
        memberRepository.save(member);

        QrGenerateReq request = new QrGenerateReq();
        request.setUsePoint(1000);

        String token = provider.createAccessToken(member);

        MvcResult mvcResult = mockMvc.perform(
                post("/qr/generate")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(request))
            )
            .andExpect(status().isOk())
            .andReturn();

        log.debug(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void show_success() throws Exception {
        // given
        Member member = Member.builder()
            .loginId("TEST")
            .password("1234")
            .name("TEST")
            .email("TEST")
            .rewardPoint(1000)
            .build();
        memberRepository.save(member);

        String qrId = "8ac61ddc-8ea0-4ab7-98b1-6a5605f32783";
        String qrImage = "iVBORw0KGgoAAAANSUhEUgAAAMgAAADIAQAAAACFI5MzAAABbElEQVR4Xu2WUWoDMQxEDb6WQVc36FoGd55204aSfnXnz4qSrPwCka2Rdtv+y9rvhW875BDskH+Q2VrPnC3WGEFgIilfrcWMlFfsIbPL9Z1yAivJObqf5OId20rwOUZXDlmBh1wKue2Ddp4iZdqn+IxXbCDSOv88pMU+4/1EnyUqmpzOCmWxEaWH6M9lSkP1Iw0TYW+ry8kgtvRoIswhtqrW2jATSQbRkt6HznONCxnIUk81Ddfq4qS3PEQnujRepXZa+GpgB2FhaYp3jXOS+VHIsySpF0pPhoZqaCJXBndTSf5hIhxiLrorJErt2UU4TF6I8AYOUnrngicHpVLXBnK1FtvNekZxEVUt6vZUv5pvlXuWJOEoiSzKlyaCMSpUOI0JNbKJXJtMxvlkJrmILmsk8UkTh4sw+hRrlfbqrLmIxDi4OfVXa5mI1iRH5XE3mYVsysYTF0LpxBaCQpRClFLgJvLRDjkEO+Rp8gVZkYQiGG7fHQAAAABJRU5ErkJggg==";

        QrHistory qrHistory = QrHistory.builder()
            .member(member)
            .qrId(qrId)
            .qrImage(qrImage)
            .requestAt(DateUtils.now())
            .requestPoint(1000)
            .status(QrHistoryStatus.AVAILABLE.name())
            .build();

        qrHistoryRepository.save(qrHistory);


        String token = provider.createAccessToken(member);

        MvcResult mvcResult = mockMvc.perform(
                get(String.format("/qr/%s", qrId))
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
            )
            .andExpect(status().isOk())
            .andReturn();

        assertThat(qrImage).isEqualTo(new String(Base64.getEncoder().encode(mvcResult.getResponse().getContentAsByteArray()), StandardCharsets.UTF_8));
    }


    @Test
    void use_success() throws Exception {
        // given
        Member member = Member.builder()
            .loginId("TEST")
            .password("1234")
            .name("TEST")
            .email("TEST")
            .rewardPoint(1000)
            .build();
        memberRepository.save(member);

        String qrId = "8ac61ddc-8ea0-4ab7-98b1-6a5605f32783";
        String qrImage = "iVBORw0KGgoAAAANSUhEUgAAAMgAAADIAQAAAACFI5MzAAABbElEQVR4Xu2WUWoDMQxEDb6WQVc36FoGd55204aSfnXnz4qSrPwCka2Rdtv+y9rvhW875BDskH+Q2VrPnC3WGEFgIilfrcWMlFfsIbPL9Z1yAivJObqf5OId20rwOUZXDlmBh1wKue2Ddp4iZdqn+IxXbCDSOv88pMU+4/1EnyUqmpzOCmWxEaWH6M9lSkP1Iw0TYW+ry8kgtvRoIswhtqrW2jATSQbRkt6HznONCxnIUk81Ddfq4qS3PEQnujRepXZa+GpgB2FhaYp3jXOS+VHIsySpF0pPhoZqaCJXBndTSf5hIhxiLrorJErt2UU4TF6I8AYOUnrngicHpVLXBnK1FtvNekZxEVUt6vZUv5pvlXuWJOEoiSzKlyaCMSpUOI0JNbKJXJtMxvlkJrmILmsk8UkTh4sw+hRrlfbqrLmIxDi4OfVXa5mI1iRH5XE3mYVsysYTF0LpxBaCQpRClFLgJvLRDjkEO+Rp8gVZkYQiGG7fHQAAAABJRU5ErkJggg==";

        QrHistory qrHistory = QrHistory.builder()
            .member(member)
            .qrId(qrId)
            .qrImage(qrImage)
            .requestAt(DateUtils.now())
            .requestPoint(1000)
            .status(QrHistoryStatus.AVAILABLE.name())
            .build();

        qrHistoryRepository.save(qrHistory);

        Shop shop = Shop.builder()
            .loginId("TEST")
            .password("TEST")
            .build();

        shopRepository.save(shop);

        String token = provider.createAccessToken(shop);

        MvcResult mvcResult = mockMvc.perform(
                post(String.format("/qr/use/%s", qrId))
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
            )
            .andExpect(status().isOk())
            .andReturn();

        log.debug(mvcResult.getResponse().getContentAsString());
    }

}
