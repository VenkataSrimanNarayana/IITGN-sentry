package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.entities.UserLog;
import com.iitgn.entryexit.models.responses.SingleLineResponse;
import com.iitgn.entryexit.services.PendingRequestService;
import com.iitgn.entryexit.services.UserLogService;
import com.iitgn.entryexit.spring.utils.UtilityFunctions;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

// user_logs(user_id, room_no, block_no, purpose, is_entry, date, time, vehicle_no)

@RestController
@RequestMapping("/api/user-logs")
@AllArgsConstructor
public class UserLogController {

    public Long getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(auth.getName());
    }

    private final PendingRequestService pendingRequestService;
    private final UserLogService userLogService;

    @PostMapping("/{id}")
    public ResponseEntity<SingleLineResponse> logUser(@PathVariable Long id){
        PendingRequest pendingRequest = pendingRequestService.findById(id);
        if(pendingRequest == null){
            return ResponseEntity.badRequest().body(new SingleLineResponse("Invalid Request"));
        }

        if (UtilityFunctions.checkValidity(pendingRequest.getValidFromTime(), pendingRequest.getValidFromDate(),
                pendingRequest.getValidUptoTime(), pendingRequest.getValidUptoDate())){
        return ResponseEntity.badRequest().body(new SingleLineResponse("Request not yet valid or expired"));
        }


        String vehicleNo = pendingRequest.getVehicleNo();

        if(vehicleNo == null){
            vehicleNo = "NO VEHICLE";
        }

        User user = pendingRequest.getUser();

        int roomNo = 0;
        String blockName = "NO BLOCK";

        if(user.getRoom() != null){
            roomNo = user.getRoom().getRoomNo();
            blockName = user.getRoom().getBlockName();
        }

        UserLog userLog = UserLog.builder()
                .purpose(pendingRequest.getReason())
                .isEntry(pendingRequest.isEntry())
                .eventDate(LocalDate.now())
                .eventTime(LocalTime.now())
                .vehicleNo(vehicleNo)
                .blockName(blockName)
                .roomNo(roomNo)
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

    @GetMapping("/all")
    public ResponseEntity<List<UserLog>> getAllLogs(){
        List<UserLog> userLogs = userLogService.getAllLogs();
        return ResponseEntity.ok().body(userLogs);
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<UserLog>> getAllLogsOfUser(){
        Long userId = getCurrentUser();
        List<UserLog> userLogs = userLogService.getAllLogsOfUser(userId);
        return ResponseEntity.ok().body(userLogs);
    }

    @GetMapping("/user/{id}/all")
    public ResponseEntity<List<UserLog>> getAllLogsOfUser(@PathVariable Long id){
        List<UserLog> userLogs = userLogService.getAllLogsOfUser(id);
        return ResponseEntity.ok().body(userLogs);
    }



}











