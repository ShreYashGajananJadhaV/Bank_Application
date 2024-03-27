package com.shreyash.BankApplication.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.SeBootstrap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RefererFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
//        String referer = request.getHeader("Referer");

//        if (referer == null || !referer.contains("http://localhost:8080")) {
//            response.sendError(
//                    HttpServletResponse.SC_FORBIDDEN,
//                    "Access Denied"
//            );
//            return;
//        }

        filterChain.doFilter(request, response);
    }

}
