package com.athidhi.auth_service.Service;

import com.athidhi.auth_service.DTO.*;
import com.athidhi.auth_service.Entity.User;
import com.athidhi.auth_service.Exception.AthidhiException;
import com.athidhi.auth_service.Logging.SecurityLoggerUtil;
import com.athidhi.auth_service.Repository.UserRepository;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final SecurityLoggerUtil securityLoggerUtil;

    public RegisterResponse registerUser(
            RegisterRequest request) throws AthidhiException {

        /* EMAIL VALIDATION */

        if (userRepository.findByEmail(
                request.getEmail()).isPresent()) {

            throw new AthidhiException("Email already registered");
        }

        /* MOBILE VALIDATION */

        if (userRepository.findByMobileNumber(
                request.getMobileNumber()).isPresent()) {

            throw new AthidhiException("Mobile number already registered");
        }

        /* GENERATE USER ID */

        String userId = generateUserId(request);

        /* CREATE USER */

        User user = new User();

        user.setUserId(userId);

        user.setFirstName(request.getFirstName());

        user.setMiddleName(request.getMiddleName());

        user.setLastName(request.getLastName());

        user.setUserRole(request.getUserRole());

        user.setDob(request.getDob());

        user.setGender(request.getGender());

        user.setMobileNumber(request.getMobileNumber());

        user.setEmail(request.getEmail());

        /* ENCRYPT PASSWORD */

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        /* SAVE USER */

        userRepository.save(user);

        /* RESPONSE */

        return new RegisterResponse("success",userId,"User Registered Successfully"
        );
    }

    private String generateUserId(RegisterRequest request) {

        long count =
                userRepository.count() + 1;

        String Role = request.getUserRole().toString();

        if(Role.equalsIgnoreCase("ADMIN"))
        {
            return "ADM" +
                    String.format("%04d", count);
        }
        else if(Role.equalsIgnoreCase("OWNER")){
            return "OWR" +
                    String.format("%04d", count);
        }
        else {
            return "TNT" +
                    String.format("%04d", count);
        }
    }

    public LoginResponse loginUser(LoginRequest request) throws AthidhiException {

        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() ->
                        {
                            securityLoggerUtil.logLoginFailure(request.getUserId(),"USER_NOT_FOUND");
                            return new AthidhiException("Invalid User-ID");
                        }

                );

        /* PASSWORD VALIDATION */

        boolean passwordMatches = passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
        );

        if (!passwordMatches) {

            securityLoggerUtil.logLoginFailure(request.getUserId(),"INVALID_PASSWORD");
            throw new AthidhiException("Invalid Password");
        }

        /* GENERATE TOKEN */

        String token = jwtService.generateToken(user.getUserId());

        securityLoggerUtil.logTokenGenerated(user.getUserId());

        securityLoggerUtil.logLoginSuccess(user.getUserId());
        return new LoginResponse("success", token, user.getUserId(), "Login Successful");
    }

    public RegisterResponse findUserIdByEmailAndDob(String email, LocalDate dob) throws AthidhiException {

        Optional<User> user = userRepository.findByEmailAndDob(email, dob);

        if (user.isPresent()) {
            return new RegisterResponse("success", user.get().getUserId(), "User Found");
        }
        else {
            throw new AthidhiException("Invalid Email or DOB Details");
        }
    }

    public RegisterResponse findUserIdByMobileNumberAndDOB(String mobileNumber, LocalDate dob) throws AthidhiException {

        Optional<User> user = userRepository.findByMobileNumberAndDob(mobileNumber, dob);
        if (user.isPresent()) {
            return new RegisterResponse("success", user.get().getUserId(), "User Found");
        }
        else  {
            throw new AthidhiException("Invalid Mobile Number or DOB Details");
        }
    }

    public RegisterResponse findUserIdByMobileNumberAndEmail(String mobileNumber, String email) throws AthidhiException {
        Optional<User> user = userRepository.findByMobileNumberAndEmail(mobileNumber, email);
        if (user.isPresent()) {
            return new RegisterResponse("success", user.get().getUserId(), "User Found");
        }
        else {
            throw new AthidhiException("Invalid Mobile Number or Email Details");
        }
    }

    public RegisterResponse findUserIDByDOBAndMobileNumberAndEmail(LocalDate dob,
                                            String mobileNumber, String email) throws AthidhiException {

        Optional<User> user = userRepository.findUserIDByDobAndMobileNumberAndEmail(dob,mobileNumber,email);

        if (user.isPresent()) {
            return new RegisterResponse("success", user.get().getUserId(), "User Found");
        }
        else  {
            throw new AthidhiException("Invalid Mobile Number or Email or DOB Details");
        }

    }

    public RegisterResponse findUserIdByDob(LocalDate dob) throws AthidhiException {
        Optional<User> user = userRepository.findByDob(dob);
        if (user.isPresent()) {
            return new RegisterResponse("success", user.get().getUserId(), "User Found");
        }
        else  {
            throw new AthidhiException("Invalid Dob Details");
        }
    }

    public RegisterResponse findUserIdByEmail(String email) throws AthidhiException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return new RegisterResponse("success", user.get().getUserId(), "User Found");
        }
        else  {
            throw new AthidhiException("Invalid Email ID");
        }
    }

    public RegisterResponse findUserIdByMobileNumber(String mobileNumber) throws AthidhiException {
        Optional<User> user = userRepository.findByMobileNumber(mobileNumber);
        if (user.isPresent()) {
            return new RegisterResponse("success", user.get().getUserId(), "User Found");
        }
        else  {
            throw new AthidhiException("Invalid Mobile Number");
        }
    }

    public ForgotPasswordResponse verifyUser(ForgotPasswordRequest request) throws AthidhiException {

        userRepository.findByUserIdAndDobAndMobileNumberAndEmail(
                        request.getUserId(),
                        request.getDob(),
                        request.getMobileNumber(),
                        request.getEmail()
                )
                .orElseThrow(() ->new AthidhiException("Entered details do not match")
                );

        securityLoggerUtil.logPasswordResetVerification(request.getUserId());

        return new ForgotPasswordResponse("success","User Verified Successfully");
    }

    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) throws AthidhiException {

        User user = userRepository.findByUserId(request.getUserId())
                        .orElseThrow(() -> new AthidhiException("User Not Found")
                        );

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        securityLoggerUtil.logPasswordReset(request.getUserId());

        return new ResetPasswordResponse("success","Password Reset Successful");
    }
}
