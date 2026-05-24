package com.linkedin.APIGateway.service;



import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;


@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String jwtSecrtKey=null;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecrtKey.getBytes(StandardCharsets.UTF_8));
    }

    public String parseAccessToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId").toString();


    }

}
