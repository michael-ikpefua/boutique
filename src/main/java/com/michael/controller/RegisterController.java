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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "redirect:/checkout";
    }

    @PostMapping("profile/update")
    public String update(@ModelAttribute("customer") User customer, RedirectAttributes redirectAttributes) {


        if (customer.getId() == null) {
            redirectAttributes.addFlashAttribute("invalid_profile", "Please create an accout!!!");
            return "redirect:/register";
        } else {
            User user = userService.getUserById(customer.getId());
            user.setPhone(customer.getPhone());
            user.setAddress(customer.getAddress());
            user.setLocation(customer.getLocation());
            userService.addUser(user);
            return "redirect:/checkout";

        }

    }
}
