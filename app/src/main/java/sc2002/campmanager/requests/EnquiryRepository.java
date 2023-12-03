package sc2002.campmanager.requests;

import org.apache.commons.csv.CSVRecord;
import sc2002.campmanager.camp.CsvUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *  Enquiry repository class to manage enquiry objects
 */
public class EnquiryRepository {
    private static List<Enquiry> enquiries;
    private static final String pathname = "/Users/mythilimulani/Desktop/GitHub/SC2002-CampManager/app/src/main/resources/EnquiryList.csv";
    private static final String[] headers = {"Request ID", "Camp ID", "Author", "Request Description", "Status", "Timestamp",
            "Response"};

    static {
        try {
            enquiries = findAllFromCsv();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds all enquiries from CSV
     * @return list of enquiries
     * @throws IOException if file not found
     */
    private static List<Enquiry> findAllFromCsv() throws IOException {
        List<Enquiry> e = new ArrayList<>();
        List<CSVRecord> records = CsvUtils.readCsv(pathname);
        for (CSVRecord csvRecord : records) {
            e.add(EnquiryConverter.csvRecordToEnquiry(csvRecord));
        }
        return e;
    }

    /**
     * saves an equiry
     * @param enquiry enquiry to be saved
     * @return added enquiry
     */
    public static Enquiry save(Enquiry enquiry) {
        enquiries.add(enquiry);
        return enquiry;
    }

    /**
     * Gets all enquiries
     * @return list of all enquiries
     */
    public static List<Enquiry> getAllEnquiries() {
        return enquiries;
    }

    /**
     * Finds an enquiry by ID
     * @param id ID of the enquiry
     * @return enquiry with the ID
     */
    public static Optional<Enquiry> findById(int id) {
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getReqID() == id) {
                return Optional.of(enquiry);
            }
        }
        return Optional.empty();
    }

    /**
     * Finds all enquiries by camp ID
     * @param campId ID of the camp
     * @return list of enquiries with the camp ID
     */
    public static List<Enquiry> findByCampId(int campId) {
        List<Enquiry> enquiryList = new ArrayList<>();
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getCampID() == campId) {
                enquiryList.add(enquiry);
            }
        }
        return enquiryList;
    }

    /**
     * Finds all enquiries by user ID
     * @param userId ID of the user
     * @return list of enquiries with the user ID
     */
    public static List<Enquiry> findByUserId(String userId) {
        List<Enquiry> enquiryList = new ArrayList<>();
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getReqAuthor().equals(userId)) {
                enquiryList.add(enquiry);
            }
        }
        return enquiryList;
    }

    /**
     * Deletes an enquiry
     * @param enquiry enquiry to be deleted
     * @return deleted enquiry
     */
    public static Optional<Enquiry> delete(Enquiry enquiry) {
        if (enquiries.remove(enquiry)) {
            return Optional.of(enquiry);
        }
        return Optional.empty();
    }

    /**
     * Updates an enquiry
     * @param enquiry Old enquiry
     * @param newEnquiry New enquiry
     */
    public static void update(Enquiry enquiry, Enquiry newEnquiry) {
        enquiries.remove(enquiry);
        enquiries.add(newEnquiry);
    }

    /**
     * Saves all enquiries to CSV
     * @throws IOException if file not found
     */
    public static void saveToCsv() throws IOException {
        CsvUtils.writeCsvWithArrayOfData(pathname, EnquiryConverter.enquiryListToCSV(enquiries), headers);
    }
}
