package com.project.SpringClean.service;

import com.project.SpringClean.model.Customer;
import com.project.SpringClean.model.Wallet;
import com.project.SpringClean.repository.CustomerRepository;
import com.project.SpringClean.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepo;

    public double checkBalance(Long customerId) {
        Wallet wallet = walletRepo.findByCustomer_CustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        return wallet.getBalance();
    }

    public void deductBalance(Long customerId, double amount) {
        Wallet wallet = walletRepo.findByCustomer_CustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        wallet.setBalance(wallet.getBalance() - amount);
        walletRepo.save(wallet);
    }
}


