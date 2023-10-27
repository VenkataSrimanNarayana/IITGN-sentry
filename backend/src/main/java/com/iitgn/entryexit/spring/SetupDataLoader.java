package com.iitgn.entryexit.spring;

import com.iitgn.entryexit.entities.Privilege;
import com.iitgn.entryexit.repositories.PrivilegeRepository;
import com.iitgn.entryexit.repositories.RoleRepository;
import com.iitgn.entryexit.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.iitgn.entryexit.entities.Role;
import com.iitgn.entryexit.entities.User;

import java.util.*;


@Component
@RequiredArgsConstructor
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PrivilegeRepository privilegeRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(@NonNull  ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        Privilege updatePrivilege
                = createPrivilegeIfNotFound("UPDATE_PRIVILEGE");
        Privilege deletePrivilege
                = createPrivilegeIfNotFound("DELETE_PRIVILEGE");
        Privilege managePrivilege
                = createPrivilegeIfNotFound("MANAGE_PRIVILEGE");


        List<Privilege> userPrivileges = Arrays.asList(
                readPrivilege, deletePrivilege, updatePrivilege);

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege, updatePrivilege, deletePrivilege, managePrivilege);


        createRoleIfNotFound("ROLE_ADMIN", new HashSet<>(adminPrivileges));
        createRoleIfNotFound("ROLE_USER", new HashSet<>(userPrivileges));

        Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setId(20110242);
        user.setName("Zeeshan Snehil Bhagat");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("test@iitgn.ac.in");
        user.setUserType("Student");
        adminRole.ifPresent(user::setRole);
        userRepository.save(user);
        alreadySetup = true;
    }

    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {

        Optional<Privilege> privilegeTemp = privilegeRepository.findByName(name);

        return privilegeTemp.orElseGet(() -> {
            Privilege privilege1 = Privilege.builder().name(name).build();
            privilegeRepository.save(privilege1);
            return privilege1;
        });
    }

    @Transactional
    public void createRoleIfNotFound(
            String name, Set<Privilege> privileges) {
        Optional<Role> roleTemp = roleRepository.findByName(name);

        roleTemp.orElseGet(() -> {
            Role role = Role.builder().name(name).build();
            role.setPrivileges(privileges);
            roleRepository.save(role);
            return role;
        });
    }
}