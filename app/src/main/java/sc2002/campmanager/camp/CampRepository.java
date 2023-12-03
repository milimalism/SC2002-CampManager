package sc2002.campmanager.camp;

import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Camp repository class to manage camp objects
 */
public class CampRepository {
    private static final String pathName = "/Users/mythilimulani/Desktop/GitHub/SC2002-CampManager/app/src/main/resources/CampList.csv";
    private static final String[] headers = {"campId", "campName", "visibility", "dates", "Registration Closing Date", "Location",
    "Camp Attendee Slots", "Staff In Charge", "Camp Attendees", "Camp Committee Slots", "Camp Committee Members", "Description", "Currently Available Attendee Slots", "Currently Available Committee Slots", "User Group", "Suggestion", "Enquiry"};
    private static List<Camp> campList;

    static {
        try {
            campList = findAllFromCSV();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load data!");
        }
    }

    /**
     * Save camp to repository
     * @param entity camp object
     * @return camp object
     */
    public static Camp save(Camp entity){
        campList.add(entity);
        return entity;
    }

    /**
     * Get list of all camps
     * @return campList
     */
    public static List<Camp> getAllCamps() {
        return campList;
    }

    /**
     * Find camp by ID
     * @param id the id of the camp
     * @return  camp object
     */
    public static Optional<Camp> findByID(int id) {
//        List<CSVRecord> records = CsvUtils.readCsv(pathName);
        for (Camp camp : campList) {
            //Camp c = CampConverter.csvToCamp(csvRecord);
            if (camp.getCampId() == id)
                return Optional.of(camp);
        }
        return Optional.empty();
    }


    /**
     * Find all camps from CSV
     * @return list of camp objects
     * @throws IOException throws exception
     */
    public static List<Camp> findAllFromCSV() throws IOException{
        List<Camp> c = new ArrayList<>();
        List<CSVRecord> records = CsvUtils.readCsv(pathName);
        for (CSVRecord csvRecord : records) {
            c.add(CampConverter.csvToCamp(csvRecord));
        }
        return c;
    }

    /**
     * Delete camp from repository
     * @param c camp object
     * @return camp object
     */
    public static Optional<Camp> delete(Camp c) {
        if (campList.remove(c))
            return Optional.of(c);
        else return Optional.empty();
    }

    /**
     * Update camp in repository
     * @param entity camp object
     * @param newEntity new camp object
     */
    public static void update(Camp entity, Camp newEntity) {
        campList.remove(entity);
        campList.add(newEntity);
    }

    /**
     * Save camp list to CSV
     * @throws IOException throws exception
     */
    public static void saveToCsv() throws IOException{
        CsvUtils.writeCsvWithArrayOfData(pathName, CampConverter.campListToCsv(campList), headers);
    }
}
