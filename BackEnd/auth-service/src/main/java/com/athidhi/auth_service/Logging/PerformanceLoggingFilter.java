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
import org.springframework.core.annotation.Order;
import java.io.IOException;

@Component
@Order(3)
public class PerformanceLoggingFilter
        extends OncePerRequestFilter {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(
                    LoggingConstants.PERFORMANCE_LOGGER);

    @Value("${athidhi.logging.duration.threashold.slowRequest}")
    private long SLOW_REQUEST_THRESHOLD;

    @Value("${athidhi.logging.duration.threashold.criticalRequest}")
    private long CRITICAL_REQUEST_THRESHOLD;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        long startTime =
                System.currentTimeMillis();

        filterChain.doFilter(
                request,
                response
        );

        long executionTime =
                System.currentTimeMillis() - startTime;

        String uri =
                request.getRequestURI();

        if (executionTime >=
                CRITICAL_REQUEST_THRESHOLD) {

            LOGGER.error(
                    """

                    ================ PERFORMANCE ================
                    URI          : {}
                    METHOD       : {}
                    EXECUTION_MS : {}
                    CATEGORY     : CRITICAL_REQUEST
                    =============================================

                    """,
                    uri,
                    request.getMethod(),
                    executionTime
            );
        }

        else if (executionTime >=
                SLOW_REQUEST_THRESHOLD) {

            LOGGER.warn(
                    """

                    ================ PERFORMANCE ================
                    URI          : {}
                    METHOD       : {}
                    EXECUTION_MS : {}
                    CATEGORY     : SLOW_REQUEST
                    =============================================

                    """,
                    uri,
                    request.getMethod(),
                    executionTime
            );
        }
        else {
            LOGGER.info(
                    """

                    ================ PERFORMANCE ================
                    URI          : {}
                    METHOD       : {}
                    EXECUTION_MS : {}
                    CATEGORY     : NORMAL_REQUEST
                    =============================================

                    """,
                    uri,
                    request.getMethod(),
                    executionTime
            );
        }
    }
}