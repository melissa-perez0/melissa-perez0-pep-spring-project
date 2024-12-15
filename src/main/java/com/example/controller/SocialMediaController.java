package com.example.controller;

import java.lang.annotation.Repeatable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;

import com.example.service.MessageService;
import com.example.service.AccountService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService =  accountService;
        this.messageService = messageService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account) {
        // Check first to see if username and password are valid
        boolean isValidUsernamePassword = validateUsernamePassword(account.getUsername(), account.getPassword());

        if (isValidUsernamePassword) {
            Account usernameAccount = accountService.searchUsername(account.getUsername());

            if(usernameAccount != null) {
                return ResponseEntity.status(409).body("Username already already exists.")
            }
            else {
                accountService.createAccount(account);
                return ResponseEntity.status(200).body(account); 
            }
        }
        return ResponseEntity.status(400).body("Client error.");
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

}
