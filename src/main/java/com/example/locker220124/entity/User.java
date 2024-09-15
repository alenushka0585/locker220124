package com.example.locker220124.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.lang.annotation.Target;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String username;

    @NonNull
    @JsonIgnore
    private String password;

    @ToString.Exclude
    @OneToMany(
        mappedBy = "user",
        fetch = FetchType.EAGER
    )
    private Set<Authority> authorities = new HashSet<>();
}
