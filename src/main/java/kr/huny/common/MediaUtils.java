package kr.huny.common;

import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class MediaUtils {
    private static Map<String, String> mediaMap;

    static {
        mediaMap = new HashMap<String, String>();
        mediaMap.put(MediaType.IMAGE_JPEG_VALUE.toString(), "JPG");
        mediaMap.put(MediaType.IMAGE_GIF_VALUE.toString(), "GIF");
        mediaMap.put(MediaType.IMAGE_PNG_VALUE.toString(), "PNG");
    }

    public static String getMediaType(String type)
    {
        return mediaMap.get(type.toLowerCase());
    }
}
