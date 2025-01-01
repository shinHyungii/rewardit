package kr.rewordit.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.rewordit.api.model.RewardRate;
import kr.rewordit.api.repository.RewardRateRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
class CampaignTests {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    RewardRateRepository rewardRateRepository;


    @BeforeEach
    void setup() {
        rewardRateRepository.save(
            RewardRate.builder()
                .rewardRate(80)
                .build()
        );
    }


    @Test
    void save_success() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                get("/campaign")
                    .queryParam("page", "1")
                    .queryParam("perPage", "10")
            )
            .andExpect(status().isOk())
            .andReturn();

        log.debug(mvcResult.getResponse().getContentAsString());
    }

}
