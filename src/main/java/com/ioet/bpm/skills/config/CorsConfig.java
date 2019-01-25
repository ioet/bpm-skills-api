package com.ioet.bpm.skills.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsConfig extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        response.setHeader("Access-Control-Allow-Origin", "*");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setHeader("Access-Control-Allow-Headers", "origin, authorization, accept, content-type, x-requested-with");
            response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS");
        } else if (!response.isCommitted()) {
            try {
                chain.doFilter(request, response);
            } catch (IOException | ServletException e) {
                logger.error("An error occurred", e);
            }
        }
    }
}
