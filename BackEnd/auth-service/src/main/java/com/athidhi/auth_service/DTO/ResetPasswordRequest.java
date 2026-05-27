package com.athidhi.auth_service.DTO;

import lombok.*;

@Getter
@Setter
public class ResetPasswordRequest {

    private String userId;

    private String newPassword;
}
