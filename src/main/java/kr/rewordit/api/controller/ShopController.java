package kr.rewordit.api.controller;

import jakarta.validation.Valid;
import kr.rewordit.api.common.BaseController;
import kr.rewordit.api.common.CommonRes;
import kr.rewordit.api.dto.ShopLoginReq;
import kr.rewordit.api.dto.ShopStoreReq;
import kr.rewordit.api.dto.TokenInfo;
import kr.rewordit.api.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController extends BaseController {


    private final ShopService service;


    @PostMapping("")
    public CommonRes<Void> store(@Valid @RequestBody ShopStoreReq request) {
        log.info("[store] {}", request.getShopName());

        service.store(request);

        return response();
    }

    @PostMapping("/login")
    public CommonRes<TokenInfo> login(@Valid @RequestBody ShopLoginReq request) {
        log.info("[login] {}", request.getLoginId());

        return response(service.login(request));
    }
}
