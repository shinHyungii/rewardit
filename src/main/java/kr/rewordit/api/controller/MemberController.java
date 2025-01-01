package kr.rewordit.api.controller;

import jakarta.validation.Valid;
import kr.rewordit.api.common.BaseController;
import kr.rewordit.api.common.CommonRes;
import kr.rewordit.api.dto.GoogleLoginReq;
import kr.rewordit.api.dto.MemberShowRes;
import kr.rewordit.api.dto.MemberStoreReq;
import kr.rewordit.api.dto.TokenInfo;
import kr.rewordit.api.security.CustomUserDetails;
import kr.rewordit.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController extends BaseController {


    private final MemberService service;

    @PostMapping("/google/exists")
    public CommonRes<Boolean> existsGoogleAccount(@Valid @RequestBody MemberStoreReq request) {
        log.info("[exists] {}", request.getCode());

        return response(service.existsGoogleAccount(request));
    }


    @PostMapping("/google/login")
    public CommonRes<TokenInfo> googleLogin(@Valid @RequestBody GoogleLoginReq request) {
        log.info("[googleLogin]");

        return response(service.googleLogin(request));
    }


    @GetMapping("/refresh")
    public CommonRes<TokenInfo> refresh(@AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("[refresh]");

        return response(service.refresh(userDetails));
    }


    @GetMapping("")
    public CommonRes<MemberShowRes> show(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return response(service.show(userDetails));
    }
}
