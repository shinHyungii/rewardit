package kr.rewordit.api.controller;

import kr.rewordit.api.common.BaseController;
import kr.rewordit.api.common.CommonRes;
import kr.rewordit.api.common.PaginateResponse;
import kr.rewordit.api.dto.GetCampaignListReq;
import kr.rewordit.api.dto.GetCampaignListRes;
import kr.rewordit.api.dto.SuccessParticipateReq;
import kr.rewordit.api.security.CustomUserDetails;
import kr.rewordit.api.service.CampaignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/campaign")
@RequiredArgsConstructor
public class CampaignController extends BaseController {

    private final CampaignService service;


    @GetMapping("")
    public CommonRes<PaginateResponse<GetCampaignListRes>> index(GetCampaignListReq request) {
        log.info("[index] {}", request);

        return response(service.index(request));
    }

    @PostMapping("/participate/{adsIdx}")
    public CommonRes<Void> participate(@PathVariable("adsIdx") Integer adsIdx,
                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("[participate]");
        // TODO 광고 참여 방식 정립

        return response();
    }

    @PostMapping("/complete")
    public CommonRes<Void> successParticipate(SuccessParticipateReq request) {
        log.info("[successParticipate] {}", request);

        return response();
    }
}
