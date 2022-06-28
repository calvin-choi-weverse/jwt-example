package com.example.jwtexample.server;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ServerController {

    @GetMapping("/server")
    public String serve(HttpServletRequest httpRequest) {
        String authorizationHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        Claims claims = parseJwtToken(authorizationHeader);
        return claims.getSubject();
    }

    public Claims parseJwtToken(String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());

        return Jwts.parser()
                .setSigningKey("test")
                .parseClaimsJws(token)
                .getBody();
    }


}
