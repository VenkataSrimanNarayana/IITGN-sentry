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


}
