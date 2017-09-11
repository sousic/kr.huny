package kr.huny.controller.rest;

import kr.huny.model.db.BoardFree;
import kr.huny.model.db.common.BoardInfo;
import kr.huny.model.db.common.BoardPageInfo;
import kr.huny.service.BoardFreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/api/tools/board")
public class BoardRestController {
    @Autowired
    CookieLocaleResolver localeResolver;
    @Autowired
    BoardFreeService boardFreeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BoardPageInfo<List<BoardFree>> getBoardList(@ModelAttribute BoardInfo boardInfo)
    {
        log.debug("boardInfo => " + boardInfo.toString());

        return boardFreeService.getBoardList(boardInfo);
    }
}
