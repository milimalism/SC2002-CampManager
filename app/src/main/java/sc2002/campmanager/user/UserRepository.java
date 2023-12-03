package sc2002.campmanager.user;

import java.io.IOException;
import java.util.HashMap;

/**
 * The UserRepository interface defines methods for saving and loading user data.
 *
 * @param <T> The type of user entity (e.g., Student, Staff).
 */
public interface UserRepository<T> {

    /**
     * Saves user data to a persistent storage medium.
     *
     */
    public void saveData();

    /**
     * Loads user data from a persistent storage medium.
     *
     */
    public void loadData() ;
}

