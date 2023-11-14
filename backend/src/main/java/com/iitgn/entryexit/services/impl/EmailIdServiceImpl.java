package com.iitgn.entryexit.services.impl;

import com.iitgn.entryexit.entities.Email;
import com.iitgn.entryexit.repositories.EmailIdRepository;
import com.iitgn.entryexit.services.EmailIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailIdServiceImpl implements EmailIdService {
    private final EmailIdRepository emailIdRepository;

    @Override
    public void saveEmailId(Email email) {
        emailIdRepository.save(email);
    }
}
