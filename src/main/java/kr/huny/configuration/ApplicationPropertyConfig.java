package kr.huny.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by sousic on 2017-07-13.
 */
@Getter
@Component
@PropertySource({"classpath:config/application.properties"})
public class ApplicationPropertyConfig {

    @Value("${social.facebook.id}")
    private String app_id;

    @Value("${social.facebook.appSecret}")
    private String app_secret;

    @Value("${storage.image.path}")
    private String storageImagePath;

    @Value("${stroage.thumbnail.path}")
    private String storageThumbnailPath;
}
