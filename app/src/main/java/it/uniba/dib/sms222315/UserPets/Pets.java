package it.uniba.dib.sms222315.UserPets;

public class Pets {

    private String prv_str_namePets;
    private String prv_sex;
    private String prv_Razza;
    private String prv_Mantello;
    private String prv_DataNascita;
    private String prv_SegniParticolari;
    private String prv_specie;

    public Pets(String namePets,String specie,  String sex, String razza,
                String mantello, String dataNascita, String segniParticolari) {

        prv_str_namePets = namePets; //required
        prv_specie = specie; //required
        prv_sex = sex; //required
        prv_Razza = razza; //required
        prv_Mantello = mantello;
        prv_DataNascita = dataNascita;
        prv_SegniParticolari = segniParticolari;
    }


    public Pets() {}


    public String getPrv_str_namePets() {
        return prv_str_namePets;
    }

    public String getPrv_sex() {
        return prv_sex;
    }

    public String getPrv_Razza() {
        return prv_Razza;
    }

    public String getPrv_Mantello() {
        return prv_Mantello;
    }

    public String getPrv_DataNascita() {
        return prv_DataNascita;
    }

    public String getPrv_SegniParticolari() {
        return prv_SegniParticolari;
    }

    public String getPrv_specie() {
        return prv_specie;
    }



}//END CLASS
