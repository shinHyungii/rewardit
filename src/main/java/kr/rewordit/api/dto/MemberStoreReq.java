package kr.rewordit.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberStoreReq {

    @NotNull
    private String code;

    @Email
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String phone;

    private String address;
}
