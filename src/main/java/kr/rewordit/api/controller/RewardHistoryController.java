package kr.rewordit.api.controller;

import kr.rewordit.api.common.BaseController;
import kr.rewordit.api.common.CommonRes;
import kr.rewordit.api.common.PaginateResponse;
import kr.rewordit.api.dto.RewardHistoryIndexRequest;
import kr.rewordit.api.dto.RewardHistoryIndexRes;
import kr.rewordit.api.security.CustomUserDetails;
import kr.rewordit.api.service.RewardHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/reward")
@RequiredArgsConstructor
public class RewardHistoryController extends BaseController {

    private final RewardHistoryService service;


    @GetMapping("")
    public CommonRes<PaginateResponse<RewardHistoryIndexRes>> index(RewardHistoryIndexRequest request,
                                                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("[index]");

        return response(service.index(request, userDetails));
    }
}
