package edu.bbte.idde.jdim2141.spring.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
public class CookieTokenTransformer implements BearerTokenResolver {

    @Override
    public String resolve(HttpServletRequest request) {
        var cookie = WebUtils.getCookie(request, "jwt");
        if (cookie != null) {
            return cookie.getValue();
        }

        return null;
    }
}
