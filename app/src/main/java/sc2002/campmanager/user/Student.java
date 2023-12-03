package sc2002.campmanager.user;
//Optional<Camp> camp = CampManager.getCampById(campCommittee);
//        if(camp.isPresent())
//        return camp.get();
//        return null;
import sc2002.campmanager.camp.Camp;
import sc2002.campmanager.camp.CampManager;
import sc2002.campmanager.camp.CampRegistrationManager;
import sc2002.campmanager.requests.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;


/**
 * Represents a student user in the camp management system, extending the base User class.
 */
public class Student extends User {

    // Fields...

    private List<Integer> ownEnquiry;

    private List<Integer> ownSuggestion;
    private ArrayList<Integer> withdrawnFrom;
    private int campCommittee;
    private int points;

    /**
     * Constructs a new Student object with the given details.
     *
     * @param userId          The unique identifier of the student.
     * @param faculty         The faculty to which the student belongs.
     * @param userName        The unique username of the student.
     * @param email           The email address associated with the student's account.
     * @param password        The password associated with the student's account.
     */
    public Student(String userId, String faculty, String userName, String email, String password) {
        super( userId,  faculty,  userName,  email,  password);
        withdrawnFrom = new ArrayList<>();
        userType = UserType.STUDENT;
        campCommittee = -1;
        points = 0;
        ownEnquiry = null;
        ownSuggestion = null;
    }

    /**
     * Constructs a new Student object with the given details, including additional parameters.
     *
     * @param userId          The unique identifier of the student.
     * @param faculty         The faculty to which the student belongs.
     * @param userName        The unique username of the student.
     * @param email           The email address associated with the student's account.
     * @param password        The password associated with the student's account.
     * @param campCommittee   The camp committee ID associated with the student.
     * @param ownCamps        The list of camp IDs owned by the student.
     * @param withdrawnFrom   The list of camp IDs from which the student has withdrawn.
     * @param points          The points the student has
     * @param ownEnquiry      The list of enquiry IDs associated with the student.
     * @param ownSuggestion   The list of suggestion IDs associated with the student.
     */
    public Student(String userId, String faculty, String userName, String email,
                   String password, int campCommittee, ArrayList<Integer> ownCamps,
                   ArrayList<Integer> withdrawnFrom, int points,
                   List<Integer> ownEnquiry, List<Integer> ownSuggestion) {

        super(userId,  faculty,  userName,  email,  password);
        this.withdrawnFrom = withdrawnFrom;
        this.ownCamps = ownCamps;
        userType = UserType.STUDENT;
        this.campCommittee = campCommittee;
        this.points = points;
        this.ownEnquiry = ownEnquiry;
        this.ownSuggestion = ownSuggestion;
    }


    /**
     * Gets the camp committee ID associated with the student.
     *
     * @return The camp committee ID.
     */
    public int getCampCommittee() {
        return campCommittee;
    }

    /**
     * Sets the camp committee ID associated with the student.
     *
     * @param campCommittee The new camp committee ID to set.
     */
    public void setCampCommittee(int campCommittee) {
        this.campCommittee = campCommittee;
    }

    /**
     * Gets the list of camp IDs from which the student has withdrawn.
     *
     * @return The list of withdrawn camp IDs.
     */
    public ArrayList<Integer> getWithdrawnFrom() {
        return withdrawnFrom;
    }

    /**
     * Increments the points for the student.
     */
    public void setPoints(){
        points = points+1;
    }

    /**
     * Gets the points accumulated by the student.
     *
     * @return The points accumulated.
     */
    public int getPoints(){
        return points;
    }

    /**
     * Gets the points accumulated by the student.
     *
     * @return The points accumulated.
     */
    public boolean isCampCommittee() {
        return campCommittee != -1;
    }

    /**
     * Gets the list of enquiry IDs associated with the student.
     *
     * @return The list of enquiry IDs.
     */
    public List<Integer> getOwnEnquiry() {
        return ownEnquiry;
    }

    /**
     * Gets the list of suggestion IDs associated with the student.
     *
     * @return The list of suggestion IDs.
     */
    public List<Integer> getOwnSuggestion() {
        return ownSuggestion;
    }

