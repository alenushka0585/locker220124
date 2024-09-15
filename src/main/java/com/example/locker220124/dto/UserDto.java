package com.example.locker220124.dto;

import com.example.locker220124.entity.Authority;
import com.example.locker220124.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// реализуйте методы
// добавьте конструктор инициализирующий user
@AllArgsConstructor
public class UserDto  implements UserDetails {
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // "hello" -> new SimpleGrantedAuthority("hello")
        // замапьте группы в коллекцию SimpleGrantedAuthority и верните ее
        return user.getAuthorities().stream()
            .map(Authority::getName)
            .map(SimpleGrantedAuthority::new)
            .toList();


    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
