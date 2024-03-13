package com.jsy.clonegram.config.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * SecurityConfig에서 AccessDeniedHandler를 구현해서 거부된 요청에 따른 핸들링을 구현해야하는데 이 클래스가 그 구현클래스
 */

@Component
public class AccessRejectedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String requestURI = request.getRequestURI();

        if (requestURI.contains("/dashboard")) {
//            request.setAttribute("msg", "접근 불가능한 페이지입니다."); // 데이터를 보내지 않고 messageSource를 사용해 메시지 출력  
            request.setAttribute("nextPage", "/");
            request.getRequestDispatcher("/error/redirect").forward(request, response);
        }
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);

    }
}
