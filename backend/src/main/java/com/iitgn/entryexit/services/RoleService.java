package com.iitgn.entryexit.services;

import com.iitgn.entryexit.entities.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role addRole(Role role);

    void deleteRole(Role role);
}
