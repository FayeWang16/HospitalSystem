package com.example.outpatient.utils;

import cn.hutool.core.convert.Convert;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class HttpServletUtils {

    /**
     * Get a String parameter from the request.
     * @param name The name of the parameter.
     * @return The value of the parameter as a String.
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * Get a String parameter from the request with a default value.
     * @param name The name of the parameter.
     * @param defaultValue The default value to return if the parameter is not present.
     * @return The value of the parameter as a String, or the default value if the parameter is missing.
     */
    public static String getParameter(String name, String defaultValue) {
        return Convert.toStr(getRequest().getParameter(name), defaultValue);
    }

    /**
     * Get all request parameters as a Map.
     * @param request The ServletRequest object {@link ServletRequest}.
     * @return A Map containing all the request parameters.
     */
    public static Map<String, String[]> getParams(ServletRequest request) {
        final Map<String, String[]> map = request.getParameterMap();
        return Collections.unmodifiableMap(map);
    }

    /**
     * Get all request parameters as a Map with String keys and values.
     * @param request The ServletRequest object {@link ServletRequest}.
     * @return A Map containing all the request parameters with values joined into Strings.
     */
    public static Map<String, String> getParamMap(ServletRequest request) {
        Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, String[]> entry : getParams(request).entrySet()) {
            params.put(entry.getKey(), StringUtils.join(entry.getValue(), ","));
        }
        return params;
    }

    /**
     * Get the current HttpServletRequest object.
     * @return The current HttpServletRequest.
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getRequest();
    }

    /**
     * Get the current HttpServletResponse object.
     * @return The current HttpServletResponse.
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getResponse();
    }
}