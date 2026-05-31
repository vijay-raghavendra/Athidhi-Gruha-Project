package com.athidhi.auth_service.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
public class ResetPasswordRequest {

    @NotBlank(message = "UserId is mandatory")
    @Schema(
            description = "UserID",
            example = "ADM0001 or OWR0001 or TNT0001"
    )
    private String userId;

    @NotBlank(message = "New-Password is mandatory")
    @Pattern(
            regexp =
                    "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).{8,18}$",
            message =
                    "Password must contain uppercase, lowercase, number and special character"
    )
    @Size(min = 8, max = 18)
    @Schema(
            description = "Password",
            example = "Virat@051188"
    )
    private String newPassword;
}
