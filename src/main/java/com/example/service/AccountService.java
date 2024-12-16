package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account){
        return accountRepository.save(account);
    }

    public Account searchUsername(String username){
        Account searchedAccount = accountRepository.findByUsername(username);
        return searchedAccount != null ? searchedAccount : null; 
    }

    public Account searchUsernameAndPassword(String username, String password) {
        Account searchedAccount = accountRepository.findByUsernameAndPassword(username, password);
        return searchedAccount != null ? searchedAccount : null; 
    }

    public Account searchByAccountId(int postedBy) {
        Account searchedAccount = accountRepository.findByAccountId(postedBy);
        return searchedAccount != null ? searchedAccount : null; 
    }
}
