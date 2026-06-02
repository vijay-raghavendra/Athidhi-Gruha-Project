package com.athidhi.auth_service.Logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;

@Component
@Order(2)
public class RequestResponseLoggingFilter
        extends OncePerRequestFilter {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(
                    LoggingConstants.COMMUNICATION_LOGGER);

    @Value("${athidhi.logging.request-cache-size}")
    private int CACHE_LIMIT;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request,CACHE_LIMIT);

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        String uri = request.getRequestURI();

        boolean skipLogging = uri.contains("swagger-ui") || uri.contains("v3/api-docs") || uri.contains("favicon") || uri.contains("actuator");

        String requestId = UUID.randomUUID().toString().substring(0, 8);

        filterChain.doFilter(requestWrapper,responseWrapper);

        long endTime =
                System.currentTimeMillis();

        if (!skipLogging) {

            String requestBody = getRequestBody(requestWrapper);

            String responseBody = getResponseBody(responseWrapper);

            LOGGER.info(
                    """
                    
                    ==================== REQUEST ====================    
                    REQUEST_ID   : {}    
                    TIMESTAMP    : {}    
                    METHOD       : {}    
                    URI          : {}    
                    QUERY_PARAMS : {}    
                    CLIENT_IP    : {}    
                    REQUEST_BODY : {}
                    ================================================
                    """,
                    requestId,
                    LocalDateTime.now(),
                    request.getMethod(),
                    uri,
                    request.getQueryString() == null
                            ? "N/A"
                            : request.getQueryString(),
                    request.getRemoteAddr(),
                    requestBody
            );

            LOGGER.info(
                    """
                    
                    ==================== RESPONSE ===================
                    REQUEST_ID   : {}    
                    STATUS       : {}    
                    URI          : {}    
                    DURATION_MS  : {}    
                    RESPONSE_BODY :{}    
                    ================================================
                    """,
                    requestId,
                    responseWrapper.getStatus(),
                    uri,
                    (endTime - startTime),
                    responseBody
            );
        }


            responseWrapper.copyBodyToResponse();

    }
    private String getRequestBody(
            ContentCachingRequestWrapper request) {

        byte[] content =
                request.getContentAsByteArray();

        if (content.length == 0) {

            return "N/A";
        }

        return new String(
                content,
                StandardCharsets.UTF_8
        );
    }
    private String getResponseBody(
            ContentCachingResponseWrapper response) {

        byte[] content =
                response.getContentAsByteArray();

        if (content.length == 0) {

            return "N/A";
        }

        return new String(
                content,
                StandardCharsets.UTF_8
        );
    }
    private String maskSensitiveData(
            String json) {

        if (json == null) {

            return "N/A";
        }

        return json
                .replaceAll(
                        "(\"password\"\\s*:\\s*\")(.*?)(\")",
                        "$1********$3"
                )
                .replaceAll(
                        "(\"newPassword\"\\s*:\\s*\")(.*?)(\")",
                        "$1********$3"
                )
                .replaceAll(
                        "(\"confirmPassword\"\\s*:\\s*\")(.*?)(\")",
                        "$1********$3"
                );
    }
}