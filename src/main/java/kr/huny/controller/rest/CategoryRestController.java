package kr.huny.controller.rest;

import kr.huny.model.db.BoardCategory;
import kr.huny.model.db.common.AjaxJsonCommon;
import kr.huny.model.db.common.BoardInfo;
import kr.huny.model.db.common.BoardPageInfo;
import kr.huny.service.BoardCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 * Created by sousic on 2017-08-02.
 */
@Slf4j
@RestController
@RequestMapping(value="/api/tools/category")
public class CategoryRestController {
    @Autowired
    CookieLocaleResolver localeResolver;

    @Autowired
    BoardCategoryService boardCategoryService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BoardPageInfo<List<BoardCategory>> listJSON(@ModelAttribute BoardInfo boardInfo)
    {
        log.debug("BoardInfo => " + boardInfo.toString());
        BoardPageInfo<List<BoardCategory>> listBoardPageInfo = boardCategoryService.getCategoryList(boardInfo);

        return listBoardPageInfo;
    }

    @RequestMapping(value = "/remove/{categorySeq}", method = RequestMethod.POST)
    public AjaxJsonCommon categoryDelete(@PathVariable long categorySeq, HttpServletRequest request)
    {
        Locale locale = localeResolver.resolveLocale(request);

        AjaxJsonCommon ajaxJsonCommon = boardCategoryService.deleteCategory(categorySeq, locale);

        return ajaxJsonCommon;
    }
}
