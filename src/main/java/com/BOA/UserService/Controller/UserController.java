package com.BOA.UserService.Controller;




import com.BOA.UserService.Models.User;
import com.BOA.UserService.Service.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserServiceImpl userServiceImpl;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {

        return new ResponseEntity<>(userServiceImpl.createUser(user), HttpStatus.CREATED);
        //User createdUser = userServiceImpl.createUser(user);
        //return ResponseEntity.status(201).body(createdUser);
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getStudent(){
        return new ResponseEntity<>(userServiceImpl.getAllUsers(), HttpStatus.OK);
    }


    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setUserId(id);
        User updatedUser = userServiceImpl.updateUser(user);
        return ResponseEntity.ok(updatedUser);

    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {  // Use @PathVariable to bind id from URL
        boolean isDeleted = userServiceImpl.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("User not found.");
        }
    }

}
