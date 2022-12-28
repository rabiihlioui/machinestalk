package com.pack.machinestalk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pack.machinestalk.model.UserMachinestalk;
import com.pack.machinestalk.repo.UserMachinestalkRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMachinestalkServiceImpl implements UserMachinestalkService{
    private static final String URL = "https://gorest.co.in/public-api/users";

    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private UserMachinestalkRepo usersRepo;

    @Override
    public List<UserMachinestalk> findAllUsers() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String jsonString = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class).getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);
        JsonNode idNode = rootNode.path("data");
        /*System.out.println("testttt " + jsonString);
        System.out.println("idNode " + idNode);
        ResponseEntity<String> result = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);

        System.out.println("###########################################");
        System.out.println("Printing GET Response in String");
        System.out.println("###########################################");

        System.out.println(result.getBody());*/
        List<UserMachinestalk> userMachinestalkList = objectMapper.readValue(idNode.toString(), new TypeReference<List<UserMachinestalk>>() {});
        //System.out.println("studentList " + userMachinestalkList);
        usersRepo.saveAll(userMachinestalkList);
        return userMachinestalkList;
        //Object[] objs = restTemplate.getForObject("https://gorest.co.in/public-api/users", Object[].class);
        //System.out.println("Objs " + Arrays.asList(objs));
        //return Arrays.asList(objs);
    }

    public List<UserMachinestalk> findByGender(String type) {
        return usersRepo.findByGender(type);
    }

    public List<UserMachinestalk> findByStatus(String type) {
        return usersRepo.findByStatus(type);
    }
}
