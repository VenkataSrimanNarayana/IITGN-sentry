package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.Privilege;
import com.iitgn.entryexit.services.PrivilegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.iitgn.entryexit.repositories.PrivilegeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    @Override
    public List<Privilege> getAllPrivileges() {
        return privilegeRepository.findAll();
    }

    @Override
    public List<Privilege> getPrivileges(List<Integer> privilegeIds) {
        return privilegeRepository.findAllById(privilegeIds);
    }
}
