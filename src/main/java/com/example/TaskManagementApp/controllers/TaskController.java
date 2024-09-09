package com.example.TaskManagementApp.controllers;

import com.example.TaskManagementApp.Service.TaskService;
import com.example.TaskManagementApp.entity.Task;
import com.example.TaskManagementApp.exceptions.InvalidTaskStatusException;
import com.example.TaskManagementApp.exceptions.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;


    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task newTask = taskService.createTask(task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Integer id) {
        Task task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer id, @RequestBody Task task) {
        Task updateTask = taskService.updateTask(id, task);
        return new ResponseEntity<>(updateTask, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> getTasks(@RequestParam(required = false) String category,
                                      @RequestParam(required = false) Integer userId,
                                      @RequestParam(required = false) String status) {
        try {
            List<Task> tasks = taskService.getAllTasks(category, userId, status);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (InvalidTaskStatusException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }
}
