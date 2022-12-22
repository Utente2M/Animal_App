package it.uniba.dib.sms222315.Friends;

public class MyFriends {

    private String NameFriend;
    private String MailFriend;
    private String numberOfLike;

    public MyFriends(){}

    public MyFriends(String nameFriend, String mailFriend, String numberOfLike) {
        NameFriend = nameFriend;
        MailFriend = mailFriend;
        this.numberOfLike = numberOfLike;
    }

    public String getNameFriend() {
        return NameFriend;
    }

    public String getMailFriend() {
        return MailFriend;
    }

    public String getNumberOfLike() {
        return numberOfLike;
    }
}
