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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import it.uniba.dib.sms222315.R;


public class Fragment_MyPets_Modify extends Fragment implements DatePickerDialog.OnDateSetListener {

    //DB VARIABLE
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //picker calendar
    int new_day,new_month,new_year;


    //BUNDLE
    Pets receivedPet;

    EditText data_nasc , razza, mantello , segniPart ;
    ImageButton BT_confermePet , BT_backPet;

    //FRAGMENT VAR
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;

    private static final String TAG = "TAG_Frag_MyPet_MODIFY";



    public Fragment_MyPets_Modify() {
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

        View my_view = inflater.inflate(R.layout.fragment__my_pets__modify, container, false);
        Log.d(TAG , "onCreateView ");


        setfind(my_view);
        setTextfromPets();
        setAllOnClick();


        return my_view;
    }

    private void setAllOnClick() {
        BT_backPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        BT_confermePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateAnimaleIntoDB();

            }
        });


        data_nasc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(MotionEvent.ACTION_UP == motionEvent.getAction()){
                    showDataPickerDialog();
                }

                return false;
            }
        });





    }

    private void updateAnimaleIntoDB() {

        DocumentReference animalRef = db.collection("Animal DB").
                document(receivedPet.getPrv_doc_id());

// Set the "isCapital" field of the city 'DC'
        animalRef
                .update("prv_DataNascita", data_nasc.getText().toString() ,
                        "prv_Razza" , razza.getText().toString() ,
                        "prv_Mantello" , mantello.getText().toString() ,
                        "prv_SegniParticolari" , segniPart.getText().toString() )

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        //return to home animal

                        Fragment_MyPets_Home my_fragment = new Fragment_MyPets_Home();
                        my_frag_manager = getActivity().getSupportFragmentManager();
                        my_frag_trans = my_frag_manager.beginTransaction();
                        my_frag_trans.replace(R.id.Frame_Act_MyPets , my_fragment);
                        my_frag_trans.commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }


    private void setTextfromPets() {

        if (!receivedPet.getPrv_DataNascita().isEmpty()){
            data_nasc.setText(receivedPet.getPrv_DataNascita());
        }
        if (!receivedPet.getPrv_Razza().isEmpty()){
            razza.setText(receivedPet.getPrv_Razza());
        }
        if (!receivedPet.getPrv_Mantello().isEmpty()){
            mantello.setText(receivedPet.getPrv_Mantello());
        }
        if (!receivedPet.getPrv_SegniParticolari().isEmpty()){
            segniPart.setText(receivedPet.getPrv_SegniParticolari());
        }


    }//END SET TEXT

    //set all find from layout
    private void setfind(View my_view) {


        data_nasc = my_view.findViewById(R.id.ET_MyPetModify_data);

        razza = my_view.findViewById(R.id.ET_MyPetModify_razza);
        mantello = my_view.findViewById(R.id.ET_MyPetModify_mantello);
        segniPart = my_view.findViewById(R.id.ET_MyPetModify_segPartic);

        BT_backPet = my_view.findViewById(R.id.BT_Back_MyPetsModify);
        BT_confermePet = my_view.findViewById(R.id.BT_Apply_MyPetsModify);

    }

    private void showDataPickerDialog (){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity() ,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }




    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day_ofYear) {
        month +=1;
        //"day/month/year : "
        String Str_Date = day_ofYear + "/" + month + "/" + year ;

        new_day=day_ofYear;
        new_month = month;
        new_year = year;

        data_nasc.setText(Str_Date);

    }
}//END CLASS