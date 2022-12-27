package com.cbms.users.app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Collection;


@Data
@Entity
@Table(name = "tblroles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleId")
    private Long roleId;

    @Column(name = "roleName", unique = true)
    private String roleName;
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tblroleswithprivileges",
            joinColumns = @JoinColumn(name = "roleId", referencedColumnName = "roleId"),
            inverseJoinColumns = @JoinColumn(name = "privilegeId", referencedColumnName = "privilegeId")
    )
    @ToString.Exclude
    private Collection<Privilege> privileges;

}
