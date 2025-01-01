package kr.rewordit.api.common;

public abstract class BaseController {


    public CommonRes<Void> response() {
        return this.response("OK", "요청 처리 완료", null);
    }

    public <T> CommonRes<T> response(T data) {
        return this.response("OK", "요청 처리 완료", data);
    }

    private <T> CommonRes<T> response(String result, String message, T data) {
        return CommonRes.<T>builder()
            .result(result)
            .message(message)
            .data(data)
            .build();
    }
}
