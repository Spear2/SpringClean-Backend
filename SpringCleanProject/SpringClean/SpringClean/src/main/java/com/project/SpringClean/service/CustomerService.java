package com.project.SpringClean.service;

import com.project.SpringClean.dto.CustomerUpdateRequest;
import com.project.SpringClean.model.CompanyCleaner;
import com.project.SpringClean.model.Customer;
import com.project.SpringClean.model.Wallet;
import com.project.SpringClean.repository.CustomerRepository;
import com.project.SpringClean.repository.WalletRepository;
import com.project.SpringClean.serviceinterface.CustomerServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements CustomerServiceInt {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public Customer registerCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        // Save customer first
        Customer saved = customerRepository.save(customer);

        // Create wallet AFTER customer exists
        Wallet wallet = new Wallet();
        wallet.setBalance(1000000);
        wallet.setCustomer(saved);

        saved.setWallet(wallet);
        return customerRepository.save(customer);
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
