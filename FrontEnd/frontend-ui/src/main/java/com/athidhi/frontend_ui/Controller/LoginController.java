package com.athidhi.frontend_ui.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/findUserDetails")
    public String findUserDetails()
    {
        return "findUserId";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {

        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage() {

        return "reset-password";
    }
}
