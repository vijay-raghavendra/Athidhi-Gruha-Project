package com.athidhi.auth_service.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "UserId is mandatory")
    @Schema(
            description = "UserID",
            example = "ADM0001 or OWR0001 or TNT0001"
    )
    private String userId;

    @NotBlank(message = "Password is mandatory")
    @Schema(
            description = "Password",
            example = "Virat@051188"
    )
    private String password;
}
