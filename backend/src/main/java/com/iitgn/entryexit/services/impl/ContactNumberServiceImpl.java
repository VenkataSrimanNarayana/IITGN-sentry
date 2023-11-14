package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.ContactNumber;
import com.iitgn.entryexit.repositories.ContactNumberRepository;
import com.iitgn.entryexit.services.ContactNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactNumberServiceImpl implements ContactNumberService {
    private final ContactNumberRepository contactNumberRepository;

    @Override
    public void saveContactNumber(ContactNumber contactNumber) {
        contactNumberRepository.save(contactNumber);
    }
}
