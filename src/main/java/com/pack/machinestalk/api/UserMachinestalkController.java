package com.pack.machinestalk.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pack.machinestalk.model.UserMachinestalk;
import com.pack.machinestalk.service.UserMachinestalkServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserMachinestalkController {
    @Autowired
    private UserMachinestalkServiceImpl userMachinestalkServiceImpl;

    @GetMapping("/home")
    public String home() {
        return "Home";
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserMachinestalk>> getUsers() throws JsonProcessingException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users").toUriString());
        return ResponseEntity.created(uri).body(userMachinestalkServiceImpl.findAllUsers());
    }

    @GetMapping("/users/gender/{type}")
    public ResponseEntity<List<UserMachinestalk>> getUsersByGender(@PathVariable String type) throws JsonProcessingException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/gender/" + type).toUriString());
        return ResponseEntity.created(uri).body(userMachinestalkServiceImpl.findByGender(type));
    }

    @GetMapping("/users/status/{type}")
    public ResponseEntity<List<UserMachinestalk>> getUsersByStatus(@PathVariable String type) throws JsonProcessingException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/status/" + type).toUriString());
        return ResponseEntity.created(uri).body(userMachinestalkServiceImpl.findByStatus(type));
    }
}
