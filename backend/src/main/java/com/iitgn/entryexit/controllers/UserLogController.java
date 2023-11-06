package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.entities.UserLog;
import com.iitgn.entryexit.models.responses.SingleLineResponse;
import com.iitgn.entryexit.services.PendingRequestService;
import com.iitgn.entryexit.services.UserLogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

// user_logs(user_id, room_no, block_no, purpose, is_entry, date, time, vehicle_no)
@RestController
@RequestMapping("/api/user-logs")
@AllArgsConstructor
public class UserLogController {

    private final PendingRequestService pendingRequestService;
    private final UserLogService userLogService;

    @PostMapping("/{id}")
    public ResponseEntity<SingleLineResponse> logUser(@PathVariable Long id){
        PendingRequest pendingRequest = pendingRequestService.findById(id);
        if(pendingRequest == null){
            return ResponseEntity.badRequest().body(new SingleLineResponse("Invalid Request"));
        }

        // TODO: Check if request is valid

        String vehicleNo = pendingRequest.getVehicleNo();

        if(vehicleNo == null){
            vehicleNo = "NO VEHICLE";
        }

        UserLog userLog = UserLog.builder()
                .purpose(pendingRequest.getReason())
                .isEntry(pendingRequest.isEntry())
                .eventDate(LocalDate.now())
                .eventTime(LocalTime.now())
                .vehicleNo(vehicleNo)
                .blockName(pendingRequest.getUser().getRoom().getBlockName())
                .roomNo(pendingRequest.getUser().getRoom().getRoomNo())
                .build();

        userLog.setUser(pendingRequest.getUser());
        pendingRequestService.deleteRequest(id);
        userLogService.saveUserLog(userLog);
        return ResponseEntity.ok().body(new SingleLineResponse("User Logged"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SingleLineResponse> deleteUserLog(@PathVariable Long id){
        userLogService.deleteUserLog(id);
        return ResponseEntity.ok().body(new SingleLineResponse("User Log Deleted"));
    }




}











