package com.jrosa.myAmb.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    //  If "required" is removed, in the signup page by inspect element in the browser,
    //  no error message and the user will NOT be saved to the database

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long fire_department_id;

    @Column(nullable = false)
    private Long internal_id;

    @Column(nullable = false)
    private String role = "ROLE_UNASSIGNED";
}