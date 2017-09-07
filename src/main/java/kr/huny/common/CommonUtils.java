package kr.huny.common;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by sousic on 2017. 7. 3..
 */
public class CommonUtils {

    public static String GetSavePath() {
        return GetSavePath("");
    }
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

    public static void makeThumbnail(String strThumbPath, int thumbnailSizeWidth, int thumbnailSizeHeight, int thumbnailImageType, InputStream orignalFile, String tempFileName) throws IOException {
        try {
            Path thumbPath = Paths.get(strThumbPath, tempFileName);
            BufferedImage bufferedImage = ImageIO.read(orignalFile);
            BufferedImage thumbImage = new BufferedImage(thumbnailSizeWidth, thumbnailSizeHeight, thumbnailImageType);
            Image tempImg = bufferedImage.getScaledInstance(thumbnailSizeWidth, thumbnailSizeHeight, thumbnailImageType);
            Graphics2D g2 = thumbImage.createGraphics();
            g2.drawImage(tempImg, 0, 0, thumbnailSizeWidth, thumbnailSizeHeight, null);

            ImageIO.write(thumbImage, "jpg", thumbPath.toFile());
        }
        catch (IOException e)
        {
            throw e;
        }
    }
}
