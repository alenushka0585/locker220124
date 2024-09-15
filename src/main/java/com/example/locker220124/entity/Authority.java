package com.example.locker220124.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @NonNull
    @Column(unique = true)
    private String name;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = true
    )
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "id",
        nullable = true
    )
    private User user;
}
