package com.roomie.roomie;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.ModelMap;

@Controller
public class LoginController {
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String loginPage() {
        return "login";
    }
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String welcomePage(ModelMap model, @RequestParam String userId, @RequestParam String password) {
        if(userId.equals("admin") && password.equals("root")){
            return "welcome";
        }
        model.put("errorMsg", "Please provide correct userId and Password");
        return "login";
    }
    
    
}
