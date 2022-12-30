package com.pack.machinestalk.service.Implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pack.machinestalk.model.UserMachinestalk;
import com.pack.machinestalk.repo.UserMachinestalkRepo;
import com.pack.machinestalk.service.UserMachinestalkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserMachinestalkServiceImpl implements UserMachinestalkService {
    private static final String URL = "https://gorest.co.in/public-api/users";

    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private UserMachinestalkRepo usersRepo;

    @Override
    public Page<UserMachinestalk> getUsers(String gender, String status, int page, int size) throws JsonProcessingException, InterruptedException {
        getNbreTotalPagesFromUrl(gender, status, page, size);
        log.info("Fetching users for page {} of size {}", page, size);
        return (!gender.isEmpty() || !status.isEmpty()) ?
                usersRepo.findByGenderAndStatusContaining(gender, status, PageRequest.of(page, size)) :
                usersRepo.findAll(PageRequest.of(page, size));
    }

    @Override
    public List<UserMachinestalk> findAllUsersFromUrlAndSaveThem(String gender, String status, int page, int size) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode idNode = jsonNodeResult(gender, status, page, size, "data");
        List<UserMachinestalk> userMachinestalkList = objectMapper.readValue(idNode.toString(), new TypeReference<List<UserMachinestalk>>() {});
        usersRepo.saveAll(userMachinestalkList);
        return userMachinestalkList;
    }

    public JsonNode jsonNodeResult(String gender, String status, int page, int size, String rootNodePath) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String jsonString = restTemplate.exchange(URL + "?gender=" + gender + "&status=" + status + "&page=" + page,
                HttpMethod.GET, entity, String.class).getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);
        return rootNodePath.equals("meta") ?
                objectMapper.readTree((objectMapper.readTree(rootNode.path(rootNodePath).toString()).path("pagination")).path("pages").toString())
                : rootNode.path(rootNodePath);
    }

    @Override
    public int getNbreTotalPagesFromUrl(String gender, String status, int page, int size) throws JsonProcessingException {
        JsonNode idNode = jsonNodeResult(gender, status, page, size, "meta");
        return idNode.asInt();
    }

}
