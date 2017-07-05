package kr.huny.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by sousic on 2017-07-05.
 * 공통 서비스 모음
 */
@Slf4j
@Service
public class CommonService {
    /**
     * 메시지 출력
     * @param locale
     * @param bundle
     * @param getString
     * @param params
     * @return
     */
    public String getResourceBudleMessage(Locale locale, String bundle, String getString, Object... params)
    {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(bundle,locale);
        return MessageFormat.format(resourceBundle.getString(getString), params);
    }
}
