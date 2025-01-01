package kr.rewordit.api.controller;

import jakarta.validation.Valid;
import kr.rewordit.api.common.BaseController;
import kr.rewordit.api.common.CommonRes;
import kr.rewordit.api.dto.QrGenerateReq;
import kr.rewordit.api.security.CustomUserDetails;
import kr.rewordit.api.service.QrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/qr")
@RequiredArgsConstructor
public class QrController extends BaseController {

    private final QrService service;


    @PostMapping("/generate")
    public CommonRes<Void> generate(@Valid @RequestBody QrGenerateReq request,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("[generate]");

        service.generate(request, userDetails);

        return response();
    }


    @GetMapping("/{qrId}")
    public ResponseEntity<byte[]> show(@PathVariable String qrId,
                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("[show] {}", qrId);

        byte[] qrImage = service.show(qrId, userDetails);

        return ResponseEntity
            .ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(qrImage);
    }


    @PostMapping("/use/{qrId}")
    public CommonRes<Void> use(@PathVariable String qrId,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("[use] {}", qrId);

        service.use(qrId, userDetails);

        return response();
    }
}
