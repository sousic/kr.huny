package kr.huny.service;

import kr.huny.model.db.BoardCategory;
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
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
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
    LocaleResolver localeResolver;
    @Autowired
    BoardCategoryRepository boardCategoryRepository;

    public void getCategoryList(Model model, Locale locale, BoardInfo boardInfo) {
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

    public String addCategory(CategoryRegister categoryRegister, BindingResult bindingResult, Model model, HttpServletRequest request) {
        Locale locale = localeResolver.resolveLocale(request);

        log.debug(categoryRegister.toString());

        if(bindingResult.hasErrors())
        {
            model.addAttribute("categoryRegister",categoryRegister);
            return "tools/category/write";
        }

        //중복 확인
        BoardCategory boardCategory = boardCategoryRepository.findByRestName(categoryRegister.getRestName());

        if(Objects.nonNull(boardCategory))
        {
            bindingResult.reject("restNameError");
            model.addAttribute("restNameError", true);
            return "tools/category/write";
        }

        boardCategory = BoardCategory.builder()
                            .categoryName(categoryRegister.getCategoryName())
                            .restName(categoryRegister.getRestName())
                            .isUsed(categoryRegister.isUsed()).build();

        boardCategoryRepository.save(boardCategory);

        return "tools/category/list";
    }
}
