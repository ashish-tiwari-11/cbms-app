package com.cbms.users.app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Collection;

@Entity
@Table(name = "tbluser")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private long userId;

    @Column(name = "userFName",nullable = false)
    private String userFName;

    @Column(name = "userLName", nullable = false)
    private String userLName;

    @Column(name = "userEmail", unique = true,nullable = false)
    private String userEmail;
    @Column(name = "userMobile", unique = true, nullable = false)
    private String userMobile;

    @Column(name = "userPassword", nullable = false)
    private String userPassword;

    @Column(name = "userAccountNonExpired")
    private boolean userAccountNonExpired;

    @Column(name = "userAccountNonLocked")
    private boolean userAccountNonLocked;

    @Column(name = "userCredentialsNonExpired")
    private boolean userCredentialsNonExpired;

    @Column(name = "userAccountEnabled")
    private boolean userAccountEnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tbluserwithroles",
            joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    )
    @ToString.Exclude
    private Collection<Role> roles;
}
