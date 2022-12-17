package it.uniba.dib.sms222315.UserExpense;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MyExpense implements Parcelable {

    //data type
    private String prv_Data_MyExpense;
    private String prv_Category_MyExpense;
    private String prv_valFloat_MyExpense;
    private String prv_Description_MyExpense;
    private String prv_CreatAt_Time;


    public MyExpense(String data_myExpense, String category_myExpense,
                     String valFloat_myExpense, String description_myExpense , String currentTime) {
        prv_Data_MyExpense = data_myExpense;
        prv_Category_MyExpense = category_myExpense;
        prv_valFloat_MyExpense = valFloat_myExpense;
        prv_Description_MyExpense = description_myExpense;
        prv_CreatAt_Time = currentTime;
    }


//costructor for expenseview
    public MyExpense(String data_myExpense, String category_myExpense,
                     String valFloat_myExpense, String description_myExpense) {
        prv_Data_MyExpense = data_myExpense;
        prv_Category_MyExpense = category_myExpense;
        prv_valFloat_MyExpense = valFloat_myExpense;
        prv_Description_MyExpense = description_myExpense;
    }



    public MyExpense (){}


    protected MyExpense(Parcel in) {
        prv_Data_MyExpense = in.readString();
        prv_Category_MyExpense = in.readString();
        prv_valFloat_MyExpense = in.readString();
        prv_Description_MyExpense = in.readString();
        prv_CreatAt_Time = in.readString();
    }

    public static final Creator<MyExpense> CREATOR = new Creator<MyExpense>() {
        @Override
        public MyExpense createFromParcel(Parcel in) {
            return new MyExpense(in);
        }

        @Override
        public MyExpense[] newArray(int size) {
            return new MyExpense[size];
        }
    };

    public String getPrv_Data_MyExpense() {
        return prv_Data_MyExpense;
    }

    public String getPrv_Category_MyExpense() {
        return prv_Category_MyExpense;
    }

    public String getPrv_valFloat_MyExpense() {
        return prv_valFloat_MyExpense;
    }


    public String getPrv_Description_MyExpense() {
        return prv_Description_MyExpense;
    }

    public String getPrv_CreatAt_Time() {
        return prv_CreatAt_Time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(prv_Data_MyExpense);
        parcel.writeString(prv_Category_MyExpense);
        parcel.writeString(prv_valFloat_MyExpense);
        parcel.writeString(prv_Description_MyExpense);
        parcel.writeString(prv_CreatAt_Time);
    }
}
