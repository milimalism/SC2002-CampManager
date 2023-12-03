package sc2002.campmanager.user;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * The StaffRepository class is responsible for handling the persistence of staff-related data.
 */
public class StaffRepository implements UserRepository<Staff>{

    final String filepath = "/Users/mythilimulani/Desktop/GitHub/SC2002-CampManager/app/src/main/resources/staffList.xlsx";
    final String fileName = "/staffList.xlsx";
    
    /**
     * Saves staff data to the specified file.
     */
    public void saveData() {
        try (FileOutputStream outputStream = new FileOutputStream(filepath)){
            StaffConverter.staffToFile(outputStream);
            System.out.println("File written successfully");
        } catch (IOException e){
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Loads staff data from a specified file.
     */
    public void loadData() {
        try(InputStream inputStream = this.getClass().getResourceAsStream(fileName);) {
            //StaffRepository staffRepository = new StaffRepository();
            //InputStream inputStream = staffRepository.getClass().getResourceAsStream("/testing1.xlsx");
            StaffConverter.fileToStaffMap(inputStream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
