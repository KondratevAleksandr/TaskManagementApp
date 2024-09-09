package com.example.TaskManagementApp.Service;

import com.example.TaskManagementApp.entity.Category;
import com.example.TaskManagementApp.entity.Task;
import com.example.TaskManagementApp.entity.TaskStatus;
import com.example.TaskManagementApp.exceptions.InvalidTaskStatusException;
import com.example.TaskManagementApp.exceptions.TaskNotFoundException;
import com.example.TaskManagementApp.repository.CategoryRepository;
import com.example.TaskManagementApp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task getTaskById(Integer id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }


    public Task updateTask(Integer id, Task task) {
        Task existingTask = getTaskById(id);
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        existingTask.setCategory(task.getCategory());
        existingTask.setAssignedUsers(task.getAssignedUsers());
        existingTask.setGroup(task.getGroup());
        existingTask.setCreationDate(task.getCreationDate());
        existingTask.setDueDate(task.getDueDate());
        return taskRepository.save(existingTask);

    }

    public List<Task> getAllTasks(String categoryName, Integer userId, String status) {
        TaskStatus taskStatus = null;
        if (status != null) {
            try {
                taskStatus = TaskStatus.valueOf(status);
            } catch (IllegalArgumentException ex) {
                throw new InvalidTaskStatusException("Invalid task status: " + status);
            }
        }

        if (categoryName != null) {
            Category category = categoryRepository.findByName(categoryName).orElse(null);
            if (category != null) {
                return taskRepository.findTasksByCategory(category.getId(), userId, taskStatus);
            }
        }
        return taskRepository.findTaskByCriteria(null, userId, taskStatus);
    }

    public void deleteTask(Integer id) {
        if (!existsById(id)) {
            throw new TaskNotFoundException("Task not found with id " + id);
        }
        taskRepository.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return taskRepository.existsById(id);
    }
}
