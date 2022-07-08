package com.project.store.mapper;

import com.project.store.dto.customer.CustomerDto;
import com.project.store.model.customer.Customer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class CustomerMapper {

    public CustomerDto fromEntityToDto(Customer entity) {
        CustomerDto customerDto = new CustomerDto();

        customerDto.setId(entity.getId());
        customerDto.setFirstName(entity.getFirstName());
        customerDto.setLastName(entity.getLastName());
        customerDto.setCnp(entity.getCnp());
        customerDto.setEmail(entity.getEmail());

        return customerDto;
    }

    public Customer fromDtoToEntity(CustomerDto dto) {
        Customer entity = new Customer();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setCnp(dto.getCnp());
        entity.setEmail(dto.getEmail());

        return entity;
    }

    public List<CustomerDto> fromEntitiesToDtos (List<Customer> customerEntitiesList){
        if(customerEntitiesList.isEmpty()){
            return Collections.emptyList();
        }
        return customerEntitiesList.stream()
                .map(customerEntity -> fromEntityToDto(customerEntity))
                .collect(Collectors.toList());
    }
}
