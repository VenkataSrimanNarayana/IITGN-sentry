package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.entities.Privilege;
import com.iitgn.entryexit.entities.Role;
import com.iitgn.entryexit.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
@PreAuthorize("hasAuthority('ROLES_PRIVILEGE')")
public class RoleController {
    private final RoleService roleService;


    @GetMapping("/all")
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @PostMapping("/add")
    public Role addRole(Role role, Set<Privilege> privileges) {
        role.setPrivileges(privileges);
        return roleService.addRole(role);
    }

    @PostMapping("/delete")
    public void deleteRole(Role role) {
        roleService.deleteRole(role);
    }

}
