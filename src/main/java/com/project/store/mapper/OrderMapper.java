package com.project.store.mapper;

import com.project.store.dto.order.OrderDto;
import com.project.store.model.customer.Customer;
import com.project.store.model.order.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order fromDtoToEntity(OrderDto orderDto, Customer customer) {
        Order orderEntity = new Order();
        orderEntity.setId(orderDto.getId());
        orderEntity.setDateTime(orderDto.getDateTime());
        orderEntity.setCustomer(customer);

        return orderEntity;
    }

    public OrderDto fromEntityToDto(Order orderEntity) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderEntity.getId());
        orderDto.setDateTime(orderEntity.getDateTime());
        orderDto.setCustomerId(orderEntity.getCustomer().getId());

        return orderDto;
    }

    public Set<OrderDto> fromEntitiesToDtos(Set<Order> orderSet) {
        if(orderSet.isEmpty()){
            return Collections.emptySet();
        }
        return orderSet.stream()
                .map(order -> fromEntityToDto(order))
                .collect(Collectors.toSet());
    }


}
