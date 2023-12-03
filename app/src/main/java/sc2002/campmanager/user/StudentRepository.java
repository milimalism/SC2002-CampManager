package sc2002.campmanager.user;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The StudentRepository class is responsible for handling the persistence of student-related data.
 */
public class StudentRepository implements UserRepository<Student>{


    final String filepath = "/Users/mythilimulani/Desktop/GitHub/SC2002-CampManager/app/src/main/resources/studentList.xlsx";
    final String fileName = "/studentList.xlsx";

    /**
     * Saves student data to the specified file.
     */
    public void saveData(){
        try (FileOutputStream outputStream = new FileOutputStream(filepath)) {
            StudentConverter.studentToFile(outputStream);
            System.out.println("File written successfully");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Loads student data from a specified file.
     */
    public void loadData() {
        try(InputStream inputStream = this.getClass().getResourceAsStream(fileName)) {
            StudentConverter.fileToStudentMap(inputStream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
