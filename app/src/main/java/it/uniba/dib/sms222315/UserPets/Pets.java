package it.uniba.dib.sms222315.UserPets;

import java.util.List;

public class Pets {

    private String prv_str_namePets;
    private String prv_sex;
    private String prv_Razza;
    private String prv_Mantello;
    private String prv_DataNascita;
    private String prv_SegniParticolari;
    private String prv_specie;
    private List<String> prv_Str_responsabili;


    public Pets(String namePets,String specie,  String sex, String razza,
                String mantello, String dataNascita, String segniParticolari ,
                List<String> responsabili ) {

        prv_str_namePets = namePets; //required
        prv_specie = specie; //required
        prv_sex = sex; //required
        prv_Razza = razza; //required
        prv_Mantello = mantello;
        prv_DataNascita = dataNascita;
        prv_SegniParticolari = segniParticolari;
        //is a ListStryng
        prv_Str_responsabili = responsabili;
    }


    public Pets() {}

    //Create the pets object with the information
   public Pets (String namePets,String specie,
                         String sex, String razza){
       prv_str_namePets = namePets; //required
       prv_specie = specie; //required
       prv_sex = sex; //required
       prv_Razza = razza; //required

   }


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


    public List<String> getPrv_Str_responsabili() {
        return prv_Str_responsabili;
    }
}//END CLASS
