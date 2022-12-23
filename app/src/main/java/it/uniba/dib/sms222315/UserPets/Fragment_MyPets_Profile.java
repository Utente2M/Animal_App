package it.uniba.dib.sms222315.UserPets;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import it.uniba.dib.sms222315.R;


public class Fragment_MyPets_Profile extends Fragment {

    //FRAGMENT VAR
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;

    TextView nome,data_nasc, sex , specie, razza, mantello , segniPart ;
    ImageView PetImage;
    Button BT_deletePet , BT_modifyPet, BT_newOwner;

    //ISTANCE DB
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private static final String TAG = "TAG_Frag_MyPet_PROFILE";
    Pets receivedPet;


    public Fragment_MyPets_Profile() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LOAD DATA FROM FRAG MY EXPENSE HOME
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            receivedPet = bundle.getParcelable("modPets"); // Key
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG , "onCreateView ");
        View my_view = inflater.inflate(R.layout.fragment__my_pets__profile, container , false);





        setfind(my_view);
        setTextfromPets();
        setAllOnClick();


        // Inflate the layout for this fragment
        return my_view;
    }

    private void setAllOnClick() {
        BT_deletePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteIntoDB();
            }
        });
        BT_modifyPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                my_fragment = new Fragment_MyPets_Modify();
                my_frag_manager = getActivity().getSupportFragmentManager();
                my_frag_trans = my_frag_manager.beginTransaction();
                Bundle bundle = new Bundle();
                //this is pass
                bundle.putParcelable("modPets", receivedPet);
                my_fragment.setArguments(bundle);
                //si aggiunge il richiamo allo stack
                my_frag_trans.addToBackStack(null);
                //add diventa replace
                my_frag_trans.replace(R.id.Frame_Act_MyPets , my_fragment );
                my_frag_trans.commit();
            }
        });

        BT_newOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_fragment = new Fragment_MyPets_Add_Owner();
                my_frag_manager = getActivity().getSupportFragmentManager();
                my_frag_trans = my_frag_manager.beginTransaction();
                Bundle bundle = new Bundle();
                //this is pass
                bundle.putParcelable("modPets", receivedPet);
                my_fragment.setArguments(bundle);
                //si aggiunge il richiamo allo stack
                my_frag_trans.addToBackStack(null);
                //add diventa replace
                my_frag_trans.replace(R.id.Frame_Act_MyPets , my_fragment );
                my_frag_trans.commit();
            }
        });
    }

    private void deleteIntoDB() {
        db.collection("Animal DB").document(receivedPet.getPrv_doc_id())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        getActivity().onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    private void setTextfromPets() {
        nome.setText(receivedPet.getPrv_str_namePets());
        data_nasc.setText(receivedPet.getPrv_DataNascita());
        sex.setText(receivedPet.getPrv_sex());
        specie.setText(receivedPet.getPrv_specie());
        razza.setText(receivedPet.getPrv_Razza());
        mantello.setText(receivedPet.getPrv_Mantello());
        segniPart.setText(receivedPet.getPrv_SegniParticolari());


        if (specie.equals("Cane")){
            PetImage.setImageResource(R.drawable.icon_dog);

        }else if (specie.equals("Gatto")){
            PetImage.setImageResource(R.drawable.icon_cat);

        }else if (specie.equals("Coniglio")){
            PetImage.setImageResource(R.drawable.icon_rabbit);
        }else{
            Log.d(TAG , "specie not found");
        }

    }//END SET TEXT

    //set all find from layout
    private void setfind(View my_view) {

        nome = my_view.findViewById(R.id.TV_MyPetProfile_name);
        data_nasc = my_view.findViewById(R.id.TV_MyPetProfile_data);
        sex = my_view.findViewById(R.id.TV_MyPetProfile_sex);
        specie = my_view.findViewById(R.id.TV_MyPetProfile_specie);
        razza = my_view.findViewById(R.id.TV_MyPetProfile_razza);
        mantello = my_view.findViewById(R.id.TV_MyPetProfile_mantello);
        segniPart = my_view.findViewById(R.id.TV_MyPetProfile_segPartic);
        PetImage = my_view.findViewById(R.id.IV_MyPetProfile_picture);
        BT_deletePet = my_view.findViewById(R.id.BT_DEL_MyPets);
        BT_modifyPet = my_view.findViewById(R.id.BT_MOD_MyPets);
        BT_newOwner = my_view.findViewById(R.id.BT_ADD_RESP_MyPets);

    }
}