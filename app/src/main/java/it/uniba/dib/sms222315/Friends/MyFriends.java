package it.uniba.dib.sms222315.Friends;

public class MyFriends {

    private String nameFriend;
    private String mailFriend;
    private String numberOfLike;
    private String secretId;

    public MyFriends(String name, String mail, String Like, String UId){
        nameFriend = name;
        mailFriend = mail;
        numberOfLike = Like;
        secretId = UId;
    }

    //CONSTRUCTOR FOR LISTVIEW
    public MyFriends(String name, String mail, String Like){
        nameFriend = name;
        mailFriend = mail;
        numberOfLike = Like;

    }

    public MyFriends(){}


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
}
