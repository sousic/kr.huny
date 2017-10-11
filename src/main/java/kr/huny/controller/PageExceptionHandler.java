package kr.huny.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.AuthenticationException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created by sousic on 2017-07-03.
 */
//@ControllerAdvice
public class PageExceptionHandler {

    @ExceptionHandler(value = { RuntimeException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String runtimeException(Exception e, Model model)
    {
        model.addAttribute("message", e.getMessage());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        e.printStackTrace(printStream);
        model.addAttribute("setStackTrace", byteArrayOutputStream.toString());
        return "error/error";
    }


    @ExceptionHandler(InternalAuthenticationServiceException.class)
    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView internalAuthenticationServiceException(AuthenticationException e)
    {
        ModelAndView model = new ModelAndView("error/error");
        model.addObject("message", e.getMessage());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        e.printStackTrace(printStream);
        model.addObject("setStackTrace", byteArrayOutputStream.toString());
        return model;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(Exception e, Model model)
    {
        model.addAttribute("message", e.getMessage());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        e.printStackTrace(printStream);
        model.addAttribute("setStackTrace", byteArrayOutputStream.toString());
        return "error/error";
    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String illegalArgumentException(IllegalArgumentException e, Model model)
//    {
//        model.addAttribute("message", e.getMessage());
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        PrintStream printStream = new PrintStream(byteArrayOutputStream);
//        e.printStackTrace(printStream);
//        model.addAttribute("setStackTrace", byteArrayOutputStream.toString());
//        return "error/error";
//    }
}
