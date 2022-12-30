package com.pack.machinestalk.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pack.machinestalk.model.HttpResponse;
import com.pack.machinestalk.model.UserMachinestalk;
import com.pack.machinestalk.service.Implementation.UserMachinestalkServiceImpl;
import com.pack.machinestalk.service.UserMachinestalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users").toUriString());
        return ResponseEntity.created(uri).body(userMachinestalkService.findAllUsers());
    }

    /*@GetMapping("/users/gender/{type}")
    public ResponseEntity<List<UserMachinestalk>> getUsersByGender(@PathVariable String type) throws JsonProcessingException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/gender/" + type).toUriString());
        return ResponseEntity.created(uri).body(userMachinestalkServiceImpl.findByGender(type));
    }

    @GetMapping("/users/status/{type}")
    public ResponseEntity<List<UserMachinestalk>> getUsersByStatus(@PathVariable String type) throws JsonProcessingException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/status/" + type).toUriString());
        return ResponseEntity.created(uri).body(userMachinestalkServiceImpl.findByStatus(type));
    }*/

    @GetMapping("/userstest")
    public ResponseEntity<HttpResponse> getUsers(@RequestParam Optional<String> gender,
                                                 @RequestParam Optional<String> status,
                                                 @RequestParam Optional<Integer> page,
                                                 @RequestParam Optional<Integer> size) throws InterruptedException, JsonProcessingException {
        //TimeUnit.SECONDS.sleep(3); // just a delay to see the spinner in the LOADING phase
        //throw new RuntimeException("Forced exception for testing");
        System.out.println("gender " + gender + "status " + status + "page " + page + "size " + size);
        //System.out.println("testttt " + userMachinestalkService.getUsers(gender.orElse(""), status.orElse(""), page.orElse(0), size.orElse(10)));
        return ResponseEntity.ok().body(
                HttpResponse.builder().timestamp(LocalDateTime.now().toString())
                        .data(Map.of("page", userMachinestalkService.getUsers(gender.orElse(""), status.orElse(""), page.orElse(0), size.orElse(10))))
                        .message("Users Retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
}
