package sc2002.campmanager.user;

import sc2002.campmanager.camp.Camp;
import sc2002.campmanager.camp.CampEditManager;
import sc2002.campmanager.camp.CampManager;
import sc2002.campmanager.requests.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents a staff user in the camp management system, extending the base User class.
 */
public class Staff extends User {

    /**
     * Constructs a new Staff object with the specified details.
     *
     * @param userId    The unique identifier of the staff user.
     * @param faculty   The faculty to which the staff user belongs.
     * @param userName  The unique username of the staff user.
     * @param email     The email address associated with the staff user's account.
     * @param password  The password associated with the staff user's account.
     */
    public Staff(String userId, String faculty, String userName, String email, String password) {
        super( userId,  faculty,  userName,  email,  password);
        this.userType = UserType.STAFF;
        ownCamps = new ArrayList<>();
    }

    /**
     * Constructs a new Staff object with the specified details, including the list of camp IDs owned by the staff.
     *
     * @param userId    The unique identifier of the staff user.
     * @param faculty   The faculty to which the staff user belongs.
     * @param userName  The unique username of the staff user.
     * @param email     The email address associated with the staff user's account.
     * @param password  The password associated with the staff user's account.
     * @param ownCamps  The list of camp IDs owned by the staff.
     */
    public Staff(String userId, String faculty, String userName, String email, String password, ArrayList<Integer> ownCamps) {
        super( userId,  faculty,  userName,  email,  password);
        this.userType = UserType.STAFF;
        this.ownCamps = ownCamps;
    }

    /**
     * Creates a new camp with the specified details and adds it to the list of owned camps.
     *
     * @param campName             The name of the camp.
     * @param visibility           The visibility status of the camp.
     * @param dates                The list of dates for the camp.
     * @param registrationCloseDate The registration close date for the camp.
     * @param userGroup            The user group associated with the camp.
     * @param location             The location of the camp.
     * @param totalSlots           The total number of slots available in the camp.
     * @param campCommitteeSlots   The number of slots reserved for camp committee members.
     * @param description          The description of the camp.
     */
    public void createCamp(String campName, boolean visibility,
                           List<LocalDate> dates, LocalDate registrationCloseDate, Camp.UserGroup userGroup,
                           String location, int totalSlots, int campCommitteeSlots,
                           String description) {
        ownCamps.add(CampManager.createCamp(campName, visibility, dates, registrationCloseDate,
                location, totalSlots, this.userId, campCommitteeSlots, description, userGroup).getCampId());
    }

    /**
     * Deletes a camp with the given camp ID.
     *
     * @param campId The unique identifier of the camp to be deleted.
     * @return returns a string indicating whether the deletion was successful or not
     */
    public String deleteCamp(int campId) {
        Camp camp = CampManager.getCampById(campId).get();
        if(camp.isVisible()){
            return "Cannot delete a visible camp.";
        }
        CampManager.deleteCamp(campId);
        ownCamps.remove(Integer.valueOf(campId));
        return "Successful";
    }

    /**
     * Edits the name of a camp.
     *
     * @param campId   The unique identifier of the camp.
     * @param campName The new name for the camp.
     * @return A string indicating the success of the operation.
     */
    public String editCampName(int campId, String campName) {
        CampEditManager.editCampName(campId, campName);
        return "Success";
    }

    /**
     * Toggles the visibility of a camp.
     *
     * @param campId The unique identifier of the camp.
     * @return A string indicating the success of the operation.
     */
    public String editVisibility(int campId) {
        CampEditManager.toggleVisibility(campId);
        return "Success";
    }

    /**
     * Adds dates to a camp.
     *
     * @param campId The unique identifier of the camp.
     * @param dates  The list of dates to be added.
     * @return A string indicating the success of the operation.
     */
    public String addDates(int campId, List<LocalDate> dates) {
        CampEditManager.addDates(campId, dates);
        return "Success";
    }

    /**
     * Deletes dates from a camp.
     *
     * @param campId The unique identifier of the camp.
     * @param dates  The list of dates to be deleted.
     * @return A string indicating the success of the operation.
     */
    public String deleteDates(int campId, List<LocalDate> dates) {
        CampEditManager.deleteDates(campId, dates);
        return "Success";
    }

