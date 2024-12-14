package com.example.controller;

import java.lang.annotation.Repeatable;

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

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account) {
        Account registerAccount = accountService.registerAccount(account);
        boolean isValidUsernamePassword = validateUsernamePassword(account.getUsername(), account.getPassword());
        
        if(registerAccount != null) {
            return ResponseEntity.status(200).body(registerAccount);
        } else if(accountService.searchUsername(account) != null) {
            return ResponseEntity.status(400).body("Username already already exists.")
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
