package sc2002.campmanager.requests;

import org.apache.commons.csv.CSVRecord;
import sc2002.campmanager.util.DateTimeConverter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Enquiry converter class to convert enquiry object to csv record and vice versa
 */
public class EnquiryConverter {
    // This seemed cool so am working on this newer implementation for converters. If works will implement
//    private static Map<String, String> invokeGettersAndConvertToString(Enquiry object) {
//        Map<String, String> results = new HashMap<>();
//        Method[] methods = object.getClass().getMethods();
//
//        for (Method method : methods) {
//            if (isGetter(method))
//                try {
//                    Object returnValue = method.invoke(object);
//                    String stringValue = returnValue != null ? returnValue.toString() : "null";
//                    results.put(method.getName().replace("get", ""), stringValue);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//        }
//        return results;
//    }
//
//    private static boolean isGetter(Method method) {
//        if (!method.getName().startsWith("get")) return false;
//        if (method.getParameterTypes().length != 0) return false;
//        if (void.class.equals(method.getReturnType())) return false;
//        return true;
//    }
    /**
     * Converts an enquiry to a list of strings for CSV
     * @param enquiry enquiry to be converted
     * @return list of strings for CSV
     */
    public static List<String> enquiryToCsvRecord(Enquiry enquiry) {
        List<String> s = new ArrayList<>();
        s.add(Integer.toString(enquiry.getReqID()));
        s.add(Integer.toString(enquiry.getCampID()));
        s.add(enquiry.getReqAuthor());
        s.add(enquiry.getReqDescription());
        s.add(enquiry.getStatus().name());
        s.add(enquiry.getTimestamp().toString());
        if (enquiry.getResponse() == null)
            s.add("null");
        else
            s.add("[" + enquiry.getResponse().getAuthor() + ", " + enquiry.getResponse().getResponseDesc() + "]");
        return s;
    }
    /**
     * Converts a CSV record to an enquiry
     * @param csvRecord CSV record to be converted
     * @return enquiry
     */
    public static Enquiry csvRecordToEnquiry(CSVRecord csvRecord) {
        int reqId = Integer.parseInt(csvRecord.get("Request ID"));
        int campId = Integer.parseInt(csvRecord.get("Camp ID"));
        String author = csvRecord.get("Author");
        String description = csvRecord.get("Request Description");
        RequestStatus requestStatus = RequestStatus.valueOf(csvRecord.get("Status"));
        Timestamp timestamp = Timestamp.valueOf(csvRecord.get("Timestamp"));
        String[] response = csvRecord.get("Response").split(",\\s*");
        Enquiry enquiry = new Enquiry(reqId, campId, author, description, requestStatus, timestamp);
        String responseAuthor;
        String responseDesc;
        if (response.length == 2) {
            responseAuthor = response[0];
            responseDesc = response[1];
            enquiry.setResponse(responseAuthor, responseDesc);
        }
        return enquiry;
    }

    /**
     * Converts a list of enquiries to a list of strings for CSV
     * @param suggestionList list of enquiries to be converted
     * @return list of strings for CSV
     */
    public static List<List<String>> enquiryListToCSV(List<Enquiry> suggestionList) {
        List<List<String>> enquiryListString = new ArrayList<>();
        for (Enquiry e : suggestionList) {
            enquiryListString.add(enquiryToCsvRecord(e));
        }
        return enquiryListString;
    }

}
