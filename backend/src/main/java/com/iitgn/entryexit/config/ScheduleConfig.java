package com.iitgn.entryexit.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
@Configuration
@EnableScheduling
public class ScheduleConfig {
    @Scheduled(cron = "0 0 0 * * ?") // runs at 12 am every night
    public void yourScheduledFunction() {
        // Your function's logic goes here
        System.out.println("Running your function at 12 am every night.");
    }
}

