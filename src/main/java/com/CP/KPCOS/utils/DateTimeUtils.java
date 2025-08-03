package com.CP.KPCOS.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    public static Timestamp parseStringToTimestamp(String str, String pattern) {
        if (CommonUtils.isEmptyString(str)){
            return null;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            Date date = dateFormat.parse(str);
            return new Timestamp(date.getTime());
        } catch (Exception e) {
            return null;
        }
    }
}
