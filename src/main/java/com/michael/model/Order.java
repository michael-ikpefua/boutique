package com.michael.model;

import com.michael.service.ProductService;
import com.michael.service.contracts.IProductService;
import com.michael.utils.AutoDate;
import com.michael.utils.ShoppingCart;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends AutoDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "order_key")
    private String orderKey;
    @Column(name = "total_price")
    private String totalPrice;
    @Column(columnDefinition = "TEXT")
    private String body;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;


    public void addOrderItems(ArrayList<ShoppingCart> productsInSession, IProductService productService) {
        if (productsInSession != null) {

            orderItems = new ArrayList<>();

            for(ShoppingCart cart : productsInSession) {

                Product product = productService.getProductById(cart.getProductId());
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(this);
                orderItem.setProduct(product);
                orderItem.setProductTitle(cart.getProductTitle());
                orderItem.setProductAmount(cart.getCartPrice());
                orderItem.setProductQuantity(cart.getProductQuantity());
                orderItem.setCartTotalPrice(cart.getCartTotal());

                orderItems.add(orderItem);

            }


        }
    }

    public String generateOrder() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

    }

}
