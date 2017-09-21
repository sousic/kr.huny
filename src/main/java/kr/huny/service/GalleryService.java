package kr.huny.service;

import kr.huny.authentication.common.CommonPrincipal;
import kr.huny.common.CommonUtils;
import kr.huny.common.MediaUtils;
import kr.huny.configuration.ApplicationPropertyConfig;
import kr.huny.model.db.BoardFree;
import kr.huny.model.db.Gallery;
import kr.huny.model.db.common.AjaxJsonCommon;
import kr.huny.model.db.embedded.AttachmentStatus;
import kr.huny.model.db.web.response.GallerySimple;
import kr.huny.repository.GalleryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class GalleryService {
    public static class GalleryExtention
    {
        public ByteArrayOutputStream byteArrayOutputStream;
        public String contentType;
    }

    @Autowired
    ApplicationPropertyConfig applicationPropertyConfig;
    @Autowired
    CommonService commonService;
    @Autowired
    GalleryRepository galleryRepository;

    public AjaxJsonCommon<GallerySimple> insertImage(Locale locale, MultipartFile[] files) {

        try {
            AjaxJsonCommon<GallerySimple> gallerySimpleAjaxJsonCommon = new AjaxJsonCommon<>();

            CommonPrincipal commonPrincipal = commonService.getCommonPrincipal();

            for(MultipartFile file : files) {
                if(file.getContentType().indexOf("image/") > -1) {
                    String tempFileName = UUID.randomUUID().toString();
                    Gallery gallery = new Gallery();
                    gallery.setUserSeq(commonPrincipal.getSeq());
                    gallery.setUsername(commonPrincipal.getUsername());
                    gallery.setStatus(AttachmentStatus.QUEUE);
                    gallery.setFileName(file.getOriginalFilename());
                    gallery.setSize(file.getSize());
                    gallery.setContentType(file.getContentType());
                    gallery.setSaveName(String.format("%s.%s", tempFileName, FilenameUtils.getExtension(gallery.getFileName())));
                    gallery.setSaveThumbName(String.format("%s_thumb.%s", tempFileName, FilenameUtils.getExtension(gallery.getFileName())));
                    gallery.setSavePath(CommonUtils.GetSavePath());

                    Path destPath = Paths.get(applicationPropertyConfig.getStorageImagePath(), gallery.getSavePath());
                    Path destThumbPath = Paths.get(applicationPropertyConfig.getStorageThumbnailPath(), gallery.getSavePath());

                    if (Files.notExists(destPath)) {
                        Files.createDirectories(destPath);
                    }
                    if(Files.notExists(destThumbPath))
                        Files.createDirectories(destThumbPath);

                    destPath = destPath.resolve(gallery.getSaveName());
                    if (Files.notExists(destPath)) {
                        Files.write(destPath, file.getBytes());

                        log.debug("[image type check] => " +  MediaUtils.getMediaType(gallery.getContentType()));
                        if(MediaUtils.getMediaType(gallery.getContentType()) != null)
                            CommonUtils.makeThumbnail(destThumbPath.toString(), 150, 150, BufferedImage.TYPE_INT_RGB, file.getInputStream(), gallery.getSaveThumbName());
                    }

                    galleryRepository.save(gallery);

                    //파일저장
                    log.debug("gallery =>" + gallery);

                    //반환 결과 생성

                    GallerySimple gallerySimple = GallerySimple.builder()
                            .urlPath("/gallery/")
                            .status(gallery.getStatus())
                            .fSeq(gallery.getGallerySeq())
                            .build();

                    gallerySimpleAjaxJsonCommon.addResultItem(gallerySimple);
                }
                else
                {
                    GallerySimple gallerySimple = GallerySimple.builder()
                            .fSeq(-1)
                            .build();

                    gallerySimpleAjaxJsonCommon.addResultItem(gallerySimple);
                }

                gallerySimpleAjaxJsonCommon.setRetCode(1);
                gallerySimpleAjaxJsonCommon.setRetMsg(commonService.getResourceBudleMessage(locale, "messages.gallery", "gallery.msg.api.save.ok"));
            }

            return gallerySimpleAjaxJsonCommon;
        }
        catch (IOException e)
        {
            throw new RuntimeException(commonService.getResourceBudleMessage(locale, "message.common","common.exception.io"));
        }
    }

    public ByteArrayOutputStream getImage(Locale locale, Gallery gallery) throws IOException {
        Path destPath = Paths.get(applicationPropertyConfig.getStorageImagePath(), gallery.getSavePath(), gallery.getSaveName());
        if(Files.exists(destPath))
        {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(destPath.toString()));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);

            int imageByte;

            while((imageByte = bufferedInputStream.read()) != -1)
            {
                byteArrayOutputStream.write(imageByte);
            }

            bufferedInputStream.close();

            return byteArrayOutputStream;
        }
        else
        {
            throw new RuntimeException(commonService.getResourceBudleMessage(locale, "message.common","common.exception.io"));
        }
    }

    /**
     * 대기 이미지 삭제 처리
     * @param locale
     * @param fseq
     * @return
     */
    public AjaxJsonCommon<GallerySimple> deleteQueueImage(Locale locale, long fseq) {
        AjaxJsonCommon<GallerySimple> gallerySimpleAjaxJsonCommon = new AjaxJsonCommon<>();

        Gallery gallery = galleryRepository.findByGallerySeqAndStatusEquals(fseq, AttachmentStatus.QUEUE);

        try {
            if (Objects.nonNull(gallery)) {
                Path destPath = Paths.get(applicationPropertyConfig.getStorageImagePath(), gallery.getSavePath(), gallery.getSaveName());
                Path destThumbPath = Paths.get(applicationPropertyConfig.getStorageThumbnailPath(), gallery.getSavePath(), gallery.getSaveThumbName());

                if (Files.exists(destPath)) {
                    Files.delete(destPath);
                    Files.delete(destThumbPath);

                    galleryRepository.delete(fseq);
                }
            }

            gallerySimpleAjaxJsonCommon.setRetCode(1);
            gallerySimpleAjaxJsonCommon.setRetMsg(commonService.getResourceBudleMessage(locale, "messages.gallery", "gallery.msg.api.remove.ok"));
        }
        catch (IOException e)
        {
            throw new RuntimeException(commonService.getResourceBudleMessage(locale, "message.common","common.exception.io"));
        }

        return gallerySimpleAjaxJsonCommon;
    }

    public GalleryExtention getGallery(Locale locale, long fSeq) throws IOException
    {
        GalleryExtention galleryExtention = new GalleryExtention();

        Gallery gallery = galleryRepository.findOne(fSeq);

        if(Objects.isNull(gallery))
            throw new RuntimeException(commonService.getResourceBudleMessage(locale, "message.common","common.objects.notnull"));

        try {
            galleryExtention.contentType = gallery.getContentType();
            galleryExtention.byteArrayOutputStream = getImage(locale, gallery);

            return galleryExtention;
        }
        catch (IOException e)
        {
            throw new RuntimeException(commonService.getResourceBudleMessage(locale, "message.common","common.exception.io"));
        }
    }


    public void updateGalleryQueueList(List<String> seqList, BoardFree boardFree) {
        //List<String> seqList = Arrays.asList(galleryQueueList.split(","));

        for(String seq : seqList) {
            galleryRepository.updateBoardSeq(boardFree, AttachmentStatus.STORED, Long.parseLong(seq));
        }
    }
}
