package com.example.controller;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import org.springframework.web.bind.annotation.PatchMapping;


import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<Account> registerUser(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
     String password = requestBody.get("password");

        Account registeredAccount = accountService.persistAccount(username, password);

        if (registeredAccount != null) {
            // Registration successful
            return new ResponseEntity<>(registeredAccount, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);

    }
    @PostMapping("/login")
    public ResponseEntity<Account> loginUser(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String password = requestBody.get("password"); 
        Account loggedInAccount = accountService.Login(username, password);
 
    if (loggedInAccount != null) {
        // Login successful
        return new ResponseEntity<>(loggedInAccount, HttpStatus.OK);
    }
    // Login failed
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
}
@PostMapping("/messages")
public ResponseEntity<Message> createMessage(@RequestBody Map<String, Object> requestBody) {
    
        
        Integer postedBy = (Integer) requestBody.get("posted_by");
        String messageText = (String) requestBody.get("message_text");
        Long timePostedEpoch = ((Number) requestBody.get("time_posted_epoch")).longValue();
        Message newMsg = messageService.creatMessage(postedBy,messageText,timePostedEpoch );
        if (newMsg != null) {
            return new ResponseEntity<>(newMsg, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("messages/{messageId}")
public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId) {
    int deleted = messageService.deleteMessageByMessageId(messageId);
    
    if (deleted > 0) {
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    } else {
        return new ResponseEntity<>( HttpStatus.OK);
    }
}

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> allMessages = messageService.getAllmessages();

        if (!allMessages.isEmpty()) {
            return new ResponseEntity<>(allMessages, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesForUser(@PathVariable Integer accountId) {
        List<Message> userMessages = messageService.getAllMessagesByAccountId(accountId);

        if (!userMessages.isEmpty()) {
            return new ResponseEntity<>(userMessages, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(userMessages,HttpStatus.OK);
        }
    }
    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
    Message message = messageService.getmessageById(messageId);

    if (message != null) {
        return new ResponseEntity<>(message, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
@PatchMapping("messages/{messageId}")
public ResponseEntity<Integer> updateMessage(@PathVariable Integer messageId, @RequestBody Map<String, String> requestBody) {
    String updatedText = requestBody.get("message_text");

    // Validate the input
    if (updatedText == null || updatedText.isEmpty() || updatedText.length() > 255) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Call your service method to update the message
    int rowsModified = messageService.updateMessage(messageId, updatedText);

    // Check the result and return the appropriate response
    if (rowsModified > 0) {
        return new ResponseEntity<>(rowsModified, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}


}