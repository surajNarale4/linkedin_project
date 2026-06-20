package com.linkedIn.APIGateway.filter;

import com.linkedIn.APIGateway.service.JwtService;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;


import java.util.List;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{
    private  final JwtService jwtService;



    public AuthenticationFilter(JwtService jwtService){
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) ->{
            log.info("Filter working  fine");
            List<String> tokenHeader =exchange.getRequest().getHeaders().get("authorization");

            if(tokenHeader == null || tokenHeader.toArray().length==0 || !tokenHeader.get(0).startsWith("Bearer ")){
                log.info("token is wrong {}",tokenHeader==null);
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String jwt = tokenHeader.get(0).substring(7);

            try {
                String userId = jwtService.parseAccessToken(jwt);

               ServerWebExchange mutateExchange =exchange.mutate()
                        .request(r-> r.headers(h->h.add("Y-USER-ID",userId)).build()).build();

                return chain.filter(mutateExchange);
            }catch (JwtException e){

                System.out.println(e.getMessage());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

        } );
    }

    static class Config{

    }
}