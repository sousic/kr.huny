package kr.huny.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by sousic on 2017-07-31.
 */
@Controller
@Slf4j
@RequestMapping(value="/board")
public class BoardFreeWriteController {

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String write()
    {
        return "board/post";
    }
}
