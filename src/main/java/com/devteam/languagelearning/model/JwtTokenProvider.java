package com.devteam.languagelearning.model;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.devteam.languagelearning.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.NoSuchElementException;

public class JwtTokenProvider {

    @Autowired
    private UserRepository userRepository;

    private long validityInMilliseconds; // Token validity time

    public JwtTokenProvider(long validityInMilliseconds) {
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public JwtTokenProvider() {
    }

    public String createToken(Long id, String secretKey) {
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
            // Get secret key from user
            User user = userRepository.findById(user_id).orElseThrow();
            String secretKey = user.getToken();
            System.out.println("secret key: " + secretKey);
            // decode JWT with secret key
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
            DecodedJWT jwt = verifier.verify(token);

            System.out.println("JWT Decoded:");
            System.out.println(jwt.toString());
            System.out.println(jwt.getExpiresAt());
            System.out.println(jwt.getSubject());

            // check if userid matches userid in token
            return jwt.getSubject().equals(user_id.toString());

        } catch (JWTVerificationException | NoSuchElementException exception) {
            // Invalid token
            System.out.println("Not valid");
            System.out.println(exception);
            return false;
        }
    }

}