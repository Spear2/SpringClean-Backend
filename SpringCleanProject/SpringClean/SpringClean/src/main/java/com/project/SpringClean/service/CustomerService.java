package com.project.SpringClean.service;

import com.project.SpringClean.dto.CustomerUpdateRequest;
import com.project.SpringClean.model.CompanyCleaner;
import com.project.SpringClean.model.Customer;
import com.project.SpringClean.repository.CustomerRepository;
import com.project.SpringClean.serviceinterface.CustomerServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements CustomerServiceInt {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public Customer registerCustomer(Customer Customer) {
        if (customerRepository.existsByEmail(Customer.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        return customerRepository.save(Customer);
    }

    @Override
    public Customer login(String email, String password) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));

        if(email == null || password == null) {
            throw new RuntimeException("Fields Should not be empty");
        }

        if (!customer.getPassword().equals(password)) {
            throw new RuntimeException("Incorrect password");
        }

        return customer;
    }

    public Customer updateCustomer(Long customerId, CustomerUpdateRequest request){
        Customer customer =  customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());

        return customerRepository.save(customer);

    }
}
