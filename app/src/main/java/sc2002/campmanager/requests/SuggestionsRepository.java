package sc2002.campmanager.requests;

import org.apache.commons.csv.CSVRecord;
import sc2002.campmanager.camp.CsvUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Suggestion repository class to manage suggestion objects
 */
public class SuggestionsRepository {
    private static List<Suggestion> suggestions;
    private static final String pathName = "/Users/mythilimulani/Desktop/GitHub/SC2002-CampManager/app/src/main/resources/SuggestionList.csv";
    private static final String[] headers = {"Request ID", "Camp ID", "Author", "Request Description", "Status", "Timestamp",
    "Approval Status"};

    static {
        try {
            suggestions = findAllFromCSV();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load data!");
        }
    }

    /**
     * Find all suggestions from CSV
     * @return list of suggestions
     * @throws IOException if file not found
     */
    public static List<Suggestion> findAllFromCSV() throws IOException {
        List<Suggestion> s = new ArrayList<>();
        List<CSVRecord> records = CsvUtils.readCsv(pathName);
        for (CSVRecord csvRecord : records) {
            s.add(SuggestionConverter.csvRecordToSuggestion(csvRecord));
        }
        return s;
    }
    /**
     * Save suggestion to repository
     * @param suggestion suggestion object
     * @return suggestion object
     */ 
    public static Suggestion save(Suggestion suggestion) {
        suggestions.add(suggestion);
        return suggestion;
    }

    /**
     * Get all suggestions
     * @return list of all suggestions
     */
    public static List<Suggestion> getAllSuggestions() {
        return suggestions;
    }

    /**
     * Find suggestion by ID
     * @param id the id of the suggestion
     * @return suggestion object
     */
    public static Optional<Suggestion> findByID(int id) {
        for (Suggestion suggestion : suggestions) {
            if (suggestion.getReqID() == id) {
                return Optional.of(suggestion);
            }
        }
        return Optional.empty();
    }

    /**
     * Delete suggestion
     * @param suggestion suggestion object
     * @return  suggestion object
     */
    public static Optional<Suggestion> delete(Suggestion suggestion) {
        if (suggestions.remove(suggestion)) {
            return Optional.of(suggestion);
        }
        return Optional.empty();
    }

    /**
     * Update suggestion
     * @param suggestion suggestion object
     * @param newSuggestion new suggestion object
     */
    public static void update(Suggestion suggestion, Suggestion newSuggestion) {
        suggestions.remove(suggestion);
        suggestions.add(newSuggestion);
    }

    /**
     * Save to CSV
     * @throws IOException if file not found
     */
    public static void saveToCsv() throws IOException {
        CsvUtils.writeCsvWithArrayOfData(pathName, SuggestionConverter.suggestionListToCSV(suggestions), headers);
    }

    /**
     * Find all suggestions by camp ID
     * @param userID the user id
     * @return  list of suggestions
     */
    public static List<Suggestion> findByUserID(String userID) {
        List<Suggestion> s = new ArrayList<>();
        for (Suggestion suggestion : suggestions) {
            if (suggestion.getReqAuthor().equals(userID))
                s.add(suggestion);
        }
        return s;
    }

    /**
     * Find all suggestions by camp ID
     * @param campID            the camp id
     * @return                 list of suggestions
     */
    public static List<Suggestion> findByCamp(int campID) {
        List<Suggestion> suggestionList = new ArrayList<>();
        for (Suggestion s: suggestions) {
            if (s.getCampID() == campID)
                suggestionList.add(s);
        }
        return suggestionList;
    }
}
