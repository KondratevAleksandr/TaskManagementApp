package com.example.TaskManagementApp.controllers;

import com.example.TaskManagementApp.Service.GroupService;
import com.example.TaskManagementApp.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        Group newGroup = groupService.createGroup(group);
        return new ResponseEntity<>(newGroup, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroup(@PathVariable Integer id) {
        Group group = groupService.getGroupById(id);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable Integer id, @RequestBody Group group) {
        Group updateGroup = groupService.updateGroup(id, group);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Integer id) {
        groupService.deleteGroup(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{groupId}/addUser/{userId}")
    public ResponseEntity<Group> addUserToGroup(@PathVariable Integer groupId, @PathVariable Integer userId) {
        Group updateGroup = groupService.addUserToGroup(groupId, userId);
        return new ResponseEntity<>(updateGroup, HttpStatus.OK);
    }

    @GetMapping("/groups/{groupId}/users")
    public ResponseEntity<?> getAllUsersInGroup(@PathVariable Integer groupId) {
        Group group = groupService.getGroupById(groupId);
        return new ResponseEntity<>(group.getMembers(), HttpStatus.OK);

    }

    @PostMapping("/{groupId}/removeUser/{userId}")
    public ResponseEntity<Group> deleteUserFromGroup(@PathVariable Integer groupId, @PathVariable Integer userId) {
        Group updateGroup = groupService.removeUserFromGroup(groupId, userId);
        return new ResponseEntity<>(updateGroup, HttpStatus.OK);
    }
}
