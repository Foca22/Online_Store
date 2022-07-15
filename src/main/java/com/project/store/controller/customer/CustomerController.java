package com.project.store.controller.customer;

import com.project.store.dto.customer.CustomerDto;
import com.project.store.mapper.CustomerMapper;
import com.project.store.model.customer.Customer;
import com.project.store.service.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/customer")
public class CustomerController {

    private final CustomerServiceImpl customerService;
    private final CustomerMapper customerMapper;

    @PostMapping()
    public CustomerDto saveCustomer(@Valid @RequestBody CustomerDto requestSaveCustomer) {
        Customer customerToBeSaved = customerMapper.fromDtoToEntity(requestSaveCustomer);
        Customer savedCustomer = customerService.saveCustomer(customerToBeSaved);

        return customerMapper.fromEntityToDto(savedCustomer);
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomer(@PathVariable Integer id) {
        Customer customer = customerService.getCustomer(id);

        return customerMapper.fromEntityToDto(customer);
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customerList = customerService.getAllCustomers();
        return customerMapper.fromEntitiesToDtos(customerList);
    }

    @PutMapping()
    public CustomerDto updateCustomer(@Valid @RequestBody CustomerDto customerDto){
        Customer customerToBeUpdated = customerMapper.fromDtoToEntity(customerDto);
        Customer saveUpdatedCustomer = customerService.updateCustomer(customerToBeUpdated);

        return customerMapper.fromEntityToDto(saveUpdatedCustomer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
    }

}
