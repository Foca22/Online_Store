package com.project.store.service;

import com.project.store.model.customer.Customer;

import java.util.List;

public interface CustomerService {

    Customer saveCustomer(Customer customerToBeSaved) ;

    Customer  getCustomer(Integer id);

    List<Customer> getAllCustomers();

    Customer updateCustomer(Customer customerToBeUpdated);

    void deleteCustomer(Integer id);
}
