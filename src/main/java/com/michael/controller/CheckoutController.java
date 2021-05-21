package com.michael.controller;

import com.michael.model.Order;
import com.michael.model.User;
import com.michael.service.contracts.IUserService;
import com.michael.utils.Money;
import com.michael.utils.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class CheckoutController {

    @Autowired
    IUserService userService;

    @Autowired
    Money money;

    @GetMapping("/checkout")
    public String index(HttpSession httpSession, Model model, RedirectAttributes redirectAttributes)
    {
        ShoppingCart cart = new ShoppingCart();
        User authenticatedUser = (User) httpSession.getAttribute("user_session");
        if (authenticatedUser != null) {
            authenticatedUser = userService.getUserById(authenticatedUser.getId());
        } else {
            authenticatedUser = new User();
        }

        ArrayList<ShoppingCart> productsInSession = (ArrayList<ShoppingCart>) httpSession.getAttribute("products_in_cart");
        double totalCartPrice = (new ShoppingCart()).getTotalCartPrice(productsInSession);

        model.addAttribute("customer", authenticatedUser);
        model.addAttribute("cart_items", productsInSession);
        model.addAttribute("totalCartPrice", money.formatMoneyToLocalCurrency(String.valueOf(totalCartPrice)));
        model.addAttribute("pageName",  " Checkout Page");
        model.addAttribute("order", new Order());

        return "checkout";
    }
}
