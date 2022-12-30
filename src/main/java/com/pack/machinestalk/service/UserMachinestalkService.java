package com.pack.machinestalk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pack.machinestalk.model.UserMachinestalk;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserMachinestalkService {
    List<UserMachinestalk> findAllUsers() throws JsonProcessingException;
    List<UserMachinestalk> findByGender(String type);
    List<UserMachinestalk> findByStatus(String type);

    Page<UserMachinestalk> getUsers(String gender, String status, int page, int size) throws JsonProcessingException, InterruptedException;

    public List<UserMachinestalk> findAllUsersFromUrlAndSaveThem(String gender, String status, int page, int size) throws JsonProcessingException;
}
