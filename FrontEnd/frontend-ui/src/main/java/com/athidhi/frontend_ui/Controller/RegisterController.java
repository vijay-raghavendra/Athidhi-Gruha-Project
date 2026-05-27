package com.athidhi.frontend_ui.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    @GetMapping("/register")
    public String registerPage()
    {
        return "register";
    }

    @GetMapping("/findUserDetails")
    public String findUserDetails()
    {
        return "findUserId";
    }
}
