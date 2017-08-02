package kr.huny.controller.tools;

import kr.huny.model.db.common.BoardInfo;
import kr.huny.service.BoardCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by sousic on 2017-08-01.
 */
@Controller
@Slf4j
@RequestMapping(value="/tools/category")
public class CategoryController {
    @Autowired
    BoardCategoryService boardCategoryService;

    @Resource
    CookieLocaleResolver localeResolver;

    @RequestMapping(value = "/")
    public String Inits()
    {
        return "redirect:/list";
    }

    @RequestMapping(value = "/list")
    public String list(HttpServletRequest request, Model model, @ModelAttribute BoardInfo boardInfo)
    {
        Locale locale = localeResolver.resolveLocale(request);
        boardCategoryService.getCategoryList(model, locale, boardInfo);
        return "tools/category/list";
    }
}
