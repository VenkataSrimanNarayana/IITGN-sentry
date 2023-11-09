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

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;
    boolean alreadySetup = false;

    @Override
    @Transactional
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {

        if (alreadySetup)
            return;


        Privilege privilege1 = createPrivilegeIfNotFound("RAISE_PREQUEST_PRIVILEGE");
        Privilege privilege2 = createPrivilegeIfNotFound("READ_PREQUEST_PRIVILEGE");
        Privilege privilege3 = createPrivilegeIfNotFound("READ_USER_PREQUEST_PRIVILEGE");
        Privilege privilege4 = createPrivilegeIfNotFound("DELETE_USER_PREQUEST_PRIVILEGE");
        Privilege privilege5 = createPrivilegeIfNotFound("DELETE_PREQUEST_PRIVILEGE");

        Privilege privilege6 = createPrivilegeIfNotFound("ROOM_PRIVILEGE");

        Privilege privilege7 = createPrivilegeIfNotFound("READ_ACCOUNT_PRIVILEGE");
        Privilege privilege8 = createPrivilegeIfNotFound("READ_USER_ACCOUNT_PRIVILEGE");
        Privilege privilege9 = createPrivilegeIfNotFound("RESET_USER_PASSWORD_PRIVILEGE");
        Privilege privilege10 = createPrivilegeIfNotFound("CHANGE_USER_PASSWORD_PRIVILEGE");
        Privilege privilege11 = createPrivilegeIfNotFound("DELETE_ACCOUNT_PRIVILEGE");
        Privilege privilege12 = createPrivilegeIfNotFound("ROLE_UPDATE_PRIVILEGE");

        Privilege privilege13 = createPrivilegeIfNotFound("READ_LOG_PRIVILEGE");
        Privilege privilege14 = createPrivilegeIfNotFound("READ_USER_LOG_PRIVILEGE");
        Privilege privilege15 = createPrivilegeIfNotFound("LOG_PRIVILEGE");
        Privilege privilege16 = createPrivilegeIfNotFound("DELETE_LOG_PRIVILEGE");

        Privilege privilege17 = createPrivilegeIfNotFound("ROLES_PRIVILEGE");
        Privilege privilege18 = createPrivilegeIfNotFound("PRIVILEGES_PRIVILEGE");


        List<Privilege> userPrivileges = Arrays.asList(
                privilege1, privilege4, privilege3, privilege8, privilege9, privilege10, privilege14);

        List<Privilege> securityPrivileges = Arrays.asList(privilege2, privilege15, privilege13, privilege1,
                privilege4, privilege3, privilege8, privilege9, privilege10, privilege14);

        List<Privilege> adminPrivileges = Arrays.asList(
                privilege1, privilege2, privilege3, privilege4, privilege5, privilege6,
                privilege7, privilege8, privilege9, privilege10, privilege11, privilege12, privilege13, privilege14,
                privilege15, privilege16, privilege17, privilege18);


        createRoleIfNotFound("ROLE_ADMIN", new HashSet<>(adminPrivileges));
        createRoleIfNotFound("ROLE_USER", new HashSet<>(userPrivileges));
        createRoleIfNotFound("ROLE_SECURITY", new HashSet<>(securityPrivileges));

        Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");
        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
        Optional<Role> securityRole = roleRepository.findByName("ROLE_SECURITY");


        // Create Admin User
        Optional<User> user = userRepository.findById(20110067L);

        if (user.isEmpty()) {
            User user1 = User.builder().id(20110067)
                    .firstName("Zeeshan Snehil")
                    .lastName("Bhagat")
                    .password(passwordEncoder.encode("test"))
                    .userType("Student")
                    .area("IIT Gandhinagar")
                    .houseNo("H-102")
                    .country("India")
                    .landmark("IIT Gandhinagar")
                    .pinCode(382355)
                    .state("Gujarat")
                    .townCity("Gandhinagar")
                    .build();

            // Set Emails
            Email email = Email.builder().email("zeeshan.snehil@iitgn.ac.in").type("college").build();
            Set<Email> emails = new HashSet<>();
            emails.add(email);
            user1.setEmails(emails);

            // Set Contact Numbers
            ContactNumber contactNumber = ContactNumber.builder().phone("9434614611").type("personal").build();
            Set<ContactNumber> contactNumbers = new HashSet<>();
            contactNumbers.add(contactNumber);
            user1.setContactNumbers(contactNumbers);

            // Set User Roles
            if (adminRole.isEmpty()) {
                System.out.println("User Role is empty");
            } else {
                Role role = adminRole.get();
                user1.setRole(role);
                userRepository.save(user1);
            }
        }


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