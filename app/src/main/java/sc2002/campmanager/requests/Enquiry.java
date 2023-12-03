package sc2002.campmanager.requests;

import org.checkerframework.common.returnsreceiver.qual.This;
import sc2002.campmanager.requests.Request;
import sc2002.campmanager.requests.Response;

import java.sql.Timestamp;

/**
 * Enquiry class to create enquiry requests
 */
public class Enquiry extends Request {
    public Response response = null;
    
    /**
     * Constructor for Enquiry
     * @param reqID ID of the request
     * @param campID ID of the camp
     * @param author username of the author
     * @param reqDescription description of the request
     */
    public Enquiry(int reqID, int campID, String author, String reqDescription){
        super(reqID, campID, author, reqDescription);
        this.reqType = "Enquiry";
    }

    /**
     * Constructor for Enquiry
     * @param reqID ID of the request
     * @param campID associated camp ID
     * @param author username of the author
     * @param reqDescription description of the request
     * @param requestStatus status of the request
     * @param timestamp timestamp of the request
     */
    public Enquiry(int reqID, int campID, String author, String reqDescription, RequestStatus requestStatus, Timestamp timestamp){
        super(reqID, campID, author, reqDescription, timestamp, requestStatus);
        this.reqType = "Enquiry";
    }

    /**
     * Gets the status if the enquiry has been responded to
     * 
     * @return status of the enquiry
     */
    public RequestStatus getReqStatusResponded(){
        return this.status;
    }
    /**
     * Gets the response of the enquiry
     * 
     * @return response of the enquiry
     */
    public Response getResponse(){
        return this.response;
    }

    /**
     * Gets the response description of the enquiry
     * 
     * @return enquiry description
     */
    public String getResponseDesc(){
        return this.response.getResponseDesc();
    }

        /**
         * Gets the response author of the enquiry
         * 
         * @return response author
         */
    public String getResponseAuthor(){
        return this.response.getAuthor();
    }

    /**
     * Check if enquiry has a response
     * 
     * @return true if enquiry has a response, false otherwise
     */
    public boolean getStatusResponded(){
        if (this.response == null){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Sets the response of the enquiry
     * @param author id of the response author
     * @param reply description of the response
     */
    public void setResponse(String author, String reply){
        this.response = new Response(author, reply);
        this.status = RequestStatus.PROCESSED;
    }

    /**
     * Edits the response of the enquiry
     * @param reply edited response description
     */
    public void editResponse(String reply){
        this.response.editResponse(reply);
        this.status = RequestStatus.PROCESSED;
    }
    /**
     * Deletes the response of the enquiry
     */
    public void delResponse(){
        this.status = RequestStatus.PENDING;
        this.response = null;
    }
}
