package kr.rewordit.api.client.greenFee.message.enumeration;

public enum AdsType {
    CPI(1),
    CPE(2),
    CPA(3),
    CPS(4),
    CPC(6),
    CPSNS(7),
    NEWS(8),
    ;

    private final Integer value;

    AdsType(Integer value) {
        this.value = value;
    }

}