    /**
     * Edits the registration close date of a camp.
     *
     * @param campId                  The unique identifier of the camp.
     * @param registrationCloseDate   The new registration close date.
     * @return A string indicating the success of the operation.
     */
    public String editRegistrationCloseDate(int campId, LocalDate registrationCloseDate) {
        CampEditManager.editRegistrationCloseDate(campId, registrationCloseDate);
        return "Success";
    }

    /**
     * Edits the location of a camp.
     *
     * @param campId   The unique identifier of the camp.
     * @param location The new location for the camp.
     * @return A string indicating the success of the operation.
     */
    public String editLocation(int campId, String location) {
        CampEditManager.editLocation(campId, location);
        return "Success";
    }

    /**
     * Edits the number of attendee slots in a camp.
     *
     * @param campId        The unique identifier of the camp.
     * @param attendeeSlots The new number of attendee slots.
     * @return A string indicating the success of the operation.
     */
    public String editAttendeeSlots(int campId, int attendeeSlots) {
        return CampEditManager.editAttendeeSlots(campId, attendeeSlots);
    }

    /**
     * Edits the number of camp committee slots in a camp.
     *
     * @param campId             The unique identifier of the camp.
     * @param campCommitteeSlots The new number of camp committee slots.
     * @return A string indicating the success of the operation.
     */
    public String editCampCommitteeSlots(int campId, int campCommitteeSlots) {
        return (CampEditManager.editCampCommitteeSlots(campId, campCommitteeSlots));
    }

    /**
     * Edits the description of a camp.
     *
     * @param campId      The unique identifier of the camp.
     * @param description The new description for the camp.
     * @return A string indicating the success of the operation.
     */
    public String editDescription(int campId, String description) {
        CampEditManager.editDescription(campId, description);
        return "Success";
    }

    /**
     * Retrieves a list of camps with filters such as visibility and sorting.
     *
     * @return The filtered list of camps.
     */
    public List<Camp> viewCamps() {//add visibility
        //add filters  (date, location etc.) , default is alphabetical
        List<Camp> camps = CampManager.getAllCamps();
        List<Camp> filteredCamps = camps.stream().filter(t -> t.isVisible()).collect(Collectors.toList());
        Collections.sort(filteredCamps, (c1, c2) -> c1.getCampName().compareTo(c2.getCampName()));
        return filteredCamps;
    }

    /**
     * Retrieves a list of camps owned by the staff user.
     *
     * @return The list of owned camps.
     */
    public List<Camp> viewOwnCamps() {
        List<Camp> camps = new ArrayList<>();
        if(ownCamps.isEmpty()){
            return camps;
        }
        for (Integer campId : ownCamps) {
            Optional<Camp> camp = CampManager.getCampById(campId);
            if (camp.isPresent())
                camps.add(camp.get());
            else
                ownCamps.remove(Integer.valueOf(campId));
        }
        Collections.sort(camps, (c1, c2) -> c1.getCampName().compareTo(c2.getCampName()));
        return camps;
    }

    /**
     * Retrieves a filtered list of camps based on location.
     *
     * @param location The location used for filtering.
     * @param camps    The list of camps to filter.
     * @return The filtered list of camps.
     */
    public List<Camp> viewFilteredCamps(String location, List<Camp> camps) {//add visibility
        List<Camp> filteredCamps = camps.stream().filter(t -> t.isVisible()).collect(Collectors.toList());
        filteredCamps = filteredCamps.stream().filter(t -> t.getLocation().equals(location)).collect(Collectors.toList());
        Collections.sort(filteredCamps, (c1, c2) -> c1.getCampName().compareTo(c2.getCampName()));
        return filteredCamps;
    }

