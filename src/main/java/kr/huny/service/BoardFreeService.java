package kr.huny.service;

import kr.huny.authentication.common.CommonPrincipal;
import kr.huny.model.db.BoardCategory;
import kr.huny.model.db.BoardFree;
import kr.huny.model.db.web.request.BoardFreeRegister;
import kr.huny.repository.BoardFreeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import java.util.Locale;

@Slf4j
@Service
public class BoardFreeService {
    @Autowired
    BoardFreeRepository boardFreeRepository;
    @Autowired
    GalleryService galleryService;
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    CommonService commonService;

    public String insertPost(BoardFreeRegister boardFreeRegister, BindingResult bindingResult, Model model, Locale locale) {

        String returnPostView = "tools/board/post";

        if(bindingResult.hasErrors())
        {
            model.addAttribute("post", boardFreeRegister);
            return returnPostView;
        }

        if(boardFreeRegister.getCategorySeq() == 0)
        {
            model.addAttribute("post", boardFreeRegister);
            bindingResult.reject("categoryNotFound");
            model.addAttribute("categoryNotFound", true);
            return returnPostView;
        }

        CommonPrincipal commonPrincipal = commonService.getCommonPrincipal();

        BoardCategory boardCategory = BoardCategory.builder().categorySeq(boardFreeRegister.getCategorySeq()).build();
        BoardFree boardFree = BoardFree.builder()
                .boardCategory(boardCategory)
                .title(boardFreeRegister.getTitle())
                .context(boardFreeRegister.getContext())
                .userSeq(commonPrincipal.getSeq())
                .username(commonPrincipal.getUsername())
                .build();

        //게시물 저장
        boardFreeRepository.save(boardFree);

        //이미지 업데이트
        if(!StringUtils.isEmpty(boardFreeRegister.getGalleryQueueList())) {
            log.debug("images proccess...");
            galleryService.updateGalleryQueueList(boardFreeRegister.getGalleryQueueList(), boardFree);
        }

        //첨부파일 업데이트
        if(!StringUtils.isEmpty(boardFreeRegister.getAttachQueueList()))
        {
            log.debug("attach proccess...");
            attachmentService.updateAttachQueueList(boardFreeRegister.getAttachQueueList(), boardFree);
        }

        return "redirect:/tools/board/list";
    }
}
