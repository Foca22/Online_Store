package com.project.store.service.impl;

import com.project.store.exceptions.customer.CustomerNotFoundException;
import com.project.store.exceptions.messages.ExceptionMessages;
import com.project.store.model.customer.Customer;
import com.project.store.repository.CustomerRepository;
import com.project.store.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(Customer customerToBeUpdated) {
        Customer updatedCustomer = findCustomerById(customerToBeUpdated.getId());
        updatedCustomer.setFirstName(customerToBeUpdated.getFirstName());
        updatedCustomer.setLastName(customerToBeUpdated.getLastName());
        updatedCustomer.setCnp(customerToBeUpdated.getCnp());
        updatedCustomer.setEmail(customerToBeUpdated.getEmail());

        return customerRepository.save(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    private Customer findCustomerById(Integer id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isEmpty()){
            throw new CustomerNotFoundException(ExceptionMessages.CUSTOMER_NOT_FOUND.getErrorMessage(),
                    ExceptionMessages.CUSTOMER_NOT_FOUND.getHttpStatusCode());
        }

        return customerOptional.get();
    }
}
