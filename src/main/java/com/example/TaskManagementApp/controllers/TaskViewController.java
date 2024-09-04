package com.example.TaskManagementApp.controllers;

import com.example.TaskManagementApp.Service.TaskService;
import com.example.TaskManagementApp.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskViewController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/list")
    public String listTasks(Model model) {
        List<Task> tasks = (List<Task>) taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }
}
