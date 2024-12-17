package com.BOA.UserService.Service.Interfaces;

import com.BOA.UserService.Models.User;

import java.util.List;

public interface UserService {

   User createUser(User user);

    List<User> getAllUsers();

    User updateUser(User user);

    boolean deleteUser(Long id);


}
