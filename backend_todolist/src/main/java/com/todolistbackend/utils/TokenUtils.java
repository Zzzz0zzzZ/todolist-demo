package com.todolistbackend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.todolistbackend.entity.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
                    .withClaim("userid", user.getUserid())
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

    public static boolean checkReq(String token,Integer userid) {
        Map<String, Claim> map = new HashMap<>();
        try {
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("teashirt")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            map = jwt.getClaims();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        }
        return Objects.equals(map.get("userid").asInt(), userid);
    }
}

