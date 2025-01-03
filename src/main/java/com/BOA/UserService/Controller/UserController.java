package com.BOA.UserService.Controller;
import com.BOA.UserService.Models.User;
import com.BOA.UserService.Service.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")  // Change "/user" to "/users" for consistency
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private static final Logger logger = LogManager.getLogger(UserController.class);

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("Creating user: {}", user);
        User createdUser = userServiceImpl.createUser(user);
        logger.info("User created with ID: {}", createdUser.getUserId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getUserId())
                .toUri();

        return ResponseEntity.created(location).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userServiceImpl.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body(null);  // Not Found
        }
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userServiceImpl.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setUserId(id);
        logger.info("Updating user with ID: {}", id);
        User updatedUser = userServiceImpl.updateUser(user);
        if (updatedUser == null) {
            logger.warn("User with ID: {} not found for update.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        logger.info("User with ID: {} updated successfully.", id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        logger.info("Attempting to delete user with ID: {}", id);
        boolean isDeleted = userServiceImpl.deleteUser(id);
        if (isDeleted) {
            logger.info("User with ID: {} deleted successfully.", id);
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            logger.warn("User with ID: {} not found for deletion.", id);
            return ResponseEntity.status(404).body("User not found.");
        }
    }
}
