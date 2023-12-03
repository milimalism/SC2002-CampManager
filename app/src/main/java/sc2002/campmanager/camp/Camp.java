package sc2002.campmanager.camp;

import sc2002.campmanager.user.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Camp class
 * Contains all the information about a camp
 */
public class Camp {

    private int campId;
    private String campName;
    private boolean visibility;
    private List<LocalDate> dates;
    private LocalDate registrationCloseDate;
    private String location;
    private int totalSlots;
    private int campAttendeeSlots;
    private final String staffInCharge;
    private List<String> campAttendees;
    private int campCommitteeSlots;
    private List<String> campCommitteeMembers;
    private String description;
    private int currentAttendeeSlots;
    private int currentCommitteeMemberSlots;
    private List<Integer> enquiries;
    private List<Integer> suggestions;
    private UserGroup userGroup;

    public enum UserGroup {
        OWN_SCHOOL,
        WHOLE_NTU
    }

    /**
     * Constructor for Camp using existing Camp object
     * @param c Camp to be copied
     */
    public Camp(Camp c) {
        this.campId = c.getCampId();
        this.campName = c.getCampName();
        this.visibility = c.isVisible();
        this.dates = c.getDates();
        this.registrationCloseDate = c.getRegistrationCloseDate();
        this.location = c.getLocation();
        this.totalSlots = c.getTotalSlots();
        this.staffInCharge = c.getStaffInCharge();
        this.campAttendees = c.getCampAttendees();
        this.campCommitteeSlots = c.getCampCommitteeSlots();
        this.campCommitteeMembers = c.getCampCommitteeMembers();
        this.description = c.getDescription();
        this.currentAttendeeSlots = c.getCurrentAttendeeSlots();
        this.currentCommitteeMemberSlots = c.getCurrentCommitteeMemberSlots();
        this.enquiries = c.getEnquiries();
        this.suggestions = c.getSuggestions();
        this.userGroup = c.getUserGroup();
        this.campAttendeeSlots = c.getCampAttendeeSlots();
    }

    /**
     * Constructor for Camp to be created from params
     * @param campId Camp ID
     * @param campName Camp name
     * @param visibility Visibility of camp
     * @param dates List of dates
     * @param registrationCloseDate Registration close date
     * @param location Location of camp
     * @param campAttendeeSlots Number of slots for attendees
     * @param staffInCharge Staff in charge
     * @param campCommitteeSlots Number of slots for committee members
     * @param description  Description of camp
     * @param userGroup enum OWN_SCHOOL or WHOLE_NTU
     */
    public Camp(int campId, String campName, boolean visibility, List<LocalDate> dates, LocalDate registrationCloseDate,
                String location, int campAttendeeSlots, String staffInCharge, int campCommitteeSlots, String description, UserGroup userGroup) {
        this.campId = campId;
        this.campName = campName;
        this.visibility = visibility;
        this.dates = dates;
        this.registrationCloseDate = registrationCloseDate;
        this.location = location;
        this.campAttendeeSlots = campAttendeeSlots;
        this.staffInCharge = staffInCharge;
        this.campCommitteeSlots = campCommitteeSlots;
        this.description = description;
        this.currentCommitteeMemberSlots = campCommitteeSlots;
        this.currentAttendeeSlots = campAttendeeSlots;
        this.totalSlots = campAttendeeSlots + campCommitteeSlots;

        // Initialize the lists that were not passed as parameters
        this.campAttendees = new ArrayList<>(); // Empty list, as no attendees are passed to the constructor
        this.campCommitteeMembers = new ArrayList<>(); // Empty list, as no committee members are passed to the constructor
        this.userGroup = userGroup;
        this.enquiries = new ArrayList<>();
        this.suggestions = new ArrayList<>();
    }

