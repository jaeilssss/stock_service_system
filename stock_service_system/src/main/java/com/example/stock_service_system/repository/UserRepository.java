package com.example.stock_service_system.repository;

import com.example.stock_service_system.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo,Long> {

    Optional<UserInfo> findById(String id);
}
