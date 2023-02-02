package it.uniba.dib.sms222315.UserPets;

public class MyPhoto {
    private String prv_photoLink;
    private String prv_DocID;

    public MyPhoto(String linkimage) {
        prv_photoLink = linkimage;
    }

    public MyPhoto(){

    }

    public String getPhotoLink() {
        return prv_photoLink;
    }


    public String getPrv_DocID() {
        return prv_DocID;
    }

    public void setPrv_DocID(String prv_DocID) {
        this.prv_DocID = prv_DocID;
    }
}
