package com.example.TaskManagementApp.Service;

import com.example.TaskManagementApp.entity.Group;
import com.example.TaskManagementApp.entity.User;
import com.example.TaskManagementApp.exceptions.GroupNotFoundException;
import com.example.TaskManagementApp.exceptions.UserNotFoundException;
import com.example.TaskManagementApp.repository.GroupRepository;
import com.example.TaskManagementApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    public Group getGroupById(Integer id) {
        return groupRepository.findById(id).orElseThrow(() -> new GroupNotFoundException("Group not found with id " + id));
    }

    public Group updateGroup(Integer id, Group group) {
        Group existGroup = getGroupById(id);
        existGroup.setName(group.getName());
        existGroup.setMembers(group.getMembers());
        existGroup.setTasks(group.getTasks());
        return groupRepository.save(existGroup);
    }

    public void deleteGroup(Integer id) {
        if (!existsById(id)) {
            throw new GroupNotFoundException("Group not found with id " + id);
        }
        groupRepository.deleteById(id);
    }

    public Group addUserToGroup(Integer groupId, Integer userId) {
        Group group = getGroupById(groupId);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
        if (group.getMembers().contains(user)) {
            throw new IllegalArgumentException("User is a member of a group");
        } else {
            group.getMembers().add(user);
            return groupRepository.save(group);
        }
    }

    public Group removeUserFromGroup(Integer groupId, Integer userId) {
        Group group = getGroupById(groupId);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
        if (!group.getMembers().contains(user)) {
            throw new IllegalArgumentException("User is not a member of the group");
        } else {
            group.getMembers().remove(user);
            return groupRepository.save(group);
        }
    }

    public boolean existsById(Integer id) {
        return groupRepository.existsById(id);
    }
}
