package it.uniba.dib.sms222315.Friends;

public class MyFriends {

    private String NameFriend;
    private String MailFriend;
    private String numberOfLike;
    private String secretId;

    public MyFriends(String name, String mail, String Like, String UId){
        NameFriend = name;
        MailFriend = mail;
        numberOfLike = Like;
        secretId = UId;
    }

    //CONSTRUCTOR FOR LISTVIEW
    public MyFriends(String name, String mail, String Like){
        NameFriend = name;
        MailFriend = mail;
        numberOfLike = Like;

    }

    public MyFriends(){}


    public String getNameFriend() {
        return NameFriend;
    }

    public String getMailFriend() {
        return MailFriend;
    }

    public String getNumberOfLike() {
        return numberOfLike;
    }

    public String getSecretId() {
        return secretId;
    }
}
