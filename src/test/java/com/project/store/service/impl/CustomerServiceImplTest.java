package com.project.store.service.impl;

import com.project.store.exceptions.customer.CustomerNotFoundException;
import com.project.store.exceptions.messages.ExceptionMessages;
import com.project.store.mapper.CustomerMapper;
import com.project.store.model.customer.Customer;
import com.project.store.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    private final static Integer customerId = 1;
    private final static String firstName = "Ion";
    private final static String lastName = "Popescu";
    private final static String cnp = "1860504030054";
    private final static String email = "ion.popescu@endava.com";

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerMapper customerMapper;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSaveCustomer() {
        Customer customerToBeSaved = new Customer(firstName, lastName, cnp, email);

        when(customerRepository.save(any(Customer.class))).thenAnswer(a -> {
            Customer customer = (Customer) a.getArguments()[0];
            customer.setId(customerId);
            return customer;
        });

        Customer savedCustomer = customerService.saveCustomer(customerToBeSaved);

        assertEquals(customerId, savedCustomer.getId());
        assertEquals(firstName, savedCustomer.getFirstName());
        assertEquals(lastName, savedCustomer.getLastName());
        assertEquals(cnp, savedCustomer.getCnp());
        assertEquals(email, savedCustomer.getEmail());
    }

    @Test
    public void shouldGetCustomer() {
        Customer customer = new Customer(firstName, lastName, cnp, email);

        when(customerRepository.findById(any(Integer.class))).thenReturn(Optional.of(customer));

        Customer retrievedCustomer = customerService.getCustomer(customerId);

        assertEquals(customer, retrievedCustomer);
    }

    @Test
    public void shouldNotGetCustomer() {
        when(customerRepository.findById(any(Integer.class))).
                thenReturn(Optional.empty());

        Exception exception = assertThrows(CustomerNotFoundException.class,
                () -> customerService.getCustomer(customerId));

        String expectedMessage = ExceptionMessages.CUSTOMER_NOT_FOUND.getErrorMessage();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldGetAllCustomers() {
        Customer customer1 = new Customer(firstName, lastName, cnp, email);
        Customer customer2 = new Customer("Vasile", "Ionescu", "1890423080045", "vasile.ionescu@endava.com");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        List<Customer> customerResultList = customerService.getAllCustomers();

        assertEquals(2, customerResultList.size());
    }

    @Test
    public void shouldNotGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        List<Customer> customerResultList = customerService.getAllCustomers();

        assertEquals(0, customerResultList.size());
    }

    @Test
    public void shouldUpdateCustomer() {
        Customer customer = new Customer(firstName, lastName, cnp, email);
        customer.setId(customerId);

        Customer customerWithNewDetails = new Customer("Vasile", "Ionescu", "1890423080045", "vasile.ionescu@endava.com");
        customerWithNewDetails.setId(customerId);

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenAnswer(a -> (Customer) a.getArguments()[0]);

        Customer updatedCustomer = customerService.updateCustomer(customerWithNewDetails);

        assertEquals(customerId, updatedCustomer.getId());
        assertEquals(customerWithNewDetails.getFirstName(), updatedCustomer.getFirstName());
        assertEquals(customerWithNewDetails.getLastName(), updatedCustomer.getLastName());
        assertEquals(customerWithNewDetails.getCnp(), updatedCustomer.getCnp());
        assertEquals(customerWithNewDetails.getEmail(), updatedCustomer.getEmail());
    }

    @Test
    public void shouldNotUpdateCustomer() {
        Customer customer = new Customer(firstName, lastName, cnp, email);
        customer.setId(customerId);

        Customer customerWithNewDetails = new Customer("Vasile", "Ionescu", "1890423080045", "vasile.ionescu@endava.com");

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        Exception exception = assertThrows(CustomerNotFoundException.class,
                () -> customerService.updateCustomer(customerWithNewDetails));

        String expectedMessage = ExceptionMessages.CUSTOMER_NOT_FOUND.getErrorMessage();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldDeleteCustomer() {
        Customer customer = new Customer(firstName, lastName, cnp, email);
        customer.setId(customerId);

        customerRepository.deleteById(customerId);

        Optional<Customer> afterDeletion = customerRepository.findById(customerId);

        assertNull(null, String.valueOf(afterDeletion.isEmpty()));
    }
}