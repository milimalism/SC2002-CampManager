package sc2002.campmanager.user;


import java.util.*;
import sc2002.campmanager.util.PasswordHash;

/**
 * The UserManager class is responsible for managing user-related operations,
 * including loading and saving user data, retrieving user information, and
 * verifying user credentials.
 */
public class UserManager {

    /** A HashMap to store student information using user IDs as keys. */
    public static HashMap<String, Student> studentMap = new HashMap<>();

    /** A HashMap to store staff information using user IDs as keys. */
    public static HashMap<String, Staff> staffMap = new HashMap<>();

    /** The repository for handling student data persistence. */
    private static final StudentRepository studentRepository = new StudentRepository();

    /** The repository for handling staff data persistence. */
    private static final StaffRepository staffRepository = new StaffRepository();


    /**
     * Loads user data into data structures from the repositories.
     */
    public static void loadData(){
        studentRepository.loadData();
        staffRepository.loadData();
    }

    /**
     * Saves user data to the excel files through the repositories.
     */
    public static void save() {
            studentRepository.saveData();
            staffRepository.saveData();
    }

    /**
     * Retrieves the faculty information for a given staff member.
     *
     * @param staffInCharge The user ID of the staff member.
     * @return The faculty information associated with the staff member.
     */
    public static String getFacultyByUserId(String staffInCharge) {
        return staffMap.get(staffInCharge).getFaculty();
    }

    /**
     * Retrieves a student object by user ID.
     *
     * @param userId The user ID of the student.
     * @return The student object associated with the user ID.
     */
    public static Student getStudentByUserId(String userId) {
        return studentMap.get(userId);
    }


    /**
     * Retrieves a staff object by user ID.
     *
     * @param userId The user ID of the staff member.
     * @return The staff object associated with the user ID.
     */
    public static Staff getStaffByUserId(String userId) {
        return staffMap.get(userId);
    }

    /**
     * Checks the credentials of a user.
     *
     * @param userType The type of user (STUDENT or STAFF).
     * @param userId The user ID for authentication.
     * @param password The password for authentication.
     * @return True if the credentials are valid; otherwise, false.
     */
    public static boolean checkCredentials(UserType userType, String userId, String password) {
        User user;
        if(userType == UserType.STUDENT) {
            user = UserManager.studentMap.get(userId);
            if(user==null)
                return false;
            return PasswordHash.verifyPassword(password, user.getPassword());
        }
        else{
            user =  UserManager.staffMap.get(userId);
            if(user==null)
                return false;
            return PasswordHash.verifyPassword(password, user.getPassword());
            //if (PasswordHash.verifyPassword(password, user.getPassword())) return UserManager.getStaffByUserId(userId);
        }
        //System.out.println("Incorrect Password");
    }

    public static void closeStreams(){

    }

//

}