    /**
     * Registers the student for a camp as either a committee member or an attendee.
     *
     * @param campId              The ID of the camp to register for.
     * @param requestCampCommittee True if the student wants to register as a committee member.
     * @return A status message indicating the result of the registration.
     */
    public String register(int campId, boolean requestCampCommittee) {
        if (requestCampCommittee) {
            switch (CampRegistrationManager.registerCommitteeMember(campId, this)) {
                case 200:
                    this.setCampCommittee(campId);
                    return "Success";
                case 201:
                    return "All slots full";
                case 202:
                    return "Can't be member of more than one camp committee.";
                case 203:
                    return "Can't be the committee member for a camp you are attending.";
                case 204:
                    return "Deadline passed";
                case 205:
                    return "Cannot register as camp committee member as you are already a camp committee member in campID : " + campId;
                default:
                    return "Internal Error";
            }
        } else {
            if (!withdrawnFrom.contains(campId)) {
                switch (CampRegistrationManager.registerAttendee(campId, this)) {
                    case 200:
                        ownCamps.add(campId);
                        return "Success";
                    case 201:
                        return "You are not an attendee in this camp";
                    case 202:
                        return "Can't withdraw from a camp committee position.";
                    case 203:
                        return "Not a valid camp";
                    default:
                        return "Internal error";
                }
            }
            return "Can't sign up for a camp you have withdrawn from.";

        }

    }

    /**
     * Withdraws the student from a camp.
     *
     * @param campId The ID of the camp to withdraw from.
     * @return A status message indicating the result of the withdrawal.
     */
    public String withdraw(int campId){
        switch (CampRegistrationManager.withdraw(campId, this)) {
            case 200:
                ownCamps.remove(Integer.valueOf(campId));
                withdrawnFrom.add(campId);
                return "Success";
            case 201:
                return "All slots full";
            case 202:
                return "Can't be member of more than one camp committee.";
            case 203:
                return "Can't be the committee member for a camp you are attending.";
            case 204:
                return "Deadline passed";
            case 205:
                return "Cannot register as camp committee member as you are already a camp committee member in campID : " + campId;
            default:
                return "Internal Error";
        }
    }

    /**
     * Views all available camps based on certain filters (e.g., date, location).
     *
     * @return The list of camps that match the specified filters.
     */
    public List<Camp> viewCamps() {
        List<Camp> camps = CampManager.getAllCamps();
        List<Camp> filteredCamps = camps.stream().filter(t -> t.isVisible()).collect(Collectors.toList());
        filteredCamps = filteredCamps.stream().filter(t -> (t.getUserGroup()==Camp.UserGroup.WHOLE_NTU) ||(UserManager.getFacultyByUserId(t.getStaffInCharge()).equalsIgnoreCase(this.getFaculty()))).collect(Collectors.toList());
        //filteredCamps = filteredCamps.stream().filter(t-> ).collect(Collectors.toList());
        Collections.sort(filteredCamps, (c1, c2) -> c1.getCampName().compareTo(c2.getCampName()));
        return filteredCamps;
    }

    /**
     * Views camps filtered by location.
     *
     * @param location The location to filter camps by.
     * @param camps    The list of camps to filter.
     * @return The list of camps that match the specified location.
     */
    public List<Camp> viewFilteredCamps(String location, List<Camp> camps) {//add visibility
        List<Camp> filteredCamps = new ArrayList<>();
        filteredCamps.stream().filter(t -> t.getLocation().equals(location)).collect(Collectors.toList());
        Collections.sort(filteredCamps, (c1, c2) -> c1.getCampName().compareTo(c2.getCampName()));
        return filteredCamps;
    }

    /**
     * Views camps filtered by date.
     *
     * @param date  The date range to filter camps by.
     * @param camps The list of camps to filter.
     * @return The list of camps that match the specified date range.
     */
    public List<Camp> viewFilteredCamps(List<LocalDate> date, List<Camp> camps) {
        List<Camp> filteredCamps = new ArrayList<>();
        filteredCamps = camps.stream().filter(c -> c.getDates().get(0).isAfter(date.get(0))).collect(Collectors.toList());
        filteredCamps = camps.stream().filter(c -> c.getDates().get(c.getDates().size()-1).isBefore(date.get(1))).collect(Collectors.toList());
        Collections.sort(filteredCamps, (c1, c2) -> c1.getCampName().compareTo(c2.getCampName()));
        return filteredCamps;
    }

