package com.lpras.jmsamq.controller;

import com.lpras.jmsamq.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {


    @Autowired
    private MessageService messageService;

    @GetMapping("message/{message}")
    public ResponseEntity<String> publish(@PathVariable("message") final String message) {
        String str = messageService.publishMessage(message);
        return new ResponseEntity("Email sent body: "+str, HttpStatus.OK);
    }
}
