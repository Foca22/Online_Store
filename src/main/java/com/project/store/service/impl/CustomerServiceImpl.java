package com.project.store.service.impl;

import com.project.store.dto.request.CreateCustomerRequest;
import com.project.store.dto.response.CustomerResponse;
import com.project.store.exceptions.customer.CustomerExceptionMessages;
import com.project.store.exceptions.customer.CustomerNotFoundException;
import com.project.store.model.customer.Customer;
import com.project.store.repository.CustomerRepository;
import com.project.store.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    private CustomerServiceImpl (CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse saveCustomer(CreateCustomerRequest createCustomerRequest) {
        Customer customer = new Customer(createCustomerRequest.getFirstName(), createCustomerRequest.getLastName(),
                createCustomerRequest.getCnp(), createCustomerRequest.getEmail());
        Customer savedCustomer = customerRepository.save(customer);
        CustomerResponse customerResponse = toCustomerResponse(savedCustomer);
        return customerResponse;
    }

    @Override
    public CustomerResponse getCustomer(Integer id) {
        Customer customer = findCustomerById(id);
        CustomerResponse customerResponse = toCustomerResponse(customer);
        return customerResponse;
    }

    private CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getCnp(),
                customer.getEmail());
    }

    private Customer findCustomerById(Integer id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isEmpty()){
            throw new CustomerNotFoundException(CustomerExceptionMessages.CUSTOMER_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
        return customerOptional.get();
    }
}
