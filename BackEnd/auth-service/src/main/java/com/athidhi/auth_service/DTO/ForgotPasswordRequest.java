package com.athidhi.auth_service.DTO;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
public class ForgotPasswordRequest {

    private String userId;

    private LocalDate dob;

    private String mobileNumber;

    private String email;
}
