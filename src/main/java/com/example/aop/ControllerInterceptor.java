package com.example.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xuyufengyy on 2017/3/15.
 */
@Aspect//定义为切面类
@Component
public class ControllerInterceptor {

    private Logger logger = Logger.getLogger(ControllerInterceptor.class);

    /**
     * 定义拦截规则：拦截com.example.controller包下面的所有类中
     */
    @Pointcut("execution(public * com.example.controller..*.*(..))")
    public void webLog(){}

    /**
     * 在切入点开始处切入内容
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes == null){ //socket 获取attributes为null
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();

        List<String> list = new ArrayList<String>();
        list.add("URL : " + request.getRequestURL().toString());
        list.add("HTTP_METHOD : " + request.getMethod());
        list.add("IP : " + request.getRemoteAddr());
        list.add("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        list.add("ARGS : " + Arrays.toString(joinPoint.getArgs()));

        session.setAttribute("list", list);

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 在切入点结尾处切入内容
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
    }
}
