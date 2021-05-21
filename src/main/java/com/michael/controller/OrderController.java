package com.michael.controller;

import com.michael.model.Order;
import com.michael.model.OrderItem;
import com.michael.model.User;
import com.michael.service.contracts.IOrderService;
import com.michael.service.contracts.IProductService;
import com.michael.utils.Money;
import com.michael.utils.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class OrderController {

    @Autowired
    Money money;

    @Autowired
    IOrderService orderService;

    @Autowired
    IProductService productService;


    @PostMapping("order")
    public String store(Order order, HttpSession httpSession, RedirectAttributes redirectAttributes) {


        User authenticatedUser = (User) httpSession.getAttribute("user_session");

        if (authenticatedUser == null) {
            redirectAttributes.addFlashAttribute("invalid_user", "Please Login to Continue");
            return "redirect:/login";
        }
        ArrayList<ShoppingCart> productsInSession = (ArrayList<ShoppingCart>) httpSession.getAttribute("products_in_cart");
        if (productsInSession == null) {
            redirectAttributes.addFlashAttribute("empty_cart", "Add Product to Cart");
            return "redirect:/shop";
        }

        double totalCartPrice = (new ShoppingCart()).getTotalCartPrice(productsInSession);

        order.setUser(authenticatedUser);
        order.setOrderKey(order.generateOrder());
        order.setTotalPrice(money.formatMoneyToLocalCurrency(String.valueOf(totalCartPrice)));
        order.setBody(order.getBody());
        order.addOrderItems(productsInSession, productService);

        orderService.addOrder(order);


        return "confirmation";
    }
}
