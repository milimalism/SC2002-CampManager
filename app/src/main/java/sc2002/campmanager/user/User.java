package sc2002.campmanager.user;

import sc2002.campmanager.util.PasswordHash;

import java.util.ArrayList;

/**
 * The base class representing a user in the camp management system.
 * This class provides essential attributes and methods common to all user types.
 */
public class User {
    protected String userName;
    protected String userId;
    protected String password;
    protected String email;
    protected UserType userType;
    protected String faculty;
    protected ArrayList<Integer> ownCamps;


    /**
     * Constructs a new User with specified details.
     *
     * @param userId   The unique identifier of the user.
     * @param faculty  The faculty to which the user belongs.
     * @param userName The unique username of the user.
     * @param email    The email address associated with the user's account.
     * @param password The password associated with the user's account.
     */
    public User(String userId, String faculty, String userName, String email, String password) {
        this.userName = userName;
        this.userId = userId;
        this.faculty = faculty;
        this.email = email;
        this.password = password;
        this.ownCamps = new ArrayList<>();
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return The unique identifier of the user.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets a new password for the user's account.
     *
     * @param password The new password to set.
     */
    public void setPassword(String password) {
        this.password = PasswordHash.createHash(password);
    }

    /**
     * Gets the password associated with the user's account.
     *
     * @return The password associated with the user's account.
     */
    public String getPassword() {
        return password;
    }


    /**
     * Gets the faculty to which the user belongs.
     *
     * @return The faculty to which the user belongs.
     */
    public String getFaculty() {
        return faculty;
    }

    //public abstract void setUserType(String userType);
    public UserType getUserType() {
        return userType;
    }

    //public abstract void setUserType(String userType);

    /**
     * Gets the list of camp IDs owned by the user.
     *
     * @return The list of camp IDs owned by the user.
     */
    public ArrayList<Integer> getOwnCamps() {
        return ownCamps;
    }

    /**
     * Sets the list of camp IDs owned by the user.
     *
     * @param ownCamps The new list of camp IDs to set.
     */
    public void setOwnCamps(ArrayList<Integer> ownCamps) {
        this.ownCamps = ownCamps;
    }

    /**
     * Gets the email address associated with the user's account.
     *
     * @return The email address associated with the user's account.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address associated with the user's account.
     *
     * @param email The new email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
