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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
            Set<Privilege> privilegeSet = new HashSet<>(privileges);
            role.setPrivileges(privilegeSet);
            roleService.saveRole(role);
            return new ResponseEntity<>(new SingleLineResponse("Role added successfully"), HttpStatus.OK);
        }
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SingleLineResponse> deleteRole(@PathVariable int id) {
        Optional<Role> role = roleService.findById(id);



        if (role.isEmpty())
            return new ResponseEntity<>(new SingleLineResponse("Role not found"), HttpStatus.NOT_FOUND);

        if(role.get().getName().equals("ROLE_ADMIN")){
            return new ResponseEntity<>(new SingleLineResponse("Cannot delete admin role"), HttpStatus.BAD_REQUEST);
        }

        if(role.get().getName().equals("ROLE_USER")){
            return new ResponseEntity<>(new SingleLineResponse("Cannot delete user role"), HttpStatus.BAD_REQUEST);
        }

        role.ifPresent(roleService::deleteRole);
        return new ResponseEntity<>(new SingleLineResponse("Role deleted successfully"), HttpStatus.OK);
    }


    @PostMapping("/update")
    public ResponseEntity<SingleLineResponse> updateRole(@RequestBody AddRoleRequestDto addRoleRequestDto){
        if(addRoleRequestDto.getRoleName().isEmpty()){
            return new ResponseEntity<>(new SingleLineResponse("Role name cannot be empty"), HttpStatus.BAD_REQUEST);
        }else if (addRoleRequestDto.getPrivilegeIds().isEmpty()){
            return new ResponseEntity<>(new SingleLineResponse("Privileges cannot be empty"), HttpStatus.BAD_REQUEST);
        }

        if (roleService.findAll().isEmpty())
            return new ResponseEntity<>(new SingleLineResponse("Role not found"), HttpStatus.NOT_FOUND);

        if (addRoleRequestDto.getRoleName().equals("ADMIN")){
            return new ResponseEntity<>(new SingleLineResponse("Cannot update admin role"), HttpStatus.BAD_REQUEST);
        }

        if (addRoleRequestDto.getRoleName().equals("USER")){
            return new ResponseEntity<>(new SingleLineResponse("Cannot update user role"), HttpStatus.BAD_REQUEST);
        }

        if(roleService.findByName(addRoleRequestDto.getRoleName()).isPresent()){
            Role role = roleService.findByName(addRoleRequestDto.getRoleName()).get();
            List<Privilege> privileges = privilegeService.getPrivileges(addRoleRequestDto.getPrivilegeIds());
            Set<Privilege> privilegeSet = new HashSet<>(privileges);
            role.setPrivileges(privilegeSet);
            roleService.saveRole(role);
            return new ResponseEntity<>(new SingleLineResponse("Role updated successfully"), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new SingleLineResponse("Role does not exist"), HttpStatus.BAD_REQUEST);
        }
    }
}
