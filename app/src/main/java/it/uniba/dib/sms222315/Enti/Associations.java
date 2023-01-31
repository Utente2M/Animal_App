package it.uniba.dib.sms222315.Enti;

import java.util.List;

public class Associations {

    private String prv_associationName;
    private String prv_phone;
    private String prv_address;
    private String prv_description;
    private String prv_associationLogo;

    private String prv_creatorUID;
    private String prv_creatingTime;
    private String prv_associationUID;

    private List<String> prv_Str_member;


    public Associations(String associationName, String phone,
                        String address, String description,
                        String associationLogo, String creatorUID,
                        String creatingTime, String associationUID, List<String> str_member) {

        prv_associationName = associationName;
        prv_phone = phone;
        prv_address = address;
        prv_description = description;
        prv_associationLogo = associationLogo;
        prv_creatorUID = creatorUID;
        prv_creatingTime = creatingTime;
        prv_associationUID = associationUID;
        prv_Str_member = str_member;
    }

    public Associations() {

    }

    public String getPrv_associationName() {
        return prv_associationName;
    }

    public String getPrv_phone() {
        return prv_phone;
    }

    public String getPrv_address() {
        return prv_address;
    }

    public String getPrv_description() {
        return prv_description;
    }

    public String getPrv_associationLogo() {
        return prv_associationLogo;
    }

    public String getPrv_creatorUID() {
        return prv_creatorUID;
    }

    public String getPrv_creatingTime() {
        return prv_creatingTime;
    }

    public String getPrv_associationUID() {
        return prv_associationUID;
    }

    public void setPrv_doc_id(String prv_doc_id) {
        prv_associationUID = prv_doc_id;
    }

    public List<String> getPrv_Str_member() {
        return prv_Str_member;
    }





}
