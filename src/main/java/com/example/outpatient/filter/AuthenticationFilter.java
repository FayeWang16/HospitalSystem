package com.example.outpatient.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        // Define the whitelist paths
        String[] whiteList = {"/login", "/doLogin", "/register", "/doRegister", "/error", "/doc.html", "/v3/api-docs", "/webjars", "/static/", "/css/", "/js/", "/images/", "/layui/"};

        for (String path : whiteList) {
            if (httpRequest.getRequestURI().startsWith(path)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        Object user = httpRequest.getSession().getAttribute("user");
        if (user == null) {
            // If not logged in, redirect to the login page
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}