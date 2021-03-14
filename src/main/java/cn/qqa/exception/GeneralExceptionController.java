package cn.qqa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 处理全局异常：
 *
 *  优先级：1控制器异常>2全局异常中具体异常>3全局异常
 *  就近原则>精准度原则
 *  1控制器异常 优先级最高 就近原则
 *  2全局异常中具体异常 优先级中等 如果控制器内没有定义异常处理 精准原则
 *  3全局异常 优先级最低 当异常没有满足上面两种情况，会交给全局异常处理
 *
 *  实际项目开发中，往往只需要3全局异常
 *
 *  统一异常处理：同时处理普通请求和ajax请求
 *  普通请求：返回视图以及错误信息
 *  ajax：返回json{
 *      code:
 *      message:
 *  }
 */

@ControllerAdvice
public class GeneralExceptionController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Exception ex,
                                        HandlerMethod handlerMethod){
        //如果当前请求是ajax就返回json

/*
        1.根据用户请求的处理方法是否是一个返回json的处理方法
        RestController annotation = handlerMethod.getClass().getAnnotation(RestController.class);//获得类上的某个注解
        ResponseBody annotation1 = handlerMethod.getMethod().getAnnotation(ResponseBody.class);//获得方法上的某个注解
        if(annotation != null||annotation1 != null)
*/

/*
        2.可以根据请求头中的类型conten-type包含"application/json"（比如"application/json;encode=utf-8"）
        本人版本
        if(request.getContentType().equals("application/json")){
            System.out.println("处理ajax异常");
        }
*/
/*        if(request.getHeader("Accept").indexOf("application/json")>-1){
            //可以直接输出json response.getWriter().write();
            //集成jackson
            //ModelAndView同时支持返回view和json
            //这种方式就是返回json
            ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
            //通常根据不同的异常返回不同的编码（code）
            modelAndView.addObject("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            modelAndView.addObject("message", ex.getMessage());
            return modelAndView;
        }else{
            System.out.println("全局异常处理");
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("error");
            modelAndView.addObject("ex",ex);
            StringWriter sw = new StringWriter();
            PrintWriter printWriter = new PrintWriter(sw);
            ex.printStackTrace(printWriter);
            System.out.println(sw.toString());//日志记录
            return modelAndView;
        }*/

        if(request.getHeader("Content-Type").indexOf("application/json")>-1){
            // 可以直接输出json  reponse.getWriter().write();  或者集成jackson

            // 集成jackson的方式：
            //ModelAndView 同时支持视图返回和json返回
            // 这种方式就是返回json
            ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
            // 通常会根据不同的异常返回不同的编码
            modelAndView.addObject("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            modelAndView.addObject("message",ex.getMessage());
            return  modelAndView;
        }
        else{
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("error");
            modelAndView.addObject("ex", ex);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());   // 日志记录
            return modelAndView;
        }
    }

/*    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleExceptionMissingRequestParameter(Exception ex){
        System.out.println("全局异常处理-------------MissingServletRequestParameterException");
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
