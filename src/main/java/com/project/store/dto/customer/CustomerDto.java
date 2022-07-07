package com.project.store.dto.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CustomerDto {

    private Integer id;

    @NotEmpty(message = "customer.first.name.cannot.be.empty")
    private String firstName;

    @NotEmpty(message = "customer.last.name.cannot.be.empty")
    private String lastName;

    @NotEmpty(message = "customer.cnp.cannot.be.empty")
    @Size(min = 13, max = 13, message = "customer.cnp.must.have.13.digits")
    private String cnp;

    @NotEmpty(message = "customer.email.cannot.be.empty")
    @Email(regexp = ".+[@].+[\\.].+", message = "email.must.match.\'firstName.lastName@domain.com\'.format")
    private String email;

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
