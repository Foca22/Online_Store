package com.project.store.service;

import com.project.store.dto.request.CreateCustomerRequest;
import com.project.store.dto.response.CustomerResponse;

public interface CustomerService {

    CustomerResponse saveCustomer(CreateCustomerRequest createCustomerRequest) ;

    CustomerResponse  getCustomer(Integer id);
}
