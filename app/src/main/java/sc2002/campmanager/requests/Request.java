package sc2002.campmanager.requests;

import java.sql.Timestamp;

/**
 * Request class to create requests
 */
public class Request {
    protected int reqID;
    protected int campID;
    protected String reqType;
    protected String author;
    protected String reqDescription;
    protected RequestStatus status;
    protected Timestamp timestamp;

    /**
     * Constructor for Request
     * @param reqID ID of the request
     * @param campID ID of the camp
     * @param author username of the author
     * @param reqDescription description of the request
     */
    public Request(int reqID, int campID, String author, String reqDescription){
        this.reqID = reqID;
        this.campID = campID;
        this.author = author;
        this.reqDescription = reqDescription;
        this.status = RequestStatus.PENDING;
        this.setTimestamp();
    }

    /**
     * Constructor for Request
     * @param reqID ID of the request
     * @param campID associated camp ID
     * @param author username of the author
     * @param reqDescription description of the request
     * @param timestamp timestamp of the request
     * @param requestStatus status of the request
     */
    public Request(int reqID, int campID, String author, String reqDescription, Timestamp timestamp, RequestStatus requestStatus){
        this.reqID = reqID;
        this.campID = campID;
        this.author = author;
        this.reqDescription = reqDescription;
        this.status = RequestStatus.PENDING;
        this.timestamp = timestamp;
        this.status = requestStatus;
    }

    /**
     * Gets the ID of the request
     * @return ID of the request
     */
    public int getReqID(){
        return this.reqID;
    }

    /**
     * Gets the ID of the camp
     * @return ID of the camp
     */
    public int getCampID(){
        return this.campID;
    }

    /**
     * Gets the type of the request
     * @return type of the request
     */
    public String getReqType(){
        return this.reqType;
    }

    /**
     * Gets the author of the request
     * @return author of the request
     */
    public String getReqAuthor(){
        return this.author;
    }

    /**
     * Gets the description of the request
     * @return description of the request
     */
    public String getReqDescription(){
        return this.reqDescription;
    }

    /**
     * Gets the status of the request
     * @return status of the request
     */
    public RequestStatus getStatus(){
        return this.status;
    }

    /**
     * Gets the timestamp of the request
     * @return timestamp of the request
     */
    public Timestamp getTimestamp(){
        return this.timestamp;
    }

    /**
     * Sets the timestamp of the request
     */
    public void setTimestamp(){
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Edits the description of the request
     * @param newDesc new description of the request
     */
    public void editReqDesc(String newDesc){
        this.reqDescription = newDesc;
    }

    /**
     * Sets the status of the request to pending
     */ 
    public void setReqStatusPending(){
        this.status = RequestStatus.PENDING;
    }

    /**
     * Sets the status of the request to processed
     */
    public void setReqStatusProcessed() {
        this.status = RequestStatus.PROCESSED;
    }
}
