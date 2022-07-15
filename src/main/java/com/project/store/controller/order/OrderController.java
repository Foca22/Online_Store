package com.project.store.controller.order;

import com.project.store.dto.order.OrderDto;
import com.project.store.mapper.OrderMapper;
import com.project.store.model.customer.Customer;
import com.project.store.model.order.Order;
import com.project.store.service.impl.CustomerServiceImpl;
import com.project.store.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;
    private final OrderMapper orderMapper;

    private final CustomerServiceImpl customerService;

    @PostMapping()
    public OrderDto addOrder(@Valid @RequestBody OrderDto orderDto) {
        Customer customer = customerService.getCustomer(orderDto.getCustomerId());
        Order orderToBeAdded = orderMapper.fromDtoToEntity(orderDto, customer);
        Order savedOrder = orderService.addOrder(orderToBeAdded);

        return orderMapper.fromEntityToDto(savedOrder);
    }
}
