package com.example.TaskManagementApp.Service;

import com.example.TaskManagementApp.entity.User;
import com.example.TaskManagementApp.exceptions.UserNotFoundException;
import com.example.TaskManagementApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void changePassword(Integer userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void resetPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String temporaryPassword = generateTemporaryPassword();
        user.setPassword(passwordEncoder.encode(temporaryPassword));
        userRepository.save(user);

        sendPasswordResetEmail(email, temporaryPassword);
    }

    private String generateTemporaryPassword() {
//        SecureRandom random = new SecureRandom();
//        byte[] bytes = new byte[12];
//        random.nextBytes(bytes);
//        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    private void sendPasswordResetEmail(String email, String temporaryPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("Your temporary password is: " + temporaryPassword);
        mailSender.send(message);
    }
}
