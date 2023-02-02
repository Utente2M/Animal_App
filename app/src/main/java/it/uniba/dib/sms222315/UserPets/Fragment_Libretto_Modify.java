package it.uniba.dib.sms222315.UserPets;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import it.uniba.dib.sms222315.R;


public class Fragment_Libretto_Modify extends Fragment {



    //DB VARIABLE
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //BUNDLE
    Visite receivedVisite;
    Pets receivedPet;

    TextView nameMod , dataMod, categoriaMod;
    Spinner SP_diagnosiMod;
    String sendDiagnosiMod;
    EditText descrizioneMod;
    Button BT_CreaMod , BT_EliminaMod;

    //FRAGMENT VAR
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;

    private static final String TAG = "TAG_Frag_Libretto_MODIFY";


    public Fragment_Libretto_Modify() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LOAD DATA FROM FRAG MY EXPENSE HOME
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            receivedVisite = bundle.getParcelable("modVisite"); // Key
            receivedPet = bundle.getParcelable("modPet");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View my_view = inflater.inflate(R.layout.fragment__libretto__modify, container, false);

        setfind(my_view);
        setTextfromVisit();
        setAllSpinner(my_view);
        setAllOnClick();

        return my_view;
    }

    private void setAllOnClick() {

        BT_CreaMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            updateLibrettoIntoDB();
                Log.d(TAG,"Nome pet"+ receivedPet.getPrv_str_namePets());
            }
        });

        BT_EliminaMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Attenzione!");
                builder.setMessage("Sei sicuro di eliminare questa visita?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteIntoDB();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

                //qui
                //deleteIntoDB();
            }
        });


    }

    private void deleteIntoDB() {
        //caricamentodbcollection
        DocumentReference visiteRef = db.collection("Animal DB").
                document(receivedPet.getPrv_doc_id())
                .collection("Visite")
                .document(receivedVisite.getDocID());
        visiteRef.delete()
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


    private void updateLibrettoIntoDB() {
        DocumentReference visiteRef = db.collection("Animal DB").
                document(receivedPet.getPrv_doc_id())
                .collection("Visite")
                .document(receivedVisite.getDocID());

// Set the "isCapital" field of the city 'DC'
        visiteRef
                .update("prv_Data", dataMod.getText().toString() ,
                        "prv_Description" , descrizioneMod.getText().toString() ,
                        "prv_Name" , nameMod.getText().toString() ,
                        "prv_categoria" , categoriaMod.getText().toString()
                    //    "prv_diagnosi" , SP_diagnosiMod.getText().toString()

                )//end update

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        //return to home animal

                    getActivity().onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });

    }



    private void setAllSpinner(View my_view) {
        ArrayAdapter<CharSequence> adapter_spin_categoria = ArrayAdapter.createFromResource(getContext(), R.array.DiagnosiLib, android.R.layout.simple_spinner_item);
        adapter_spin_categoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP_diagnosiMod.setAdapter(adapter_spin_categoria);
        SP_diagnosiMod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sendDiagnosiMod = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setTextfromVisit() {
        if (!receivedVisite.getPrv_Name().isEmpty()){
            nameMod.setText(receivedVisite.getPrv_Name());
        }
        if (!receivedVisite.getPrv_Data().isEmpty()){
            dataMod.setText(receivedVisite.getPrv_Data());
        }
        if (!receivedVisite.getPrv_categoria().isEmpty()){
            categoriaMod.setText(receivedVisite.getPrv_categoria());
        }
        if (!receivedVisite.getPrv_Description().isEmpty()){
            descrizioneMod.setText(receivedVisite.getPrv_Description());
        }
    }

    private void setfind(View my_view) {
        nameMod = my_view.findViewById(R.id.et_Frag_ModifyLib_name);

        dataMod = my_view.findViewById(R.id.et_Frag_ModifyLib_data);
        categoriaMod = my_view.findViewById(R.id.spinner_modifyattività);
        SP_diagnosiMod = my_view.findViewById(R.id.spinner_modifydiagnosi);

        descrizioneMod = my_view.findViewById(R.id.et_Frag_MofidyLib_descrizione);
        BT_CreaMod = my_view.findViewById(R.id.bt_Frag_ModifyLib_button_Cre);
        BT_EliminaMod = my_view.findViewById(R.id.bt_Frag_MofifyLib_button_Elim);


    }
}