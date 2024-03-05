package edu.bbte.idde.jdim2141.spring.controller.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.jdim2141.spring.model.dto.out.ErrorMessageDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContentTypeInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        String contentType = request.getContentType();
        String method = request.getMethod();

        if ("POST".equals(method) && (contentType == null || !contentType.contains(
            "application/json"))) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            ErrorMessageDto errorMessageDto = new ErrorMessageDto("Unsupported Media Type");

            response.getWriter().print(objectMapper.writeValueAsString(errorMessageDto));
            response.getWriter().flush();
            response.setContentType("application/json");
            return false;
        }

        return true;
    }
}
