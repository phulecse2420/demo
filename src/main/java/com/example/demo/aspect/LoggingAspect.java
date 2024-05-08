package com.example.demo.aspect;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Set;
import java.util.stream.IntStream;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Aspect
@Component
public class LoggingAspect {

    private static final Set<String> ignoreParams = Set.of("password", "newPassword");

    @Around("execution(public * com.example.demo.controllers..*.*(..))")
    public Object logAroundControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String clazz = joinPoint.getTarget().getClass().getSimpleName();
        String method = signature.getName();
        StringBuilder msg = new StringBuilder("----------").append(clazz).append('.').append(method)
            .append("---------- <= ");
        int length = msg.length();
        msg.append("START");

        appendParameters(joinPoint, signature, msg);
        var log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        log.info("{}", msg);

        // method running
        var start = System.currentTimeMillis();
        Object retVal = executeMethod(joinPoint, clazz, method, log);
        long end = System.currentTimeMillis();

        msg.setLength(length);
        msg.append("  END in [").append(end - start).append("] ms.");
        if (!(retVal instanceof ModelAndView)) {
            msg.append(" Result: [").append(retVal).append(']');
        }
        log.info("{}", msg);

        return retVal;
    }

    private static void appendParameters(ProceedingJoinPoint joinPoint, MethodSignature signature,
        StringBuilder msg) {
        String[] paramNames = signature.getParameterNames();
        if (null != paramNames && paramNames.length > 0) {
            Object[] args = joinPoint.getArgs();
            StringBuilder paramMsg = new StringBuilder();
            IntStream.range(0, paramNames.length).forEach((int i) -> {
                Object arg = args[i];
                if (arg instanceof ServletResponse || arg instanceof ServletRequest
                    || arg instanceof HttpSession || arg instanceof MultipartFile
                    || ignoreParams.contains(paramNames[i])) {
                    return;
                }
                if (paramMsg.length() > 0) {
                    paramMsg.append(", ");
                }
                paramMsg.append(paramNames[i]).append("=").append(arg);
            });
            if (paramMsg.length() > 0) {
                msg.append(" Parameters: [").append(paramMsg).append("]");
            }
        }
    }

    private static Object executeMethod(ProceedingJoinPoint joinPoint, String clazz, String method,
        Logger log) throws Throwable {
        Object retVal;
        try {
            retVal = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("{}.{} exception.", clazz, method, e);
            throw e;
        }
        return retVal;
    }
}
