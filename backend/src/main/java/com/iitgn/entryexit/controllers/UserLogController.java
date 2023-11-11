package com.iitgn.entryexit.controllers;

import com.iitgn.entryexit.entities.PendingRequest;
import com.iitgn.entryexit.entities.User;
import com.iitgn.entryexit.entities.UserLog;
import com.iitgn.entryexit.models.requestdto.UserLogDto;
import com.iitgn.entryexit.models.responses.SingleLineResponse;
import com.iitgn.entryexit.models.responses.UserLogResponse;
import com.iitgn.entryexit.services.PendingRequestService;
import com.iitgn.entryexit.services.UserLogService;
import com.iitgn.entryexit.services.UserService;
import com.iitgn.entryexit.spring.utils.UtilityFunctions;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// user_logs(user_id, room_no, block_no, purpose, is_entry, date, time, vehicle_no)

@RestController
@RequestMapping("/api/user-log")
@AllArgsConstructor
public class UserLogController {

    public Long getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(auth.getName());
    }

    private final PendingRequestService pendingRequestService;
    private final UserLogService userLogService;
    private final UserService userService;


//    @PreAuthorize("hasAuthority('LOG_PRIVILEGE')")


    @PreAuthorize("hasAuthority('LOG_PRIVILEGE')")
    @PostMapping("/{id}")
    public ResponseEntity<SingleLineResponse> logUser(@PathVariable UUID id){
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

    @PreAuthorize("hasAuthority('DELETE_LOG_PRIVILEGE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<SingleLineResponse> deleteUserLog(@PathVariable UUID id){
        userLogService.deleteUserLog(id);
        return ResponseEntity.ok().body(new SingleLineResponse("User Log Deleted"));
    }

    @PreAuthorize("hasAuthority('READ_LOG_PRIVILEGE')")
    @GetMapping("/all")
    public ResponseEntity<List<UserLogResponse>> getAllLogs(){
        List<UserLogResponse> userLogResponses = userLogService.getAllLogs();
        return ResponseEntity.ok().body(userLogResponses);
    }

    @PreAuthorize("hasAuthority('READ_USER_LOG_PRIVILEGE')")
    @GetMapping("/user/all")
    public ResponseEntity<List<UserLogResponse>> getAllLogsOfUser(){
        Long userId = getCurrentUser();
        List<UserLogResponse> userLogs = userLogService.getAllLogsOfUser(userId);
        return ResponseEntity.ok().body(userLogs);
    }

    @PreAuthorize("hasAuthority('LOG_PRIVILEGE')")
    @PostMapping("/user/log-manual")
    public ResponseEntity<SingleLineResponse> logUserManual(@RequestBody UserLogDto userLogDto){
        UserLog userLog = UserLog.builder().isEntry(userLogDto.isEntry()).roomNo(userLogDto.getRoomNo())
                .blockName(userLogDto.getBlockName()).eventDate(userLogDto.getEventDate())
                .eventTime(userLogDto.getEventTime()).purpose(userLogDto.getPurpose())
                .vehicleNo(userLogDto.getVehicleNo()).build();

        Optional<User> user = userService.getUserById(userLogDto.getUserId());

        if(user.isEmpty()){
            return ResponseEntity.badRequest().body(new SingleLineResponse("Invalid User"));
        }

        userLog.setUser(user.get());
        userLogService.saveUserLog(userLog);
        return ResponseEntity.ok().body(new SingleLineResponse("User Logged"));
    }







}











