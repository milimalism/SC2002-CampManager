package sc2002.campmanager.userinterface;

import sc2002.campmanager.camp.*;
import sc2002.campmanager.main.Manager;
import sc2002.campmanager.requests.Enquiry;
import sc2002.campmanager.requests.RequestManager;
import sc2002.campmanager.requests.RequestStatus;
import sc2002.campmanager.requests.Suggestion;
import sc2002.campmanager.util.DateTimeConverter;
import sc2002.campmanager.util.Printer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static sc2002.campmanager.main.Manager.currentStaffUser;
import static sc2002.campmanager.main.Manager.sc;

/**
 * User interface for staff members in the CampManager application.
 */
public class StaffUI {
    /**
     * Displays the staff homepage user interface.
     */
    public static void displayStaffHomepageUI() {
        System.out.println("Welcome, " + currentStaffUser.getUserName());
        System.out.println( "What would you like to do today?" +
                "\n1. View All Camps" +
                "\n2. View Created Camps" +
                "\n3. Create New Camp" +
                "\n4. Log Out");

        int choice = validateIfInt(sc.next());
        while (choice != 4) {
            switch(choice) {
                case 1:
                    listCampsUI(currentStaffUser.viewCamps(), false);
                    break;
                case 2:
                    listCampsUI(currentStaffUser.viewOwnCamps(), true);
                    break;
                case 3:
                    createCampUI();
                    break;
                default:
                    System.out.println("Wrong choice! Try again");
                    break;
            }
            System.out.println( "What would you like to do today?" +
                    "\n1. View All Camps" +
                    "\n2. View Created Camps" +
                    "\n3. Create New Camp" +
                    "\n4. Log Out");
            choice = validateIfInt(sc.next());
        } Manager.save();
    }


    /**
     * Displays the user interface for creating a new camp.
     */
    private static void createCampUI() {

        sc.nextLine();
        System.out.println("Enter Camp Name");
        String campName = sc.nextLine().trim();

        System.out.println("Set Visibilty: Y/N");
        boolean visibilty = sc.next().equalsIgnoreCase("y");

        List<LocalDate> dates = pickDates();

        System.out.println("Enter the registration close date. It will be assumed to be open until 23.59 on that date.");
        LocalDate registrationCloseDate = DateTimeConverter.convertToDate(sc.next());

        System.out.println("The camp is open to: \n1. Own School\n2. Whole of NTU");
        Camp.UserGroup userGroup;
        int choice = sc.nextInt();
        if(choice==1)
            userGroup = Camp.UserGroup.OWN_SCHOOL;
        else if(choice==2)
            userGroup = Camp.UserGroup.WHOLE_NTU;
        else
            userGroup =  Camp.UserGroup.WHOLE_NTU;

        System.out.println("Enter location");
        sc.nextLine();
        String location = sc.nextLine();
//        while(sc.hasNextLine())
//            location.append(sc.nextLine());

        System.out.println("Enter the number of committee members. Max 10, Min 1");
        int committeeSlots = sc.nextInt();
        if(committeeSlots>10) committeeSlots=10;
        if(committeeSlots<0) committeeSlots=1;

        System.out.println("Enter the number of attendee members.");
        int attendeeSlots = sc.nextInt();

        System.out.println("Enter description");
        String description;
        sc.nextLine();
        description = sc.nextLine();
//        do {
//            description.append(buffer);
//            buffer = sc.nextLine();
//        } while (!buffer.equals("\n"));
//        while(sc.hasNextLine()) {
//            String buffer = sc.nextLine();
//            if (buffer.equals("\n")) break;
//            description.append(buffer);
//        }

        currentStaffUser.createCamp(campName,visibilty, dates, registrationCloseDate, userGroup, location.toString(),
                attendeeSlots,committeeSlots,description);
    }

