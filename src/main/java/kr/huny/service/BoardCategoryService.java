package kr.huny.service;

import kr.huny.model.db.BoardCategory;
import kr.huny.model.db.common.AjaxJsonCommon;
import kr.huny.model.db.common.BoardInfo;
import kr.huny.model.db.common.BoardPageInfo;
import kr.huny.model.db.common.PageNaviInfo;
import kr.huny.model.db.web.CategoryRegister;
import kr.huny.repository.BoardCategoryRepository;
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

/**
 * Created by sousic on 2017-08-01.
 */
@Service
@Slf4j
public class BoardCategoryService {

    @Autowired
    BoardCategoryRepository boardCategoryRepository;

    @Autowired
    CommonService commonService;

    public void getCategoryList(Model model, BoardInfo boardInfo, Locale locale) {
        BoardPageInfo<List<BoardCategory>> boardPageInfo = new BoardPageInfo<>();

        Page<BoardCategory> tmpList = getBoardCategories(boardInfo, boardPageInfo);
        boardPageInfo.setPageNaviInfo(
            PageNaviInfo.builder()
                .currentPage(tmpList.getNumber())
                .totalPage(tmpList.getTotalPages())
                .size(boardInfo.getSize())
                .build()
        );

        model.addAttribute("categoryList",boardPageInfo);
    }

    public BoardPageInfo<List<BoardCategory>> getCategoryList(BoardInfo boardInfo) {
        BoardPageInfo<List<BoardCategory>> boardPageInfo = new BoardPageInfo<>();

        Page<BoardCategory> tmpList = getBoardCategories(boardInfo, boardPageInfo);
        boardPageInfo.setPageNaviInfo(
                PageNaviInfo.builder()
                        .currentPage(tmpList.getNumber())
                        .totalPage(tmpList.getTotalPages())
                        .size(boardInfo.getSize())
                        .build()
        );
        return boardPageInfo;
    }

    private Page<BoardCategory> getBoardCategories(BoardInfo boardInfo, BoardPageInfo<List<BoardCategory>> boardPageInfo) {
        Sort sort = new Sort(Sort.Direction.DESC, Arrays.asList("categorySeq"));
        Pageable pageable = new PageRequest(boardInfo.getPage()-1, boardInfo.getSize(), sort);

        Page<BoardCategory> tmpList = boardCategoryRepository.findAll(pageable);

        boardPageInfo.setList(tmpList.getContent());
        return tmpList;
    }

    public String addCategory(CategoryRegister categoryRegister, BindingResult bindingResult, Model model, Locale locale) {
        BoardCategory boardCategory;

        log.debug(categoryRegister.toString());

        if(bindingResult.hasErrors())
        {
            model.addAttribute("categoryRegister",categoryRegister);
            return "tools/category/write";
        }

        if(categoryRegister.getCategorySeq() == 0) {
            //중복 확인
            boardCategory = boardCategoryRepository.findByRestName(categoryRegister.getRestName());

            if (Objects.nonNull(boardCategory)) {
                bindingResult.reject("restNameError");
                model.addAttribute("restNameError", true);
                return "tools/category/write";
            }
        }

        boardCategory = BoardCategory.builder()
                            .categoryName(categoryRegister.getCategoryName())
                            .restName(categoryRegister.getRestName())
                            .isUsed(categoryRegister.isUsed())
                            .build();

        if(categoryRegister.getCategorySeq() > 0) {
            boardCategory.setCategorySeq(categoryRegister.getCategorySeq());
        }

        boardCategoryRepository.save(boardCategory);

        return "redirect:/tools/category/list";
    }


    public void findCategory(long categorySeq, Model model) {
        BoardCategory boardCategory = boardCategoryRepository.findOne(categorySeq);

        CategoryRegister categoryRegister;
        if(categorySeq > 0) {
            categoryRegister = CategoryRegister.builder()
                    .categoryName(boardCategory.getCategoryName())
                    .restName(boardCategory.getRestName())
                    .isUsed(boardCategory.isUsed())
                    .categorySeq(boardCategory.getCategorySeq())
                    .build();
        }
        else
        {
            categoryRegister = CategoryRegister.builder().build();
        }

        model.addAttribute("categoryRegister", categoryRegister);
    }

    public AjaxJsonCommon categoryDelete(long categorySeq, Locale locale) {
        AjaxJsonCommon ajaxJsonCommon = new AjaxJsonCommon();

        //수량 체크
        BoardCategory boardCategory = boardCategoryRepository.findOne(categorySeq);
        if(boardCategory.getCreateCount() > 0)
        {
            ajaxJsonCommon.setRetMsg(commonService.getResourceBudleMessage(locale, "message.category","category.msg.delete.fail"));
            ajaxJsonCommon.setRetCode(-1);
            return ajaxJsonCommon;
        }

        //삭제
        boardCategoryRepository.delete(categorySeq);
        ajaxJsonCommon.setRetMsg(commonService.getResourceBudleMessage(locale,"message.category","category.msg.delete.ok"));
        ajaxJsonCommon.setRetCode(1);
        return ajaxJsonCommon;
    }
}
