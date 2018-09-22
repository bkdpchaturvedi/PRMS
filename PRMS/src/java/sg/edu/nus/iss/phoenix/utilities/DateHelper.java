/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.utilities;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;

/**
 *
 * @author MyatMin
 */
public class DateHelper {

    private static final DayOfWeek START_WEEK_DAY = DayOfWeek.MONDAY;

    public static LocalDate getWeekStartDate(LocalDate input) {
        return input.with(TemporalAdjusters.previousOrSame(START_WEEK_DAY));
    }

    public static LocalDate getWeekStartDate(LocalDate input, Integer weekNumber) {
        return getWeekStartDate(getWeekYear(input), weekNumber);
    }

    public static LocalDate getWeekStartDate(Integer weekYear, Integer weekNumber) {
        return LocalDate.ofYearDay(
                weekYear, 1
        ).with(
                WeekFields.ISO.weekOfYear(), weekNumber
        ).with(
                TemporalAdjusters.previousOrSame(START_WEEK_DAY)
        );
    }
    
    public static LocalDate getWeekEndDate(LocalDate input) {
        return getWeekStartDate(input).plusDays(6);
    }

    public static LocalDate getWeekEndDate(LocalDate input, Integer weekNumber) {
        return getWeekStartDate(input, weekNumber).plusDays(6);
    }

    public static LocalDate getWeekEndDate(Integer weekYear, Integer weekNumber) {
        return getWeekStartDate(weekYear, weekNumber).plusDays(6);
    }

    public static Integer getWeekNumber(LocalDate input) {
        return input.get(WeekFields.ISO.weekOfWeekBasedYear());
    }

    public static Integer getWeekYear(LocalDate input) {
        return input.get(WeekFields.ISO.weekBasedYear());
    }

}
