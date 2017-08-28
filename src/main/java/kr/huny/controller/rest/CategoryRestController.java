package kr.huny.controller.rest;

import kr.huny.model.db.BoardCategory;
import kr.huny.model.db.common.AjaxJsonCommon;
import kr.huny.model.db.common.BoardInfo;
import kr.huny.model.db.common.BoardPageInfo;
import kr.huny.service.BoardCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by sousic on 2017-08-02.
 */
@Slf4j
@RestController
@RequestMapping(value="/api/tools/category")
public class CategoryRestController {
    @Autowired
    BoardCategoryService boardCategoryService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BoardPageInfo<List<BoardCategory>> listJSON(@ModelAttribute BoardInfo boardInfo)
    {
        log.debug("BoardInfo => " + boardInfo.toString());
        BoardPageInfo<List<BoardCategory>> listBoardPageInfo = boardCategoryService.getCategoryList(boardInfo);

        return listBoardPageInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/remove/{categorySeq}", method = RequestMethod.POST)
    public AjaxJsonCommon categoryDelete(@PathVariable long categorySeq, HttpServletRequest request)
    {
        AjaxJsonCommon ajaxJsonCommon = boardCategoryService.categoryDelete(categorySeq, request);

        return ajaxJsonCommon;
    }
}
