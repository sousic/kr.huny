package kr.huny.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sousic on 2017-06-28.
 */
@Controller
public class HomeController
{
    @RequestMapping(value="/")
    public String home()
    {
        return "home/index";
    }
}
