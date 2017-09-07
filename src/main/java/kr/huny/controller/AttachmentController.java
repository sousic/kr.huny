package kr.huny.controller;

import kr.huny.configuration.ApplicationPropertyConfig;
import kr.huny.model.db.Attachments;
import kr.huny.service.AttachmentService;
import kr.huny.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Locale;

@Controller
@Slf4j
@RequestMapping("/attachment")
public class AttachmentController {
    @Autowired
    CookieLocaleResolver localeResolver;
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    CommonService commonService;
    @Autowired
    ApplicationPropertyConfig applicationPropertyConfig;

    @RequestMapping("/{fseq}")
    public void download(@PathVariable long fseq, HttpServletRequest request, HttpServletResponse response)
    {
        Locale locale = localeResolver.resolveLocale(request);

        try {
            Attachments attachments = attachmentService.findOne(fseq);
            byte fileByte[] = FileUtils.readFileToByteArray(new File(Paths.get(applicationPropertyConfig.getStorageAttachmentPath(), attachments.getSavePath(), attachments.getSaveName()).toString()));

            response.setContentType("application/octet-stream");
            response.setContentLength((int)attachments.getSize());
            String downloadFileName = new String(attachments.getFileName().getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFileName + "\"");
            //response.setHeader("Content-Transfer-Encoding", "binary");
            response.getOutputStream().write(fileByte);

            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(commonService.getResourceBudleMessage(locale, "message.common","common.exception.io"));
        }
    }
}
