package sc2002.campmanager.main;

import sc2002.campmanager.camp.CampRepository;
import sc2002.campmanager.requests.EnquiryRepository;
import sc2002.campmanager.requests.SuggestionsRepository;
import sc2002.campmanager.user.Staff;
import sc2002.campmanager.user.Student;
import sc2002.campmanager.user.UserManager;
import sc2002.campmanager.userinterface.StaffUI;
import sc2002.campmanager.userinterface.StudentUI;
import sc2002.campmanager.userinterface.loginUI;

import java.io.IOException;
import java.util.Scanner;


/**
 * The Manager class is the main class of the application.
 */
public class Manager {

    public static final Scanner sc = new Scanner(System.in);
    public static Student currentStudentUser = null;
    public static Staff currentStaffUser = null;
    /**
     * Loads data from csv files
     */
    public static void loadData(){
        UserManager.loadData();
    }

    /**
     * Displays the login page
     */
    public static void login()  {
        try {
            loginUI.pageZero();
            loginUI.displayLoginPage();
            if (currentStudentUser != null) {
                StudentUI.displayStudentHomepageUI();
            } else if (currentStaffUser != null)
                StaffUI.displayStaffHomepageUI();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Saves data to csv files
     */
    public static void save() {
        UserManager.save();
        try {
            EnquiryRepository.saveToCsv();
            SuggestionsRepository.saveToCsv();
            CampRepository.saveToCsv();
        } catch(IOException e) {
            System.out.println("Failed to writeback!");
        }
        System.exit(0);
    }

    /**
     * Main method
     * @param args command line arguments
     */
    public static void main(String[] args) {
        loadData();
        login();
        save();
    }
}