    /**
     * Retrieves a filtered list of camps based on date.
     *
     * @param date  The date range used for filtering.
     * @param camps The list of camps to filter.
     * @return The filtered list of camps.
     */
    public List<Camp> viewFilteredCamps(List<LocalDate> date, List<Camp> camps) {//add visibility
        List<Camp> filteredCamps = camps.stream().filter(c -> c.isVisible()).collect(Collectors.toList());
        filteredCamps = camps.stream().filter(c -> c.getDates().get(0).isAfter(date.get(0))).collect(Collectors.toList());
        filteredCamps = camps.stream().filter(c -> c.getDates().get(c.getDates().size()-1).isBefore(date.get(1))).collect(Collectors.toList());
        Collections.sort(filteredCamps, (c1, c2) -> c1.getCampName().compareTo(c2.getCampName()));
        return filteredCamps;
    }

    /**
     * Retrieves a list of enquiries for a specific camp.
     *
     * @param campId The unique identifier of the camp.
     * @return The list of enquiries.
     */
    public List<Enquiry> viewEnquiries(int campId) {
            return RequestManager.getEnquiriesCamp(campId);
    }

    /**
     * Retrieves a list of open enquiries for a specific camp.
     *
     * @param campId The unique identifier of the camp.
     * @return The list of open enquiries.
     */
    public List<Enquiry> viewOpenEnquiries(int campId) {
        List<Enquiry> enquiries =  RequestManager.getEnquiriesCamp(campId);
        List<Enquiry> openEnquiries = enquiries.stream().filter(s -> s.getStatus()==RequestStatus.PENDING).collect(Collectors.toList());
        return openEnquiries;
    }

    /**
     * Replies to an enquiry with a given ID.
     *
     * @param reqId The unique identifier of the enquiry.
     * @param reply The reply message.
     * @return A string indicating the success of the operation.
     */
    public String replyEnquiry(int reqId, String reply) {
        Optional<Enquiry> enquiry = RequestManager.getEnquiry(reqId);
        if(!enquiry.isPresent()) return "Invalid Enquiry Id";
        if (enquiry.get().getStatus()== RequestStatus.PROCESSED)
            return "Enquiry has already been processed.";
        RequestManager.replyEnquiry(reqId, this.userId, reply);
        return "Successful";
    }

    /**
     * Retrieves a list of suggestions for a specific camp.
     *
     * @param campId The unique identifier of the camp.
     * @return The list of suggestions.
     */
    public List<Suggestion> viewSuggestions(int campId) {
        return RequestManager.getSuggestionsCamp(campId);

    }

    /**
     * Retrieves a list of open suggestions for a specific camp.
     *
     * @param campId The unique identifier of the camp.
     * @return The list of open suggestions.
     */
    public List<Suggestion> viewOpenSuggestions( int campId) {
        List<Suggestion> suggestions = RequestManager.getSuggestionsCamp(campId);
        List<Suggestion> openSuggestions = suggestions.stream().filter(s -> s.getStatus()==RequestStatus.PENDING).collect(Collectors.toList());
        return openSuggestions;

    }

    /**
     * Approves or rejects a suggestion with a given ID.
     *
     * @param reqId   The unique identifier of the suggestion.
     * @param approve A boolean indicating whether to approve or reject the suggestion.
     * @return A string indicating the success of the operation.
     */
    public String approveSuggestion(int reqId, boolean approve) {
        //add point
        Optional<Suggestion> suggestionOpt = RequestManager.getSuggestion(reqId);
        if(!suggestionOpt.isPresent()) return "Invalid Suggestion Id";
        Request suggestion = suggestionOpt.get();
        if (suggestion.getStatus()==RequestStatus.PROCESSED) {
            return "Unsuccessful. Suggestion already processed.";
        }
        if (approve) {
            RequestManager.acceptSuggestion(reqId);
            UserManager.getStudentByUserId(suggestion.getReqAuthor()).setPoints();
        } else
            RequestManager.rejectSuggestion(reqId);
        return "Successful";
    }

//    A staff can generate a report of the list of students attending each camp that his/her has
//    created. The list will include details of the camp as well as the roles of the  participants.
//    There should be filters for how the staff would want to generate the list.  (attendee, camp
//    committee, etc.) (generate in either txt or csv format).
//    A staff can also generate a performance report of the camp committee members.

