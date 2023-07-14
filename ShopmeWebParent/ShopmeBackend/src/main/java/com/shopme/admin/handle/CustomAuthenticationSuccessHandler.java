package com.shopme.admin.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopme.admin.security.MyUserDetails;
import com.shopme.admin.services.RefreshTokenService;
import com.shopme.admin.utils.SecurityConstants;
import com.shopme.admin.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

//    private final RefreshTokenService refreshTokenService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, "/users");
 //        this.redirectAfterLogin(request,response,authentication);
//        this.createToken(request,response,authentication);
    }
    public void redirectAfterLogin(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        boolean hasUserRole = false;
        boolean hasAdminRole = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                hasUserRole = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                hasAdminRole = true;
                break;
            }
        }


        if (hasUserRole) {
            redirectStrategy.sendRedirect(request, response, "/success");
        } else if (hasAdminRole) {
            redirectStrategy.sendRedirect(request, response, "/admin/index");
        } else {
            throw new IllegalStateException();
        }
    }
    public void createToken(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
//        Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.MY_SECRET_KEY);
//        String access_token = JWT.create()
//                .withSubject(user.getUsername())
//                .withIssuer(request.getRequestURL().toString())
//                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TIME_EXPIRED_ACCESS_TOKEN))
//                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                .sign(algorithm);
//        String refresh_token = JWT.create()
//                .withSubject(user.getUsername())
//                .withIssuer(request.getRequestURL().toString())
//                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                .sign(algorithm);

        String access_token = TokenUtils.createToken(user,  SecurityConstants.TIME_EXPIRED_ACCESS_TOKEN, request);
        String refresh_token = TokenUtils.createToken(user,  SecurityConstants.TIME_EXPIRED_REFRESH_TOKEN, request);
//        refreshTokenService.createRefreshToken(user.getUsername(), refresh_token);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token",access_token);
        tokens.put("refresh_token",refresh_token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}