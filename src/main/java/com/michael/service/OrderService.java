package com.michael.service;

import com.michael.model.Order;
import com.michael.repository.OrderRepository;
import com.michael.service.contracts.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public void addOrder(Order order) {
        orderRepository.save(order);
    }
}
