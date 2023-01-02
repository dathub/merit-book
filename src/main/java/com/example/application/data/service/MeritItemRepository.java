package com.example.application.data.service;

import com.example.application.data.entity.MeritItem;
import com.example.application.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeritItemRepository extends JpaRepository<MeritItem, Long> {
}
