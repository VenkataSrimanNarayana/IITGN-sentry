package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.entities.Role;
import com.iitgn.entryexit.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    @PreAuthorize("hasAuthority('ROLES_PRIVILEGE')")
    public List<Role> findAll() {
        return roleService.findAll();
    }
}
