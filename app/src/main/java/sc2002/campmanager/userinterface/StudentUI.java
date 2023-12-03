package sc2002.campmanager.userinterface;

import sc2002.campmanager.camp.Camp;
import sc2002.campmanager.camp.CampManager;
import sc2002.campmanager.main.Manager;
import sc2002.campmanager.requests.Enquiry;
import sc2002.campmanager.requests.RequestManager;
import sc2002.campmanager.requests.RequestStatus;
import sc2002.campmanager.requests.Suggestion;
import sc2002.campmanager.util.DateTimeConverter;
import sc2002.campmanager.util.Printer;

import static sc2002.campmanager.main.Manager.sc;
import static sc2002.campmanager.main.Manager.currentStudentUser;
import static sc2002.campmanager.userinterface.StaffUI.isValidDate;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class StudentUI {

    /**
     * Displays the user interface for the student's homepage, allowing them to view camps and perform related actions.
     * If the student is a camp committee member, the committee member's homepage is displayed.
     */
    public static void displayStudentHomepageUI() {
        while(currentStudentUser.isCampCommittee()) {
            displayCommitteeMemberHomepageUI();
        }
        System.out.println("Welcome, " + currentStudentUser.getUserName());
        System.out.println("What would you like to do today?" +
                "\n1. View All Camps" +
                "\n2. View Registered Camps" +
                "\n3. Log Out");
        int choice = validateIfInt(sc.next());

        while (choice != 3) {
            switch (choice) {
                case 1:
                    listCampsUI(currentStudentUser.viewCamps(), false);
                    if (currentStudentUser.isCampCommittee())
                        displayCommitteeMemberHomepageUI();
                    break;
                case 2:
                    listCampsUI(currentStudentUser.viewOwnCamps(), true);
                    break;
                default:
                    System.out.println("Wrong choice! Try again");
                    break;
            }
            System.out.println("What would you like to do today?" +
                    "\n1. View All Camps" +
                    "\n2. View Registered Camps" +
                    "\n3. Log Out");
            choice = validateIfInt(sc.next());
        }
        Manager.save();
    }


    /**
     * Displays the user interface for the camp committee member's homepage, allowing them to view camps and perform related actions.
     */

    public static void displayCommitteeMemberHomepageUI() {
        System.out.println("Welcome, " + currentStudentUser.getUserName());
        System.out.println("What would you like to do today?" +
                "\n1. View All Camps" +
                "\n2. View Registered Camps" +
                "\n3. View Committee Camp" +
                "\n4. Log Out");
        int choice = validateIfInt(sc.next());

        while (choice != 4) {
            switch (choice) {
                case 1:
                    listCampsUI(currentStudentUser.viewCamps(), false);
                    break;
                case 2:
                    listCampsUI(currentStudentUser.viewOwnCamps(), true);
                    break;
                case 3:
                    campViewCommittee(currentStudentUser.getCampCommittee());
                    break;
                default:
                    System.out.println("Wrong choice! Try again");
                    break;
            }
            System.out.println("What would you like to do today?" +
                    "\n1. View All Camps" +
                    "\n2. View Registered Camps" +
                    "\n3. View Committee Camp" +
                    "\n4. Log Out");
            choice = validateIfInt(sc.next());
        }
        Manager.save();
    }

    /**
     * Displays the user interface for listing camps based on different criteria such as location and date range.
     * Allows the user to filter and view camps, select a specific camp, or go back to the previous menu.
     *
     * @param campList The list of camps to be displayed in the UI.
     * @param own      A boolean indicating whether the displayed camps belong to the user.
     */
    public static void listCampsUI(List<Camp> campList, boolean own) {
        if(campList.isEmpty()) {
            System.out.println("No camps available");
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
            switch (choice) {
                case 1:
                    System.out.println("Enter Location");
                    sc.nextLine();
                    listCampsUI(currentStudentUser.viewFilteredCamps(sc.nextLine(), campList), own);
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
                    listCampsUI(currentStudentUser.viewFilteredCamps(dateRange, campList), own);
                    break;
                case 3:
                    if (!own) {
                        System.out.println("Enter Camp Id");
                        campView(sc.nextInt());
                    } else {
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
            Printer.printCampList(campList);
            System.out.println("What would you like to do" +
                    "\n1. Filter by Location" +
                    "\n2. Filter by Date Range" +
                    "\n3. Select Camp" +
                    "\n4. Back" +
                    "\n5. Log Out");
            choice = validateIfInt(sc.next());
        }
        Manager.save();
    }

    /**
     * Displays the detailed view of a camp based on the provided campId.
     * Allows the user to register as a committee member or attendee, enquire about the camp, view their enquiries, or go back.
     *
     * @param campId The ID of the camp to be viewed.
     */
    public static void campView(int campId) {
        Optional<Camp> camp = CampManager.getCampById(campId);
        if (!camp.isPresent()) {
            System.out.println("Invalid CampId");
            return;
        }
        Printer.printCampDetails(camp.get());
        System.out.println("What would you like to do:" +
                "\n1. Register as Committee Member" +
                "\n2. Register as Attendee " +
                "\n3. Enquire" +
                "\n4. View My Enquiries" +
                "\n5. Back" +
                "\n6. Log Out"
        );

        int choice = validateIfInt(sc.next());
        String buffer;
        while (choice != 6) {
            switch (choice) {
                case 1:
                    System.out.println(currentStudentUser.register(campId, true));
                    break;
                case 2:
                    System.out.println(currentStudentUser.register(campId, false));
                    break;
                case 3:
                    System.out.println("Enter the enquiry");
                    sc.nextLine();
                    String enquiry = sc.nextLine();
                    System.out.println(currentStudentUser.enquire(campId, enquiry));
                    break;
                case 4:
                    enquiriesListViewUI(currentStudentUser.viewAllEnquiries(), campId);
                    break;
                case 5:
                    return;
                case 6:
                    Manager.save();
                default:
                    System.out.println("Wrong choice! Try again");
                    break;
            }
            System.out.println("What would you like to do:" +
                    "\n1. Register as Committee Member" +
                    "\n2. Register as Attendee " +
                    "\n3. Enquire" +
                    "\n4. View My Enquiries" +
                    "\n5. Back" +
                    "\n6. Log Out"
            );
            choice = validateIfInt(sc.next());
        }
        Manager.save();

    }

    /**
     * Displays the detailed view of a camp for the user who owns the camp.
     * Allows the user to withdraw from the camp, enquire about the camp, view their enquiries, or go back.
     *
     * @param campId The ID of the camp to be viewed.
     */
    public static void campViewOwn(int campId) {
        Optional<Camp> camp = CampManager.getCampById(campId);
        if (!camp.isPresent()) {
            System.out.println("Invalid CampId");
            return;
        }
        Printer.printCampDetails(camp.get());
        System.out.println("What would you like to do:" +
                "\n1. Withdraw" +
                "\n2. Enquire" +
                "\n3. View My Enquiries" +
                "\n4. Back" +
                "\n5. Log Out"
        );
        int choice = validateIfInt(sc.next());
        String buffer;
        while (choice != 5) {
            switch (choice) {
                case 1:
                    if(campId!=currentStudentUser.getCampCommittee())
                        System.out.println(currentStudentUser.withdraw(campId));
                    else System.out.println("Invalid Camp Id");
                    break;
                case 2:
                    System.out.println("Enter the enquiry");
                    String enquiry;
                    sc.nextLine();
                    enquiry = sc.nextLine();
//                    do {
//                        enquiry.append(buffer);
//                        buffer = sc.nextLine();
//                    } while(!buffer.equals("\n"));
                    System.out.println(currentStudentUser.enquire(campId, enquiry));
                    break;
                case 3:
                    enquiriesListViewUI(currentStudentUser.viewAllEnquiries(), campId);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Wrong choice! Try again");
                    break;
            }
            System.out.println("What would you like to do:" +
                    "\n1. Withdraw" +
                    "\n2. Enquire" +
                    "\n3. View My Enquiries" +
                    "\n4. Back" +
                    "\n5. Log Out"
            );
            choice = validateIfInt(sc.next());
        }
        Manager.save();
    }

    /**
     * Displays the detailed view of a camp for the camp committee member.
     * Allows the committee member to make suggestions, view their suggestions, view all enquiries, view open enquiries,
     * generate reports, or go back.
     *
     * @param campId The ID of the camp to be viewed.
     */
    public static void campViewCommittee(int campId) {
        Optional<Camp> camp = CampManager.getCampById(campId);
        if (!camp.isPresent()) {
            System.out.println("Invalid CampId");
            if(campId==currentStudentUser.getCampCommittee())
                currentStudentUser.setCampCommittee(-1);
            return;
        }
        Printer.printAllCampDetails(camp.get());
        System.out.println("What would you like to do:" +
                "\n1. Make a Suggestion" +
                "\n2. View My Suggestions" +
                "\n3. View All Enquiries" +
                "\n4. View Open Enquiries" +
                "\n5. Generate Camp Attendee Report" +
                "\n6. Generate Camp Committee Report" +
                "\n7. Generate Camp Report" +
                "\n8. Back" +
                "\n9. Log Out");

        int choice = validateIfInt(sc.next());
        String buffer;
        while (choice != 9) {
            switch (choice) {
                case 1:
                    System.out.println("Enter the suggestion");
                    String suggestion;
                    sc.nextLine();
                    suggestion = sc.nextLine();
                    currentStudentUser.suggest(campId, suggestion);
                    break;
                case 2:
                    suggestionsListViewUI(currentStudentUser.viewAllSuggestions(), campId);
                    break;
                case 3:
                    enquiriesListCampCommitteeViewUI(RequestManager.getEnquiriesCamp(campId), campId);
                    break;
                case 4:
                    enquiriesListCampCommitteeViewUI(currentStudentUser.viewOpenEnquiries(), campId);
                    break;
                case 5:
                    System.out.println(currentStudentUser.generateReport(campId, 1));
                    break;
                case 6:
                    System.out.println(currentStudentUser.generateReport(campId, 2));
                    break;
                case 7:
                    System.out.println(currentStudentUser.generateReport(campId, 0));
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Wrong choice! Try again");
                    break;
            }
            System.out.println("What would you like to do:" +
                    "\n1. Make a Suggestion" +
                    "\n2. View My Suggestions" +
                    "\n3. View All Enquiries" +
                    "\n4. View Open Enquiries" +
                    "\n5. Generate Camp Attendee Report" +
                    "\n6. Generate Camp Committee Report" +
                    "\n7. Generate Camp Report" +
                    "\n8. Back" +
                    "\n9. Log Out");
            choice = validateIfInt(sc.next());
        }
        Manager.save();
    }

    /**
     * Displays a list of enquiries and provides options to select a specific enquiry, go back, or log out.
     *
     * @param enquiriesCamp The list of enquiries to be displayed.
     * @param campId      The ID of the camp associated with the enquiries.
     */
    public static void enquiriesListCampCommitteeViewUI(List<Enquiry> enquiriesCamp, int campId) {
        if(enquiriesCamp.isEmpty()){
            System.out.println("No enquiries");
            return;
        }
        Printer.printEnquiryList(enquiriesCamp);
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
                    enquireViewCampCommitteeUI(reqId);
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Wrong choice! Try again");
                    break;
            }
            Printer.printEnquiryList(enquiriesCamp);
            System.out.println("What would you like to do:" +
                    "\n1. Select Enquiry" +
                    "\n2. Back" +
                    "\n3. Log out"
            );
            choice = validateIfInt(sc.next());
        }
        Manager.save();
    }

    /**
     * Displays a list of enquiries and provides options to select a specific enquiry, go back, or log out.
     *
     * @param enquiryList The list of enquiries to be displayed.
     * @param campId      The ID of the camp associated with the enquiries.
     */
    public static void enquiriesListViewUI(List<Enquiry> enquiryList, int campId) {
        if(enquiryList.isEmpty()){
            System.out.println("No enquiries");
            return;
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
                    enquireViewUI(reqId, campId);
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
        }
        Manager.save();
    }


    /**
     * Displays the detailed view of an enquiry for the camp committee member.
     * Allows the committee member to reply to the enquiry, go back, or log out.
     *
     * @param reqId The ID of the enquiry to be viewed.
     */
    public static void enquireViewCampCommitteeUI(int reqId) {
        Optional<Enquiry> enquiry = RequestManager.getEnquiry(reqId);
        if (!enquiry.isPresent()) {
            System.out.println("Invalid Enquiry Id");
            return;
        }
        Printer.printEnquiryDetails(enquiry.get());
        String pending = "What would you like to do:" +
                "\n1. Reply" +
                "\n2. Back" +
                "\n3. Log out";
        String processed = "What would you like to do:" +
                "\n1. Back" +
                "\n2. Log out";
        if (enquiry.get().getStatus() == RequestStatus.PENDING) {
            boolean replied = false;
            System.out.println(pending);
            int choice = validateIfInt(sc.next());
            String buffer;
            while (choice != 3) {
                switch (choice) {
                    case 1:
                        System.out.println("Enter your reply");
                        String reply;
                        sc.nextLine();
                        reply = sc.nextLine();
                        System.out.println(currentStudentUser.replyEnquiry(reqId, reply));
                        replied=true;
                        break;
                    case 2:
                        return;
                    default:
                        System.out.println("Wrong choice! Try again");
                        break;
                }
                if(replied) break;
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
     * Displays the detailed view of an enquiry and provides options based on its status.
     * For pending enquiries, allows the user to edit, delete, go back, or log out.
     * For processed enquiries, allows the user to go back or log out.
     *
     * @param reqId   The ID of the enquiry to be viewed.
     * @param campId  The ID of the camp associated with the enquiry.
     */
    public static void enquireViewUI(int reqId, int campId) {
        Optional<Enquiry> enquiry = RequestManager.getEnquiry(reqId);
        if (!enquiry.isPresent()) {
            System.out.println("Invalid Enquiry Id");
            return;
        }
        Printer.printEnquiryDetails(enquiry.get());
        String pending = "What would you like to do:" +
                "\n1. Edit Enquiry" +
                "\n2. Delete Enquiry" +
                "\n3. Back" +
                "\n4. Log out";
        String processed = "What would you like to do:" +
                "\n1. Back" +
                "\n2. Log out";
        if (enquiry.get().getStatus() == RequestStatus.PENDING) {
            System.out.println(pending);

            int choice = validateIfInt(sc.next());
            String buffer;
            while (choice != 4) {
                switch (choice) {
                    case 1:
                        System.out.println("Enter the edited enquiry");
                        String enquiryDesc;
                        sc.nextLine();
                        enquiryDesc = sc.nextLine();
                        System.out.println(currentStudentUser.editEnquiry(reqId, enquiryDesc));
                        break;
                    case 2:
                        System.out.println(currentStudentUser.deleteEnquiry(reqId));
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
     * Displays a list of suggestions and provides options to select a suggestion for detailed view.
     * Options include selecting a suggestion, going back, or logging out.
     *
     * @param suggestionList The list of suggestions to be displayed.
     * @param campId         The ID of the camp associated with the suggestions.
     */
    public static void suggestionsListViewUI(List<Suggestion> suggestionList, int campId) {
        if (suggestionList.isEmpty()) {
            System.out.println("No Suggestions");
            return;
        }
        Printer.printSuggestionList(suggestionList);
        System.out.println("What would you like to do:" +
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
                    suggestionViewUI(reqId, campId);
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
        }
        Manager.save();
    }

    /**
     * Displays detailed information about a suggestion and provides options based on the suggestion's status.
     * If the suggestion is pending, options include editing, deleting, going back, or logging out.
     * If the suggestion is processed, options include going back or logging out.
     *
     * @param reqId The ID of the suggestion to be viewed in detail.
     * @param campId     The ID of the camp associated with the suggestion.
     */
    public static void suggestionViewUI(int reqId, int campId) {
        Optional<Suggestion> suggestion = RequestManager.getSuggestion(reqId);
        if (!suggestion.isPresent()) {
            System.out.println("Invalid Enquiry Id");
            return;
        }
        Printer.printSuggestionDetails(suggestion.get());
        String pending = "What would you like to do:" +
                "\n1. Edit Suggestion" +
                "\n2. Delete Suggestion" +
                "\n3. Back" +
                "\n4. Log out";
        String processed = "What would you like to do:" +
                "\n1. Back" +
                "\n2. Log out";
        if (suggestion.get().getStatus() == RequestStatus.PENDING) {
            System.out.println(pending);

            int choice = validateIfInt(sc.next());
            String buffer;
            while (choice != 4) {
                switch (choice) {
                    case 1:
                        System.out.println("Enter the edited suggestion");
                        String suggestionDesc;
                        sc.nextLine();
                        suggestionDesc = sc.nextLine();
                        System.out.println(currentStudentUser.editSuggestion(reqId, suggestionDesc));
                        break;
                    case 2:
                        System.out.println(currentStudentUser.deleteSuggestion(reqId));
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
     * Validates if the input string can be converted to an integer.
     *
     * @param input The input string to validate.
     * @return The parsed integer if successful, or -1 if not a valid integer.
     */
    public static int validateIfInt(String input) {
        try {
            // return the processed int
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // Return -1 if not a valid integer
            return -1;
        }
    }

}


