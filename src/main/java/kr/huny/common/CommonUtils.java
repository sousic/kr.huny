package kr.huny.common;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by sousic on 2017. 7. 3..
 */
public class CommonUtils {

    /**
     * 저장 경로 생성
     * @param strPath
     * @return
     */
    public static String GetSavePath(String strPath) {
        Instant instant = Instant.now();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        Path savePath = Paths.get(strPath, String.valueOf(localDateTime.getYear()), String.valueOf(localDateTime.getMonthValue()), String.valueOf(localDateTime.getDayOfMonth()));

        return savePath.toString();
    }
}
