package com.iitgn.entryexit.services;

import com.iitgn.entryexit.entities.Privilege;

import java.util.List;
import java.util.Set;

public interface PrivilegeService {

    List<Privilege> getAllPrivileges();

    List<Privilege> getPrivileges(List<Integer> privilegeIds);
}
