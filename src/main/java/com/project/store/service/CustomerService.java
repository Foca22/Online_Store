package com.project.store.service;

import com.project.store.model.customer.Customer;

public interface CustomerService {

    Customer saveCustomer(Customer customerToBeSaved) ;

    Customer  getCustomer(Integer id);
}
