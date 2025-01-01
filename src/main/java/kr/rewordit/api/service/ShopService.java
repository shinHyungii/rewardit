package kr.rewordit.api.service;

import kr.rewordit.api.dto.ShopLoginReq;
import kr.rewordit.api.dto.ShopStoreReq;
import kr.rewordit.api.dto.TokenInfo;
import kr.rewordit.api.exception.CustomException;
import kr.rewordit.api.model.Shop;
import kr.rewordit.api.repository.ShopRepository;
import kr.rewordit.api.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopService {


    private final ShopRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider provider;


    public void store(ShopStoreReq request) {

        String encode = passwordEncoder.encode(request.getPassword());

        repository.save(request.toEntity(encode));
    }


    public TokenInfo login(ShopLoginReq request) {
        Shop shop = repository.findByLoginId(request.getLoginId())
            .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "해당 회원이 존재하지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), shop.getPassword())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        return TokenInfo.builder()
            .accessToken(provider.createAccessToken(shop))
            .refreshToken(provider.createRefreshToken(shop))
            .build();
    }
}
