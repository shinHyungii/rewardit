package kr.rewordit.api.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonRes<T> {

    private String result;

    private String message;

    private T data;

    public static CommonRes<Void> failure(String message) {
        return CommonRes.<Void>builder()
            .result("FAIL")
            .message(message)
            .build();
    }
}
