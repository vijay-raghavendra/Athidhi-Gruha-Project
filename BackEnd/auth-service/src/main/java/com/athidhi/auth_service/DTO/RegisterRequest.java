package com.athidhi.auth_service.DTO;

import com.athidhi.auth_service.Enums.Gender;
import com.athidhi.auth_service.Enums.UserRole;
import lombok.*;
import java.time.LocalDate;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "First Name is mandatory")
    @Pattern(
            regexp = "^[A-Za-z]+$",
            message = "First Name should contain only alphabets"
    )
    @Size(max = 50)
    @Schema(
            description = "First Name",
            example = "Virat"
    )
    private String firstName;

    @Pattern(
            regexp = "^[A-Za-z]*$",
            message = "Middle Name should contain only alphabets"
    )
    @Size(max = 50)
    @Schema(
            description = "Middle Name",
            example = "Prem"
    )
    private String middleName;

    @NotBlank(message = "Last Name is mandatory")
    @Pattern(
            regexp = "^[A-Za-z]+$",
            message = "Last Name should contain only alphabets"
    )
    @Size(max = 50)
    @Schema(
            description = "First Name",
            example = "Kohli"
    )
    private String lastName;

    @NotNull(message = "Role is mandatory")
    @Schema(
            description = "User Role",
            allowableValues = {
                    "ADMIN",
                    "OWNER",
                    "TENANT"
            },
            example = "ADMIN"
    )
    private UserRole userRole;

    @NotNull(message = "Date Of Birth is mandatory")
    @Past(message = "DOB should be a past date")
    private LocalDate dob;

    @NotNull(message = "Gender is mandatory")
    @Schema(
            description = "Gender",
            allowableValues = {
                    "MALE",
                    "FEMALE",
                    "OTHER"
            },
            example = "MALE"
    )
    private Gender gender;

    @NotBlank(message = "Mobile Number is mandatory")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Mobile Number should contain exactly 10 digits"
    )
    @Schema(
            description = "Mobile Number",
            example = "1234567809"
    )
    private String mobileNumber;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid Email format")
    @Schema(
            description = "Email Address",
            example = "virat@gmail.com"
    )
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Pattern(
            regexp =
                    "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).{8,18}$",
            message =
                    "Password must contain uppercase, lowercase, number and special character"
    )
    @Schema(
            description = "Password",
            example = "Virat@051188"
    )
    private String password;
}
