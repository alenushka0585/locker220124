package com.example.locker220124.config;

import com.example.locker220124.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration // компонент Spring Boot предоставляющий зависимости
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    // так как подключен Spring Boot Security
    // у него уже есть конфигурация безопасности по-умолчанию
    // источник данных о пользователях - UserDetailsService
    // метод проверки паролей - PasswordEncoder
    // описание как проверять доступ к методам контроллеров, сессии

    @Bean // можно будет использовать зависимость возвращаемую из этого метода
    public PasswordEncoder getEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // UserDetailsService - это источник информации о пользователях
    @Autowired
    private UserService userService;

    @Bean
    public AuthenticationProvider getProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(getEncoder());
        provider.setUserDetailsService(userService);
        return provider;
    }

    @Bean
    public SecurityFilterChain getChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(
                auth -> auth
                    .requestMatchers(toH2Console()).permitAll()
                    .requestMatchers("/open").permitAll()
                    .requestMatchers("/super").hasRole("ADMIN")
                    .anyRequest().authenticated()
            )
            .formLogin()
            .and()
            .csrf()
            .disable()
            .headers().frameOptions().disable()
            .and()
            .httpBasic(Customizer.withDefaults())
            .logout()
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
        ;


        return http.build();
    }
}
