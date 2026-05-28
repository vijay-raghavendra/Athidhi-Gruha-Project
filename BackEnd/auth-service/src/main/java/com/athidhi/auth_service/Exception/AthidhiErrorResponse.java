package com.athidhi.auth_service.Exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AthidhiErrorResponse {

    private int statusCode;

    private String statusType;

    private String errorMessage;

    private long timestamp;
}
