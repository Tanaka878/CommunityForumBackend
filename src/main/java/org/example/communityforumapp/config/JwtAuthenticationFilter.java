package org.example.communityforumapp.config;

import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request
            , @NonNull HttpServletResponse response,
                                   @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String AuthHeader = request.getHeader("Authorization");
        final String jwt;
        if (AuthHeader != null && AuthHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }

        jwt = AuthHeader.substring(7);


    }
}
