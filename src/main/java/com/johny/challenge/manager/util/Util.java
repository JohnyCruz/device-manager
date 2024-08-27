package com.johny.challenge.manager.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Util {
    
    public static LocalDateTime getCurrentDate(){
        return LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }
}
