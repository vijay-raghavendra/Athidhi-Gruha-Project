package com.athidhi.auth_service.Logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SecurityLoggerUtil {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(
                    LoggingConstants.SECURITY_LOGGER);

    public void logLoginSuccess(
            String userId) {

        LOGGER.info(
                """

                ================= SECURITY =================

                ACTION      : LOGIN_SUCCESS

                USER_ID     : {}

                ============================================

                """,
                userId
        );
    }

    public void logLoginFailure(
            String userId,
            String reason) {

        LOGGER.warn(
                """

                ================= SECURITY =================

                ACTION      : LOGIN_FAILED

                USER_ID     : {}

                REASON      : {}

                ============================================

                """,
                userId,
                reason
        );
    }

    public void logTokenGenerated(
            String userId) {

        LOGGER.info(
                """

                ================= SECURITY =================

                ACTION      : TOKEN_GENERATED

                USER_ID     : {}

                ============================================

                """,
                userId
        );
    }

    public void logPasswordReset(
            String userId) {

        LOGGER.info(
                """

                ================= SECURITY =================

                ACTION      : PASSWORD_RESET

                USER_ID     : {}

                ============================================

                """,
                userId
        );
    }

    public void logPasswordResetVerification(
            String userId) {

        LOGGER.info(
                """

                ================= SECURITY =================

                ACTION      : PASSWORD_RESET_VERIFICATION

                USER_ID     : {}

                ============================================

                """,
                userId
        );
    }
}