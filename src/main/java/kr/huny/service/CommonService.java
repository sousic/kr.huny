package kr.huny.service;

import kr.huny.authentication.BasicPrincipal;
import kr.huny.authentication.common.CommonPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
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

    /**
     * 로그인 정보 반환
     * @return
     */
    public CommonPrincipal getCommonPrincipal()
    {
        CommonPrincipal commonPrincipal = null;

        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated())
        {
                BasicPrincipal basicPrincipal = (BasicPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                commonPrincipal = CommonPrincipal.builder()
                        .seq(basicPrincipal.getSeq())
                        .email(basicPrincipal.getEmail())
                        .username(basicPrincipal.getUsername())
                        .providerId(basicPrincipal.getProviderId())
                        .build();

        }

        return commonPrincipal;
    }
}
