package sc2002.campmanager.camp;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Camp manager class to manage camp objects
 */
public class CampManager {
    private static int lastId;
    static {
        if (CampRepository.getAllCamps().isEmpty()) {
            lastId = 1000;
        }
        else {
            lastId = CampRepository.getAllCamps().stream().mapToInt(Camp::getCampId).max().orElse(1000);
        }
    }
    /**
     * generate a new camp id
     * @return new camp id
     */
    public static int generateCampId() {
        lastId++;
        return lastId;
    }

    /**
     * Create a new camp
     * @param campName camp name
     * @param visibility visibility
     * @param dates dates
     * @param registrationCloseDate registration close date
     * @param location location
     * @param campAttendeeSlots camp attendee slots
     * @param staffInCharge staff in charge
     * @param campCommitteeSlots camp committee slots
     * @param description description
     * @param userGroup user group
     * @return new camp
     */
    public static Camp createCamp(String campName, boolean visibility, List<LocalDate> dates, LocalDate registrationCloseDate,
                                  String location, int campAttendeeSlots, String staffInCharge, int campCommitteeSlots, String description, Camp.UserGroup userGroup) {
        Camp c = new Camp(generateCampId(), campName, visibility, dates, registrationCloseDate, location, campAttendeeSlots, staffInCharge, campCommitteeSlots, description, userGroup);
        return CampRepository.save(c);
    }
    /**
     * delete a camp by ID
     * @param campId camp id
     * @return  deleted camp
     */
    public static Optional<Camp> deleteCamp(int campId) {
        Optional<Camp> camp = CampRepository.findByID(campId);
        if (camp.isPresent())
            return CampRepository.delete(camp.get());
        else
            return Optional.empty();
    }

    /**
     * get all camps
     * @return list of camps
     */
    public static List<Camp> getAllCamps() {
        return CampRepository.getAllCamps();
    }

    /**
     * get ccamp by ID
     * @param campId camp id
     * @return camp
     */
    public static Optional<Camp> getCampById(int campId) {
        return CampRepository.findByID(campId);
    }


}
