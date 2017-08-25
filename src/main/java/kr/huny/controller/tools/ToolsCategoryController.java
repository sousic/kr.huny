package kr.huny.controller.tools;

import kr.huny.model.db.common.BoardInfo;
import kr.huny.model.db.web.CategoryRegister;
import kr.huny.service.BoardCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by sousic on 2017-08-01.
 */
@Controller
@Slf4j
@RequestMapping(value="/tools/category")
public class ToolsCategoryController {
    @Autowired
    BoardCategoryService boardCategoryService;

    @Resource
    CookieLocaleResolver localeResolver;

    @RequestMapping(value = "")
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

    @RequestMapping(value="write", method = RequestMethod.GET)
    public String write(@RequestParam(value = "seq", required = false, defaultValue = "0") long categorySeq, Model model)
    {
        if(categorySeq > 0) {
            boardCategoryService.findCategory(categorySeq, model);
        }
        return "tools/category/write";
    }

    @RequestMapping(value="write", method = RequestMethod.POST)
    public String writePost(@Valid CategoryRegister categoryRegister, BindingResult bindingResult, Model model, HttpServletRequest request)
    {
        String redirectView = boardCategoryService.addCategory(categoryRegister, bindingResult, model, request);

        if(!StringUtils.isEmpty(redirectView))
            return redirectView;
        else
            return "tools/category/write";
    }
}
