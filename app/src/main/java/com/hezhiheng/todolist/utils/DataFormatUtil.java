package com.hezhiheng.todolist.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DataFormatUtil {
    public static Date transLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
