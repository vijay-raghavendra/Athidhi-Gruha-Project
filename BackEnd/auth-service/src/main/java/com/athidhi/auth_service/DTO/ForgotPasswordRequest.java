package com.athidhi.auth_service.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
public class ForgotPasswordRequest {

    @NotBlank(message = "UserId is mandatory")
    @Schema(
            description = "UserID",
            example = "ADM0001 or OWR0001 or TNT0001"
    )
    private String userId;

    @NotNull(message = "Date Of Birth is mandatory")
    @Past(message = "DOB should be a past date")
    private LocalDate dob;

    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Mobile Number should contain exactly 10 digits"
    )
    private String mobileNumber;

    @Email(
            message = "Invalid Email format"
    )
    private String email;
}
