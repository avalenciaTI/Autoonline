package com.solera.global.qa.template.web.behavior.data.tools;

import com.solera.global.qa.template.web.behavior.data.types.CurrentDateTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestDateGenerator {

    private TestDateGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static String todayPlusDays(int days) {
        LocalDate endDate = LocalDate.now();
        endDate = endDate.plusDays(days);
        LocalDateTime calcDate = getRunningTime(LocalDateTime.now(), 25);
        log.info("Calculated date: {} test", calcDate);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return endDate.format(dateFormat);
    }

    public static String currentTimePlusHours(int hours) {
        LocalDateTime calculatedHour = getRunningTime(LocalDateTime.now(), hours);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        log.info("Calculated hour: {}test", calculatedHour);
        int length = calculatedHour.toString().length();
        log.info("Calculated hour: {}test {}", calculatedHour, length);

        String result = calculatedHour.format(timeFormat);
        log.info("Result: {}test {}", result, result.length());
        return result;
    }

    private static LocalDateTime getRunningTime(LocalDateTime calculatedHour, int hours) {
        String runningSystem = System.getenv("JENKINS_HOME");
        LocalDateTime currentHour = LocalDateTime.now();

        return runningSystem != null && runningSystem.contains("jenkins")
            ? currentHour.minusHours(hours + 2)
            : currentHour.plusHours(hours);
    }

    public static CurrentDateTime getCUrrentDateTime() {
        int offsetHours = 12;
        LocalDateTime currentHour = LocalDateTime.now().plusHours(offsetHours);

        String date = currentHour.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String time = currentHour.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return new CurrentDateTime(date, time);
    }

}
