package com.cbms.users.app.utils;

import com.cbms.users.app.model.Privilege;
import com.cbms.users.app.model.Role;
import com.cbms.users.app.model.User;
import com.cbms.users.app.repository.PrivilegeRepo;
import com.cbms.users.app.repository.RoleRepo;
import com.cbms.users.app.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean isRolesAndPrivilegeAccountSetup = false;

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PrivilegeRepo privilegeRepo;

    private final PasswordEncoder passwordEncoder;

    public SetupDataLoader(UserRepo userRepo, RoleRepo roleRepo, PrivilegeRepo privilegeRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.privilegeRepo = privilegeRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (isRolesAndPrivilegeAccountSetup)
            return;

        Privilege readPrivilege = createPrivilegeIfNotFound("PRIVILEGE_READ");
        Privilege writePrivilege = createPrivilegeIfNotFound("PRIVILEGE_WRITE");

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_USER", Collections.singletonList(readPrivilege));
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);

        Role adminRole = roleRepo.findByRoleName("ROLE_ADMIN");
        Optional<User> adminUserOptional = userRepo.findByUserEmail("admin@cbms.com");
        if (Objects.nonNull(adminRole) && adminUserOptional.isEmpty()) {
            User user = new User();
            user.setUserFName("admin");
            user.setUserLName("admin");
            user.setUserPassword(passwordEncoder.encode("admin"));
            user.setUserEmail("admin@cbms.com");
            user.setUserMobile("9167072373");
            user.setRoles(List.of(adminRole));
            user.setUserAccountEnabled(true);
            user.setUserAccountNonLocked(true);
            user.setUserCredentialsNonExpired(true);
            user.setUserAccountNonExpired(true);
            userRepo.save(user);
        }
        isRolesAndPrivilegeAccountSetup = true;
    }

    @Transactional
    private void createRoleIfNotFound(String roleName, List<Privilege> privilegeList) {
        Role role = roleRepo.findByRoleName(roleName);
        if (Objects.isNull(role)) {
            role = new Role();
            role.setRoleName(roleName);
            role.setPrivileges(privilegeList);
            roleRepo.saveAndFlush(role);
        }
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(String privilegeName) {
        Privilege privilege = privilegeRepo.findByPrivilegeName(privilegeName);
        if (Objects.isNull(privilege)) {
            privilege = new Privilege();
            privilege.setPrivilegeName(privilegeName);
            return privilegeRepo.saveAndFlush(privilege);
        }
        return privilege;
    }
}
