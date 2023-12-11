package com.example.service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Transactional
    public Account persistAccount(String username, String password) {
        // Check if the username already exists
        Optional<Account> existingAccount = accountRepository.findByUsername(username);
        if (existingAccount.isPresent()) {
            
            return null;
        }

        // Create a new account
        Account newAccount = new Account(username, password);

        // Save the new account
        return accountRepository.save(newAccount);
    }
    public Account Login(String username, String password) {
        
        Optional<Account> existingAccount = accountRepository.findByUsernameAndPassword(username,password);
        if (existingAccount.isPresent()) {
            
            return existingAccount.get();
        }

        return null;
    }


}
