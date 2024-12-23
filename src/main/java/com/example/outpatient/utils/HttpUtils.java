package com.example.outpatient.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class HttpUtils {

    /**
     * Retrieves parameters from the request body.
     * The parameters in the body can only be retrieved once.
     * @param request The HttpServletRequest object.
     * @return A Map containing the parameters from the body.
     */
    public static Map<String, String> getBodyParams(HttpServletRequest request) {
        Map<String, String> bodyParams = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = request.getInputStream()) {
            bodyParams = objectMapper.readValue(is, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bodyParams == null) {
            bodyParams = new HashMap<>();
        }
        return bodyParams;
    }

    /**
     * Retrieves the list of parameters from the body of the request.
     * @param request The HttpServletRequest object.
     * @return A String representing the body content.
     */
    public static String getBodyString(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try (InputStream inputStream = request.getInputStream()) {
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            // Handle the exception here if needed.
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // Handle the exception here if needed.
                }
            }
        }
        return sb.toString();
    }

    /**
     * Outputs JSON using the response.
     * @param response The ServletResponse object.
     * @param result The object to be serialized and written as JSON.
     */
    public static void outJson(ServletResponse response, Object result) {
        OutputStream out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            out = response.getOutputStream();
            String strJson = new ObjectMapper().writeValueAsString(result);
            out.write(strJson.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Renders a string to the client.
     * @param response The HttpServletResponse object.
     * @param string The string to be rendered.
     * @return null.
     */
    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Outputs a file using the response.
     * @param response The HttpServletResponse object.
     * @param is The InputStream of the file.
     * @param fileName The name of the file to be output.
     */
    public static void outFile(HttpServletResponse response, InputStream is, String fileName) {
        // Set the content type to force download, not open.
        response.setContentType("application/force-download");
        // Set the file name in the header.
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        BufferedInputStream bis = null;
        try {
            // Handle potential Chinese character encoding issues.
            fileName = URLEncoder.encode(fileName, "UTF-8");
            OutputStream outputStream = response.getOutputStream();
            // Copy the input stream to the output stream.
            IOUtils.copy(is, outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}