package kr.rewordit.api.client.greenFee.message.enumeration;

public enum AdsSubType {
    N_PLACE_QUIZ("O"),
    N_SHOPPING_QUIZ("S"),
    PLACE_QUIZ("5"),
    SCREENSHOT("E"),
    SEARCH_SCREENSHOT("R"),
    ETC("Z"),
    SUBSCRIBE_YOUTUBE("Y"),
    FOLLOW_INSTAGRAM("I"),
    KAKAO_FRIEND("K"),
    ;


    private final String value;

    AdsSubType(String value) {
        this.value = value;
    }
}
