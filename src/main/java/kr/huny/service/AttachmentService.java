package kr.huny.service;

import kr.huny.authentication.common.CommonPrincipal;
import kr.huny.common.CommonUtils;
import kr.huny.configuration.ApplicationPropertyConfig;
import kr.huny.model.db.Attachments;
import kr.huny.model.db.BoardFree;
import kr.huny.model.db.common.AjaxJsonCommon;
import kr.huny.model.db.embedded.AttachmentStatus;
import kr.huny.model.db.web.response.AttachmentSimple;
import kr.huny.repository.AttachmentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Service
public class AttachmentService {
    public static class AttachmentExtention
    {
        public ByteArrayOutputStream byteArrayOutputStream;
        public Attachments attachments;
    }

    @Autowired
    ApplicationPropertyConfig applicationPropertyConfig;
    @Autowired
    AttachmentsRepository attachmentsRepository;
    @Autowired
    CommonService commonService;

    public AjaxJsonCommon<AttachmentSimple> insertAttachments(Locale locale, MultipartFile[] files) {
        try {
            AjaxJsonCommon<AttachmentSimple> attachmentSimpleAjaxJsonCommon = new AjaxJsonCommon<>();

            CommonPrincipal commonPrincipal = commonService.getCommonPrincipal();

            for(MultipartFile file : files) {
                String tempFileName = UUID.randomUUID().toString();
                Attachments attachments = new Attachments();
                attachments.setUserSeq(commonPrincipal.getSeq());
                attachments.setUsername(commonPrincipal.getUsername());
                attachments.setStatus(AttachmentStatus.QUEUE);
                attachments.setFileName(file.getOriginalFilename());
                attachments.setSize(file.getSize());
                attachments.setContentType(file.getContentType());
                attachments.setSaveName(String.format("%s.%s", tempFileName, FilenameUtils.getExtension(attachments.getFileName())));
                attachments.setSavePath(CommonUtils.GetSavePath());

                Path destPath = Paths.get(applicationPropertyConfig.getStorageAttachmentPath(), attachments.getSavePath());

                if (Files.notExists(destPath)) {
                    Files.createDirectories(destPath);
                }

                destPath = destPath.resolve(attachments.getSaveName());
                if (Files.notExists(destPath)) {
                    Files.write(destPath, file.getBytes());
                }

                attachmentsRepository.save(attachments);

                //파일저장
                log.debug("gallery =>" + attachments);

                //반환 결과 생성

                AttachmentSimple attachementSimple = AttachmentSimple.builder()
                        .urlPath("/attach/")
                        .status(attachments.getStatus())
                        .fSeq(attachments.getAttachSeq())
                        .size(attachments.getSize())
                        .fileName(attachments.getFileName())
                        .fileSize(FileUtils.byteCountToDisplaySize(attachments.getSize()))
                        .build();

                attachmentSimpleAjaxJsonCommon.addResultItem(attachementSimple);

                attachmentSimpleAjaxJsonCommon.setRetCode(1);
                attachmentSimpleAjaxJsonCommon.setRetMsg(commonService.getResourceBudleMessage(locale, "messages.attachment", "attachment.msg.api.save.ok"));
            }

            return attachmentSimpleAjaxJsonCommon;
        }
        catch (IOException e)
        {
            throw new RuntimeException(commonService.getResourceBudleMessage(locale, "message.common","common.exception.io"));
        }
    }

    /**
     * 대기 첨부 파일 삭제
     * @param locale
     * @param fseq
     * @return
     */
    public AjaxJsonCommon<AttachmentSimple> deleteQueueAttachments(Locale locale, long fseq) {
        AjaxJsonCommon<AttachmentSimple> attachmentSimpleAjaxJsonCommon = new AjaxJsonCommon<>();

        Attachments attachments = attachmentsRepository.findByAttachSeqAndStatusEquals(fseq, AttachmentStatus.QUEUE);

        try {
            if (Objects.nonNull(attachments)) {
                Path destPath = Paths.get(applicationPropertyConfig.getStorageAttachmentPath(), attachments.getSavePath(), attachments.getSaveName());

                if (Files.exists(destPath)) {
                    Files.delete(destPath);

                    attachmentsRepository.delete(fseq);
                }
            }

            attachmentSimpleAjaxJsonCommon.setRetCode(1);
            attachmentSimpleAjaxJsonCommon.setRetMsg(commonService.getResourceBudleMessage(locale, "messages.attachment", "attachment.msg.api.remove.ok"));
        }
        catch (IOException e)
        {
            throw new RuntimeException(commonService.getResourceBudleMessage(locale, "message.common","common.exception.io"));
        }

        return attachmentSimpleAjaxJsonCommon;
    }

    public Attachments findOneWithDownload(long fseq) {
        Attachments attachments = attachmentsRepository.findOne(fseq);
        attachments.setDownloads(attachments.getDownloads()+1);
        attachmentsRepository.save(attachments);
        return attachments;
    }

    public void updateAttachQueueList(String attachQueueList, BoardFree boardFree) {
        List<String> seqList = Arrays.asList(attachQueueList.split(","));
        for(String seq : seqList) {
            attachmentsRepository.updateBoardSeq(boardFree, AttachmentStatus.QUEUE, Long.parseLong(seq));
        }
    }

    /**
     * 글 등록시 사용하는 첨부파일 정보 조회
     * @param fseq
     * @param locale
     * @return
     */
    public AjaxJsonCommon<AttachmentSimple> getAttachments(String fseq, Locale locale) {
        AjaxJsonCommon<AttachmentSimple> attachmentsInfo = new AjaxJsonCommon<>();
        if(StringUtils.isEmpty(fseq))
        {
            attachmentsInfo.setRetCode(-1);
            attachmentsInfo.setRetMsg(commonService.getResourceBudleMessage(locale,"messages.attachment", "attachement.msg.api.parameter.error"));
        }
        else
        {
            ArrayList<String> fileTargets = new ArrayList<>(Arrays.asList(fseq.split(",")));
            Attachments attachments;
            for(String seq : fileTargets)
            {
                attachments = attachmentsRepository.findOne(Long.parseLong(seq));
                AttachmentSimple attachmentSimple = AttachmentSimple.builder()
                        .urlPath("/attach/")
                        .status(attachments.getStatus())
                        .fSeq(attachments.getAttachSeq())
                        .size(attachments.getSize())
                        .fileName(attachments.getFileName())
                        .fileSize(FileUtils.byteCountToDisplaySize(attachments.getSize()))
                        .build();

                attachmentsInfo.addResultItem(attachmentSimple);
            }


            attachmentsInfo.setRetCode(1);
            attachmentsInfo.setRetMsg(commonService.getResourceBudleMessage(locale, "messages.attachment", "attachement.msg.api.ok"));
        }

        return attachmentsInfo;
    }

    public List<Attachments> getPostWithAttachList(long boardSeq)
    {
        return attachmentsRepository.findByBoardFreeBoardSeq(boardSeq);
    }
}
