package com.cbms.users.app.repository;

import com.cbms.users.app.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepo extends JpaRepository<Privilege, Long> {

    Privilege findByPrivilegeName(String privilegeName);
}
