package com.cbms.users.app.repository;

import com.cbms.users.app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
