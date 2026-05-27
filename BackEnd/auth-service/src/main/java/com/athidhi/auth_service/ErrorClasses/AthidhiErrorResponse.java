package com.athidhi.auth_service.ErrorClasses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AthidhiErrorResponse {

    private int statusCode;

    private String errorMessage;

    private long timestamp;
}
