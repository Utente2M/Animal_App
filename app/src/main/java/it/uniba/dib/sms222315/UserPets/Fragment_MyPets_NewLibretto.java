package it.uniba.dib.sms222315.UserPets;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

    Spinner SP_Attività;
    Spinner SP_Diagnosi;
    String sendAttività;
    String sendDiagnosi;
    Button BT_Create;



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




        return my_view;
    }



    private void setAllButton(View my_view) {
        BT_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createVisitaDB();
            }
        });
        ET_Data.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(MotionEvent.ACTION_UP == motionEvent.getAction()){
                    showDataPickerDialog();
                }

                return false;
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
        ArrayAdapter <CharSequence> adapter_spin_categoria = ArrayAdapter.createFromResource(getContext(), R.array.DiagnosiLib, android.R.layout.simple_spinner_item);
        adapter_spin_categoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP_Diagnosi.setAdapter(adapter_spin_categoria);
        SP_Diagnosi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sendDiagnosi = adapterView.getItemAtPosition(i).toString();
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
        SP_Diagnosi = my_view.findViewById(R.id.spinner_diagnosi);
        BT_Create = my_view.findViewById(R.id.bt_Frag_NewLib_button);

    }



    private void createVisitaDB() {
        //data
        //nome vet
        //spinner(vaccino, visita, operazione)
        //spinner
        //descrizione
        SimpleDateFormat format = new SimpleDateFormat("d,MM,yyyy,HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String formatData = format.format(calendar.getTime());

        String nomeVet = ET_Name.getText().toString();
        //sendAttività
        String descrizione = ET_Descrizione.getText().toString();

        Visite newVisite = new Visite(nomeVet, formatData, descrizione, sendAttività, sendDiagnosi);

        //PROVA DI CREAZIONE SUBCOLLECTION

        db.collection("Animal DB")
                .document(receivedPet.getPrv_doc_id())
                .collection("Visite")
                .add(newVisite)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        resetFrontEnd();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }


    private void showDataPickerDialog (){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day_ofYear) {
                        month +=1;
                        String Str_Date = day_ofYear + "/" + month + "/" + year ;
                        ET_Data.setText(Str_Date);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }


    private void resetFrontEnd() {
        getActivity().onBackPressed();
        ET_Name.setText("");
        ET_Data.setText("");
        ET_Descrizione.setText("");
    }
}