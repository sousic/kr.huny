package kr.huny.controller;

import kr.huny.service.CommonService;
import kr.huny.service.GalleryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

@Slf4j
@Controller
@RequestMapping(value = "/gallery")
public class GalleryController {
    @Autowired
    CookieLocaleResolver localeResolver;
    @Autowired
    CommonService commonService;
    @Autowired
    GalleryService galleryService;

    @RequestMapping("/{gallerySeq}")
    public void gallery(@PathVariable long gallerySeq, HttpServletRequest request, HttpServletResponse response)
    {
        Locale locale = localeResolver.resolveLocale(request);

        try {

            GalleryService.GalleryExtention galleryExtention = galleryService.getGallery(locale, gallerySeq);

            ByteArrayOutputStream byteArrayOutputStream = galleryExtention.byteArrayOutputStream;
            response.setContentType(galleryExtention.contentType);
            byteArrayOutputStream.writeTo(response.getOutputStream());
        }
        catch (IOException e)
        {
            throw new RuntimeException(commonService.getResourceBudleMessage(locale, "message.common","common.exception.io"));
        }
    }
}
