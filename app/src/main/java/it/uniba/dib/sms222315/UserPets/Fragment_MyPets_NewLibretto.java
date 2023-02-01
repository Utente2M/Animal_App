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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import it.uniba.dib.sms222315.Enti.Associations;
import it.uniba.dib.sms222315.R;

public class Fragment_MyPets_NewLibretto extends Fragment {


    Pets receivedPet;

    EditText ET_Name , ET_Descrizione ,ET_Data;
    ListView mListView;
    Spinner SP_Attività;
    String sendAttività;
    Button BT_Create;

    VisiteAdapter adapter;

    //Control ListView
    ArrayList<Visite> originalList = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "TAG_Frag_NewLibretto";


    public Fragment_MyPets_NewLibretto() {
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
        // Inflate the layout for this fragment
        View my_view = inflater.inflate(R.layout.fragment__my_pets__new_libretto, container, false);
        setAllfind(my_view);
        setAllSpinner(my_view);
        setAllButton(my_view);

        if (adapter ==null){
            originalList.clear();
            //filteredList.clear();
            popolateList();
            Log.d(TAG , "ok popolateList ");
        }


        return my_view;
    }

    private void popolateList() {
        originalList.clear();

        /*
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Log.d(TAG,"This is UID " + userID);
         */


        CollectionReference animalRef = db.collection("Animal DB")
                .document(receivedPet.getPrv_doc_id())
                .collection("Visite");

        animalRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());


                        Visite myvisita = document.toObject(Visite.class);
                        myvisita.setDocID(document.getId());
                        originalList.add(myvisita);
                        Log.d(TAG , "uid doc : " + myvisita.getDocID());

                    }//end for

                    VisiteAdapter adapter = new VisiteAdapter(getContext(),
                            R.layout.adapter_my_pets_list, originalList);

                    mListView.setAdapter(adapter);


                }//end if
                else {
                    //nessun animale da mostrare
                }//fine else

            }//end on complete

        }); //end Listners


    }//END popolateList



    private void setAllButton(View my_view) {
        BT_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createVisitaDB();
            }
        });
    }


    private void setAllSpinner(View my_view) {
        ArrayAdapter<CharSequence> adapter_spin = ArrayAdapter.createFromResource(getContext(), R.array.CategoryLibretto, android.R.layout.simple_spinner_item);
        adapter_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP_Attività.setAdapter(adapter_spin);
        SP_Attività.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sendAttività = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setAllfind(View my_view) {
        ET_Name = my_view.findViewById(R.id.et_Frag_NewLib_name);
        ET_Descrizione = my_view.findViewById(R.id.et_Frag_NewLib_descrizione);
        ET_Data = my_view.findViewById(R.id.et_Frag_NewLib_data);
        SP_Attività = my_view.findViewById(R.id.spinner_attività);
        BT_Create = my_view.findViewById(R.id.bt_Frag_NewLib_button);

    }



    private void createVisitaDB() {
        //data
        //nome vet
        //spinner(vaccino, visita, operazione)
        //descrizione
        SimpleDateFormat format = new SimpleDateFormat("d,MM,yyyy,HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String formatData = format.format(calendar.getTime());

        String nomeVet = ET_Name.getText().toString();
        //sendAttività
        String descrizione = ET_Descrizione.getText().toString();

        Visite newVisite = new Visite(nomeVet, formatData, descrizione, sendAttività);

        //PROVA DI CREAZIONE SUBCOLLECTION

        db.collection("Animal DB")
                .document(receivedPet.getPrv_doc_id())
                .collection("Visite")
                .add(newVisite)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }
}