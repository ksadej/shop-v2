package com.example.shopv2.security;

import com.example.shopv2.service.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtRequestFilterConfiguration extends OncePerRequestFilter {
    final private JWTService jwtService;
    final private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public JwtRequestFilterConfiguration(JWTService jwtService, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtService = jwtService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        var authHeader = httpServletRequest.getHeader("Authorization");

        String username = null;
        String jwt = null;

        String authPrefix = "Bearer ";
        if (Objects.nonNull(authHeader) && authHeader.startsWith(authPrefix)) {
            jwt = authHeader.substring(authPrefix.length());
            username = jwtService.getUsernameFromToken(jwt);
        }


        if (Objects.nonNull(username)) {
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
            if (jwtService.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
