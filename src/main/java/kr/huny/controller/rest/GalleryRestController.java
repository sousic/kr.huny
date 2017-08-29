package kr.huny.controller.rest;

import kr.huny.model.db.common.AjaxJsonCommon;
import kr.huny.model.db.web.GallerySimple;
import kr.huny.service.GalleryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping(value="/api/gallery")
public class GalleryRestController {
    @Autowired
    CookieLocaleResolver localeResolver;
    @Autowired
    GalleryService galleryService;

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public AjaxJsonCommon<GallerySimple> uploadImage(@RequestParam(required = true)MultipartFile file, HttpServletRequest request)
    {
        Locale locale = localeResolver.resolveLocale(request);

        AjaxJsonCommon<GallerySimple> gallery = galleryService.updateImage(locale, file);

        return gallery;
    }
}