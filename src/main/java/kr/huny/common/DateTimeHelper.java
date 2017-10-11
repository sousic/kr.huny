package kr.huny.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DateTimeHelper {
    public static final String DEFAULT_DATETIME_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_YYYYMMDD = "yyyy-MM-dd";
    public static final String DATETIME_HHMMSS = "HH:mm:ss";

    public static String GetDateTime(Date date)
    {
        if(Objects.nonNull(date)) {
            return GetDateTime(date, DEFAULT_DATETIME_YYYYMMDDHHMMSS);
        }
        return "&nbsp;";
    }

    public static String GetDateTime(Date date, String dateFormat)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String strDate = simpleDateFormat.format(date);
        return strDate;
    }
}
