package com.pack.machinestalk.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pack.machinestalk.model.UserMachinestalk;
import com.pack.machinestalk.repo.UserMachinestalkRepo;
import com.pack.machinestalk.service.UserMachinestalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserMachinestalkController {
    @Autowired
    private UserMachinestalkService userMachinestalkService;

    @GetMapping("/home")
    public String home() {
        return "Home";
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserMachinestalk>> getUsers() throws JsonProcessingException {
        return ResponseEntity.ok(userMachinestalkService.findAllUsers());
    }

    @GetMapping("/users/gender/{type}")
    public ResponseEntity<List<UserMachinestalk>> getUsersByGender(@PathVariable String type) throws JsonProcessingException {
        return ResponseEntity.ok(userMachinestalkService.findByGender(type));
    }

    @GetMapping("/users/status/{type}")
    public ResponseEntity<List<UserMachinestalk>> getUsersByStatus(@PathVariable String type) throws JsonProcessingException {
        return ResponseEntity.ok(userMachinestalkService.findByStatus(type));
    }
}
