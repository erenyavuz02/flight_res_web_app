package com.hitit.project.microservices.user_app.entity;




import org.springframework.validation.annotation.Validated;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "u_id")
@ToString
@Data
@Entity
@Table(name = "user")
@Validated

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long u_id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Username cannot be null")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Password cannot be null")
    private String password;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email cannot be null")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Name cannot be null")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Surname cannot be null")
    private String surname;


}
