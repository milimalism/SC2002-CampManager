package sc2002.campmanager.requests;

/**
 * Response class to create responses
 */
public class Response {
    private String author;
    private String responseDesc;

    /**
     *  Constructor for Response
     * @param author username of the author
     * @param responseDesc  description of the response
     */
    public Response(String author, String responseDesc){;
        this.author = author;
        this.responseDesc = responseDesc;
    }

    /**
     * returns the author of the response
     * @return author
     */
    public String getAuthor(){
        return this.author;
    }

    /**
     * returns the response description
     * @return response description
     */
    public String getResponseDesc(){
        return this.responseDesc;

    }

    /**
     * edits the response description
     * @param reply new response description
     */
    public void editResponse(String reply){
        this.responseDesc = reply;
    }
}
