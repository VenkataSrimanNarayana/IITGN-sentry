package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.entities.Privilege;
import com.iitgn.entryexit.entities.Role;
import com.iitgn.entryexit.models.requestdto.AddRoleRequestDto;
import com.iitgn.entryexit.models.responses.SingleLineResponse;
import com.iitgn.entryexit.services.PrivilegeService;
import com.iitgn.entryexit.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
@PreAuthorize("hasAuthority('ROLES_PRIVILEGE')")
public class RoleController {
    private final RoleService roleService;
    private final PrivilegeService privilegeService;


    @GetMapping("/all")
    public ResponseEntity<List<Role>> findAll() {
        if (roleService.findAll().isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<SingleLineResponse> addRole(@RequestBody AddRoleRequestDto roleRequest) {
        if(roleService.findByName(roleRequest.getRoleName()).isPresent()){
            return new ResponseEntity<>(new SingleLineResponse("Role already exists"), HttpStatus.BAD_REQUEST);
        }else if (roleRequest.getPrivilegeIds().isEmpty()){
            return new ResponseEntity<>(new SingleLineResponse("Privileges cannot be empty"), HttpStatus.BAD_REQUEST);
        }else if (roleRequest.getRoleName().isEmpty()){
            return new ResponseEntity<>(new SingleLineResponse("Role name cannot be empty"), HttpStatus.BAD_REQUEST);
        }else{
            Role role = Role.builder().name("ROLE_"+roleRequest.getRoleName()).build();
            List<Privilege> privileges = privilegeService.getPrivileges(roleRequest.getPrivilegeIds());
            Set<Privilege> privilegeSet = Set.copyOf(privileges);
            role.setPrivileges(privilegeSet);
            roleService.addRole(role);
            return new ResponseEntity<>(new SingleLineResponse("Role added successfully"), HttpStatus.OK);
        }
    }

    @PostMapping("/delete")
    public void deleteRole(Role role) {
        roleService.deleteRole(role);
    }

    @PostMapping("/update")
    public ResponseEntity<SingleLineResponse> updateRole(@RequestBody AddRoleRequestDto addRoleRequestDto){
        if(roleService.findByName(addRoleRequestDto.getRoleName()).isPresent()){
            Role role = roleService.findByName(addRoleRequestDto.getRoleName()).get();
            List<Privilege> privileges = privilegeService.getPrivileges(addRoleRequestDto.getPrivilegeIds());
            Set<Privilege> privilegeSet = Set.copyOf(privileges);
            role.setPrivileges(privilegeSet);
            roleService.addRole(role);
            return new ResponseEntity<>(new SingleLineResponse("Role updated successfully"), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new SingleLineResponse("Role does not exist"), HttpStatus.BAD_REQUEST);
        }
    }
}
