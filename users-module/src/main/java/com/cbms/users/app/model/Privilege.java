package com.cbms.users.app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Collection;

@Data
@Entity
@Table(name = "tblprivilege")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "privilegeId")
    private Long privilegeId;

    @Column(name = "privilegeName")
    private String privilegeName;

    @ManyToMany(mappedBy = "privileges")
    @ToString.Exclude
    private Collection<Role> roles;

}
