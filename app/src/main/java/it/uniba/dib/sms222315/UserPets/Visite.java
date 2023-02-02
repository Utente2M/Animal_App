package it.uniba.dib.sms222315.UserPets;

import android.os.Parcel;
import android.os.Parcelable;

public class Visite implements Parcelable {
    private String prv_Name;
    private String prv_Data;
    private String prv_Description;
    private String prv_categoria;
    private String prv_diagnosi;

    private String docID;


    public Visite(String name, String data, String description, String categoria, String diagnosi) {
        prv_Name = name;
        prv_Data = data;
        prv_Description = description;
        prv_categoria = categoria;
        prv_diagnosi = diagnosi;
    }


    public Visite() {}

    public Visite(String name, String data, String categoria,String diagnosi) {
        prv_Name = name;
        prv_Data = data;
        prv_categoria = categoria;
        prv_diagnosi = diagnosi;


    }


    protected Visite(Parcel in) {
        prv_Name = in.readString();
        prv_Data = in.readString();
        prv_Description = in.readString();
        prv_categoria = in.readString();
        prv_diagnosi = in.readString();
        docID = in.readString();
    }

    public static final Creator<Visite> CREATOR = new Creator<Visite>() {
        @Override
        public Visite createFromParcel(Parcel in) {
            return new Visite(in);
        }

        @Override
        public Visite[] newArray(int size) {
            return new Visite[size];
        }
    };

    public String getPrv_Name() {
        return prv_Name;
    }

    public String getPrv_Data() {
        return prv_Data;
    }

    public String getPrv_Description() {
        return prv_Description;
    }

    public String getPrv_categoria() {
        return prv_categoria;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getPrv_diagnosi() {
        return prv_diagnosi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(prv_Name);
        parcel.writeString(prv_Data);
        parcel.writeString(prv_Description);
        parcel.writeString(prv_categoria);
        parcel.writeString(prv_diagnosi);
        parcel.writeString(docID);
    }
}
