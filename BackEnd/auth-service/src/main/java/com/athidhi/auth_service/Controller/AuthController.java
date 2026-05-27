package com.athidhi.auth_service.Controller;


import com.athidhi.auth_service.DTO.*;
import com.athidhi.auth_service.Exception.AthidhiException;
import com.athidhi.auth_service.Service.AuthService;

import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest request) throws AthidhiException {

        RegisterResponse response = authService.registerUser(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest request) throws AthidhiException {

        LoginResponse response = authService.loginUser(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/findUserID")
    public ResponseEntity<RegisterResponse> findUserIDByDOBAndMobileNumberAndEmail(
            @RequestParam(required = false) LocalDate dob,
            @RequestParam(required = false) String mobileNumber,
            @RequestParam(required = false) String email) throws AthidhiException {

        RegisterResponse response = new  RegisterResponse();

        if (dob != null &&
                mobileNumber != null &&
                email != null) {

            response =  authService.findUserIDByDOBAndMobileNumberAndEmail(dob,mobileNumber,email);
        }
        else if (dob != null && email != null) {

            response =  authService.findUserIdByEmailAndDob(email,dob);
        }
        else if (dob != null && mobileNumber != null) {

            response = authService.findUserIdByMobileNumberAndDOB(mobileNumber,dob);
        }

        return ResponseEntity.ok(response);

    }

    @PostMapping("/verify-user")
    public ResponseEntity<ForgotPasswordResponse> verifyUser(@RequestBody ForgotPasswordRequest request)
            throws AthidhiException {

        ForgotPasswordResponse response =authService.verifyUser(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResetPasswordResponse> esetPassword(@RequestBody ResetPasswordRequest request)
            throws AthidhiException {

        ResetPasswordResponse response = authService.resetPassword(request);

        return ResponseEntity.ok(response);
    }

}
