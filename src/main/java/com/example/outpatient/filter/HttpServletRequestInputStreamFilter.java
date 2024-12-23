package com.example.outpatient.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;


@Component
@Order(HIGHEST_PRECEDENCE + 1)
public class HttpServletRequestInputStreamFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest servletRequest = null;
        if (request instanceof HttpServletRequest) {
            String contentType = request.getContentType();
            String method = "multipart/form-data";
            if (contentType != null && !contentType.contains(method)) {
                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                servletRequest = new InputStreamHttpServletRequestWrapper(httpServletRequest, response);
            }
        }
        if (servletRequest == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(servletRequest, response);
        }
    }

}
