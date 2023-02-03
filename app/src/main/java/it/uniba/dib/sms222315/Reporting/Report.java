package it.uniba.dib.sms222315.Reporting;

import android.os.Parcel;
import android.os.Parcelable;

public class Report implements Parcelable {
    private String prv_secretDocID;
    private String prv_linkImg;
    private String prv_category;
    private String prv_description;
    private int prv_numberLike;
    private String prv_authorName;
    private String prv_authorID;
    private String createAtTime;
    private String addressReport;

    public Report(String DocID, String linkImg, String category,
                  String description, int numberLike, String authorName ,String authorID ,
                  String dataCreated, String address) {
        prv_secretDocID = DocID;
        prv_linkImg = linkImg;
        prv_category = category;
        prv_description = description;
        prv_numberLike = numberLike;
        prv_authorID = authorID;
        prv_authorName = authorName;
        createAtTime = dataCreated;
        addressReport = address;

    }

    public Report (String link , String creatorName, String description , String numberLikeSTR,
                   String address, String category, String docId ){
        prv_linkImg = link ;
        prv_authorName = creatorName;
        prv_description = description;
        prv_numberLike = Integer.parseInt(numberLikeSTR);
        addressReport = address;
        prv_category = category;
        prv_secretDocID = docId;
    }
    public Report (){}

    protected Report(Parcel in) {
        prv_secretDocID = in.readString();
        prv_linkImg = in.readString();
        prv_category = in.readString();
        prv_description = in.readString();
        prv_numberLike = in.readInt();
        prv_authorName = in.readString();
        prv_authorID = in.readString();
        createAtTime = in.readString();
        addressReport = in.readString();
    }

    public static final Creator<Report> CREATOR = new Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel in) {
            return new Report(in);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
        }
    };

    public String getPrv_secretDocID() {
        return prv_secretDocID;
    }
    public void setPrv_secretDocID(String doc_id) {
        prv_secretDocID = doc_id;
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

    public String getPrv_authorName() {
        return prv_authorName;
    }

    public String getAddressReport() {
        return addressReport;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(prv_secretDocID);
        parcel.writeString(prv_linkImg);
        parcel.writeString(prv_category);
        parcel.writeString(prv_description);
        parcel.writeInt(prv_numberLike);
        parcel.writeString(prv_authorName);
        parcel.writeString(prv_authorID);
        parcel.writeString(createAtTime);
        parcel.writeString(addressReport);
    }

    public void setPrv_numberLike(int i) {
        prv_numberLike = i;
    }
}