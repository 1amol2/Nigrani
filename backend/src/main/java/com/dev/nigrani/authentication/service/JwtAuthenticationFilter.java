package com.dev.nigrani.authentication.service;

import com.dev.nigrani.authentication.models.User;
import com.dev.nigrani.authentication.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Check whether Authorization header contains a Bearer token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT token
        String token = authHeader.substring(7);

        try {

            // Extract officialId from JWT
            String officialId = jwtService.extractUsername(token);

            // Check if user is not already authenticated
            if (officialId != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                // Find user from database
                User user = userRepository
                        .findByOfficialId(officialId)
                        .orElse(null);

                // Validate user and JWT
                if (user != null &&
                        user.isActive() &&
                        jwtService.isTokenValid(token, user)) {

                    // Create authority using user's role
                    SimpleGrantedAuthority authority =
                            new SimpleGrantedAuthority(
                                    "ROLE_" + user.getRole().name()
                            );

                    // Create authentication object
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    List.of(authority)
                            );

                    // Store authentication in SecurityContext
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);
                }
            }

        } catch (JwtException | IllegalArgumentException e) {

            // Invalid or expired JWT
            // Request will continue without authentication
        }

        // Continue the request
        filterChain.doFilter(request, response);
    }
}









//package com.dev.nigrani.authentication.service;
//
//import com.dev.nigrani.authentication.models.User;
//import com.dev.nigrani.authentication.repository.UserRepository;
//import io.jsonwebtoken.JwtException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtService;
//    private final UserRepository userRepository;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain
//    ) throws ServletException, IOException {
//
//        final String authHeader = request.getHeader("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        String token = authHeader.substring(7);
//        try {
//            String officialId = jwtService.extractUsername(token);
//            if (officialId != null
//                    && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//                User user = userRepository
//                        .findByOfficialId(officialId)
//                        .orElse(null);
//
//                if (user != null
//                        && user.isActive()
//                        && jwtService.isTokenValid(token, user)) {
//
//                    SimpleGrantedAuthority authority =
//                            new SimpleGrantedAuthority(
//                                    "ROLE_" + user.getRole().name()
//                            );
//
//                    UsernamePasswordAuthenticationToken authentication =
//                            new UsernamePasswordAuthenticationToken(
//                                    user,
//                                    null,
//                                    List.of(authority)
//                            );
//
//                    SecurityContextHolder
//                            .getContext()
//                            .setAuthentication(authentication);
//                }
//            }
//
//        } catch (JwtException | IllegalArgumentException e) {
//            // Invalid or expired token.
//            // Do not authenticate the request.
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}