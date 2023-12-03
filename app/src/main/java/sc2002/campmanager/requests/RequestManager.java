package sc2002.campmanager.requests;

import sc2002.campmanager.camp.Camp;
import sc2002.campmanager.camp.CampRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Request manager class to manage requests
 */
public class RequestManager {
//    ArrayList<Enquiry> enquiries;
//    ArrayList<Suggestion> suggestions;
//    ArrayList<Response> responses;
//
//
//    public RequestManager() {
//        this.enquiries = new ArrayList<Enquiry>();
//        this.suggestions = new ArrayList<Suggestion>();
//    }
    /**
     * Last enquiry ID
     */
    private static int lastEnquiryId;
    static {
        if (EnquiryRepository.getAllEnquiries().isEmpty()) {
            lastEnquiryId = 1000;
        }
        else {
            lastEnquiryId = EnquiryRepository.getAllEnquiries().stream().mapToInt(Request::getReqID).max().orElse(1000);
        }
    }
    /**
     * Last suggestion ID
     */
    private static int lastSuggestionId;
    static {
        if (SuggestionsRepository.getAllSuggestions().isEmpty()) {
            lastSuggestionId = 1000;
        }
        else {
            lastSuggestionId = SuggestionsRepository.getAllSuggestions().stream().mapToInt(Request::getReqID).max().orElse(1000);
        }
    }

    /**
     * Get all enquiries
     * @param reqID the request id
     * @return list of all enquiries
     */
    public static Optional<Enquiry> getEnquiry(int reqID){
        return EnquiryRepository.findById(reqID);
    }

    /**
     * Get all enquiries
     * @param campID   the camp id
     * @return list of all enquiries
     */
    public static List<Enquiry> getEnquiriesCamp(int campID){
        return EnquiryRepository.findByCampId(campID);
    }

    /**
     * Get all enquiries
     * @param userID    the user id
     * @return list of all enquiries
     */
    public static List<Enquiry> getEnquiriesUser(String userID){
        return EnquiryRepository.findByUserId(userID);
    }

    /**
     * Get all suggestions
     * @param reqID the request id
     * @return list of all suggestions
     */
    public static Optional<Suggestion> getSuggestion(int reqID){
        return SuggestionsRepository.findByID(reqID);
    }

    /**
     * Get all suggestions
     * @param userID   the user id
     * @return list of all suggestions
     */
    public static List<Suggestion> getSuggestionsUser(String userID){
        return SuggestionsRepository.findByUserID(userID);
    }

    /**
     * Get all suggestions
     * @param campID    the camp id
     * @return list of all suggestions
     */
    public static List<Suggestion> getSuggestionsCamp(int campID){
        return SuggestionsRepository.findByCamp(campID);
    }

    /**
     * Generate a new suggestion ID
     * @return new suggestion ID
     */
    public static int genSuggestionID(){
        lastSuggestionId++;
        return lastSuggestionId;
    }

    /**
     * Generate a new enquiry ID
     * @return new enquiry ID
     */
    public static int genEnquiryID() {
        lastEnquiryId++;
        return lastEnquiryId;
    }

    /**
     * Create a new enquiry
     * @param campID the camp id
     * @param author the author
     * @param reqDescription the request description
     * @return new enquiry ID
     */
    public static int newEnquiry(int campID, String author, String reqDescription){
        int newID = genEnquiryID();
        Enquiry enq = new Enquiry(newID, campID, author, reqDescription);
        Optional<Camp> camp = CampRepository.findByID(campID);
        if (camp.isPresent())
            camp.get().addEnquiries(newID);

        EnquiryRepository.save(enq);

        return newID;
    }

    /**
     * Create a new suggestion
     * @param campID the camp id
     * @param author the author
     * @param reqDescription the request description
     * @return new suggestion ID
     */
    public static int newSuggestion(int campID, String author, String reqDescription){
        int newID = genSuggestionID();
        Suggestion suggestion = new Suggestion(newID, campID, author, reqDescription, false);
        Optional<Camp> camp = CampRepository.findByID(campID);
        if (camp.isPresent())
            camp.get().addSuggestions(newID);

        SuggestionsRepository.save(suggestion);

        return newID;
    }

