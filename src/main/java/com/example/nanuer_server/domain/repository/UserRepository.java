package com.example.nanuer_server.domain.repository;

import com.example.nanuer_server.domain.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface  UserRepository extends JpaRepository<UserEntity,Integer> {
     Optional<UserEntity> findByUserId(int userId);
     Optional<UserEntity> findByEmail(String email);
     Optional<UserEntity> findByPhone(String phone);

     List<UserEntity> findAll();
}
