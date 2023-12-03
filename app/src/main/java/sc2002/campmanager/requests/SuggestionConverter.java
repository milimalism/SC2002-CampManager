package sc2002.campmanager.requests;

import org.apache.commons.csv.CSVRecord;
import sc2002.campmanager.util.DateTimeConverter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Suggestion converter class to convert suggestion object to csv record and vice versa
 */
public class SuggestionConverter {
    /**
     * Convert suggestion object to csv record
     * @param suggestion suggestion object
     * @return csv record
     */
    public static List<String> suggestionToCsvRecord(Suggestion suggestion) {
        List<String> s = new ArrayList<>();
        s.add(Integer.toString(suggestion.getReqID()));
        s.add(Integer.toString(suggestion.getCampID()));
        s.add(suggestion.getReqAuthor());
        s.add(suggestion.getReqDescription());
        s.add(suggestion.getStatus().name());
        s.add(suggestion.getTimestamp().toString());
        s.add(Boolean.toString(suggestion.getApproval()));
        return s;
    }

    /**
     * Convert csv record to suggestion object
     * @param csvRecord csv record
     * @return suggestion object
     */
    public static Suggestion csvRecordToSuggestion(CSVRecord csvRecord) {
        int reqId = Integer.parseInt(csvRecord.get("Request ID"));
        int campId = Integer.parseInt(csvRecord.get("Camp ID"));
        String author = csvRecord.get("Author");
        String description = csvRecord.get("Request Description");
        RequestStatus requestStatus = RequestStatus.valueOf(csvRecord.get("Status"));
        Timestamp timestamp = Timestamp.valueOf(csvRecord.get("Timestamp"));
        boolean approvalStatus = Boolean.parseBoolean(csvRecord.get("Approval Status"));
        return new Suggestion(reqId, campId, author, description, approvalStatus, timestamp, requestStatus);
    }

    /**
     *  Convert list of suggestions to csv
     * @param suggestionList list of suggestions
     * @return list of csv records
     */
    public static List<List<String>> suggestionListToCSV(List<Suggestion> suggestionList) {
        List<List<String>> suggestionListString = new ArrayList<>();
        for (Suggestion s : suggestionList) {
            suggestionListString.add(suggestionToCsvRecord(s));
        }
        return suggestionListString;
    }
}
