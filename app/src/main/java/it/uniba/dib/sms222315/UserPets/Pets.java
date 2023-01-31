package it.uniba.dib.sms222315.UserPets;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Pets implements Parcelable {

    private String prv_str_namePets;
    private String prv_sex;
    private String prv_Razza;
    private String prv_Mantello;
    private String prv_DataNascita;
    private String prv_SegniParticolari;
    private String prv_specie;
    private List<String> prv_Str_responsabili;
    private String prv_doc_id;
    private String linkPhotoPets;

    private String prv_numChip;
    private String prv_dataChip;
    private String prv_addressPet;


    public Pets(String namePets,String specie,  String sex, String razza,
                String mantello, String dataNascita, String segniParticolari ,
                String numeroChip, String dataChip , String indirizzo ,
                List<String> responsabili , String photoLink ) {

        prv_str_namePets = namePets; //required
        prv_sex = sex; //required
        prv_specie = specie; //required
        prv_DataNascita = dataNascita;

        prv_Mantello = mantello;
        prv_Razza = razza; //required
        prv_SegniParticolari = segniParticolari;

        prv_numChip = numeroChip;
        prv_dataChip = dataChip;
        prv_addressPet = indirizzo;

        //is a ListStryng
        prv_Str_responsabili = responsabili;
        linkPhotoPets = photoLink;
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


    protected Pets(Parcel in) {
        prv_str_namePets = in.readString();
        prv_sex = in.readString();
        prv_Razza = in.readString();
        prv_Mantello = in.readString();
        prv_DataNascita = in.readString();
        prv_SegniParticolari = in.readString();
        prv_specie = in.readString();
        prv_Str_responsabili = in.createStringArrayList();
    }

    public static final Creator<Pets> CREATOR = new Creator<Pets>() {
        @Override
        public Pets createFromParcel(Parcel in) {
            return new Pets(in);
        }

        @Override
        public Pets[] newArray(int size) {
            return new Pets[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(prv_str_namePets);
        parcel.writeString(prv_sex);
        parcel.writeString(prv_Razza);
        parcel.writeString(prv_Mantello);
        parcel.writeString(prv_DataNascita);
        parcel.writeString(prv_SegniParticolari);
        parcel.writeString(prv_specie);
        parcel.writeStringList(prv_Str_responsabili);
    }

    public String getPrv_doc_id() {
        return prv_doc_id;
    }

    public void setPrv_doc_id(String prv_doc_id) {
        this.prv_doc_id = prv_doc_id;
    }

    public String getLinkPhotoPets() {
        return linkPhotoPets;
    }

    public String getPrv_numChip() {
        return prv_numChip;
    }

    public String getPrv_dataChip() {
        return prv_dataChip;
    }

    public String getPrv_addressPet() {
        return prv_addressPet;
    }
}//END CLASS
