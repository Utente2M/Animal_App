package it.uniba.dib.sms222315.Reporting;

public class Comment {

    private String prv_authorID;
    private String prv_authorName;
    private String prv_Date;
    private String prv_CommentText;
    private String prv_docID;

    public Comment(String authorID, String authorName, String date, String commentText) {
        prv_authorID = authorID;
        prv_authorName = authorName;
        prv_Date = date;
        prv_CommentText = commentText;
    }

    public Comment() {

    }

    public String getPrv_docID() {
        return prv_docID;
    }

    public void setPrv_docID(String docID) {
        prv_docID = docID;
    }

    public String getPrv_authorID() {
        return prv_authorID;
    }

    public String getPrv_authorName() {
        return prv_authorName;
    }

    public String getPrv_Date() {
        return prv_Date;
    }

    public String getPrv_CommentText() {
        return prv_CommentText;
    }
}
