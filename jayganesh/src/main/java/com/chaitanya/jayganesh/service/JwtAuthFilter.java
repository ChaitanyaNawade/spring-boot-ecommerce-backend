package com.chaitanya.jayganesh.service;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtAuthFilter extends OncePerRequestFilter
{
    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailService customUserDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer "))
        {
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(7);
        System.out.println("Auth Header: " + authHeader);
        String email = jwtService.extractEmail(token);
        System.out.println("Extracted Email: " + email);

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails userDetails = customUserDetailService.loadUserByUsername(email);

            if(jwtService.isTokenValid(token, email))
            {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("Is Token Valid: " + jwtService.isTokenValid(token, email));
            }
        }

        filterChain.doFilter(request, response);
    }
}
