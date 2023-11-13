package com.iitgn.entryexit.config;

import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.repositories.PendingRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ScheduleConfig {

    private final PendingRequestRepository pendingRequestRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredPendingRequests(){
        List<PendingRequest> pendingRequests = pendingRequestRepository.findPendingRequestByValidUptoDateAfterAndValidUptoTimeAfter(LocalDateTime.now().toLocalDate(), LocalDateTime.now().toLocalTime());
        pendingRequestRepository.deleteAll(pendingRequests);
    }
}

