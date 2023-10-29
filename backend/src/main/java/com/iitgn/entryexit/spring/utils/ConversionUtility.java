package com.iitgn.entryexit.spring.utils;

import java.time.LocalDate;
import java.time.LocalTime;

public class ConversionUtility {
    public static LocalTime convertJsonTimeToLocalTime(String jsonTime) {
        return LocalTime.parse(jsonTime);
    }

    public static LocalDate convertJsonDateToLocalDate(String jsonDate) {
        return LocalDate.parse(jsonDate);
    }
}