    /**
     * Constructor for Camp
     * @param campId Camp ID
     * @param campName Camp name
     * @param visibility Visibility of camp
     * @param dates List of dates
     * @param registrationCloseDate Registration close date
     * @param location Location of camp
     * @param campAttendeeSlots Number of slots for attendees
     * @param staffInCharge Staff in charge
     * @param campCommitteeSlots Number of slots for committee members
     * @param description Description of camp
     * @param userGroup enum OWN_SCHOOL or WHOLE_NTU
     * @param currentCampCommitteeSlots Number of current committee member slots
     * @param currentCampAttendeeSlots Number of current attendee slots
     */
    public Camp(int campId, String campName, boolean visibility, List<LocalDate> dates, LocalDate registrationCloseDate,
                String location, int campAttendeeSlots, String staffInCharge, int campCommitteeSlots, String description, UserGroup userGroup, int currentCampCommitteeSlots, int currentCampAttendeeSlots) {
        this.campId = campId;
        this.campName = campName;
        this.visibility = visibility;
        this.dates = dates;
        this.registrationCloseDate = registrationCloseDate;
        this.location = location;
        this.campAttendeeSlots = campAttendeeSlots;
        this.staffInCharge = staffInCharge;
        this.campCommitteeSlots = campCommitteeSlots;
        this.description = description;
        this.currentCommitteeMemberSlots = currentCampCommitteeSlots;
        this.currentAttendeeSlots = currentCampAttendeeSlots;
        this.totalSlots = campAttendeeSlots + campCommitteeSlots;

        // Initialize the lists that were not passed as parameters
        this.campAttendees = new ArrayList<>(); // Empty list, as no attendees are passed to the constructor
        this.campCommitteeMembers = new ArrayList<>(); // Empty list, as no committee members are passed to the constructor
        this.userGroup = userGroup;
        this.enquiries = new ArrayList<>();
        this.suggestions = new ArrayList<>();
    }


    // Getters

    /**
     * Return total num of slots for attendees
     * @return int total num of slots for attendees
     */
    public int getCampAttendeeSlots() {
        return campAttendeeSlots;
    }
    /**
     * Return List of enquiries
     * @return List of enquiries
     */
    public List<Integer> getEnquiries() {
        return enquiries;
    }

    /**
     * Return List of suggestions
     * @return List of suggestions
     */
    public List<Integer> getSuggestions() {
        return suggestions;
    }

    /**
     * Return the campId
     * @return int campId
     */
    public int getCampId() {
        return campId;
    }

    /**
     * Return the campName
     * @return String campName
     */
    public String getCampName() {
        return campName;
    }

    /**
     * Check if the camp is visible or not
     * @return boolean visibility
     */
    public boolean isVisible() {
        return visibility;
    }

    /**
     * Return the list of dates
     * @return List of dates
     */
    public List<LocalDate> getDates() {
        return dates;
    }

    /**
     * Return the registration close date
     * @return LocalDate registrationCloseDate
     */
    public LocalDate getRegistrationCloseDate() {
        return registrationCloseDate;
    }

    /**
     * Return the location of the camp
     * @return String location
     */ 
    public String getLocation() {
        return location;
    }

    /**
     * Return the total number of slots
     * @return int totalSlots
     */
    public int getTotalSlots() {
        return totalSlots;
    }

    /**
     * Return the staff in charge
     * @return String staffInCharge
     */
    public String getStaffInCharge() {
        return staffInCharge;
    }

    /**
     * Return the list of camp attendees
     * @return List of camp attendees
     */
    public List<String> getCampAttendees() {
        return campAttendees;
    }

    /**
     * Return the number of slots for committee members
     * @return int campCommitteeSlots
     */
    public int getCampCommitteeSlots() {
        return campCommitteeSlots;
    }


    /**
     * Return the list of camp committee members
     * @return List of camp committee members
     */
    public List<String> getCampCommitteeMembers() {
        return campCommitteeMembers;
    }

    /**
     * Return the description of the camp
     * @return String description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Return enum of whether the camp is for whole NTU or own school
     * @return enum OWN_SCHOOL or WHOLE_NTU
     */
    public UserGroup getUserGroup() {
        return this.userGroup;
    }

//    public RequestManager getRequestManager() {
//        return requestManager;
//    }

    /**
     * Return the number of current attendee slots
     * @return int currentAttendeeSlots
     */
    public int getCurrentAttendeeSlots() {
        return this.currentAttendeeSlots;
    }

    /**
     * Return the number of current committee member slots
     * @return int currentCommitteeMemberSlots
     */
    public int getCurrentCommitteeMemberSlots() {
        return this.currentCommitteeMemberSlots;
    }

