package com.UserManagement.service.impl;
import com.UserManagement.entity.User;
import com.UserManagement.entity.UserPrincipal;
import com.UserManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements  UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");

        }

        return new UserPrincipal(user);
    }
}
