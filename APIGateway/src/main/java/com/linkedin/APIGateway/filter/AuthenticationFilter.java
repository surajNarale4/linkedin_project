package com.linkedin.APIGateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Configuration;

public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{

    public AuthenticationFilter(){
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return null;
    }

    static class Config{

    }
}