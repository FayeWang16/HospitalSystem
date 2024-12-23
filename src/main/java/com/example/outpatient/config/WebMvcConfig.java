package com.example.outpatient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;


@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };

    /**
     * Resolves cross-origin issues
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                // allowedOrigins() should not use a wildcard (*) for security reasons
                // Allows cookies in cross-origin requests
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    /**
     * Configures resource paths
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        List<StringHttpMessageConverter> stringHttpMessageConverters = converters.stream()
                .filter(converter -> converter instanceof StringHttpMessageConverter)
                .map(converter -> (StringHttpMessageConverter) converter)
                .collect(Collectors.toList());
        if (stringHttpMessageConverters.isEmpty()) {
            converters.add(responseBodyStringConverter());
        } else {
            stringHttpMessageConverters.forEach(converter -> converter.setDefaultCharset(StandardCharsets.UTF_8));
        }
    }

    public HttpMessageConverter<String> responseBodyStringConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }
}