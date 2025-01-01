package kr.rewordit.api.utils;

import java.time.OffsetDateTime;
import java.time.ZoneId;

public class DateUtils {

    public static OffsetDateTime now() {
        return OffsetDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
