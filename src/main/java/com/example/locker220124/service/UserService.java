package com.example.locker220124.service;

import com.example.locker220124.dto.UserDto;
import com.example.locker220124.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // получить из репо пользователя по имени
        // замапить его в UserDto и вернуть
        // выбросить исключение если пользователя с таким именем нет в репо
        return userRepository.getUserByUsername(username).map(UserDto::new).orElseThrow(
            () -> new UsernameNotFoundException("User with username " + username + " not found")
        );
    }
}
