package it.uniba.dib.sms222315.Friends;

import android.os.Parcel;
import android.os.Parcelable;

public class MyFriends implements Parcelable {

    private String nameFriend;
    private String mailFriend;
    private String numberOfLike;
    private String secretId;
    private String urlPhotoProfile;

    public MyFriends(String name, String mail, String Like, String UId , String imgProfileLink){
        nameFriend = name;
        mailFriend = mail;
        numberOfLike = Like;
        secretId = UId;
        urlPhotoProfile = imgProfileLink;
    }

    //CONSTRUCTOR FOR LISTVIEW
    public MyFriends(String name, String mail, String Like){
        nameFriend = name;
        mailFriend = mail;
        numberOfLike = Like;

    }

    public MyFriends(){}


    protected MyFriends(Parcel in) {
        nameFriend = in.readString();
        mailFriend = in.readString();
        numberOfLike = in.readString();
        secretId = in.readString();
    }

    public static final Creator<MyFriends> CREATOR = new Creator<MyFriends>() {
        @Override
        public MyFriends createFromParcel(Parcel in) {
            return new MyFriends(in);
        }

        @Override
        public MyFriends[] newArray(int size) {
            return new MyFriends[size];
        }
    };

    public String getNameFriend() {
        return nameFriend;
    }

    public String getMailFriend() {
        return mailFriend;
    }

    public String getNumberOfLike() {
        return numberOfLike;
    }

    public String getSecretId() {
        return secretId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nameFriend);
        parcel.writeString(mailFriend);
        parcel.writeString(numberOfLike);
        parcel.writeString(secretId);
    }

    public String getUrlPhotoProfile() {
        return urlPhotoProfile;
    }
}
