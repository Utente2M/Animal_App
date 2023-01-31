package it.uniba.dib.sms222315.UserPets;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

import it.uniba.dib.sms222315.R;


public class Fragment_MyPets_Modify extends Fragment  {

    //DB VARIABLE
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //picker calendar
    int new_day,new_month,new_year;


    //BUNDLE
    Pets receivedPet;

    EditText data_nasc ,mantello , razza,  segniPart , numeroChip , dataChip ;
    AutoCompleteTextView indirizzoAnimale;
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


        /*
         data_nasc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                showDataPickerDialog(data_nasc);
            }
        });

        dataChip.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                showDataPickerDialog(dataChip);
            }
        });
         */


        data_nasc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(MotionEvent.ACTION_UP == motionEvent.getAction()){
                    showDataPickerDialog(data_nasc);
                }

                return false;
            }
        });
        dataChip.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(MotionEvent.ACTION_UP == motionEvent.getAction()){
                    showDataPickerDialog(dataChip);
                }

                return false;
            }
        });


        indirizzoAnimale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mapsPrediction (charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
                        "prv_SegniParticolari" , segniPart.getText().toString() ,
                        "prv_numChip" , numeroChip.getText().toString(),
                        "prv_dataChip"  , dataChip.getText().toString(),
                        "prv_addressPet" , indirizzoAnimale.getText().toString()
                )//end update

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

        if (!receivedPet.getPrv_numChip().isEmpty()){
            numeroChip.setText(receivedPet.getPrv_numChip());
        }
        if (!receivedPet.getPrv_dataChip().isEmpty()){
            dataChip.setText(receivedPet.getPrv_dataChip());
        }
        if (!receivedPet.getPrv_addressPet().isEmpty()){
            indirizzoAnimale.setText(receivedPet.getPrv_addressPet());
        }


    }//END SET TEXT

    //set all find from layout
    private void setfind(View my_view) {


        data_nasc = my_view.findViewById(R.id.ET_MyPetModify_data);

        razza = my_view.findViewById(R.id.ET_MyPetModify_razza);
        mantello = my_view.findViewById(R.id.ET_MyPetModify_mantello);
        segniPart = my_view.findViewById(R.id.ET_MyPetModify_segPartic);

        numeroChip = my_view.findViewById(R.id.ET_MyPetModify_NumeroChip);
        dataChip = my_view.findViewById(R.id.ET_MyPetModify_DataChip);
        indirizzoAnimale = my_view.findViewById(R.id.ET_MyPetModify_ResidAnimale);


        BT_backPet = my_view.findViewById(R.id.BT_Back_MyPetsModify);
        BT_confermePet = my_view.findViewById(R.id.BT_Apply_MyPetsModify);

    }

    private void showDataPickerDialog (TextView textView) {


        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day_ofYear) {
                        month += 1;
                        //"day/month/year : "
                        String Str_Date = day_ofYear + "/" + month + "/" + year;
                        textView.setText(Str_Date);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void mapsPrediction(CharSequence charSequence) {

        Context context = getActivity().getApplicationContext();
        String apiKey = context.getString(R.string.api_key);
        if (!Places.isInitialized()) {
            Places.initialize(getActivity().getApplicationContext(), apiKey);
        }


        String query = String.valueOf(charSequence);
        ArrayList<String> predictionList = new ArrayList<>();

        // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
        // and once again when the user makes a selection (for example when calling fetchPlace()).
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        // Crea un oggetto RectangularBounds
        RectangularBounds bounds = RectangularBounds.newInstance(
                new LatLng(41.894802, 12.485332),
                new LatLng(45.465422, 9.185924));
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                // Chiama setLocationBias() o setLocationRestriction()
                .setLocationBias(bounds)
                //.setLocationRestriction(bounds)
                .setOrigin(new LatLng(41.894802, 12.4853379))
                .setCountries("IT")
                //.setTypesFilter(Arrays.asList(TypeFilter.ADDRESS.toString()))
                .setSessionToken(token)
                .setQuery(query)
                .build();
        Log.d(TAG, "query : " + query);

        PlacesClient placesClient = Places.createClient(getContext());

        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                Log.i(TAG, prediction.getPlaceId());
                Log.i(TAG, prediction.getPrimaryText(null).toString());

                predictionList.add(prediction.getFullText(null).toString());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_dropdown_item_1line, predictionList);
            indirizzoAnimale.setAdapter(adapter);
            indirizzoAnimale.showDropDown();
            Log.d(TAG, "maps risult arrayList : " + predictionList);


        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                Log.e(TAG, "Place not found: " + apiException.getStatusCode());
            }
        });


    }




}//END CLASS