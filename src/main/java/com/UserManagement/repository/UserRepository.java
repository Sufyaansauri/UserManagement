package com.UserManagement.repository;
import com.UserManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

     User findUserById(Long id);

     User findByUsernameAndPassword(String username, String password);

     User findByUsername(String username);

     User findByEmail(String email);


}
