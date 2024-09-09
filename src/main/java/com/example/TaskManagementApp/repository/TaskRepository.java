package com.example.TaskManagementApp.repository;

import com.example.TaskManagementApp.entity.Task;
import com.example.TaskManagementApp.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("SELECT t FROM Task t WHERE (:category IS NULL OR t.category.name = :category)" +
            " AND (:userId is NULL OR :userId MEMBER OF t.assignedUsers)" +
            " AND (:status IS NULL OR t.status = :status)")
    List<Task> findTaskByCriteria(@Param("category") String category,
                                  @Param("userId") Integer userId,
                                  @Param("status") TaskStatus status);


    @Query("SELECT t FROM Task t WHERE (:categoryId IS NULL OR t.category.id = :categoryId)" +
            " AND (:userId IS NULL OR :userId MEMBER OF t.assignedUsers)" +
            " AND (:status IS NULL OR t.status = :status)")
    List<Task> findTasksByCategory(@Param("categoryId") Integer categoryId,
                                   @Param("userId") Integer userId,
                                   @Param("status") TaskStatus status);
}
