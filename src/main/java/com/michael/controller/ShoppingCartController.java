package com.michael.controller;

import com.michael.model.Product;
import com.michael.service.contracts.IProductService;
import com.michael.utils.Money;
import com.michael.utils.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class ShoppingCartController {

    @Autowired
    IProductService productService;

//    @Autowired
//    ShoppingCart cart;

    @Autowired
    Money money;

    @GetMapping("/product/{productId}/add-to-cart")
    public String store(@PathVariable(value = "productId") long productId, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            return "Something went wrong. Add Product to cart again";
        }

        ArrayList<ShoppingCart> productsInSession = (ArrayList<ShoppingCart>) session.getAttribute("products_in_cart");

        if (productsInSession == null) {
            session.setAttribute("products_in_cart", new ArrayList<>());
            productsInSession = (ArrayList<ShoppingCart>) session.getAttribute("products_in_cart");
            ShoppingCart cart = this.addItemToCart(product);
            productsInSession.add(cart);

            redirectAttributes.addFlashAttribute("cart_messasge", "Product Added to Cart Successfully");

        } else {
            ShoppingCart productInCart1 =  new ShoppingCart();
            boolean productExistInSession = false;
            int productInSessionIndex = 0;
            for (ShoppingCart cartItem :  productsInSession) {
                if(cartItem.getProductId() == productId) {
                    productInCart1.setProductId(cartItem.getProductId());
                    productInCart1.setProductTitle(cartItem.getProductTitle());
                    productInCart1.setProductAmount(cartItem.getProductAmount());
                    productInCart1.setProductQuantity(cartItem.getProductQuantity() + 1);
                    productInCart1.setCartPrice(cartItem.getCartPrice());
                    double cartPriceUpdated = this.calculateCartPriceTotal(productInCart1.getProductAmount(), productInCart1.getProductQuantity());

                    System.err.println("Cart Quantity::" + productInCart1.getProductQuantity());
                    System.err.println("Cart Price::" + cartPriceUpdated);

                    productInCart1.setCartTotal(money.formatMoneyToLocalCurrency(String.valueOf(cartPriceUpdated)));

                    productExistInSession = true;

                    productInSessionIndex = productsInSession.indexOf(cartItem);


                }
            }

            if (productExistInSession) {

                productsInSession.set(productInSessionIndex, productInCart1);

                redirectAttributes.addFlashAttribute("cart_message", "Product in cart Updated Successfully!");

            } else {
                ShoppingCart cart = this.addItemToCart(product);
                productsInSession.add(cart);
                redirectAttributes.addFlashAttribute("cart_messasge", "Product Added to Cart Successfully");

            }
        }

        model.addAttribute("cart_items", productsInSession);

        return "shopping-cart";

    }

    private ShoppingCart addItemToCart(Product product) {

        ShoppingCart cart = new ShoppingCart();

        cart.setProductId(product.getId());
        cart.setProductTitle(product.getTitle());
        cart.setProductAmount(money.getFormattedMoneyToNumber(product.getPrice()));
        cart.setProductQuantity(1);
        double cartPrice = this.calculateCartPriceTotal(cart.getProductAmount(), 1);
        cart.setCartPrice(money.formatMoneyToLocalCurrency(String.valueOf(cart.getProductAmount())));
        cart.setCartTotal(money.formatMoneyToLocalCurrency(String.valueOf(cartPrice)));

        return cart;

    }

    private double calculateCartPriceTotal(double productAmount, int quantity) {
        return productAmount * quantity;
    }


}
