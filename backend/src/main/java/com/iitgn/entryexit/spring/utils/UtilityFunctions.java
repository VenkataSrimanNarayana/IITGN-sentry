package com.iitgn.entryexit.spring.utils;
import java.time.LocalDate;
import java.time.LocalTime;

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
