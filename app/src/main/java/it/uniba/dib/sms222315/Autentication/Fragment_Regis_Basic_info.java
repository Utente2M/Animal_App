package it.uniba.dib.sms222315.Autentication;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import it.uniba.dib.sms222315.R;


public class Fragment_Regis_Basic_info extends Fragment implements DatePickerDialog.OnDateSetListener {

    //inizialiamo i fragment
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;

    AutoCompleteTextView ET_mapsAddress;
    EditText  ET_Name , ET_Phone , ET_DateBorn;
    Button BT_Create;
    CallbackFragment myListnerCall;

    //picker calendar
    int new_day,new_month,new_year;
    //private String sendDateBorn;


    public Fragment_Regis_Basic_info() {
        // Required empty public constructor
    }



    private static final String TAG = "TAG_Frag_Regis_Basic_Info";

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



        ET_mapsAddress = my_view.findViewById(R.id.ET_autocomp_address);
        ET_mapsAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                new MapsPredictionTask().execute(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );

        ET_DateBorn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                showDataPickerDialog();
            }
        });






        BT_Create = my_view.findViewById(R.id.FragRegi_basiInfo_BT_Create);
        BT_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendName = ET_Name.getText().toString();
                String sendPhone = ET_Phone.getText().toString();

                String sendAddress = ET_mapsAddress.getText().toString();

                String sendDateBorn = ET_DateBorn.getText().toString();

                if (sendName.isEmpty()){
                    ET_Name.setError("Field Required ! ");
                }else if (sendPhone.isEmpty()){
                    ET_Phone.setError("Field Required ! ");
                } else if (sendAddress.isEmpty()){
                ET_mapsAddress.setError("Field Required ! ");
                } else if (sendDateBorn.isEmpty()){
                ET_DateBorn.setError("Field Required ! ");
                }
                else{
                    addInformationToProfile (sendName);
                    createUserBasicInfo (sendPhone , sendAddress , sendDateBorn);
                }

            }
        });

        // Inflate the layout for this fragment
        return my_view;
    }//END on Create view



    private class MapsPredictionTask extends AsyncTask<CharSequence, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(CharSequence... charSequences) {

            ArrayList<String> predictionList = new ArrayList<>();
            // Crea un nuovo token per la sessione di autocompletamento
            AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
            // Crea un oggetto RectangularBounds
            RectangularBounds bounds = RectangularBounds.newInstance(
                    new LatLng(41.894802, 12.485332),
                    new LatLng(45.465422, 9.185924));
            // Crea una richiesta di previsioni di autocompletamento
            String query = String.valueOf(charSequences[0]);
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

                }//end for
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, predictionList);
                ET_mapsAddress.setAdapter(adapter);
                ET_mapsAddress.showDropDown();
                Log.d(TAG, "maps risult arrayList : " + predictionList);

            }).addOnFailureListener((exception) -> {
                if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                }
            });
            return predictionList;
        }

        @Override
        protected void onPostExecute(ArrayList<String> predictionList) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_dropdown_item_1line, predictionList);
            ET_mapsAddress.setAdapter(adapter);
            ET_mapsAddress.showDropDown();
            Log.d(TAG, "maps risult arrayList : " + predictionList);
        }
    }


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