    /**
     * Views camps owned by the student.
     *
     * @return The list of camps owned by the student.
     */
    public List<Camp> viewOwnCamps() {
        List<Camp> camps = new ArrayList<>();
        if(ownCamps.isEmpty())
            return camps;
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
     * Submits an enquiry for a specific camp.
     *
     * @param campId   The ID of the camp to enquire about.
     * @param enquiry  The enquiry message.
     * @return A status message indicating the result of the enquiry submission.
     */
    public String enquire(int campId, String enquiry ){
        //String enquiry = sc.nextLine();
        if(campCommittee==campId){
            return "Can't enquire about your camp";
        }
        else {
            ownEnquiry.add(RequestManager.newEnquiry(campId, this.userId, enquiry));
            return "Success";
        }
    }

    /**
     * Edits a previously submitted enquiry.
     *
     * @param ReqId          The ID of the enquiry to edit.
     * @param editedEnquiry  The edited enquiry message.
     * @return A status message indicating the result of the edit operation.
     */
    public String editEnquiry(int ReqId, String editedEnquiry){
        Enquiry enquiry = RequestManager.getEnquiry(ReqId).get();
        if (enquiry.getStatus()==RequestStatus.PROCESSED)
            return "Unable to update as enquiry has already been processed.";
        else {
            RequestManager.editEnquiry(enquiry.getReqID(), editedEnquiry);
            return "Enquiry updated";
        }
    }

    /**
     * Deletes a previously submitted enquiry.
     *
     * @param ReqId  The ID of the enquiry to delete.
     * @return A status message indicating the result of the delete operation.
     */
    public String deleteEnquiry(int ReqId){
        Enquiry enquiry = RequestManager.getEnquiry(ReqId).get();
        if(enquiry.getStatus()==RequestStatus.PROCESSED){
            return "Enquiry has been processed you cant delete it now.";
        }
        RequestManager.delEnquiry(ReqId);
        return "Success";
    }

    /**
     * Submits a suggestion for a specific camp.
     *
     * @param campId     The ID of the camp to suggest for.
     * @param suggestion The suggestion message.
     */
    public void suggest(int campId, String suggestion) {
        points++;
        ownSuggestion.add(RequestManager.newSuggestion(campId, this.userId, suggestion));
        //give them a point
        //the calling method should have checked if the user is a committee member before offering this method.
    }

    /**
     * Edits a previously submitted suggestion.
     *
     * @param ReqId            The ID of the suggestion to edit.
     * @param editedSuggestion The edited suggestion message.
     * @return A status message indicating the result of the edit operation.
     */
    public String editSuggestion( int ReqId, String editedSuggestion ){
        Optional<Suggestion> suggestion = RequestManager.getSuggestion(ReqId);
        if (!suggestion.isPresent()) {
            return "Invalid Enquiry Id";
        }
        if (suggestion.get().getStatus()== RequestStatus.PROCESSED)
            return "Unable to update as suggestion has already been processed.";
        else {
            RequestManager.editSuggestion(ReqId, editedSuggestion);
            return "Suggestion updated";
        }
    }

    /**
     * Deletes a previously submitted suggestion.
     *
     * @param ReqId The ID of the suggestion to delete.
     * @return A status message indicating the result of the delete operation.
     */
    public String deleteSuggestion(int ReqId) {
        Optional<Suggestion> suggestion = RequestManager.getSuggestion(ReqId);
        if (!suggestion.isPresent()) return "Invalid Suggestion Id";
        if (suggestion.get().getStatus()==RequestStatus.PROCESSED) return "Suggestion has been processed you can't delete it now.";
        RequestManager.delSuggestion(ReqId);
        return "Success";
    }

    /**
     * Views all enquiries submitted by the student.
     *
     * @return The list of all enquiries submitted by the student.
     */
    public List<Enquiry> viewAllEnquiries() {
        List<Enquiry> ownEnquiries = new ArrayList<>();
        if(ownEnquiry.isEmpty())
            return ownEnquiries;
        for (int reqId:
             ownEnquiry) {
            if(RequestManager.getEnquiry(reqId).isPresent()) ownEnquiries.add(RequestManager.getEnquiry(reqId).get());
        }
        return ownEnquiries;
    }

    /**
     * Views all suggestions submitted by the student.
     *
     * @return The list of all suggestions submitted by the student.
     */
    public List<Suggestion> viewAllSuggestions() {
        List<Suggestion> ownSuggestions = new ArrayList<>();
        if(ownSuggestion.isEmpty())
            return ownSuggestions;
        for (int reqId:
                ownSuggestion) {
            if(RequestManager.getSuggestion(reqId).isPresent()) ownSuggestions.add(RequestManager.getSuggestion(reqId).get());
        }
        return ownSuggestions;
    }

    /**
     * Views open enquiries related to the camp committee the student is part of.
     *
     * @return The list of open enquiries for the camp committee.
     */
    public List<Enquiry> viewOpenEnquiries() {
        List<Enquiry> openEnquiries = new ArrayList<Enquiry>();
        for (Enquiry enquiry : RequestManager.getEnquiriesCamp(campCommittee)) {
            if (enquiry.getStatus()==RequestStatus.PROCESSED)
                continue;
            openEnquiries.add(enquiry);
        }
        return openEnquiries;
    }

    /**
     * Replies to a specific enquiry.
     *
     * @param ReqId The ID of the enquiry to reply to.
     * @param reply The reply message.
     * @return A status message indicating the result of the reply operation.
     */
    public String replyEnquiry(int ReqId, String reply) {
        Optional<Enquiry> enquiry = RequestManager.getEnquiry(ReqId);
        if(!enquiry.isPresent()) return "Invalid Enquiry Id";
        if (enquiry.get().getStatus()==RequestStatus.PROCESSED) return "Enquiry has already been processed.";
        RequestManager.replyEnquiry(ReqId, this.userId, reply);
        points++;
        return "Successful";
    }

    /**
     * Generates a performance report for a specific camp based on specified filters.
     *
     * @param campId The ID of the camp for which to generate the report.
     * @param filter The filter to apply to the report (0: All, 1: Attendees, 2: Committee Members).
     * @return A status message indicating the result of the report generation.
     */
    public String generateReport(int campId, int filter) {
//        The list will include details of the camp as well as  the roles of the participants.
//        There should be filters for how the camp committee  member would want to generate the list.
//    (attendee, camp committee, etc.) (generate in  either txt or csv format).
        Optional<Camp> campOptional = CampManager.getCampById(campCommittee);
        if(!campOptional.isPresent()) return "Invalid Camp Id";
        Camp camp = campOptional.get();
        String fileName = "/Users/mythilimulani/Desktop/GitHub/SC2002-CampManager/app/src/main/resources/campId" + "_" + this.userId + "_" + "PerformanceReport_" +LocalDateTime.now().toString() +".txt";
        // Write content to the file
        String header=null;
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
                    "Author: " + this.userId+
                    "\nCreated: "+ LocalDateTime.now();
            campDetails.append("\nCommittee Members : \n");
            for (String student : camp.getCampCommitteeMembers()) {
                campDetails.append("\t\t" + student + "\n");
            }
            campDetails.append("\nAttendees : \n");
            for (String student : camp.getCampAttendees()) {
                campDetails.append("\t\t" + student + "\n");
            }
        } else if (filter == 1) {//attendees
            header = "\t\t\t\t\t Camp Attendee Report\n"+
                    "Author: " + this.userId+
                    "\nCreated: "+ LocalDateTime.now();
            campDetails.append("\nAttendees : \n");
            for (String student : camp.getCampAttendees()) {
                campDetails.append("\t\t" + student + "\n");
            }
        } else if (filter == 2) {//committee members
            header = "\t\t\t\t\t Camp Committee Report\n"+
                    "Author: " + this.userId+
                    "\nCreated: "+ LocalDateTime.now();
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

}
