package com.iitgn.entryexit.spring.utils;
import com.iitgn.entryexit.entities.UserLog;
import com.iitgn.entryexit.models.responses.UserLogResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class UtilityFunctions{

    public static boolean checkValidity(LocalTime validFromTime, LocalDate validFromDate, LocalTime validUptoTime, LocalDate validUptoDate){
        LocalTime currentTime = LocalTime.now();
        // compare current time with validFromTime and validFromDate
        if(currentTime.isBefore(validFromTime) || LocalDate.now().isBefore(validFromDate)){
            return false;
        }
        // compare current time with validUptoTime and validUptoDate
        return !currentTime.isAfter(validUptoTime) && !LocalDate.now().isAfter(validUptoDate);
    }

    public static List<UserLogResponse> getUserLogResponses(List<UserLog> userLogs) {
        List<UserLogResponse> userLogResponses = new ArrayList<>();
        for(UserLog userLog : userLogs){
            UserLogResponse userLogResponse = new UserLogResponse();
            userLogResponse.setUserLogId(userLog.getUserLogId());
            userLogResponse.setPurpose(userLog.getPurpose());
            userLogResponse.setEntry(userLog.isEntry());
            userLogResponse.setEventDate(userLog.getEventDate());
            userLogResponse.setEventTime(userLog.getEventTime());
            userLogResponse.setVehicleNo(userLog.getVehicleNo());
            userLogResponse.setBlockName(userLog.getBlockName());
            userLogResponse.setRoomNo(userLog.getRoomNo());
            try {
                userLogResponse.setUserId(userLog.getUser().getId());
            } catch (NullPointerException e) {
                System.out.println("User is null");
                userLogResponse.setUserId(0);
            }
//            userLogResponse.setUserId(userLog.getUser().getId());
            userLogResponses.add(userLogResponse);
        }
        return userLogResponses;
    }
}
