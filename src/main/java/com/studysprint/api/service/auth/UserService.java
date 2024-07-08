package com.studysprint.api.service.auth;

import com.studysprint.api.model.auth.User;
import com.studysprint.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User findByCode(String code){
        return userRepository.findByFriendCode(code).orElse(null);
    }

    public User addFriend(User user, User friend){
        List<User> userfriends = user.getFriends();
        List<User> friendfriends = friend.getFriends();
        if(!userfriends.contains(friend))
            userfriends.add(friend);
        friendfriends.add(user);
        if(!friendfriends.contains(user))
            user.setFriends(userfriends);
        friend.setFriends(friendfriends);
        userRepository.save(friend);
        return userRepository.save(user);
    }

    public User removeFriend(User user, User friend){
        List<User> userfriends = user.getFriends();
        List<User> friendfriends = friend.getFriends();
        userfriends.remove(friend);
        friendfriends.remove(user);
        user.setFriends(userfriends);
        friend.setFriends(friendfriends);
        userRepository.save(friend);
        return userRepository.save(user);
    }
}
