package it.uniba.dib.sms222315.Friends;

public class MyFriends {

    private String NameFriend;
    private String MailFriend;
    private String numberOfLike;
    private String secretId;

    public MyFriends(String nameFriend, String mailFriend, String numberOfLike, String secretId){
        NameFriend = nameFriend;
        MailFriend = mailFriend;
        this.numberOfLike = numberOfLike;
        this.secretId = secretId;
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
