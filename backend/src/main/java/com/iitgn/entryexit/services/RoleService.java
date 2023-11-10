package com.iitgn.entryexit.services;

import com.iitgn.entryexit.entities.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> findAll();

    Optional<Role> findByName(String name);

    Role addRole(Role role);

    void deleteRole(Role role);
}
