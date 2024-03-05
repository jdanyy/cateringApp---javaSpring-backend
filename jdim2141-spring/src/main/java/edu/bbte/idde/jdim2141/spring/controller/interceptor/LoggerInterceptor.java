package edu.bbte.idde.jdim2141.spring.controller.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoggerInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) throws Exception {
        String logMessage = String.format(
            "[afterCompletion][%s] - [Uri]: %s - [Response status]: %d",
            request.getMethod(),
            request.getRequestURI(),
            response.getStatus()
        );
        log.info(logMessage);
    }
}
