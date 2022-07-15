package com.project.store.service.impl;

import com.project.store.model.order.Order;
import com.project.store.repository.OrderRepository;
import com.project.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl (OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order addOrder(Order orderToBeAdded) {
        return orderRepository.save(orderToBeAdded);
    }
}
