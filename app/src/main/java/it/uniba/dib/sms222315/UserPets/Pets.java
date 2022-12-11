package it.uniba.dib.sms222315.UserPets;

public class Pets {

    private String prv_str_namePets;
    private Boolean prv_isMale;
    private String prv_Razza;
    private String prv_Mantello;
    private String prv_DataNascita;
    private String prv_SegniParticolari;

    public Pets(String namePets, Boolean isMale, String razza,
                String mantello, String dataNascita, String segniParticolari) {

        prv_str_namePets = namePets;
        prv_isMale = isMale;
        prv_Razza = razza;
        prv_Mantello = mantello;
        prv_DataNascita = dataNascita;
        prv_SegniParticolari = segniParticolari;
    }


    public String getPrv_str_namePets() {
        return prv_str_namePets;
    }

    public Boolean getPrv_isMale() {
        return prv_isMale;
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
}//END CLASS
