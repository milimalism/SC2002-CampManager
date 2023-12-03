package sc2002.campmanager.util;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Date Time Converter class to convert LocalDateTime to string and vice versa
 */
public class DateTimeConverter {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * Converts a list of LocalDateTime to a string
     * @param dateTimes list of LocalDateTime
     * @return string representation of the list
     */
    public static String convertListToString(List<LocalDate> dateTimes) {
        return "[" + dateTimes.stream().map(dateTime -> dateTime.format(formatter)).collect(Collectors.joining(", ")) + "]";
    }

    /**
     * Converts a LocalDateTime to a string
     * @param dateTime LocalDateTime
     * @return string representation of the LocalDateTime
     */
    public static String convertToString(LocalDate dateTime) {
        return dateTime.format(formatter);
    }

    /**
     * Converts a string to a list of LocalDateTime
     * @param dates string representation of the list
     * @return list of LocalDateTime
     */
    public static List<LocalDate> convertToListOfDates(String dates) {
        List<LocalDate> dateTimes = new ArrayList<>();
        String[] dateStrings = dates.replace("[", "").replace("]", "").split(",\\s*");
        for (String dateString : dateStrings) {
            LocalDate dateTime = LocalDate.parse(dateString.trim(), formatter);
            dateTimes.add(dateTime);
        }
        return dateTimes;
    }

    /**
     * Converts a string to a LocalDateTime
     * @param date string representation of the LocalDateTime
     * @return LocalDateTime
     */
    public static LocalDate convertToDate(String date) {
        return LocalDate.parse(date.trim(), formatter);
    }
}
