package sc2002.campmanager.requests;

import sc2002.campmanager.requests.Request;

import java.sql.Timestamp;

/**
 * Suggestion class to create suggestions
 */
public class Suggestion extends Request {
    boolean approvalStatus;

    /**
     * Constructor for Suggestion
     * @param reqID ID of the request
     * @param campID ID of the camp
     * @param author username of the author
     * @param reqDescription description of the request
     * @param approvalStatus status of the request
     */
    public Suggestion(int reqID, int campID, String author, String reqDescription, boolean approvalStatus){
        super(reqID, campID, author, reqDescription);
        this.approvalStatus = approvalStatus;
        this.reqType = "Suggestion";
    }

    /**
     * Constructor for Suggestion
     * @param reqID ID of the request
     * @param campID ID of the camp
     * @param author username of the author
     * @param reqDescription description of the request
     * @param approvalStatus status of the request
     * @param timeStamp timestamp of the request
     * @param requestStatus status of the request
     */
    public Suggestion(int reqID, int campID, String author, String reqDescription, boolean approvalStatus, Timestamp timeStamp, RequestStatus requestStatus){
        super(reqID, campID, author, reqDescription, timeStamp, requestStatus);
        this.approvalStatus = approvalStatus;
        this.reqType = "Suggestion";
    }

    /**
     * Sets the approval status of the suggestion to true
     */
    public void setReqStatusAccepted(){
        this.approvalStatus = true;
    }

    /**
     * Sets the approval status of the suggestion to false
     */
    public void setReqStatusRejected(){
        this.approvalStatus = false;
    }

    /**
     * Returns the approval status of the suggestion
     */
    public void setApprovalTrue(){
        this.approvalStatus = true;
        this.status = RequestStatus.PROCESSED;
    }

    /**
     * Returns the approval status of the suggestion
     */
    public void setApprovalFalse(){
        this.approvalStatus = false;
        this.status = RequestStatus.PROCESSED;
    }

    /**
     * Returns the approval status of the suggestion
     * @return approval status
     */
    public boolean getApproval(){
        return this.approvalStatus;
    }
}
