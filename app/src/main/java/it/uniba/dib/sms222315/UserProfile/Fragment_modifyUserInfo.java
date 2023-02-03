package it.uniba.dib.sms222315.UserProfile;

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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import it.uniba.dib.sms222315.R;


public class Fragment_modifyUserInfo extends Fragment {

    EditText dateBorn , phoneNumber;
    AutoCompleteTextView address;
    Button BT_accept;

    //ISTANCE DB
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "TAG_Fragment_modifyUserInfo";

    public Fragment_modifyUserInfo() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View my_view = inflater.inflate(R.layout.fragment_modify_user_info, container, false);

        setAllFind(my_view);
        setAllText();
        setAllClick();

        //street


        return my_view;
    }

    private void setAllFind(View my_view) {
        phoneNumber =my_view.findViewById(R.id.ET_phoneNumberProfile);
        dateBorn = my_view.findViewById(R.id.ET_dateBornProfile);
        address = my_view.findViewById(R.id.AC_addressProfile);
        BT_accept = my_view.findViewById(R.id.BT_modifyProfile);
    }

    private void setAllClick() {
        dateBorn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(MotionEvent.ACTION_UP == motionEvent.getAction()){
                    showDataPickerDialog();
                }

                return false;
            }
        });


        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mapsPrediction(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        BT_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyInfoProfile();
            }
        });
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
            address.setAdapter(adapter);
            address.showDropDown();
            Log.d(TAG, "maps risult arrayList : " + predictionList);

        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                Log.e(TAG, "Place not found: " + apiException.getStatusCode());
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
                        dateBorn.setText(Str_Date);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void setAllText() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();

        DocumentReference userRef = db.collection("User Basic Info").
                document(userID);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> data = document.getData();
                        if(data.containsKey("Phone")){

                            phoneNumber.setText(data.get("Phone").toString());
                            Log.d(TAG,"CLASS_Phone : " + data.get("Phone").toString());
                        }
                        if(data.containsKey("address")){

                            address.setText(data.get("address").toString());
                            Log.d(TAG,"CLASS_address : " + data.get("address").toString());
                        }
                        if(data.containsKey("dateBorn")){

                            dateBorn.setText(data.get("dateBorn").toString());
                            Log.d(TAG,"CLASS_dateBorn : " + data.get("dateBorn").toString());
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                }
            }});

    }
    private void modifyInfoProfile() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();

        DocumentReference userRef = db.collection("User Basic Info").
                document(userID);

        userRef.update("Phone" , phoneNumber.getText().toString()
        , "dateBorn" , dateBorn.getText().toString() , "address" , address.getText().toString()
                ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getActivity().onBackPressed();
            }
        });

    }

}