    // Setters

    /**
     * Set the number of slots for attendees
     * @param campAttendeeSlots int number of slots for attendees
     */
    public void setCampAttendeeSlots(int campAttendeeSlots) {
        this.campAttendeeSlots = campAttendeeSlots;
        this.totalSlots = campAttendeeSlots + this.campCommitteeSlots;
    }

    /**
     * Set the number of current attendee slots
     * @param enquiries List of enquiries
     */
    public void setEnquiries(List<Integer> enquiries) {
        this.enquiries = enquiries;
    }

    /**
     * Set the number of current committee member slots
     * @param enquiry int enquiry
     */
    public void addEnquiries(int enquiry) {
        this.enquiries.add(enquiry);
    }

    /**
     * Set the number of current committee member slots
     * @param suggestions List of suggestions
     */
    public void setSuggestions(List<Integer> suggestions) {
        this.suggestions = suggestions;
    }

    /**
     * Set the number of current committee member slots
     * @param suggestion int suggestion
     */
    public void addSuggestions(int suggestion) {
        this.suggestions.add(suggestion);
    }

    /**
     * Set the CampId
     * @param campId int campId
     */
    public void setCampId(int campId) {
        this.campId = campId;
    }

    /**
     * Set the camp name
     * @param campName String campName
     */ 
    public void setCampName(String campName) {
        this.campName = campName;
    }

    /**
     * Set the visibility of the camp
     * @param visibility boolean visibility
     */
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * Set the list of dates
     * @param dates List of dates
     */
    public void setDates(List<LocalDate> dates) {
        this.dates = dates;
    }

    /**
     * Set the registration close date
     * @param registrationCloseDate LocalDate registrationCloseDate
     */
    public void setRegistrationCloseDate(LocalDate registrationCloseDate) {
        this.registrationCloseDate = registrationCloseDate;
    }

    /**
     * Set the location of the camp
     * @param location  String location
     */
    public void setLocation(String location) {
        this.location = location;
    }


//    public void setStaffInCharge(Staff staffInCharge) {
//        this.staffInCharge = staffInCharge;
//    }

    /**
     * Set the total number of slots
     * @param campAttendees List of campAttendees
     */
    public void setCampAttendees(List<String> campAttendees) {
        this.campAttendees = campAttendees;
    }

    /**
     * Set the number of slots for committee members
     * @param campCommitteeSlots    int number of slots for committee members
     */
    public void setCampCommitteeSlots(int campCommitteeSlots) {
        this.campCommitteeSlots = campCommitteeSlots;
        this.totalSlots = campCommitteeSlots + this.campAttendeeSlots;
    }

    /**
     * Set the list of camp committee members
     * @param campCommitteeMembers List of camp committee members
     */
    public void setCampCommitteeMembers(List<String> campCommitteeMembers) {
        this.campCommitteeMembers = campCommitteeMembers;
    }

    /**
     * Set the description of the camp
     * @param description String description
     */
    public void setDescription(String description) {
        this.description = description;
    }

//    public void setRequestManager(RequestManager requestManager) {
//        this.requestManager = requestManager;
//    }

    /**
     * add a student to the list of camp committee members
     * @param student Student
     */
    public void addCampCommitteeMember(Student student) {
        this.campCommitteeMembers.add(student.getUserId());
        this.currentCommitteeMemberSlots--;
    }

    /**
     * add a student to the list of camp attendees
     * @param student Student
     */
    public void addAttendee(Student student) {
        this.campAttendees.add(student.getUserId());
        this.currentAttendeeSlots--;
    }

//    public void removeCampCommitteeMember(Student student) {
//        this.campCommitteeMembers.remove(student);
//        this.currentCommitteeMemberSlots++;
//    }

    /**
     * remove a student from the list of camp attendees
     * @param student   Student
     */
    public void removeAttendee(Student student) {
        this.campAttendees.remove(student.getUserId());
        this.currentAttendeeSlots++;
    }

    /**
     * set if the camp is for whole NTU or own school
     * @param userGroup enum OWN_SCHOOL or WHOLE_NTU
     */
    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    /**
     * return a copy of the camp
     * @return Camp copy
     */
    public Camp copy() {
        return new Camp(this);
    }
}
