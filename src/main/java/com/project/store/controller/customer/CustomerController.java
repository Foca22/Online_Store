package com.project.store.controller.customer;

import com.project.store.dto.request.CreateCustomerRequest;
import com.project.store.dto.response.CustomerResponse;
import com.project.store.service.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "api/customer")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @PostMapping()
    ResponseEntity saveCustomer (@Valid @RequestBody CreateCustomerRequest createCustomerRequest){
        CustomerResponse customerResponse = customerService.saveCustomer(createCustomerRequest);
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping("/{id}")
    ResponseEntity getCustomer (@PathVariable Integer id){
        CustomerResponse customerResponse = customerService.getCustomer(id);
        return ResponseEntity.ok(customerResponse);
    }

}