    /**
     * Edit an enquiry
     * @param enqID the enquiry id
     * @param reqDesc the request description
     */
    public static void editEnquiry(int enqID, String reqDesc){
        Optional<Enquiry> enq = EnquiryRepository.findById(enqID);
        if (enq.isPresent()) {
            enq.get().editReqDesc(reqDesc);
        }
    }

    /**
     * Edit an enquiry
     * @param suggId  the suggestion id
     * @param reqDesc the request description
     */
    public static void editSuggestion(int suggId, String reqDesc) {
        Optional<Suggestion> suggestion = SuggestionsRepository.findByID(suggId);
        if (suggestion.isPresent()) {
            suggestion.get().editReqDesc(reqDesc);
        }
    }

    /**
     * Delete an enquiry
     * @param enqID the enquiry id
     */
    public static void delEnquiry(int enqID){
        Optional<Enquiry> enq = EnquiryRepository.findById(enqID);
        if (enq.isPresent()) {
            EnquiryRepository.delete(enq.get());
            Optional<Camp> camp = CampRepository.findByID(enq.get().getCampID());
            if (camp.isPresent())
                camp.get().getEnquiries().remove(Integer.valueOf(enqID));
        }

    }

    /**
     * Reply to an enquiry
     * @param enqId the enquiry id
     * @param author the author
     * @param reply the reply
     */
    public static void replyEnquiry(int enqId, String author, String reply) {
        Optional<Enquiry> enq = EnquiryRepository.findById(enqId);
        if (enq.isPresent()) {
            enq.get().setResponse(author, reply);
        }
    }

    /**
     * Delete a suggestion
     * @param suggestionID the suggestion id
     */
    public static void delSuggestion(int suggestionID){
        Optional<Suggestion> suggestion = SuggestionsRepository.findByID(suggestionID);
        if (suggestion.isPresent()) {
            SuggestionsRepository.delete(suggestion.get());
            Optional<Camp> camp = CampRepository.findByID(suggestion.get().getCampID());
            camp.get().getSuggestions().remove(Integer.valueOf(suggestionID));
        }
    }

    /**
     * Edit a suggestion
     * @param enqID the enquiry id
     * @param description enquiry description
     */
    public static void editResponse(int enqID, String description){
        Optional<Enquiry> enq = getEnquiry(enqID);
        if (enq.isPresent())
            enq.get().editResponse(description);
    }
    /**
     * Accept a suggestion
     * @param suggestionID the suggestion id
     */
    public static void acceptSuggestion(int suggestionID){
        Optional<Suggestion> req = getSuggestion(suggestionID);
        if (req.isPresent())
            req.get().setApprovalTrue();
    }

    /**
     * Reject a suggestion
     * @param suggestionID the suggestion id
     */
    public static void rejectSuggestion(int suggestionID){
        Optional<Suggestion> req = getSuggestion(suggestionID);
        if (req.isPresent())
            req.get().setApprovalFalse();
    }

    /**
     * Get suggestion approval
     * @param suggestionID the suggestion id
     * @return true if suggestion is approved
     */
    public static boolean getSuggestionApproval(int suggestionID){
        Optional<Suggestion> req = getSuggestion(suggestionID);
        if (req.isPresent())
            return req.get().getApproval();
        return false;
    }

    /**
     * Get all approved suggestions
     * @param campID the camp id
     * @return list of approved suggestions
     */
    public static List<Suggestion> getApprovedSuggestions(int campID){
        List<Suggestion> suggestions = new ArrayList<Suggestion>();
        for (Suggestion s : SuggestionsRepository.findByCamp(campID)) {
            if (s.getApproval())
                suggestions.add(s);
        }
        return suggestions;
    }
}
