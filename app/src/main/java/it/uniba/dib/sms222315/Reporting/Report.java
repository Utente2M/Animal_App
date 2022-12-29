package it.uniba.dib.sms222315.Reporting;

public class Report {
    private String prv_secretDocID;
    private String prv_linkImg;
    private String prv_category;
    private String prv_description;
    private int prv_numberLike;
    //
    private String prv_authorID;
    private String createAtTime;

    public Report(String DocID, String linkImg, String category,
                  String description, int numberLike, String authorID , String dataCreated) {
        prv_secretDocID = DocID;
        prv_linkImg = linkImg;
        prv_category = category;
        prv_description = description;
        prv_numberLike = numberLike;
        prv_authorID = authorID;
        createAtTime = dataCreated;
    }

    public String getPrv_secretDocID() {
        return prv_secretDocID;
    }

    public String getPrv_linkImg() {
        return prv_linkImg;
    }

    public String getPrv_category() {
        return prv_category;
    }

    public String getPrv_description() {
        return prv_description;
    }

    public int getPrv_numberLike() {
        return prv_numberLike;
    }

    public String getPrv_authorID() {
        return prv_authorID;
    }

    public String getCreateAtTime() {
        return createAtTime;
    }
}
