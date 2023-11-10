package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.entities.VisitorRequestDetails;
import com.iitgn.entryexit.entities.UserVisitorLog;
import com.iitgn.entryexit.models.responses.SingleLineResponse;
import com.iitgn.entryexit.services.PendingRequestService;
import com.iitgn.entryexit.services.UserVisitorLogService;
import lombok.AllArgsConstructor;
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
@RequestMapping("/api/user-visitor-log")
@AllArgsConstructor
public class UserVisitorLogController {

    private final UserVisitorLogService userVisitorLogService;
    private final PendingRequestService pendingRequestService;

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


    @PreAuthorize("hasAuthority('LOG_PRIVILEGE')")
    @PostMapping("/{id}")
    public ResponseEntity<SingleLineResponse> logUserVisitor(@PathVariable UUID id) {
        PendingRequest pendingRequest = pendingRequestService.findById(id);
        if (pendingRequest == null) {
            return ResponseEntity.badRequest().body(new SingleLineResponse("Invalid Request"));
        }

        if (checkValidity(pendingRequest.getValidFromTime(), pendingRequest.getValidFromDate(),
                pendingRequest.getValidUptoTime(), pendingRequest.getValidUptoDate())){
            return ResponseEntity.badRequest().body(new SingleLineResponse("Request not yet valid or expired"));
        }


        boolean isEntry = pendingRequest.isEntry();

        if(isEntry){
            VisitorRequestDetails visitorRequestDetails = pendingRequest.getVisitorRequestDetails();

            UserVisitorLog userVisitorLog = UserVisitorLog.builder()
                    .purpose(pendingRequest.getReason())
                    .inTime(LocalTime.now())
                    .inDate(LocalDate.now())
                    .firstName(visitorRequestDetails.getFirstName())
                    .lastName(visitorRequestDetails.getLastName())
                    .mobileNo(visitorRequestDetails.getMobileNo())
                    .area(visitorRequestDetails.getArea())
                    .houseNo(visitorRequestDetails.getHouseNo())
                    .landmark(visitorRequestDetails.getLandmark())
                    .pinCode(visitorRequestDetails.getPinCode())
                    .state(visitorRequestDetails.getState())
                    .townCity(visitorRequestDetails.getTownCity())
                    .country(visitorRequestDetails.getCountry())
                    .state(visitorRequestDetails.getState())
                    .vehicleNo(pendingRequest.getVehicleNo())
                    .build();

            pendingRequest.setEntry(false);
            pendingRequest.setUserVisitorLog(userVisitorLog);
            userVisitorLog.setUser(pendingRequest.getUser());
            userVisitorLogService.saveUserVisitorLog(userVisitorLog);
            pendingRequestService.updateRequest(pendingRequest);
            return ResponseEntity.ok().body(new SingleLineResponse("Request in-log successful"));
        }
        else{
            UserVisitorLog userVisitorLog = pendingRequest.getUserVisitorLog();
            userVisitorLog.setOutDate(LocalDate.now());
            userVisitorLog.setOutTime(LocalTime.now());
            userVisitorLogService.saveUserVisitorLog(userVisitorLog);
            pendingRequestService.deleteRequest(id);
            return ResponseEntity.ok().body(new SingleLineResponse("Request out-log successful"));
        }
    }


    @PreAuthorize("hasAuthority('DELETE_LOG_PRIVILEGE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<SingleLineResponse> deleteRequest(@PathVariable UUID id) {
        UserVisitorLog userVisitorLog = userVisitorLogService.findById(id);
        if (userVisitorLog == null) {
            return ResponseEntity.badRequest().body(new SingleLineResponse("UserVisitorLog id not Found"));
        }
        userVisitorLogService.deleteUserVisitorLog(id);
        return ResponseEntity.ok().body(new SingleLineResponse("UserVisitorLog deleted successfully"));
    }


    //delete all
    @PreAuthorize("hasAuthority('DELETE_LOG_PRIVILEGE')")
    @DeleteMapping("/all")
    public ResponseEntity<SingleLineResponse> deleteAllUserVisitorLogs() {
        userVisitorLogService.deleteAllUserVisitorLogs();
        return ResponseEntity.ok().body(new SingleLineResponse("All UserVisitorLogs deleted successfully"));
    }


    //get all the userVisitorLogs by user id
    @PreAuthorize("hasAuthority('READ_USER_LOG_PRIVILEGE')")
    @GetMapping("/user/all")
    public ResponseEntity<List<UserVisitorLog>> getUserVisitorLogsByUserId() {
        Long id = getCurrentUser();
        List<UserVisitorLog> userVisitorLogs = userVisitorLogService.findUserVisitorLogByUserId(id);
        if (userVisitorLogs == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(userVisitorLogs);
    }


    //get mapping to get all user_visitor_logs
    @PreAuthorize("hasAuthority('READ_LOG_PRIVILEGE')")
    @GetMapping("/all")
    public ResponseEntity<List<UserVisitorLog>> getAllUserVisitorLogs() {
        List<UserVisitorLog> userVisitorLogs = userVisitorLogService.findAllUserVisitorLogs();
        if (userVisitorLogs == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(userVisitorLogs);
    }

}
