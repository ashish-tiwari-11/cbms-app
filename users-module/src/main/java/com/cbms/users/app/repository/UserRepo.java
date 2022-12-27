package com.cbms.users.app.repository;

import com.cbms.users.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {


    Optional<User> findByUserEmail(String emailOrMobile);

    Optional<User> findByUserMobile(String emailOrMobile);
}
