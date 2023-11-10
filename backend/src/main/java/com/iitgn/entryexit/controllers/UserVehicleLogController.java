package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.entities.UserVehicleLog;
import com.iitgn.entryexit.entities.UserVisitorLog;
import com.iitgn.entryexit.models.responses.SingleLineResponse;
import com.iitgn.entryexit.services.PendingRequestService;
import com.iitgn.entryexit.services.UserVehicleLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-vehicle-log")
public class UserVehicleLogController {

    public Long getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(auth.getName());
    }

    public boolean checkValidity(LocalTime validFromTime, LocalDate validFromDate, LocalTime validUptoTime, LocalDate validUptoDate){
        // compare current time with validFromTime and validFromDate

        if(LocalDate.now().isBefore(validFromDate) && LocalTime.now().isBefore(validFromTime)){
            return true;
        }
        // compare current time with validUptoTime and validUptoDate
        return LocalDate.now().isAfter(validUptoDate) && LocalTime.now().isAfter(validUptoTime);
    }

    private final UserVehicleLogService userVehicleLogService;
    private final PendingRequestService pendingRequestService;

    @PreAuthorize("hasAuthority('LOG_PRIVILEGE')")
    @PostMapping("/{id}")
    public ResponseEntity<SingleLineResponse> logUserVehicle(@PathVariable UUID id) {
        PendingRequest pendingRequest = pendingRequestService.findById(id);
        if (pendingRequest == null) {
            return ResponseEntity.badRequest().body(new SingleLineResponse("Invalid Request"));
        }

        boolean isEntry = pendingRequest.isEntry();

        if (isEntry) {
          UserVehicleLog userVehicleLog = UserVehicleLog.builder()
                  .vehicleNo(pendingRequest.getVehicleNo())
                  .firstName(pendingRequest.getVehicleRequestDetails().getFirstName())
                  .lastName(pendingRequest.getVehicleRequestDetails().getLastName())
                  .mobileNo(pendingRequest.getVehicleRequestDetails().getMobileNo())
                  .inTime(LocalTime.now())
                  .inDate(LocalDate.now())
                  .isPickup(pendingRequest.getVehicleRequestDetails().isPickup())
                  .build();

          userVehicleLog.setUser(pendingRequest.getUser());
          userVehicleLogService.saveUserVehicleLog(userVehicleLog);
          pendingRequest.setEntry(false);
          pendingRequest.setUserVehicleLog(userVehicleLog);
          pendingRequestService.updateRequest(pendingRequest);
          return ResponseEntity.ok().body(new SingleLineResponse("Vehicle in-log successful"));
        } else {
            UserVehicleLog userVehicleLog = pendingRequest.getUserVehicleLog();
            userVehicleLog.setOutDate(LocalDate.now());
            userVehicleLog.setOutTime(LocalTime.now());
            userVehicleLogService.saveUserVehicleLog(userVehicleLog);
            pendingRequestService.deleteRequest(id);
            return ResponseEntity.ok().body(new SingleLineResponse("Vehicle out-log successful"));
        }
    }

    @PreAuthorize("hasAuthority('DELETE_LOG_PRIVILEGE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<SingleLineResponse> deleteRequest(@PathVariable UUID id){
        UserVehicleLog userVehicleLog = userVehicleLogService.findById(id);
        if(userVehicleLog == null){
            return ResponseEntity.badRequest().body(new SingleLineResponse("UserVehicleLog id not Found"));
        }
        userVehicleLogService.deleteUserVehicleLog(id);
        return ResponseEntity.ok().body(new SingleLineResponse("UserVehicleLog deleted successfully"));
    }

    @PreAuthorize("hasAuthority('DELETE_LOG_PRIVILEGE')")
    @DeleteMapping("/all")
    public ResponseEntity<SingleLineResponse> deleteAllUserVehicleLogs() {
        userVehicleLogService.deleteAllUserVehicleLogs();
        return ResponseEntity.ok().body(new SingleLineResponse("All UserVehicleLogs deleted successfully"));
    }


    @PreAuthorize("hasAuthority('READ_USER_LOG_PRIVILEGE')")
    @GetMapping("/user/all")
    public ResponseEntity<List<UserVehicleLog>> getUserVehicleLogsByUserId() {
        Long id = getCurrentUser();
        List<UserVehicleLog> userVehicleLogs = userVehicleLogService.findUserVehicleLogByUserId(id);
        if (userVehicleLogs == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(userVehicleLogs);
    }

    @PreAuthorize("hasAuthority('READ_LOG_PRIVILEGE')")
    @GetMapping("/all")
    public ResponseEntity<List<UserVehicleLog>> getAllUserVehicleLogs() {
        List<UserVehicleLog> userVehicleLogs = userVehicleLogService.findAllUserVehicleLogs();
        if (userVehicleLogs == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(userVehicleLogs);
    }


    @PreAuthorize("hasAuthority('LOG_PRIVILEGE')")
    @PostMapping("/visitor/manual-log")
    public ResponseEntity<SingleLineResponse> logVisitor(@RequestBody UserVehicleLog userVisitorLog) {
        userVehicleLogService.saveUserVehicleLog(userVisitorLog);
        return ResponseEntity.ok().body(new SingleLineResponse("Visitor Logged"));
    }

}
