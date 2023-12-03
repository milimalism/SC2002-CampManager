package sc2002.campmanager.camp;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Camp edit manager class to edit camp details
 */
public class CampEditManager {

    /**
     * Edit camp name
     * @param campId camp id
     * @param campName new camp name
     */
    public static void editCampName(int campId, String campName) {
        Optional<Camp> camp = CampRepository.findByID(campId);
        if (camp.isPresent()) {
            Camp newCamp = camp.get().copy();
            newCamp.setCampName(campName);
            CampRepository.update(camp.get(), newCamp);
        }
    }

    /**
     * Toggle visibility of camp
     * @param campId camp id
     */
    public static void toggleVisibility(int campId) {
        Optional<Camp> camp = CampRepository.findByID(campId);
        if (camp.isPresent()) {
            Camp newCamp = camp.get().copy();
            newCamp.setVisibility(true);
            CampRepository.update(camp.get(), newCamp);
        }
    }

    /**
     * Add dates to camp
     * @param campId camp id
     * @param dates list of dates to add
     */
    public static void addDates(int campId, List<LocalDate> dates) {
        Optional<Camp> camp = CampRepository.findByID(campId);
        if (camp.isPresent()) {
            Camp newCamp = camp.get().copy();
            for (LocalDate date : dates)
                newCamp.getDates().add(date);
            CampRepository.update(camp.get(), newCamp);
        }
    }

    /**
     * Delete dates from camp
     * @param campId camp id
     * @param dates list of dates to delete
     */
    public static void deleteDates(int campId, List<LocalDate> dates) {
        Optional<Camp> camp = CampRepository.findByID(campId);
        if (camp.isPresent()) {
            Camp newCamp = camp.get().copy();
            for (LocalDate date : dates)
                newCamp.getDates().remove(date);
            CampRepository.update(camp.get(), newCamp);
        }
    }

    /**
     * Edit registration close date
     * @param campId camp id
     * @param date new registration close date
     */
    public static void editRegistrationCloseDate(int campId, LocalDate date) {
        Optional<Camp> camp = CampRepository.findByID(campId);
        if (camp.isPresent()) {
            Camp newCamp = camp.get().copy();
            newCamp.setRegistrationCloseDate(date);
            CampRepository.update(camp.get(), newCamp);
        }
    }

    /**
     * Edit location of camp
     * @param campId camp id
     * @param location new location
     */
    public static void editLocation(int campId, String location) {
        Optional<Camp> camp = CampRepository.findByID(campId);
        if (camp.isPresent()) {
            Camp newCamp = camp.get().copy();
            newCamp.setLocation(location);
            CampRepository.update(camp.get(), newCamp);
        }
    }

    /**
     * Edit attendee slots
     * @param campId camp id
     * @param attendeeSlots new num of attendee slots
     * @return  success message
     */
    public static String editAttendeeSlots(int campId, int attendeeSlots) {
        Optional<Camp> camp = CampRepository.findByID(campId);
        if (camp.isPresent()) {
            if (attendeeSlots > (camp.get().getTotalSlots() - camp.get().getCurrentAttendeeSlots())) {
                Camp newCamp = camp.get().copy();
                newCamp.setCampAttendeeSlots(attendeeSlots);
                CampRepository.update(camp.get(), newCamp);
                return "Successfully updated camp!";
            }
            return "Total slots less than current number of attendees!";
        }
        return "Camp Not Present";
    }

    /**
     * Edit camp committee slots
     * @param campId  camp id
     * @param campCommitteeSlots new num of camp committee slots
     * @return success message
     */
    public static String editCampCommitteeSlots(int campId, int campCommitteeSlots) {
        Optional<Camp> camp = CampRepository.findByID(campId);
        if (camp.isPresent()) {
            if (campCommitteeSlots > (camp.get().getCampCommitteeSlots() - camp.get().getCurrentCommitteeMemberSlots())) {
                Camp newCamp = camp.get().copy();
                newCamp.setCampCommitteeSlots(campCommitteeSlots);
                CampRepository.update(camp.get(), newCamp);
                return "Successfully upadted camp!";
            }
            return "New Camp Committee Slots less than current number of camp committee members";
        }
        return "Camp Not Present";
    }

    /**
     * Edit camp description
     * @param campId camp id
     * @param description new camp description
     */
    public static void editDescription(int campId, String description) {
        Optional<Camp> camp = CampRepository.findByID(campId);
        if (camp.isPresent()) {
            Camp newCamp = camp.get().copy();
            newCamp.setDescription(description);
            CampRepository.update(camp.get(), newCamp);
        }
    }

}
