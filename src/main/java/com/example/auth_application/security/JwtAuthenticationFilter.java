package com.example.auth_application.security;

import com.example.auth_application.helpers.UserHelper;
import com.example.auth_application.repositories.UserRepository;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        logger.info("Authorization header : {}", header);

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);
            try {
                // check for access token
                if (jwtService.isAccessToken(token)) {

                    Jws<Claims> parse = jwtService.parse(token);
                    Claims payload = parse.getPayload();

                    UUID userUuid = UserHelper.parseUUID(payload.getSubject());

                    userRepository.findById(userUuid).ifPresent(user -> {

                        if (user.isEnable() &&
                                SecurityContextHolder.getContext().getAuthentication() == null) {

                            List<GrantedAuthority> authorities = user.getRole() == null ? List.of() : user.getRole().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken(
                                            user.getEmail(),
                                            null,
                                            authorities
                                    );

                            authentication.setDetails(
                                    new WebAuthenticationDetailsSource().buildDetails(request)
                            );

                            SecurityContextHolder.getContext()
                                    .setAuthentication(authentication);

                            logger.info("Authentication set for user {}", user.getEmail());
                        }
                    });
                }

            } catch (MalformedJwtException e) {
                request.setAttribute("error", "Invalid Token");
            } catch (Exception e) {
                request.setAttribute("error", "Token Expired");
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri.startsWith("/api/v1/auth/")) return true;  // Skip auth endpoints
        if (uri.equals("/error")) return true;             // Skip error dispatch
        return false;
    }


}


