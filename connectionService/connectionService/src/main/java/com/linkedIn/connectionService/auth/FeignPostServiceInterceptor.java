package com.linkedIn.connectionService.auth;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignPostServiceInterceptor implements RequestInterceptor{

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Long userId= AuthContextHolder.getCurrentUserId();

        if(userId!=null){
            requestTemplate.header("Y-USER-ID",userId.toString());
        }
    }
}
