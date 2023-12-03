package sc2002.campmanager.camp;

import org.apache.commons.csv.CSVRecord;
import sc2002.campmanager.user.Student;
import sc2002.campmanager.util.DateTimeConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Camp converter class to convert camp object to csv record and vice versa
 */
public class CampConverter {
    /**
     * Convert camp object to csv record
     * @param camp camp object
     * @return csv record
     */
    public static List<String> campToCsvRecord(Camp camp) {
        List<String> s = new ArrayList<>();
        s.add(Integer.toString(camp.getCampId()));
        s.add(camp.getCampName());
        s.add(Boolean.toString(camp.isVisible()));
        s.add(DateTimeConverter.convertListToString(camp.getDates()));
        s.add(DateTimeConverter.convertToString(camp.getRegistrationCloseDate()));
        s.add(camp.getLocation());
        s.add(Integer.toString(camp.getCampAttendeeSlots()));
        s.add(camp.getStaffInCharge());
        s.add("[" + String.join(", ", camp.getCampAttendees()) + "]");
        s.add(Integer.toString(camp.getCampCommitteeSlots()));
        s.add("[" + String.join(", ", camp.getCampCommitteeMembers()) + "]");
        s.add(camp.getDescription());
        s.add(Integer.toString(camp.getCurrentAttendeeSlots()));
        s.add(Integer.toString(camp.getCurrentCommitteeMemberSlots()));
        s.add(camp.getUserGroup().toString());
        s.add("[" + camp.getSuggestions().stream().map(String::valueOf).collect(Collectors.joining(", ")) + "]");
        s.add("[" + camp.getEnquiries().stream().map(String::valueOf).collect(Collectors.joining(", ")) + "]");
        return s;
    }

    /**
     * Convert csv record to camp object
     * @param csvRecord     csv record
     * @return camp object
     */
    public static Camp csvToCamp(CSVRecord csvRecord) {
        int campId = Integer.parseInt(csvRecord.get("campId"));
        String campName = csvRecord.get("campName");
        boolean visibility = Boolean.parseBoolean(csvRecord.get("visibility"));
        List<LocalDate> dates = DateTimeConverter.convertToListOfDates(csvRecord.get("dates"));
        LocalDate registrationCloseDate = DateTimeConverter.convertToDate(csvRecord.get("Registration Closing Date"));
        String location = csvRecord.get("Location");
        int campAttendeeSlots = Integer.parseInt(csvRecord.get("Camp Attendee Slots"));
        String staffInCharge = csvRecord.get("Staff In Charge");
        int campCommitteeSlots = Integer.parseInt(csvRecord.get("Camp Committee Slots"));
        String description = csvRecord.get("Description");
        Camp.UserGroup userGroup = Camp.UserGroup.valueOf(csvRecord.get("User Group"));
        int currentAttendeeSlots = Integer.parseInt(csvRecord.get("Currently Available Attendee Slots"));
        int currentCommitteeSlots = Integer.parseInt(csvRecord.get("Currently Available Committee Slots"));
        String[] suggestionsString = csvRecord.get("Suggestion").replace("[", "").replace("]", "").split(", \\s*");
        List<Integer> suggestions = new ArrayList<>();
        for (String s: suggestionsString) {
            if (!s.equals(""))
                suggestions.add(Integer.parseInt(s));
        }
        String[] enquiriesString = csvRecord.get("Enquiry").replace("[", "").replace("]", "").split(", \\s*");
        List<Integer> enquiries = new ArrayList<>();
        for (String s : enquiriesString) {
            if (!s.equals(""))
                enquiries.add(Integer.parseInt(s));
        }
        Camp c = new Camp(campId, campName, visibility, dates, registrationCloseDate, location, campAttendeeSlots, staffInCharge, campCommitteeSlots, description, userGroup, currentCommitteeSlots, currentAttendeeSlots);
        String[] constAttendee = csvRecord.get("Camp Attendees").replace("[", "").replace("]", "").split(",\\s*");
        List<String> attendees= new ArrayList<>();
        for (String attendee : constAttendee){
            if (!attendee.equals("")) {
                attendees.add(attendee);
            }
        }
        String[] constantCommitteeMember = csvRecord.get("Camp Committee Members").replace("[", "").replace("]", "").split(",\\s*");
        List<String> committeeMembers = new ArrayList<>();
        for (String committeeMember : constantCommitteeMember){
            if (!committeeMember.equals("")) {
                committeeMembers.add(committeeMember);
            }
        }
        c.setCampAttendees(attendees);
        c.setCampCommitteeMembers(committeeMembers);
        c.setSuggestions(suggestions);
        c.setEnquiries(enquiries);
        return c;
    }

    /**
     * Convert list of camp objects to list of csv records
     * @param campList list of camp objects
     * @return list of csv records
     */
    public static List<List<String>> campListToCsv(List<Camp> campList) {
        List<List<String>> campListString = new ArrayList<>();
        for (Camp c : campList) {
            campListString.add(campToCsvRecord(c));
        }
        return campListString;
    }
}
