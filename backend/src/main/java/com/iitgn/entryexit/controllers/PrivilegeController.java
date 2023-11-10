package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.entities.Privilege;
import com.iitgn.entryexit.services.PrivilegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/privileges")
public class PrivilegeController {
    private final PrivilegeService privilegeService;

    @PreAuthorize("hasAuthority('PRIVILEGES_PRIVILEGE')")
    @GetMapping("/all")
    public List<Privilege> getAllPrivileges() {
        return privilegeService.getAllPrivileges();
    }
}
