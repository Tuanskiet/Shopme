package com.shopme.admin.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.shopme.admin.security.MyUserDetails;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class TokenUtils {
    public static String createToken(MyUserDetails user, int timeExpired, HttpServletRequest request){
        LocalDateTime dt1 = LocalDateTime.now().plusMinutes(timeExpired);
        Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.MY_SECRET_KEY);
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(request.getRequestURL().toString())
                .withExpiresAt(DateUtils.asDate(dt1))
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        return access_token;
    }
}