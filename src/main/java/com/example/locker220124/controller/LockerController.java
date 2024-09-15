package com.example.locker220124.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LockerController {
    @GetMapping("/open")
    public String open() {
        return "open";
    }


    @PreAuthorize("hasRole('ROLE_USER')") // только те у кого есть роль нужная
    @GetMapping("/secure")
    public String secure() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("secure,  user: {}, group: {}", auth.getName(), auth.getAuthorities());
        return "secure";
    }

    //Создайте в контроллере новый метод music, принимающий строку и возвращающий эту строку в верхнем регистре
    //Разрешите выполение этого метода контроллера только членам группу ARTISTS
    //Сделайте так, чтобы вызов этого метода логировался в консоль приложения с уровнем INFO c помощью AOP в виде:
    // "метод пользователь группа вход выход"
    @PreAuthorize("hasRole('ROLE_ARTISTS')")
    @GetMapping("/music/{string}")
    public String music(
            @PathVariable String string){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("music,  user: {}, group: {}, login time: {}" , auth.getName(), auth.getAuthorities(), java.time.LocalDateTime.now());
        return string.toUpperCase();
    }
}
