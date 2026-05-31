package com.athidhi.auth_service.Enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Available User Roles")
public enum UserRole {

    ADMIN,
    OWNER,
    TENANT
}
