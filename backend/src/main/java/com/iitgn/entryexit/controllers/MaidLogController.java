package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.entities.Maid;
import com.iitgn.entryexit.entities.MaidLog;
import com.iitgn.entryexit.models.responses.SingleLineResponse;
import com.iitgn.entryexit.services.MaidLogService;
import com.iitgn.entryexit.services.MaidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/maid-log")
public class MaidLogController {

    private final MaidLogService maidLogService;
    private final MaidService maidService;


    @PreAuthorize("hasAuthority('LOG_MAID_PRIVILEGE')")
    @GetMapping("/log/{id}/entry")
    public ResponseEntity<SingleLineResponse> inLogMaid(@PathVariable UUID id) {
        LocalTime time = LocalTime.now();
        LocalDate date = LocalDate.now();

        Maid maid = maidService.getMaid(id);
        MaidLog maidLog = MaidLog.builder().eventDate(date).eventTime(time).isEntry(true).maid(maid).build();
        maidLogService.saveMaidLog(maidLog);
        return ResponseEntity.ok(new SingleLineResponse("Maid Logged Successfully"));
    }

    @PreAuthorize("hasAuthority('LOG_MAID_PRIVILEGE')")
    @GetMapping("/log/{id}/exit")
    public ResponseEntity<SingleLineResponse> outLogMaid(@PathVariable UUID id){
        LocalTime time = LocalTime.now();
        LocalDate date = LocalDate.now();

        Maid maid = maidService.getMaid(id);
        MaidLog maidLog = MaidLog.builder().eventDate(date).eventTime(time).isEntry(false).maid(maid).build();
        maidLogService.saveMaidLog(maidLog);
        return ResponseEntity.ok(new SingleLineResponse("Maid Logged Successfully"));
    }

    @PreAuthorize("hasAuthority('READ_ALL_LOG_MAID_PRIVILEGE')")
    @GetMapping("/all")
    public ResponseEntity<List<MaidLog>> getAllMaidLog(){
        return ResponseEntity.ok(maidLogService.getAllMaidLog());
    }

    @PreAuthorize("hasAuthority('DELETE_LOG_MAID_PRIVILEGE')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SingleLineResponse> deleteMaidLog(@PathVariable UUID id){
        maidLogService.deleteMaidLog(id);
        return ResponseEntity.ok(new SingleLineResponse("Maid Log Deleted Successfully"));
    }


//    @PreAuthorize("hasAuthority('READ_LOG_MAID_PRIVILEGE')")
//    @GetMapping("/maid/{id}")


    @PreAuthorize("hasAuthority('READ_LOG_MAID_PRIVILEGE')")
    @GetMapping("/{id}")
    public ResponseEntity<MaidLog> getMaidLog(@PathVariable UUID id){
        MaidLog maidLog = maidLogService.getMaidLog(id);
        if(maidLog == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(maidLog);
        }
    }




}
