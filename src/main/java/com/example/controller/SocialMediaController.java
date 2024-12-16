package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;

import com.example.service.MessageService;
import com.example.service.AccountService;


@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService) {
        this.accountService =  accountService;
    }
      
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account) {
        boolean isValidUsernamePassword = validateUsernamePassword(account.getUsername(), account.getPassword());

        if (isValidUsernamePassword) {
            Account usernameAccount = accountService.searchUsername(account.getUsername());

            if(usernameAccount != null) {
                return ResponseEntity.status(409).body("Username already already exists.");
            }
            else {
                Account registeredAccount = accountService.createAccount(account);
                if (registeredAccount == null) {
                    return ResponseEntity.status(400).body("");
                }
                return ResponseEntity.status(200).body(account); 
            }
        }
        return ResponseEntity.status(400).body("");
    }

    private boolean validateUsernamePassword(String username, String password) {
        final int passwordMinLength = 4;
        if (username == null || username.trim().isEmpty()) {
            return false;
        } else if (password == null || password.length() < passwordMinLength) {
            return false;
        }
        return true;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        Account loginAccount = accountService.searchUsernameAndPassword(account.getUsername(), account.getPassword());
        if(loginAccount != null) {
            return ResponseEntity.status(200).body(loginAccount);
        }
        return ResponseEntity.status(401).body("");
    }

    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        boolean isValidMessageText = validateMessage(message.getMessageText());
        if (isValidMessageText) {
            Message newMessage = messageService.createMessage(message);
            return ResponseEntity.status(200).body(newMessage);
        }
        return ResponseEntity.status(400).body("");
    }

    private boolean validateMessage(String text) {
        final int messageMaxLength = 255;

        if (text == null || text.trim().isEmpty()) {
            return false;
        } else if (text.length() > messageMaxLength) {

            return false;
        }
        return true;
    }
}
