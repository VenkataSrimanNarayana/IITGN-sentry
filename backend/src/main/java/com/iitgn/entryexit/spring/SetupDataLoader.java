package com.iitgn.entryexit.spring;

import com.iitgn.entryexit.entities.*;
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

import java.util.*;

// READ_SINGLE_USER_PRIVILEGE
//
//READ_SELF_PRIVILEGE
//
//CHANGE_PASSWORD_PRIVILEGE
//
//DELETE_USER_PRIVILEGE
//
//READ_USERS_PRIVILEGE

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

        Privilege privilege1 = createPrivilegeIfNotFound("READ_SINGLE_USER_PRIVILEGE");
        Privilege privilege2 = createPrivilegeIfNotFound("READ_SELF_PRIVILEGE");
        Privilege privilege3 = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");
        Privilege privilege4 = createPrivilegeIfNotFound("DELETE_USER_PRIVILEGE");
        Privilege privilege5 = createPrivilegeIfNotFound("READ_USERS_PRIVILEGE");
        Privilege privilege6 = createPrivilegeIfNotFound("UPDATE_USER_PRIVILEGE");
        Privilege privilege7 = createPrivilegeIfNotFound("ROLE_UPDATE_PRIVILEGE");


        List<Privilege> userPrivileges = Arrays.asList(
                privilege2, privilege3, privilege6);

        List<Privilege> adminPrivileges = Arrays.asList(
                privilege1, privilege2, privilege3, privilege4, privilege5, privilege6, privilege7);


        createRoleIfNotFound("ROLE_ADMIN", new HashSet<>(adminPrivileges));
        createRoleIfNotFound("ROLE_USER", new HashSet<>(userPrivileges));

        Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");



        User user = User.builder().id(20110242)
                .firstName("Zeeshan Snehil")
                .lastName("Bhagat")
                .password(passwordEncoder.encode("test"))
                .userType("Student")
                .area("IIT Gandhinagar")
                .houseNo("H-102")
                .country("India")
                .landmark("IIT Gandhinagar")
                .pincode(382355)
                .state("Gujarat")
                .townCity("Gandhinagar")
                .build();


        // Set Emails
        Email email = Email.builder().email("zeeshan.snehil@iitgn.ac.in").type("college").build();
        Set<Email> emails = new HashSet<>();
        emails.add(email);
        user.setEmails(emails);


        // Set Room
        Room room = Room.builder().blockName("H").roomNo(102).build();
        user.setRoom(room);

        // Set Contact Numbers
        ContactNumber contactNumber = ContactNumber.builder().phone("9434614611").type("personal").build();
        Set<ContactNumber> contactNumbers = new HashSet<>();
        contactNumbers.add(contactNumber);
        user.setContactNumbers(contactNumbers);

        // Set Role
        user.setRole(adminRole.orElse(null));
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