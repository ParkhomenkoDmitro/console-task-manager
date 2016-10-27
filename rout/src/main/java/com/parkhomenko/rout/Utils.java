package com.parkhomenko.rout;

import com.parkhomenko.common.domain.TaskPriority;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Dmytro Parkhomenko
 *         Created on 27.10.16.
 */

public class Utils {
    public static LocalDate convertToDate(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        return LocalDate.parse(value, formatter);
    }

    public static TaskPriority convertToPriority(String value) {
        Byte index = Byte.valueOf(value);
        return TaskPriority.values()[index];
    }
}
