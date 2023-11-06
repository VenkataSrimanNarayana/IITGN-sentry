package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.entities.RequestDetails;
import com.iitgn.entryexit.entities.UserVisitorLog;
import com.iitgn.entryexit.models.responses.SingleLineResponse;
import com.iitgn.entryexit.services.PendingRequestService;
import com.iitgn.entryexit.services.UserVisitorLogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/user-visitor-log")
@AllArgsConstructor
public class UserVisitorLogController {

    private final UserVisitorLogService userVisitorLogService;
    private final PendingRequestService pendingRequestService;

    public boolean checkValidity(LocalTime validFromTime, LocalDate validFromDate, LocalTime validUptoTime, LocalDate validUptoDate){
        LocalTime currentTime = LocalTime.now();
        // compare current time with validFromTime and validFromDate
        if(currentTime.isBefore(validFromTime) || LocalDate.now().isBefore(validFromDate)){
            return false;
        }
        // compare current time with validUptoTime and validUptoDate
        return !currentTime.isAfter(validUptoTime) && !LocalDate.now().isAfter(validUptoDate);
    }


    @PostMapping("/{id}")
    public ResponseEntity<SingleLineResponse> logUserVisitor(@PathVariable Long id) {
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
            RequestDetails requestDetails = pendingRequest.getRequestDetails();

            UserVisitorLog userVisitorLog = UserVisitorLog.builder()
                    .purpose(pendingRequest.getReason())
                    .inTime(LocalTime.now())
                    .inDate(LocalDate.now())
                    .firstName(requestDetails.getFirstName())
                    .lastName(requestDetails.getLastName())
                    .mobileNo(requestDetails.getMobileNo())
                    .area(requestDetails.getArea())
                    .houseNo(requestDetails.getHouseNo())
                    .landmark(requestDetails.getLandmark())
                    .pinCode(requestDetails.getPinCode())
                    .state(requestDetails.getState())
                    .townCity(requestDetails.getTownCity())
                    .country(requestDetails.getCountry())
                    .state(requestDetails.getState())
                    .vehicleNo(pendingRequest.getVehicleNo())
                    .build();

            pendingRequest.setEntry(false);
            pendingRequest.setUserVisitorLog(userVisitorLog);
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


}
