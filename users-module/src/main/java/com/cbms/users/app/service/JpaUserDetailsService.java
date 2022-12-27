package com.cbms.users.app.service;

import com.cbms.users.app.model.User;
import com.cbms.users.app.repository.RoleRepo;
import com.cbms.users.app.repository.UserRepo;
import com.cbms.users.app.utils.JpaSecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public JpaUserDetailsService(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserDetails loadUserByUsername(String emailOrMobile) throws UsernameNotFoundException {
        Optional<User> userOptional = null;
        if (Objects.nonNull(emailOrMobile)) {
            if (emailOrMobile.contains("@"))
                userOptional = userRepo.findByUserEmail(emailOrMobile);
            else
                userOptional = userRepo.findByUserMobile(emailOrMobile);

            return userOptional
                    .map(user -> new JpaSecurityUser(user,roleRepo))
                    .orElseThrow(() -> new UsernameNotFoundException("Email Or Mobile number provided not found"));
        }
        return null;
    }
}
