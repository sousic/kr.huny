package kr.huny.service;

import kr.huny.authentication.common.CommonPrincipal;
import kr.huny.common.CommonUtils;
import kr.huny.model.db.Gallery;
import kr.huny.model.db.common.AjaxJsonCommon;
import kr.huny.model.db.embedded.GalleryStatus;
import kr.huny.model.db.web.GallerySimple;
import kr.huny.repository.GalleryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.UUID;

@Slf4j
@Service
public class GalleryService {
    @Value("${storage.image.path}")
    private String storageImagePath;

    @Value("${stroage.thumbnail.path}")
    private String storageThumbnailPath;

    @Autowired
    CommonService commonService;
    @Autowired
    GalleryRepository galleryRepository;

    public AjaxJsonCommon<GallerySimple> updateImage(Locale locale, MultipartFile[] files) {

        try {
            AjaxJsonCommon<GallerySimple> gallerySimpleAjaxJsonCommon = new AjaxJsonCommon<>();
            CommonPrincipal commonPrincipal = commonService.getCommonPrincipal();

            for(MultipartFile file : files) {
                Gallery gallery = new Gallery();
                gallery.setUserSeq(commonPrincipal.getSeq());
                gallery.setUsername(commonPrincipal.getUsername());
                gallery.setStatus(GalleryStatus.QUEUE);
                gallery.setFileName(file.getOriginalFilename());
                gallery.setSize(file.getSize());
                gallery.setContentType(file.getContentType());
                gallery.setSaveName(String.format("%s.%s", UUID.randomUUID().toString(), FilenameUtils.getExtension(gallery.getFileName())));
                gallery.setSavePath(CommonUtils.GetSavePath(storageImagePath));

                Path destPath = Paths.get(gallery.getSavePath());

                if (Files.notExists(destPath)) {
                    Files.createDirectories(destPath);
                }

                destPath = destPath.resolve(gallery.getSaveName());
                if (Files.notExists(destPath)) {
                    Files.write(destPath, file.getBytes());
                }

                galleryRepository.save(gallery);

                //파일저장
                log.debug("gallery =>" + gallery);

                //반환 결과 생성

                GallerySimple gallerySimple = GallerySimple.builder()
                        .urlPath("/gallery/")
                        .status(gallery.getStatus())
                        .gallerySeq(gallery.getGallerySeq())
                        .build();

                gallerySimpleAjaxJsonCommon.addResultItem(gallerySimple);
            }

            gallerySimpleAjaxJsonCommon.setRetCode(1);
            gallerySimpleAjaxJsonCommon.setRetMsg(commonService.getResourceBudleMessage(locale, "messages.gallery", "gallery.msg.api.save.ok"));

            return gallerySimpleAjaxJsonCommon;
        }
        catch (IOException e)
        {
            throw new RuntimeException(commonService.getResourceBudleMessage(locale, "message.common","common.exception.io"));
        }
    }

    public ByteArrayOutputStream getImage(Locale locale, Gallery gallery) throws IOException {
        Path destPath = Paths.get(gallery.getSavePath(), gallery.getSaveName());
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
}
