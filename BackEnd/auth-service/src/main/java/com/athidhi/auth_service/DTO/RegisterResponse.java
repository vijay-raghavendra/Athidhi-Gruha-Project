package com.athidhi.auth_service.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {

    private String status;

    private String userId;

    private String message;
}
