package com.yychainsaw;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGen(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","源氏");

        String token = JWT.create()
                .withClaim("user",claims)
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))
                .sign(Algorithm.HMAC256("YYchainsAw"));

        System.out.println(token);
    }

    @Test
    public void testParse(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                        ".eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6Iua6kOawjyJ9LCJleHAiOjE3NjM0MjUwNzN9" +
                         ".CknLnKoOY3kdaGjCgde1XLSv96P_wCWSlhbo4pcqgSQ";

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("YYchainsAw")).build();

        DecodedJWT decodeJWT = jwtVerifier.verify(token);  //验证token，生成解码后的JWT对象

        Map<String, Claim> claims = decodeJWT.getClaims();

        System.out.println(claims.get("user"));
    }
}