    /**
     * Generates a report for the list of students attending a specific camp.
     *
     * @param campId The unique identifier of the camp.
     * @param filter The filter for generating the list (attendee, camp committee, etc.).
     * @return A string indicating the success of the operation.
     */
    public String generateReport(int campId, int filter) {
//        The list will include details of the camp as well as  the roles of the participants.
//        There should be filters for how the camp committee  member would want to generate the list.
//    (attendee, camp committee, etc.) (generate in  either txt or csv format).
        Optional<Camp> campOpt = CampManager.getCampById(campId);
        if(!campOpt.isPresent()) return "Invalid Camp Id";
        Camp camp = campOpt.get();
        String fileName = "/Users/mythilimulani/Desktop/GitHub/SC2002-CampManager/app/src/main/resources/" +campId+ "_" + this.userId + "_" + "CampReport_" +LocalDateTime.now().toString() +".txt";
        // Write content to the file
        String header = null;
        StringBuffer campDetails = new StringBuffer("Id : " + campId
                + "\nName :" + camp.getCampName()
                + "\nStaff-in-charge : " + camp.getStaffInCharge()
                + "\nDescription : " + camp.getDescription()
                + "\nRegistration Close Date : " + camp.getRegistrationCloseDate()
                + "\nTotal Slots : " + camp.getTotalSlots()
                + "\nCommittee Slots : " + camp.getCampCommitteeSlots()
                + "\nAttendee Slots : " + (camp.getTotalSlots() - camp.getCampCommitteeSlots()));

        if (filter == 0) {
            header = "\t\t\t\t\t Camp Report\n" +
                    "Author: " + this.userId +
                    "\nCreated: " + LocalDateTime.now();
            campDetails.append("\nCommittee Members : \n");
            for (String student : camp.getCampCommitteeMembers()) {
                campDetails.append("\t\t" + student + "\n");
            }
            campDetails.append("\nAttendees : \n");
            for (String student : camp.getCampAttendees()) {
                campDetails.append("\t\t" + student + "\n");
            }
        } else if (filter == 1) {//attendees
            header = "\t\t\t\t\t Camp Attendee Report\n" +
                    "Author: " + this.userId +
                    "\nCreated: " + LocalDateTime.now();
            campDetails.append("\nAttendees : \n");
            for (String student : camp.getCampAttendees()) {
                campDetails.append("\t\t" + student + "\n");
            }
        } else if (filter == 2) {//committee members
            header = "\t\t\t\t\t Camp Committee Report\n" +
                    "Author: " + this.userId +
                    "\nCreated: " + LocalDateTime.now();
            campDetails.append("\nCommittee Members : \n");
            for (String student : camp.getCampCommitteeMembers()) {
                campDetails.append("\t\t" + student + "\n");
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(header);
            writer.newLine();
            writer.write(campDetails.toString());
            System.out.println("Text file created and written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Successful";
    }

    /**
     * Generates a performance report for a specific camp and student.
     *
     * @param campId       The unique identifier of the camp.
     * @param studentUserId The unique identifier of the student.
     * @param report        The performance report content.
     * @return A string indicating the success of the operation.
     */
    public String generatePerformanceReport(int campId, String studentUserId, String report) {
        Optional<Camp> campOpt = CampManager.getCampById(campId);
        if(!campOpt.isPresent()) return "Invalid Camp Id";
        Camp camp = campOpt.get();
        Student student = UserManager.getStudentByUserId(studentUserId);
        String fileName = "/Users/mythilimulani/Desktop/GitHub/SC2002-CampManager/app/src/main/resources/"+ campId + "_" + this.userId + "_" + "PerformanceReport_" +LocalDateTime.now().toString() +".txt";
        String header = "\t\t\t\t\tStudent Performance Report\n";
        String campDetails = "Camp Id : " + campId
                + "\nCamp Name :" + camp.getCampName()
                + "\nStaff-in-charge : " + camp.getStaffInCharge()
                + "\nStudent Id : " + student.getUserId()
                + "\nStudent Name :" + student.getUserName()
                + "\nPoints :" + student.getPoints()
                + "\n\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(header);
            writer.write(campDetails);
            writer.write(report);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return "Successful";
    }

}
