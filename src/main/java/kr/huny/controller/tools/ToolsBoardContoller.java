package kr.huny.controller.tools;

import kr.huny.model.db.common.BoardInfo;
import kr.huny.model.db.web.request.BoardFreeRegister;
import kr.huny.service.BoardCategoryService;
import kr.huny.service.BoardFreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@Controller
@Slf4j
@RequestMapping(value="/tools/board")
public class ToolsBoardContoller {
    @Autowired
    CookieLocaleResolver localeResolver;
    @Autowired
    BoardCategoryService boardCategoryService;
    @Autowired
    BoardFreeService boardFreeService;

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String write(Model model)
    {
        model.addAttribute("category", boardCategoryService.findAllWithUsed());

        return "tools/board/postWrite";
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String writePost(@Valid BoardFreeRegister boardFreeRegister, BindingResult bindingResult, Model model, HttpServletRequest request)
    {
        Locale locale = localeResolver.resolveLocale(request);

        model.addAttribute("category", boardCategoryService.findAllWithUsed());

        String redirectView = boardFreeService.insertPost(boardFreeRegister, bindingResult, model, locale);

        if(!StringUtils.isEmpty(redirectView))
            return redirectView;
        else
            return "tools/board/postWrite";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, @ModelAttribute BoardInfo boardInfo, HttpServletRequest request)
    {
        Locale locale = localeResolver.resolveLocale(request);

        boardFreeService.getBoardList(model, boardInfo, locale);

        return "tools/board/postList";
    }

    @RequestMapping(value ="/{bSeq}", method = RequestMethod.GET)
    public String viewPost(@PathVariable Long bSeq, Model model, HttpServletRequest request)
    {
        Locale locale = localeResolver.resolveLocale(request);

        if(bSeq == null)
        {
            return "redirect:/tools/board/postList";
        }

        String redirectView = boardFreeService.viewPost(bSeq, model, locale);
        if(!StringUtils.isEmpty(redirectView))
        {
            return redirectView;
        }

        return "tools/board/viewPost";
    }
}
