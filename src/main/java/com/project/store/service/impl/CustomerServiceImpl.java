package com.project.store.service.impl;

import com.project.store.exceptions.customer.CustomerExceptionMessages;
import com.project.store.exceptions.customer.CustomerNotFoundException;
import com.project.store.model.customer.Customer;
import com.project.store.repository.CustomerRepository;
import com.project.store.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Customer saveCustomer(Customer customerToBeSaved){
        return customerRepository.save(customerToBeSaved);
    }

    @Override
    public Customer getCustomer(Integer id) {
        return findCustomerById(id);
    }

    private Customer findCustomerById(Integer id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isEmpty()){
            throw new CustomerNotFoundException(CustomerExceptionMessages.CUSTOMER_NOT_FOUND.getErrorMessage(),
                    CustomerExceptionMessages.CUSTOMER_NOT_FOUND.getHttpStatusCode());
        }

        return customerOptional.get();
    }
}
