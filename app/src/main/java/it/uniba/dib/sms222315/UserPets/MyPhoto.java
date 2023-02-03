package it.uniba.dib.sms222315.UserPets;

import android.os.Parcel;
import android.os.Parcelable;

public class MyPhoto implements Parcelable {
    private String photoLink;
    private String prv_DocID;

    public MyPhoto(String linkimage , String docId) {
        photoLink = linkimage;
        prv_DocID = docId;
    }

    public MyPhoto (String linkImage ){
        photoLink = linkImage;
    }

    public MyPhoto(){

    }

    protected MyPhoto(Parcel in) {
        photoLink = in.readString();
        prv_DocID = in.readString();
    }

    public static final Creator<MyPhoto> CREATOR = new Creator<MyPhoto>() {
        @Override
        public MyPhoto createFromParcel(Parcel in) {
            return new MyPhoto(in);
        }

        @Override
        public MyPhoto[] newArray(int size) {
            return new MyPhoto[size];
        }
    };

    public String getPhotoLink() {
        return photoLink;
    }


    public String getPrv_DocID() {
        return prv_DocID;
    }

    public void setPrv_DocID(String DocID) {
        prv_DocID = DocID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(photoLink);
        parcel.writeString(prv_DocID);
    }
}
