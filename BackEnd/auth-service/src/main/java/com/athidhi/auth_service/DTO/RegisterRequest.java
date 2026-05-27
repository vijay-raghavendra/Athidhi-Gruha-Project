package com.athidhi.auth_service.DTO;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
public class RegisterRequest {

    private String firstName;

    private String middleName;

    private String lastName;

    private String userRole;

    private LocalDate dob;

    private String gender;

    private String mobileNumber;

    private String email;

    private String password;
}
