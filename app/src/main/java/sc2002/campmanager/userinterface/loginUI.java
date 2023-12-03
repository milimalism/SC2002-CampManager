package sc2002.campmanager.userinterface;

import sc2002.campmanager.user.*;

import static sc2002.campmanager.main.Manager.sc;
import static sc2002.campmanager.main.Manager.currentStudentUser;
import static sc2002.campmanager.main.Manager.currentStaffUser;
import static sc2002.campmanager.userinterface.StudentUI.validateIfInt;

/**
 * The login user interface for CampManager application.
 */
public class loginUI {

    /**
     * Display the welcome message.
     */
    public static void pageZero() {
        System.out.println("WELCOME TO CAMPMANAGER V1.0");
    }


    /**
     * Display the login page, allowing users to select their user type and enter credentials.
     */
    public static void displayLoginPage() {
        //Scanner sc = new Scanner(System.in);
        int choice;
        UserType userType = null;
        System.out.println("LOGIN \n\nSelect your user type\n1. Student \n2. Staff\n3. Back");
        choice = validateIfInt(
                sc.next());
        switch (choice) {
            case 1:
                userType = UserType.STUDENT;
                break;
            case 2:
                userType = UserType.STAFF;
                break;
            case 3:
                return;
            default:
                System.out.println("Wrong input");
        }
        while (userType == null) {
            switch (choice) {
                case 1:
                    userType = UserType.STUDENT;
                    break;
                case 2:
                    userType = UserType.STAFF;
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Wrong input");
            }
            System.out.println("LOGIN \n\nSelect your user type\n1. Student \n2. Staff\n3. Back");
            choice = validateIfInt(sc.next());
        }

        System.out.print("Enter your userId: ");
        String userId = sc.next().toUpperCase();
        System.out.print("Enter password: ");
        String password = sc.next();
        while(!UserManager.checkCredentials(userType, userId, password)) {
            System.out.println("Wrong User Id or Password");
            System.out.print("Enter your userId: ");
             userId = sc.next().toUpperCase();
            System.out.print("Enter password: ");
             password = sc.next();
        }
        if (userType == UserType.STUDENT) {
            currentStudentUser = UserManager.getStudentByUserId(userId);
        } else {
            currentStaffUser = UserManager.getStaffByUserId(userId);
        }
        if (password.equals("password"))
            setPasswordUI();

    }

    /**
     * Display the password change user interface for the current user.
     */
    private static void setPasswordUI() {
        System.out.println("Please change your password. It is currently the default password.");
        System.out.println("Enter new password");
        String password = sc.next();
        System.out.println("Reenter new password");
        String passwordCheck = sc.next();
        while (!password.equals(passwordCheck)) {
            System.out.println("The passwords don't match. Try again");
            System.out.println("Enter new password");
            password = sc.next();
            System.out.println("Reenter new password");
            passwordCheck = sc.next();
        }
        if (currentStaffUser != null)
            currentStaffUser.setPassword(password);
        else if (currentStudentUser != null) {
            currentStudentUser.setPassword(password);

        }

    }

}

