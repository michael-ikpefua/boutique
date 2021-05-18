package com.michael.controller;

import com.michael.model.User;
import com.michael.service.contracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    IUserService userService;

    @GetMapping("/register")
    public String index(Model model) {
        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String store(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return  "register";
        }
        userService.addUser(user);
        return "redirect:/confirmation";
    }
}
