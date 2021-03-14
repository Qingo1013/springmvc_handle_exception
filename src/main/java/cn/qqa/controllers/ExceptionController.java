package cn.qqa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 处理异常：
 *      通过@ExceptionHandler可以再处理方法中记录日志
 *      转发到一个友好的错误界面进行提示
 *      经验：1.将异常记录在日志中
 *           2.可以将异常转发到错误页面，将错误信息放在一个隐藏域（div）中
 *  如果@ExceptionHandler写在@Controller类中，只能处理当前控制器类的处理方法出现的异常，不能处理别的控制器类的处理方法
 */
@Controller
public class ExceptionController {
    @RequestMapping("/exception")
    public String exception(@RequestParam String name){
        System.out.println("处理异常中....");
        return "show";
    }
    @RequestMapping("/exception02")
    public String exception02(@RequestParam String name){
        System.out.println("处理异常中....");
        return "show";
    }

/*   @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex){
        System.out.println("@Controller异常处理");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("ex",ex);
        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        ex.printStackTrace(printWriter);
        System.out.println(sw.toString());//日志记录

        return modelAndView;
    }*/
}
