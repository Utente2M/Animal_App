package it.uniba.dib.sms222315.Autentication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.protobuf.Empty;

import java.util.Calendar;

import it.uniba.dib.sms222315.R;


public class Fragment_Regis_Basic_info extends Fragment implements DatePickerDialog.OnDateSetListener {

    EditText ET_AutocompletAddress , ET_Name , ET_Phone , ET_DateBorn;
    Button BT_Create;
    CallbackFragment myListnerCall;

    //picker calendar
    int new_day,new_month,new_year;
    //private String sendDateBorn;
    public Fragment_Regis_Basic_info() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View my_view = inflater.inflate(R.layout.fragment__regis__basic_info, container, false);

        ET_Name = my_view.findViewById(R.id.FragRegi_basiInfo_ET_name);
        ET_Phone = my_view.findViewById(R.id.FragRegi_basiInfo_ET_Phone);
        ET_DateBorn = my_view.findViewById(R.id.FragRegi_basiInfo_ET_Date);


        ET_AutocompletAddress = my_view.findViewById(R.id.ET_autocomp_address);
        ET_AutocompletAddress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(MotionEvent.ACTION_UP == motionEvent.getAction()){
                    startAutocompleteActivity ();
                }
                return false;
            }
        });


        ET_DateBorn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(MotionEvent.ACTION_UP == motionEvent.getAction()){
                    showDataPickerDialog();
                }
                return false;
            }
        });




        BT_Create = my_view.findViewById(R.id.FragRegi_basiInfo_BT_Create);
        BT_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendName = ET_Name.getText().toString();
                String sendPhone = ET_Phone.getText().toString();
                String sendAddress = ET_AutocompletAddress.getText().toString();
                String sendDateBorn = ET_DateBorn.getText().toString();

                if (sendName.isEmpty()){
                    ET_Name.setError("Field Required ! ");
                }else{
                    addInformationToProfile (sendName);
                    createUserBasicInfo (sendPhone , sendAddress , sendDateBorn);
                }

            }
        });



        // Inflate the layout for this fragment
        return my_view;
    }//END on Create view




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myListnerCall = (CallbackFragment) context;
    }
    private void createUserBasicInfo(String Phone, String Address,
                                     String DateBorn) {
        myListnerCall.addBasicInfoToUser (Phone , Address , DateBorn);

    }

    private void addInformationToProfile (String name){
        myListnerCall.addInformationToProfile(name);
    }

    private void startAutocompleteActivity( ) {

        myListnerCall.startAutocompleteActivity();
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
        //"day/month/year : "
        month +=1;
        String Str_Date = day_ofYear + "/" + month + "/" + year ;

        new_day=day_ofYear;
        new_month = month;
        new_year = year;

        ET_DateBorn.setText(Str_Date);

    }
}