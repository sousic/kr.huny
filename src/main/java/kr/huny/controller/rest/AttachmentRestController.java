package kr.huny.controller.rest;

import kr.huny.model.db.common.AjaxJsonCommon;
import kr.huny.model.db.embedded.AttachmentStatus;
import kr.huny.model.db.web.AttachmentSimple;
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
@RequestMapping(value="/api/attachment")
public class AttachmentRestController {
    @Autowired
    CookieLocaleResolver localeResolver;

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public AjaxJsonCommon<AttachmentSimple> uploadAttachment(@RequestParam(required = true)MultipartFile[] file, HttpServletRequest request)
    {
        Locale locale = localeResolver.resolveLocale(request);

        AjaxJsonCommon<AttachmentSimple> gallery = new AjaxJsonCommon<>();

        AttachmentSimple gallerySimple = AttachmentSimple.builder()
                .urlPath("/attchment/")
                .status(AttachmentStatus.QUEUE)
                .fileName(file[0].getOriginalFilename())
                .size(file[0].getSize())
                .attachSeq(1)
                .build();

        gallery.addResultItem(gallerySimple);

        return gallery;
    }
}
