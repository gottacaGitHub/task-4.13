package com.example.demo.controller;

import com.example.demo.dto.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class MessageController {

    private List<Message> messages = new ArrayList<>(Arrays.asList(
            new Message(1, "1", "Первое сообщение.", LocalDateTime.now()),
            new Message(2, "2", "Второе сообщение.", LocalDateTime.now())
    ));

    @PostMapping("/message")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        messages.add(message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/message")
    public Iterable<Message> getMessages() {
        return messages;
    }

    @GetMapping("/message/{id}")
    public ResponseEntity<Message> findMessageById(@PathVariable int id) {
        Optional<Message> message = messages.stream().filter(m -> m.getId() == id).findFirst();
        return message.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/message/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable int id, @RequestBody Message updatedMessage) {
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getId() == id) {
                messages.set(i, updatedMessage);
                return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/message/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable int id) {
        boolean removed = messages.removeIf(m -> m.getId() == id);
        return removed ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
