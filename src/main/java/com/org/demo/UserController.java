package com.org.demo;

import com.org.demo.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(
            @ModelAttribute("userForm")  UserForm userForm,
            BindingResult result,
            Model model) {
        // Validate passwords match
        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.userForm", "Passwords do not match");
        }

        if (result.hasErrors()) {
            return "register";
        }

        // Registration successful
        System.out.println("Success");
        User user = new User();
        user.setCommonName("Eli");
        user.setLastName(userForm.getUsername());
        user.setPassword(userForm.getPassword());
        userService.addUser(user);

        model.addAttribute("message", "Registration successful for user: " + userForm.getUsername());
        return "success";
    }

    @GetMapping("/cicddemo")
    public String cicd(){
        return "cicd";
    }
}
