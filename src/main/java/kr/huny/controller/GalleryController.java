package kr.huny.controller;

import kr.huny.model.db.Gallery;
import kr.huny.repository.GalleryRepository;
import kr.huny.service.CommonService;
import kr.huny.service.GalleryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping(value = "/gallery")
public class GalleryController {
    @Autowired
    CookieLocaleResolver localeResolver;
    @Autowired
    GalleryRepository galleryRepository;
    @Autowired
    CommonService commonService;
    @Autowired
    GalleryService galleryService;

    @ResponseBody
    @RequestMapping("/{gallerySeq}")
    public void gallery(@PathVariable long gallerySeq, HttpServletRequest request, HttpServletResponse response)
    {
        Locale locale = localeResolver.resolveLocale(request);

        Gallery gallery = galleryRepository.findOne(gallerySeq);

        if(Objects.isNull(gallery))
            throw new RuntimeException("해당 데이터를 찾을 수 없습니다.");

        try {
            ByteArrayOutputStream byteArrayOutputStream = galleryService.getImage(locale, gallery);

            response.setContentType(gallery.getContentType());
            byteArrayOutputStream.writeTo(response.getOutputStream());
        }
        catch (IOException e)
        {
            throw new RuntimeException(commonService.getResourceBudleMessage(locale, "message.common","common.exception.io"));
        }
    }
}
