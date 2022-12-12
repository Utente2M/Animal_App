package it.uniba.dib.sms222315.Autentication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.protobuf.Empty;

import it.uniba.dib.sms222315.R;


public class Fragment_Regis_Basic_info extends Fragment {

    EditText ET_AutocompletAddress , ET_Name;
    Button BT_Create;
    CallbackFragment myListnerCall;

    public Fragment_Regis_Basic_info() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View my_view = inflater.inflate(R.layout.fragment__regis__basic_info, container, false);

        ET_Name = my_view.findViewById(R.id.FragRegi_basiInfo_ET_name);
        ET_AutocompletAddress = my_view.findViewById(R.id.ET_autocomp_address);
/*
LoginOrRegisterActivity myActivity = (LoginOrRegisterActivity) getActivity();
        String myDataFromActivity = myActivity.myString_address();

        ET_AutocompletAddress.setText(myDataFromActivity);
 */
        ET_AutocompletAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                startAutocompleteActivity ();
            }
        });



        BT_Create = my_view.findViewById(R.id.FragRegi_basiInfo_BT_Create);
        BT_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendName = ET_Name.getText().toString();
                addInformationToProfile (sendName);
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

    private void addInformationToProfile (String name){
        myListnerCall.addInformationToProfile(name);
    }

    private void startAutocompleteActivity( ) {

        myListnerCall.startAutocompleteActivity();
    }
}