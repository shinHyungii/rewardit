package kr.rewordit.api.dto;

import jakarta.validation.constraints.NotNull;
import kr.rewordit.api.model.Shop;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopStoreReq {

    @NotNull
    private String shopName;

    @NotNull
    private String loginId;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String phone;

    @NotNull
    private String bank;

    @NotNull
    private String accountNo;


    public Shop toEntity(String encryptPassword) {
        return Shop.builder()
            .shopName(this.shopName)
            .loginId(this.loginId)
            .password(encryptPassword)
            .name(this.name)
            .phone(this.phone)
            .bank(this.bank)
            .accountNo(this.accountNo)
            .build();
    }
}
