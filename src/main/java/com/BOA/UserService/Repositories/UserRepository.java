package com.BOA.UserService.Repositories;




import com.BOA.UserService.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
