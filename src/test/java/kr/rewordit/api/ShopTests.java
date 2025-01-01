package kr.rewordit.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.rewordit.api.dto.ShopStoreReq;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
class ShopTests {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;


    @Test
    void save_success() throws Exception {

        ShopStoreReq request = ShopStoreReq.builder()
            .shopName("TEST")
            .name("TEST")
            .phone("010")
            .bank("농협")
            .accountNo("01001010001")
            .build();

        MvcResult mvcResult = mockMvc.perform(
                post("/shop")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(request))
            )
            .andExpect(status().isOk())
            .andReturn();

        log.debug(mvcResult.getResponse().getContentAsString());
    }

}
