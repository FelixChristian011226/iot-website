package com.felix;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGen(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","felix");
        //GENERATE JWT TOKEN
        String token = JWT.create()
                .withClaim("user",claims)   //payload
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12)) //expire time
                .sign(Algorithm.HMAC256("felix")); //signature
        System.out.println(token);
    }

    @Test
    public void testParse(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
                +".eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6ImZlbGl4In0sImV4cCI6MTcwMzI4MTg3Nn0"
                +".20_xOO8y1fUAca7gd7h7I-x7KftZVQERdI54BtCuw7c";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("felix")).build();
        DecodedJWT decodedJWT =  jwtVerifier.verify(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));
    }

}
