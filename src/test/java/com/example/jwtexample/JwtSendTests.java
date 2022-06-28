package com.example.jwtexample;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Date;

@SpringBootTest
class JwtSendTests {

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    void JWT_SEND() {

        Date now = new Date();

        String jwt = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(60).toMillis()))
                .setSubject("12345678")
                .claim("nickName", "샛별") // 비공개 클레임
                .signWith(SignatureAlgorithm.HS256, "test")
                .compact();

        String authorizationHeader = "Bearer " + jwt;

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader);

        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/server",
                HttpMethod.GET,
                request,
                String.class
        );

        System.out.println(response.getBody());

    }

}
