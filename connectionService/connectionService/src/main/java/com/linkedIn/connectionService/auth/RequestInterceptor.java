package com.linkedIn.connectionService.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // Skip preflight CORS requests
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String userIdHeader = request.getHeader("Y-USER-ID");

        if (userIdHeader != null && !userIdHeader.isBlank()) {
            Long userId = Long.valueOf(userIdHeader);
            AuthContextHolder.setCurrentUserId(userId);
        }

        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        AuthContextHolder.clear();
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
