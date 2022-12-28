package com.pack.machinestalk.repo;

import com.pack.machinestalk.model.UserMachinestalk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMachinestalkRepo extends JpaRepository<UserMachinestalk, Long> {
    List<UserMachinestalk> findAll();
    List<UserMachinestalk> findByGender(String gender);
    List<UserMachinestalk> findByStatus(String status);
}
