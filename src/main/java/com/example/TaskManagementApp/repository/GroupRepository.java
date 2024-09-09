package com.example.TaskManagementApp.repository;

import com.example.TaskManagementApp.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}
