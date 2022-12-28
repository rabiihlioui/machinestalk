package com.pack.machinestalk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pack.machinestalk.model.UserMachinestalk;

import java.util.List;

public interface UserMachinestalkService {
    List<UserMachinestalk> findAllUsers() throws JsonProcessingException;
    List<UserMachinestalk> findByGender(String type);
    List<UserMachinestalk> findByStatus(String type);
}
