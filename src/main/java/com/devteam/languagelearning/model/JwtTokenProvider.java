package com.devteam.languagelearning.model;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

public class JwtTokenProvider {

    private String secretKey; // Your secret key
    private long validityInMilliseconds; // Token validity time

    public JwtTokenProvider(String secretKey, long validityInMilliseconds) {
        this.secretKey = secretKey;
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public JwtTokenProvider() {
    }

    public String createToken(Long id) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return JWT.create()
                .withSubject(id.toString())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public boolean validateToken(String token, Long user_id) {
        System.out.println("Token inside validateToken: " + token);
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println("JWT Decoded:");
            System.out.println(jwt.toString());
            System.out.println(jwt.getExpiresAt());
            System.out.println(jwt.getSubject());
            return jwt.getSubject().equals(user_id.toString());
        } catch (JWTVerificationException exception) {
            // Invalid token
            System.out.println("Not valid");
            return false;
        }
    }

}