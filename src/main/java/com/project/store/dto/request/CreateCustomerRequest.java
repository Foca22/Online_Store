package com.project.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest {

    @NotEmpty(message = "customer.first.name.cannot.be.empty")
    private String firstName;

    @NotEmpty(message = "customer.last.name.cannot.be.empty")
    private String lastName;

    @NotEmpty(message = "customer.cnp.cannot.be.empty")
    private String cnp;

    @NotEmpty(message = "customer.email.cannot.be.empty")
    private String email;
}