    /**
     * Displays the user interface for picking the dates for the camp
     */
    private static List<LocalDate>  pickDates() {
        System.out.println("Enter start date in yyyy-mm-dd");
        String stringDate = sc.next();
        while (!isValidDate(stringDate)){
            System.out.println("Wrong format. Try again");
            stringDate = sc.next();
        }
        LocalDate startDate = DateTimeConverter.convertToDate(stringDate);
        System.out.println("Enter end date in yyyy-mm-dd");
        stringDate = sc.next();
        while (!isValidDate(stringDate)){
            System.out.println("Wrong format. Try again");
            stringDate = sc.next();
        }
        LocalDate endDate = DateTimeConverter.convertToDate(stringDate);
        while(endDate.isBefore(startDate)){
            System.out.println("End date can not be before start date. Try again.");
            System.out.println("Enter start date in yyyy-mm-dd");
            stringDate = sc.next();
            while (!isValidDate(stringDate)){
                System.out.println("Wrong format. Try again");
                stringDate = sc.next();
            }
            startDate = DateTimeConverter.convertToDate(stringDate);
            System.out.println("Enter end date in yyyy-mm-dd");
            stringDate = sc.next();
            while (!isValidDate(stringDate)){
                System.out.println("Wrong format. Try again");
                stringDate = sc.next();
            }
            endDate = DateTimeConverter.convertToDate(stringDate);
        }
        List<LocalDate> finalDates = new ArrayList<>();
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate)-1;
        if(daysBetween==0){finalDates.add(startDate); finalDates.add(endDate); return finalDates;}
        List<LocalDate> dates = Stream.iterate(startDate.plusDays(1), date -> date.plusDays(1))
                .limit(daysBetween)
                .collect(Collectors.toList());
        System.out.println("Pick the dates on which the camp will take place. Enter -1 after selection is complete");
        int iterate = 1, choice;
        for (LocalDate date:
             dates) {
            System.out.println(iterate++ + ". " + date.toString());
        }
        finalDates.add(startDate);
        choice = sc.nextInt();
        while (choice!=-1){
            if(choice<0 || choice>dates.size()) {
                System.out.println("Wrong choice! Try again");
                choice = sc.nextInt();
                continue;
            }
            finalDates.add(dates.get(choice-1));
            choice = sc.nextInt();
        }
        finalDates.add(endDate);
        return finalDates;
    }

    static boolean isValidDate(String s) {
        String dateSplit[] = s.split("-");
        if(dateSplit.length!=3)
            return false;
        if(dateSplit[0].length()!=4)
            return false;
        if(dateSplit[1].length()!=2)
            return false;
        if(dateSplit[2].length()!=2)
            return false;
        return true;
    }

    /**
     * Displays the user interface for listing camps based on user preferences.
     *
     * @param campList The list of camps to display.
     * @param own      A flag indicating whether to display camps created by the staff member.
     */
    private static void listCampsUI(List<Camp> campList, boolean own){
        if(campList==null){
            System.out.println("No Camps");
            return;
        }
        if(campList.isEmpty()){
            System.out.println("No Camps");
            return;
        }
        Printer.printCampList(campList);
        System.out.println("What would you like to do" +
                "\n1. Filter by Location" +
                "\n2. Filter by Date Range" +
                "\n3. Select Camp" +
                "\n4. Back" +
                "\n5. Log Out");

        int choice = validateIfInt(sc.next());

        while (choice != 5) {
            switch(choice) {
                case 1:
                    System.out.println("Enter Location");
                    sc.nextLine();
                    listCampsUI(currentStaffUser.viewFilteredCamps(sc.nextLine(), campList), own);
                    break;
                case 2:
                    List<LocalDate> dateRange = new ArrayList<>(2);
                    System.out.println("Enter Start Date in yyyy-mm-dd format");
                    String stringDate = sc.next();
                    while (!isValidDate(stringDate)){
                        System.out.println("Wrong format. Try again");
                        stringDate = sc.next();
                    }
                    dateRange.add(DateTimeConverter.convertToDate(stringDate));
                    System.out.println("Enter End Date in yyyy-mm-dd format");
                    stringDate = sc.next();
                    while (!isValidDate(stringDate)){
                        System.out.println("Wrong format. Try again");
                        stringDate = sc.next();
                    }
                    dateRange.add(DateTimeConverter.convertToDate(stringDate));
                    listCampsUI(currentStaffUser.viewFilteredCamps(dateRange, campList), own);
                    break;
                case 3:
                    if(!own) {
                        System.out.println("Enter Camp Id");
                        campView(sc.nextInt());
                    }
                    else {
                        System.out.println("Enter Camp Id");
                        campViewOwn(sc.nextInt());
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Wrong choice! Try again");
                    break;
            }
            if(own)
                campList = currentStaffUser.viewOwnCamps();
            else
                campList = currentStaffUser.viewCamps();
            //campList = CampManager.getCampByUserId(currentStaffUser.getUserId());
            Printer.printCampList(campList);
            System.out.println("What would you like to do" +
                    "\n1. Filter by Location" +
                    "\n2. Filter by Date Range" +
                    "\n3. Select Camp" +
                    "\n4. Back" +
                    "\n5. Log Out");
            choice = validateIfInt(sc.next());
        } Manager.save();
    }

    /**
     * Displays the user interface for viewing details of a specific camp.
     *
     * @param campId The ID of the camp to view.
     */
    private static void campView(int campId){
        Optional<Camp> camp = CampManager.getCampById(campId);
        if (!camp.isPresent()) {
            System.out.println("Invalid CampId");
            return;
        }
        Printer.printCampDetails(camp.get());
        System.out.println("What would you like to do:" +
                "\n1. Back" +
                "\n2. Log Out"
        );

        int choice = validateIfInt(sc.next());
        while (choice != 2) {
            switch(choice) {
                case 1:
                    return;
                default:
                    System.out.println("Wrong choice! Try again");
                    break;
            }
            System.out.println("What would you like to do:" +
                    "\n1. Back" +
                    "\n2. Log Out"
            );
            choice = validateIfInt(sc.next());
        } Manager.save();
    }

    /**
     * Displays the user interface for viewing details of a specific camp (for camps created by the staff member).
     *
     * @param campId The ID of the camp to view.
     */
    private static void campViewOwn(int campId){
        Optional<Camp> camp = CampManager.getCampById(campId);
        if (!camp.isPresent()) {
            System.out.println("Invalid CampId");
            return;
        }
        Printer.printCampDetails(camp.get());
        System.out.println("What would you like to do:" +
                "\n1. Edit Camp Details" +
                "\n2. View All Suggestions" +
                "\n3. View Open Suggestions"+
                "\n4. View All Enquiries" +
                "\n5. View Open Enquiries"+
                "\n6. Generate Camp Attendee Report" +
                "\n7. Generate Camp Committee Report" +
                "\n8. Generate Camp Report" +
                "\n9. Generate Performance Report" +
                "\n10. Delete Camp" +
                "\n11. Back" +
                "\n12. Log Out");

        int choice = validateIfInt(sc.next());
        while (choice != 12) {
            switch (choice) {
                case 1:
                    editCampUI(campId);
                    System.out.println("Updated Camp Details:\n");
                    Printer.printAllCampDetails(CampManager.getCampById(campId).get());
                    break;
                case 2:
                    suggestionsListViewUI(currentStaffUser.viewSuggestions(campId));
                    break;
                case 3:
                    suggestionsListViewUI(currentStaffUser.viewOpenSuggestions(campId));
                    break;
                case 4:
                    enquiriesListViewUI(currentStaffUser.viewEnquiries(campId));
                    break;
                case 5:
                    enquiriesListViewUI(currentStaffUser.viewOpenEnquiries(campId));
                    break;
                case 6:
                    currentStaffUser.generateReport(campId, 1 );
                    System.out.println("Report Created!");
                    break;
                case 7:
                    currentStaffUser.generateReport(campId, 2 );
                    System.out.println("Report Created!");
                    break;
                case 8:
                    currentStaffUser.generateReport(campId, 0 );
                    System.out.println("Report Created!");
                    break;
                case 9:
                    performanceReportGeneratorUI(campId);
                    break;
                case 10:
                    System.out.println("Are you sure you want to delete this camp? Y/N");
                    if(sc.next().equalsIgnoreCase("y"))
                        System.out.println(currentStaffUser.deleteCamp(campId));
                    break;
                case 11:
                    return;
                default:
                    System.out.println("Wrong choice! Try again");
                    break;
            }
            System.out.println("What would you like to do:" +
                    "\n1. Edit Camp Details" +
                    "\n2. View All Suggestions" +
                    "\n3. View Open Suggestions"+
                    "\n4. View All Enquiries" +
                    "\n5. View Open Enquiries"+
                    "\n6. Generate Camp Attendee Report" +
                    "\n7. Generate Camp Committee Report" +
                    "\n8. Generate Camp Report" +
                    "\n9. Generate Performance Report" +
                    "\n10. Delete Camp" +
                    "\n11. Back" +
                    "\n12. Log Out");
            choice = validateIfInt(sc.next());
        } Manager.save();
    }

    /**
     * Displays the user interface for viewing all the enquiries for a camp
     *
     * @param enquiryList The list of enquiries the user will view
     */
    private static void enquiriesListViewUI(List<Enquiry> enquiryList){
        if(enquiryList.isEmpty()){
            System.out.println("No enquiries");
        }
        Printer.printEnquiryList(enquiryList);
        System.out.println("What would you like to do:" +
                "\n1. Select Enquiry" +
                "\n2. Back" +
                "\n3. Log out"
        );
        int choice = validateIfInt(sc.next());
        while (choice != 3) {
            switch (choice) {
                case 1:
                    System.out.println("Enter the enquiry Id");
                    int reqId = validateIfInt(sc.next());
                    enquireUI(reqId);
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Wrong choice! Try again");
                    break;
            }
            Printer.printEnquiryList(enquiryList);
            System.out.println("What would you like to do:" +
                    "\n1. Select Enquiry" +
                    "\n2. Back" +
                    "\n3. Log out"
            );
            choice = validateIfInt(sc.next());
        } Manager.save();
    }

    /**
     * Displays the user interface for viewing details of a specific enquiry and the actions they can perform
     *
     * @param reqId The ID of the enquiry to view.
     */
    private static void enquireUI(int reqId){
        Optional<Enquiry> enquiry = RequestManager.getEnquiry(reqId);
        if (!enquiry.isPresent()) {
            System.out.println("Invalid Enquiry Id");
            return;
        }
        Printer.printEnquiryDetails(enquiry.get());        String pending = "What would you like to do:" +
                "\n1. Reply" +
                "\n2. Back" +
                "\n3. Log out";

        String processed = "What would you like to do:" +
                "\n1. Back" +
                "\n2. Log out";
        if(enquiry.get().getStatus()== RequestStatus.PENDING) {
            boolean replied = false;
            System.out.println(pending);
            int choice = validateIfInt(sc.next());
            while (choice != 3) {
                switch (choice) {
                    case 1:
                        System.out.println("Enter your reply");
                        String reply;
                        sc.nextLine();
                        reply = sc.nextLine();
                        System.out.println(currentStaffUser.replyEnquiry( reqId, reply));
                        replied = true;
                        break;
                    case 2:
                        return;
                    default:
                        System.out.println("Wrong choice! Try again");
                        break;
                }
                if(replied=true) break;
                System.out.println(pending);
                choice = validateIfInt(sc.next());
            }
            Manager.save();
        }
        else {
            System.out.println(processed);
            int choice = validateIfInt(sc.next());
            while (choice != 2) {
                switch (choice) {
                    case 1:
                        return;
                    default:
                        System.out.println("Wrong choice! Try again");
                        break;
                }
                System.out.println(processed);
                choice = validateIfInt(sc.next());
            }
            Manager.save();
        }
    }

    /**
     * Displays the user interface for viewing all the suggestion for a camp
     *
     * @param suggestionList The list of suggestion the user will view
     */
    private static void suggestionsListViewUI(List<Suggestion> suggestionList){
        if (suggestionList.isEmpty()) {
            System.out.println("No Suggestions");
            return;
        }
        Printer.printSuggestionList(suggestionList);
        System.out.println("\n\nWhat would you like to do:" +
                "\n1. Select Suggestion" +
                "\n2. Back" +
                "\n3. Log out"
        );
        int choice = validateIfInt(sc.next());
        while (choice != 3) {
            switch (choice) {
                case 1:
                    System.out.println("Enter the Suggestion Id");
                    int reqId = validateIfInt(sc.next());
                    suggestionViewUI(reqId);
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Wrong choice! Try again");
                    break;
            }
            Printer.printSuggestionList(suggestionList);
            System.out.println("What would you like to do:" +
                    "\n1. Select Suggestion" +
                    "\n2. Back" +
                    "\n3. Log out"
            );
            choice = validateIfInt(sc.next());
        } Manager.save();
    }

    /**
     * Displays the user interface for viewing details of a specific enquiry and the actions they can perform
     *
     * @param reqId The ID of the enquiry to view.
     */
    private static void suggestionViewUI(int reqId) {
        Optional<Suggestion> suggestion = RequestManager.getSuggestion(reqId);
        if (!suggestion.isPresent()) {
            System.out.println("Invalid Enquiry Id");
            return;
        }
        Printer.printSuggestionDetails(suggestion.get());
        String pending = "What would you like to do:" +
                "\n1. Approve Suggestion" +
                "\n2. Reject Suggestion" +
                "\n3. Back" +
                "\n4. Log out";
        String processed = "What would you like to do:" +
                "\n1. Back" +
                "\n2. Log out";
        if (suggestion.get().getStatus() == RequestStatus.PENDING) {
            System.out.println(pending);
            int choice = validateIfInt(sc.next());
            while (choice != 4) {
                switch (choice) {
                    case 1:
                        currentStaffUser.approveSuggestion(reqId, true);
                        return;
                    case 2:
                        currentStaffUser.approveSuggestion(reqId, false);
                        return;
                    case 3:
                        return;
                    default:
                        System.out.println("Wrong choice! Try again");
                        break;
                }
                System.out.println(pending);
                choice = validateIfInt(sc.next());
            }
            Manager.save();
        } else {
            System.out.println(processed);
            int choice = validateIfInt(sc.next());
            while (choice != 2) {
                switch (choice) {
                    case 1:
                        return;
                    default:
                        System.out.println("Wrong choice! Try again");
                        break;
                }
                System.out.println(processed);
                choice = validateIfInt(sc.next());
            }
            Manager.save();
        }
    }

    /**
     * Displays the user interface for generating a performance report for a specific committee member of a camp.
     *
     * @param campId The ID of the camp whose committee member's performance report is generated.
     */
    private static void performanceReportGeneratorUI(int campId) {
        Optional<Camp> camp = CampManager.getCampById(campId);
        if (!camp.isPresent()) {
            System.out.println("Invalid CampId");
            return;
        }
        List<String> campCommittee = camp.get().getCampCommitteeMembers();
        if(campCommittee.isEmpty()){
            System.out.println("No committee members to generate report for.");
            return;
        }
        System.out.println("Select the student whose performance report you want to create.\n");
        Printer.printCommitteeMembers(campCommittee);
        int choice = validateIfInt(sc.next());
        while(choice<0 || choice>campCommittee.size()){
            System.out.println("Wrong choice! Try again");
            choice = validateIfInt(sc.next());
        }
        System.out.println("\nEnter your comments on the student's performance");
        String comment;
        sc.nextLine();
        comment = sc.nextLine();
//        do {
//            comment.append(buffer);
//            buffer = sc.nextLine();
//        } while (!buffer.equals("\n"));
//        while (sc.hasNextLine())
//            comment.append(sc.nextLine());
        //String chosen = campCommittee.get(choice-1)
        currentStaffUser.generatePerformanceReport(campId, campCommittee.get(choice-1), comment);
        System.out.println("Performance Report Created!");
    }

    /**
     * Displays the user interface for editing the details of a specific camp.
     *
     * @param campId The ID of the camp whose details will be changed
     */
    private static void editCampUI(int campId) {
        Camp camp =  CampManager.getCampById(campId).get();
        if(camp.isVisible()){
            System.out.println("Cannot edit a visible camp.");
            return;
        }
        System.out.println("Pick the category you would like to edit. Enter -1 to exit editor.");
        System.out.println("\n1. Camp Name" +
                        "\n2. Toggle Visibility" +
                        "\n3. Dates" +
                        "\n4. Registration Closed Date" +
                        "\n5. Location" +
                        "\n6. Attendee Slots" +
                        "\n7. Committee Slots" +
                        "\n8. Camp Description"
                );
        int choice = validateIfInt(sc.next());
        while (choice != -1) {
            String buffer;
            switch (choice) {
                case 1:
                    System.out.println("Enter new Camp Name.");
                    sc.nextLine();
                    System.out.println(currentStaffUser.editCampName(campId,sc.nextLine().trim()));
                    break;
                case 2:
                    System.out.println(currentStaffUser.editVisibility(campId));
                    break;
                case 3:
                    System.out.println("What do you want to do:\n1.Add Dates\2.Remove Dates");
                    int dateEdit = validateIfInt(sc.next());
                    if(dateEdit==1)
                        addCampDatesUI(campId);
                    else if(dateEdit==2)
                        removeCampDatesUI(campId);
                    else System.out.println("Wrong Input.");
                    break;
                case 4:
                    System.out.println("Enter new Registration Closing Date in yyyy-mm-dd format");
                    LocalDate newDate = DateTimeConverter.convertToDate(sc.next());
                    while(newDate.isBefore(LocalDate.now())) {
                        System.out.println("The date cannot be before today. Re-enter date");
                        newDate = DateTimeConverter.convertToDate(sc.next());
                    }
                    currentStaffUser.editRegistrationCloseDate(campId,newDate);
                    break;
                case 5:
                    System.out.println("Enter new location");
                    String location;
                    sc.nextLine();
                    location = sc.nextLine();
                    System.out.println(currentStaffUser.editLocation(campId,location));
                    break;
                case 6: {
                    System.out.println("Enter new Attendee Slots");
                    int newSlots = validateIfInt(sc.next());
                    System.out.println(currentStaffUser.editAttendeeSlots(campId, newSlots));}
                    break;
                case 7:
                    System.out.println("Enter new Committee Slots");
                    int newSlots = validateIfInt(sc.next());
                    System.out.println(currentStaffUser.editCampCommitteeSlots(campId, newSlots));
                    break;
                case 8:
                    System.out.println("Enter new Description");
                    String description;
                    sc.nextLine();
                    description = sc.nextLine();
//                    do {
//                        description.append(buffer);
//                        buffer = sc.nextLine();
//                    } while (!buffer.equals("\n"));
//                    while(sc.hasNextLine())
//                        description.append(sc.nextLine());
                    System.out.println(currentStaffUser.editDescription(campId,description));
                    break;
                default:
                    System.out.println("Wrong choice! Try again");
                    break;
            }
            System.out.println("\n1. Camp Name" +
                    "\n2. Toggle Visibility" +
                    "\n3. Dates" +
                    "\n4. Registration Closed Date" +
                    "\n5. Location" +
                    "\n6. Attendee Slots" +
                    "\n7. Committee Slots" +
                    "\n8. Camp Description" +
                    "\n-1. Exit Editor"
            );
            choice = validateIfInt(sc.next());
        }
    }

    /**
     * Displays the user interface for adding dates to the schedule of a specific camp.
     *
     * @param campId The ID of the camp to which dates are being added.
     */
    private static void addCampDatesUI(int campId) {
        Camp camp = CampManager.getCampById(campId).get();
        Printer.displayDates(camp);
        List<LocalDate> newDates = new ArrayList<>();
        System.out.println("Enter the new dates in yyyy-mm-dd format. Enter -1 to end.");
        String stringDate = sc.next();
        while (!stringDate.equals("-1")) {
            if (!isValidDate(stringDate)) {
                System.out.println("Wrong format. Try again");
                stringDate = sc.next();
                continue;
            }
            newDates.add(DateTimeConverter.convertToDate(stringDate));
            stringDate = sc.next();
        }
        System.out.println(currentStaffUser.addDates(campId,newDates));
    }


    /**
     * Displays the user interface for removing dates to the schedule of a specific camp.
     *
     * @param campId The ID of the camp from which dates are being removed.
     */
    private static void removeCampDatesUI(int campId) {
        Camp camp = CampManager.getCampById(campId).get();
        int iterate = 1, choice;
        for (LocalDate date:
             camp.getDates()) {
            System.out.println(iterate++ + ". "+ date.toString());
        }
        System.out.println("Pick the dates (by number) that you want to remove. Enter -1 when done.");
        choice = sc.nextInt();
        List<LocalDate> removeDates = new ArrayList<>();
        while (choice!=-1){
            if(choice<0 || choice>camp.getDates().size()) {
                System.out.println("Wrong choice! Try again");
                choice = sc.nextInt();
                continue;
            }
            removeDates.add(camp.getDates().get(choice-1));
            choice = sc.nextInt();
        }
        System.out.println(currentStaffUser.deleteDates(campId,removeDates));
    }

    /**
     * Validates if the input string can be converted to an integer.
     *
     * @param input The input string to validate.
     * @return The parsed integer if successful, or -1 if not a valid integer.
     */
    private static int validateIfInt(String input){
            try{
                // return the processed int
                return Integer.parseInt(input);
            } catch(NumberFormatException e){
                // Return -1 if not a valid integer
                return -1;
            }
        }

}
