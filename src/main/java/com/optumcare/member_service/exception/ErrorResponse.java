package com.optumcare.member_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String errorCode;
    private String message;

    public ErrorResponse(String errorCode, String message) {
//        this.errorCode = errorCode;
        // Fixing the assignment issue
//        this.errorCode = errorCode;
        // Correct assignment
        this.message = message;

    }
}
