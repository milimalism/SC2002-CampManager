package sc2002.campmanager.util;

import sc2002.campmanager.camp.Camp;
import sc2002.campmanager.requests.Enquiry;
import sc2002.campmanager.requests.RequestStatus;
import sc2002.campmanager.requests.Suggestion;
import sc2002.campmanager.user.Student;
import sc2002.campmanager.user.UserManager;

import java.time.LocalDate;
import java.util.List;

/**
 * The Printer class provides static methods for printing details and lists related to camps, enquiries, and suggestions.
 */
public class Printer {

    /**
     * Prints details of a list of enquiries.
     *
     * @param enquiryList The list of enquiries to print.
     */
    public static void printEnquiryList(List<Enquiry> enquiryList) {
        for (Enquiry enquiry :
                enquiryList) {
            System.out.println("Enquiry Id: " + enquiry.getReqID() +
                    "\t\t\t\tStatus: " + enquiry.getStatus() +
                    "\t\t\t\t" + enquiry.getTimestamp().toString() +
                    "\n" + enquiry.getReqDescription());
        }
    }

    /**
     * Prints details of a list of suggestions.
     *
     * @param suggestionList The list of suggestions to print.
     */
    public static void printSuggestionList(List<Suggestion> suggestionList) {
        for (Suggestion suggestion :
                suggestionList) {
            System.out.println("Suggestion Id: " + suggestion.getReqID() + "\t\t\t\tStatus: " + suggestion.getStatus() +
                    "\n" + suggestion.getReqDescription());
        }
    }

    /**
     * Prints details of a specific camp.
     *
     * @param camp The camp whose details are to be printed.
     */
    public static void printCampDetails(Camp camp) {
        StringBuffer campDetails = new StringBuffer("Id : " + camp.getCampId()
                + "\nName :" + camp.getCampName()
                + "\nDescription : " + camp.getDescription()
                + "\nRegistration Close Date : " + camp.getRegistrationCloseDate()
                + "\nRemaining Committee Slots : " + camp.getCurrentCommitteeMemberSlots()
                + "\nRemaining Attendee Slots : " + camp.getCurrentAttendeeSlots()
                + "\nLocation : " + camp.getLocation()
                + "\nDates: \n");
        for (LocalDate date :
                camp.getDates()) {
            campDetails.append(date.toString()+"\n");
        }
        System.out.println(campDetails.toString());
    }


    /**
     * Prints details of all camps, including committee members and attendees.
     *
     * @param camp The camp whose details are to be printed.
     */
    public static void printAllCampDetails(Camp camp) {
        StringBuffer campDetails = new StringBuffer("Id : " + camp.getCampId()
                + "\nName :" + camp.getCampName()
                + "\nStaff-in-charge : " + camp.getStaffInCharge()
                + "\nDescription : " + camp.getDescription()
                + "\nRegistration Close Date : " + camp.getRegistrationCloseDate()
                + "\nTotal Slots : " + camp.getTotalSlots()
                + "\nCommittee Slots : " + camp.getCampCommitteeSlots()
                + "\nAttendee Slots : " + camp.getCampAttendeeSlots()
                + "\nRemaining Committee Slots : " + camp.getCurrentCommitteeMemberSlots()
                + "\nRemaining Attendee Slots : " + camp.getCurrentAttendeeSlots()
                + "\nLocation : " + camp.getLocation()
                + "\nDates: \n");
        for (LocalDate date :
                camp.getDates()) {
            campDetails.append(date.toString()+"\n");
        }
        campDetails.append("Committee Members : \n");
        for (String student : camp.getCampCommitteeMembers()) {
            campDetails.append(student + "\n");
        }
        campDetails.append("Attendees : \n");
        for (String student : camp.getCampAttendees()) {
            campDetails.append(student + "\n");
        }
        System.out.println(campDetails.toString());
    }


    /**
     * Prints details of a specific enquiry.
     *
     * @param enquiry The enquiry whose details are to be printed.
     */
    public static void printEnquiryDetails(Enquiry enquiry) {
        System.out.println("Enquiry Id: " + enquiry.getReqID() +
                "\t\t\t\tStatus: " + enquiry.getStatus() +
                "\t\t\t\t" + enquiry.getTimestamp().toString() +
                "\nAuthor : " + enquiry.getReqAuthor() +
                "\n" + enquiry.getReqDescription());
        if (enquiry.getStatus() == RequestStatus.PROCESSED)
            System.out.println("\nResponse Author: " + enquiry.getResponse().getAuthor()
                    + "\nResponse: " + enquiry.getResponse().getResponseDesc());
    }

    /**
     * Prints details of a specific suggestion.
     *
     * @param suggestion The suggestion whose details are to be printed.
     */
    public static void printSuggestionDetails(Suggestion suggestion) {
        if (suggestion.getApproval()) {
            System.out.println("Suggestion Id: " + suggestion.getReqID() +
                    "\nAuthor : " + suggestion.getReqAuthor() +
                    "\t\t\t\tStatus: " + suggestion.getStatus() +
                    "\n" + suggestion.getReqDescription() +
                    "\t\t\t\tApproval Status: " + suggestion.getApproval() +
                    "\t\t\t\t" + suggestion.getTimestamp().toString()
            );
        } else
            System.out.println("Suggestion Id: " + suggestion.getReqID() +
                    "\nAuthor : " + suggestion.getReqAuthor() +
                    "\t\t\t\tStatus: " + suggestion.getStatus() +
                    "\n" + suggestion.getReqDescription() +
                    "\t\t\t\t" + suggestion.getTimestamp().toString()
            );
    }

    /**
     * Prints a list of camps with basic information.
     *
     * @param campList The list of camps to print.
     */
    public static void printCampList(List<Camp> campList) {
        System.out.println();
        for (Camp camp :
                campList) {
            System.out.println("Camp Name: " + camp.getCampName() +
                    "\nCamp Id: " + camp.getCampId() +
                    "\n" + camp.getDescription() + "\n");
        }

    }

    /**
     * Prints details of committee members for a specific camp.
     *
     * @param campCommitteeMembers The list of committee members to print.
     */
    public static void printCommitteeMembers(List<String> campCommitteeMembers) {
        int i = 1;
        Student student;
        for (String userId :
                campCommitteeMembers) {
            student = UserManager.getStudentByUserId(userId);
            System.out.println(i + ". Name: " + userId + "\n  Id: " + student.getUserName());
            i++;
        }
    }

    /**
     * Prints details of attendees for a specific camp.
     * @param camp The camp whose attendees are to be printed.
     */
    public static void displayDates(Camp camp) {
        for (LocalDate date :
                camp.getDates()) {
            System.out.println((date.toString()));
        }
    }
}
