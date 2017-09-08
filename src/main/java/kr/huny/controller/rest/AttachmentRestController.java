package kr.huny.controller.rest;

import kr.huny.model.db.common.AjaxJsonCommon;
import kr.huny.model.db.web.response.AttachmentSimple;
import kr.huny.service.AttachmentService;
import kr.huny.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    CommonService commonService;
    @Autowired
    AttachmentService attachmentService;

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public AjaxJsonCommon<AttachmentSimple> uploadAttachment(@RequestParam(required = true)MultipartFile[] file, HttpServletRequest request)
    {
        Locale locale = localeResolver.resolveLocale(request);

        return attachmentService.insertAttachments(locale, file);
    }

    @RequestMapping(value="/remove/{fseq}", method = RequestMethod.GET)
    public AjaxJsonCommon<AttachmentSimple> removeAttachment(@PathVariable long fseq, HttpServletRequest request)
    {
        Locale locale = localeResolver.resolveLocale(request);

        return attachmentService.deleteQueueAttachments(locale, fseq);
    }
}
