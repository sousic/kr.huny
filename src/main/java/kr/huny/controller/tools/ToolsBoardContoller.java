package kr.huny.controller.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
@RequestMapping(value="/tools/board")
public class ToolsBoardContoller {

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String write()
    {
        return "tools/board/post";
    }
}
