package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;

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
                    return ResponseEntity.status(400).body("Client error.");
                }
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
