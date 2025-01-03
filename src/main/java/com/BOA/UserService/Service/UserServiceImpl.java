package com.BOA.UserService.Service;

import com.BOA.UserService.Models.User;
import com.BOA.UserService.Repositories.UserRepository;
import com.BOA.UserService.Service.Interfaces.UserService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    public final UserRepository userRepository;

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User createUser(User user) {
        if (user.getUsername() == null || user.getEmail() == null) {
            throw new IllegalArgumentException("Username and email are required");
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User updateUser(User user) {

        // Check if user exists in the repository
        if (!userRepository.existsById(user.getUserId())) {
            logger.error("User with ID {} not found for update", user.getUserId());
            return null;
        }

        // Update user and log
        logger.info("Updating user: {}", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            logger.error("User with ID {} not found", id);
            return null;
        }
    }

    @Override
    @Transactional
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            logger.error("User with ID {} not found for deletion", id);
            return false;  // User doesn't exist
        }
        userRepository.deleteById(id);
        logger.info("Deleted user with ID: {}", id);
        return true;
    }
}
