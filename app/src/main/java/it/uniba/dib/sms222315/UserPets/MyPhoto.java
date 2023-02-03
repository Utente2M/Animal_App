package it.uniba.dib.sms222315.UserPets;

public class MyPhoto {
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

    public String getPhotoLink() {
        return photoLink;
    }


    public String getPrv_DocID() {
        return prv_DocID;
    }

    public void setPrv_DocID(String DocID) {
        prv_DocID = DocID;
    }
}
