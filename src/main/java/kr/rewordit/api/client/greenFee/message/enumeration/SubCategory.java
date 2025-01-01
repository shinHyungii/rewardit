package kr.rewordit.api.client.greenFee.message.enumeration;

public enum SubCategory {
    SECOND_30(1),
    PARTICIPATE(2),
    SNS_SUBSCRIBE(3);

    private final Integer value;

    SubCategory(Integer value) {
        this.value = value;
    }
}
