package com.scout24.githubanalysis.domain;

import com.scout24.githubanalysis.dtos.AuthenticationProvider;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "loginId")
})
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String name;

    @Email
    private String email;

    private String image;

    private String location;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthenticationProvider provider;

    private String providerId;
}
