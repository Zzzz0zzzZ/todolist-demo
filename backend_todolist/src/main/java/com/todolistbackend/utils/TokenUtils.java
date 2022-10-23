package com.todolistbackend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.todolistbackend.entity.User;

import java.util.Date;

public class TokenUtils {
    private final static long TIME = 48 * 60 * 60 * 1000;
    private final static String KEY = "^A*DEMO*WITH*WHSJZ&";
    public static String createToken(User user) {
        String token = null;
        try {
            Date expire = new Date(System.currentTimeMillis() + TIME);
            token = JWT.create()
                    .withIssuer("teashirt")
                    .withClaim("username", user.getUsername())
                    .withClaim("id", user.getUserid())
                    .withExpiresAt(expire)
                    .sign(Algorithm.HMAC256(KEY));
        } catch (IllegalArgumentException | JWTCreationException ignored) {
        }
        return token;
    }
    public static Boolean checkToken(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(KEY)).withIssuer("teashirt").build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
        } catch (IllegalArgumentException | JWTVerificationException e) {
            return false;
        }
        return true;
    }
}
