package it.uniba.dib.sms222315.UserPets;

public class Visite {
    private String prv_Name;
    private String prv_Data;
    private String prv_Description;
    private String prv_categoria;

    private String docID;


    public Visite(String name, String data, String description, String categoria) {
        prv_Name = name;
        prv_Data = data;
        prv_Description = description;
        prv_categoria = categoria;
    }


    public Visite() {}

    public Visite(String name, String data, String categoria) {
        prv_Name = name;
        prv_Data = data;
        prv_categoria = categoria;


    }


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
}
