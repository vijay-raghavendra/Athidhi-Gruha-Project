package com.athidhi.auth_service.Logging;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupLogger {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(
                    LoggingConstants.STARTUP_LOGGER);

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.port}")
    private String serverPort;

    @PostConstruct
    public void logStartupInformation() {

        LOGGER.info("==========================================");

        LOGGER.info("Application Name : {}", applicationName);

        LOGGER.info("Server Port      : {}", serverPort);

        LOGGER.info("Java Version     : {}", System.getProperty("java.version"));

        LOGGER.info("Operating System : {}", System.getProperty("os.name"));

        LOGGER.info("==========================================");
    }

}