package kr.huny.controller.rest;

import kr.huny.model.db.BoardCategory;
import kr.huny.model.db.common.BoardInfo;
import kr.huny.model.db.common.BoardPageInfo;
import kr.huny.service.BoardCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        BoardPageInfo<List<BoardCategory>> listBoardPageInfo = new BoardPageInfo<>();

        listBoardPageInfo = boardCategoryService.getCategoryList(boardInfo);

        return listBoardPageInfo;
    }
}
