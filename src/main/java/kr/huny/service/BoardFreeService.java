package kr.huny.service;

import kr.huny.authentication.common.CommonPrincipal;
import kr.huny.model.db.BoardCategory;
import kr.huny.model.db.BoardFree;
import kr.huny.model.db.common.BoardInfo;
import kr.huny.model.db.common.BoardPageInfo;
import kr.huny.model.db.common.PageNaviInfo;
import kr.huny.model.db.web.request.BoardFreeRegister;
import kr.huny.repository.BoardCategoryRepository;
import kr.huny.repository.BoardFreeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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

    @Autowired
    BoardCategoryRepository boardCategoryRepository;

    public String insertPost(BoardFreeRegister boardFreeRegister, BindingResult bindingResult, Model model, Locale locale) {

        String returnPostView = "tools/board/postList";

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
                .content(boardFreeRegister.getContent())
                .userSeq(commonPrincipal.getSeq())
                .username(commonPrincipal.getUsername())
                .build();

        //이미지,첨부 수량 추출
        List<String> galleryList = Arrays.asList(boardFreeRegister.getGalleryQueueList().split(","));
        List<String> attachList = Arrays.asList(boardFreeRegister.getAttachQueueList().split(","));

        //게시물 저장
        boardFree.setAttachmentCount(attachList.size());
        boardFree.setGalleryCount(galleryList.size());
        boardFreeRepository.save(boardFree);

        //카테고리 업데이트
        boardCategoryRepository.updateCategoryCreateCount(boardFreeRegister.getCategorySeq());

        //이미지 업데이트
        if(galleryList.size() > 0) {
            log.debug("images proccess...");
            galleryService.updateGalleryQueueList(galleryList, boardFree);
        }

        //첨부파일 업데이트
        if(attachList.size() > 0)
        {
            log.debug("attach proccess...");
            attachmentService.updateAttachQueueList(attachList, boardFree);
        }

        return "redirect:/tools/board/list";
    }

    public void getBoardList(Model model, BoardInfo boardInfo, Locale locale) {
        BoardPageInfo<List<BoardFree>> boardPageInfo = getBoardList(boardInfo);

        model.addAttribute("boardFreeList", boardPageInfo);
    }

    public BoardPageInfo<List<BoardFree>> getBoardList(BoardInfo boardInfo) {
        BoardPageInfo<List<BoardFree>> boardPageInfo = new BoardPageInfo<>();

        log.debug("boardInfo => " + boardInfo.toString());

        Sort sort = new Sort(Sort.Direction.DESC, Arrays.asList("boardSeq"));
        Pageable pageable = new PageRequest(boardInfo.getPage()-1, boardInfo.getSize(), sort);

        Page<BoardFree> tmpList = boardFreeRepository.findAll(pageable);

        boardPageInfo.setList(tmpList.getContent());
        boardPageInfo.setPageNaviInfo(PageNaviInfo.builder()
                .currentPage(tmpList.getNumber())
                .totalPage(tmpList.getTotalPages())
                .size(boardInfo.getSize())
                .build());

        return boardPageInfo;
    }

    public String viewPost(Long bSeq, Model model, Locale locale) {
        String returnListView = "redirect:/tools/board/list";

        BoardFree boardFree = boardFreeRepository.findOne(bSeq.longValue());
        if(Objects.isNull(boardFree))
        {
            return returnListView;
        }

        model.addAttribute("boardFree", boardFree);
        model.addAttribute("attach",attachmentService.getPostWithAttachList(boardFree.getBoardSeq()));

        return null;
    }
}
