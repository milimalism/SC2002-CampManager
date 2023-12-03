package sc2002.campmanager.camp;

import sc2002.campmanager.user.Student;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Camp Registration Manager class to manage camp registration
 */
public class CampRegistrationManager {
    /**
     * Check if registration is before deadline
     * @param camp camp object
     * @return boolean value true if registration is before deadline
     */
    private static boolean checkIfBeforeDeadline(Camp camp) {
        return LocalDate.now().isBefore(camp.getRegistrationCloseDate()) || LocalDate.now().isEqual(camp.getRegistrationCloseDate());
    }

    /**
     * Check if student is already an attendee
     * @param camp camp object
     * @param studentId student id
     * @return boolean value true if student is already an attendee
     */
    private static boolean checkIfCampCommittee(Camp camp, String studentId) {
        for (String s : camp.getCampCommitteeMembers()) {
            if (s.equals(studentId)){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if student is already an attendee
     * @param camp camp object
     * @param studentId student id
     * @return boolean value true if student is already an attendee
     */
    private static boolean checkIfAttendee(Camp camp, String studentId) {

        for (String s : camp.getCampAttendees()) {
            if (s.equals(studentId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if there are committee member slots left
     * @param camp camp object
     * @return boolean value true if there are committee member slots left
     */
    private static boolean checkCommitteeMemberSlots(Camp camp) {
        return camp.getCurrentCommitteeMemberSlots() > 0;
    }

    /**
     * Check if there are attendee slots left
     * @param camp camp object
     * @return boolean value true if there are attendee slots left
     */
    private static boolean checkTotalSlots(Camp camp) {
        return camp.getCurrentAttendeeSlots() > 0;
    }

    /**
     * Check if there are date clashes
     * @param camp camp object
     * @param student student object
     * @return boolean value true if there are date clashes
     */
    private static boolean checkDateClash(Camp camp, Student student) {
        for (LocalDate date : camp.getDates()) {
            for (int studentCampID : student.getOwnCamps()) {
                Optional<Camp> studentCamp = CampRepository.findByID(studentCampID);
                if (studentCamp.isPresent()) {
                    if (studentCamp.get().getDates().contains(date))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * Register a student as a committee member
     * @param campId camp id
     * @param student student object
     * @return 200 if successful, 201 if no committee member slots left, 202 if student is already a committee member, 203 if student is already an attendee, 204 if registration is after deadline, 205 if there are date clashes, 206 if camp does not exist
     */
    public static int registerCommitteeMember(int campId, Student student) {
        Optional<Camp> camp = CampRepository.findByID(campId);
        if (camp.isPresent()) {
            if (!checkDateClash(camp.get(), student)) {
                if (checkIfBeforeDeadline(camp.get())) {
                    if (!checkIfAttendee(camp.get(), student.getUserId())) {
                        if (!checkIfCampCommittee(camp.get(), student.getUserId())) {
                            if (checkCommitteeMemberSlots(camp.get())) {
                                camp.get().addCampCommitteeMember(student);
                                return 200;
                            } else
                                return 201;
                        } else
                            return 202;
                    } else
                        return 203;
                } else
                    return 204;
            } else
                return 205;
        }
        else
            return 206;
    }

    /**
     * Register a student as an attendee
     * @param campId camp id
     * @param student student object
     * @return 200 if successful, 201 if no attendee slots left, 202 if student is already a committee member, 203 if student is already an attendee, 204 if registration is after deadline, 205 if there are date clashes, 206 if camp does not exist
     */
    public static int registerAttendee(int campId, Student student) {
        Optional<Camp> camp = CampRepository.findByID(campId);
        if (camp.isPresent()) {
            if (!checkDateClash(camp.get(), student)) {
                if (checkIfBeforeDeadline(camp.get())) {
                    if (!checkIfAttendee(camp.get(), student.getUserId())) {
                        if (!checkIfCampCommittee(camp.get(), student.getUserId())) {
                            if (checkTotalSlots(camp.get())) {
                                camp.get().addAttendee(student);
                                return 200;
                            } else
                                return 201;
                        } else
                            return 202;
                    } else
                        return 203;
                } else
                    return 204;
            } else
                return 205;
        }
        else
            return 206;
    }

    /**
     * Withdraw from camp
     * @param campId    camp id
     * @param student  student object
     * @return 200 if successful, 201 if student is not an attendee, 202 if student is a committee member, 203 if camp does not exist
     */
    public static int withdraw(int campId, Student student) {
        Optional<Camp> camp = CampRepository.findByID(campId);
        if (camp.isPresent()) {
            if (!checkIfCampCommittee(camp.get(), student.getUserId())) {
                if (checkIfAttendee(camp.get(), student.getUserId())) {
                    camp.get().removeAttendee(student);
                    return 200;
                }
                else
                    return 201;
            }
            else
                return 202;
        }
        else
            return 203;
    }
}
