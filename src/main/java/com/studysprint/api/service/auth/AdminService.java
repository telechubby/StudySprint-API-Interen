package com.studysprint.api.service.auth;

import com.studysprint.api.model.auth.User;
import com.studysprint.api.model.auth.Role;
import com.studysprint.api.repository.RoleRepository;
import com.studysprint.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public User addUserRole(String username, String authority)
    {
        User user = userRepository.findByUsername(username).orElse(null);
        Role role = roleRepository.findByAuthority(authority).orElse(null);
        if(user == null || role == null)
            return null;
        Set<Role> roles = user.getAuthorities();
        if(!roles.contains(role))
            roles.add(role);
        user.setAuthorities(roles);
        return userRepository.save(user);
    }

    public User removeUserRole(String username, String authority)
    {
        User user = userRepository.findByUsername(username).orElse(null);
        Role role = roleRepository.findByAuthority(authority).orElse(null);
        if(user == null || role == null)
            return null;
        Set<Role> roles = user.getAuthorities();
        if(roles.contains(role))
            roles.remove(role);
        user.setAuthorities(roles);
        return userRepository.save(user);
    }
}
