package it.uniba.dib.sms222315.Enti;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.Reporting.Fragment_Report_Add;
import it.uniba.dib.sms222315.Reporting.Report;
import it.uniba.dib.sms222315.SelectPhotoDialog;
import it.uniba.dib.sms222315.UserProfile.User_Class;


public class Fragment_Enti_Create extends Fragment implements SelectPhotoDialog.OnPhotoSelectedListener{

    ImageView Photo;
    EditText phoneNumber;
    EditText Name;
    EditText Description;
    Button createAss;

    //maps
    AutoCompleteTextView ET_mapsAddress;


    //DB connection
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    private static final String TAG = "TAG_Fragment_Report_Add";
    private static final int REQUEST_CODE = 1;

    public Fragment_Enti_Create() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View my_view = inflater.inflate(R.layout.fragment__enti__create, container, false);

        setAllFind(my_view);
        setAllClick(my_view);

        return my_view;
    }


    private void setAllFind(View my_view) {
        Photo = my_view.findViewById(R.id.IV_Add_ReportPhoto);
        Name = my_view.findViewById(R.id.editTextTextPersonName);
        phoneNumber = my_view.findViewById(R.id.editTextPhone);
        ET_mapsAddress = my_view.findViewById(R.id.autoCompleteTextView);
        Description = my_view.findViewById(R.id.editTextTextMultiLine);
        createAss = my_view.findViewById(R.id.BT_Frag_newEnte);

    }


    private void setAllClick(View my_view) {
        Photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyPermissions()){
                    Log.d(TAG , "Permission ok");
                    Log.d(TAG, "onClick: opening dialog to choose new photo");
                    SelectPhotoDialog dialog = new SelectPhotoDialog();
                    dialog.show(getFragmentManager(), getString(R.string.dialog_select_photo));
                    dialog.setTargetFragment(Fragment_Enti_Create.this, 1);
                }
            }
        });

        createAss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAss();
            }
        });

        ET_mapsAddress.addTextChangedListener(new TextWatcher() {
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
    }

    private void createNewAss() {
        String linkImg = "uri.toString()" ;
        String phone = phoneNumber.getText().toString();
        String nameass = Name.getText().toString();
        String description = Description.getText().toString();
        String address = ET_mapsAddress.getText().toString();


        User_Class userData = new User_Class();

        String author_id = userData.getPrv_str_UID();
        String authorName = userData.getPrv_str_nome();

        SimpleDateFormat format = new SimpleDateFormat("d,MM,yyyy,HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String formatData = format.format(calendar.getTime());


        Associations newAssoc = new Associations( nameass, phone, address, description, linkImg, author_id, formatData,
                "", Arrays.asList(author_id));



        db.collection("Association")
                .add(newAssoc)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        createImageIntoDB(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private void createImageIntoDB(String id_document) {

        //save image into db


        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        // Create a storage reference from our app
        String myStringRef = "LogoAssociation/"+id_document;
        final StorageReference profileImagesRef = storageRef.child(myStringRef);

        // Get the data from an ImageView as bytes
        Photo.setDrawingCacheEnabled(true);
        Photo.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) Photo.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();



        //final StorageReference ref = storageRef.child("images/mountains.jpg");
        UploadTask uploadTask = profileImagesRef.putBytes(data);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return profileImagesRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();

                    addLinkToReport(downloadUri , id_document);

                } else {
                    // Handle failures
                    // ...
                }
            }
        });



    }

    private void addLinkToReport(Uri downloadUri, String id_document) {
        DocumentReference postRef = db.collection("Association").document(id_document);
        postRef
                .update("prv_associationLogo", downloadUri.toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        resetFrontEnd();
                        Toast.makeText(getContext() , "Congratulation ! \n" +
                                "You have created new post " , Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    private void resetFrontEnd() {
        Photo.setImageDrawable(getResources().getDrawable(R.drawable.icon_conferme));
        Description.setText("");
        Name.setText("");
        ET_mapsAddress.setText("");
        phoneNumber.setText("");
    }

    private boolean verifyPermissions() {
        Log.d(TAG, "verifyPermissions: asking user for permissions");
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        if(ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED){
            return true;
            //setupViewPager();
        }else{
            /*
            ActivityCompat.requestPermissions(SearchActivity.this,
                    permissions,
                    REQUEST_CODE);
             */
            //in fragment :
            requestPermissions(permissions, REQUEST_CODE);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verifyPermissions();
    }

    //insert image load into imageview
    @Override
    public void getImagePath(Uri imagePath) {
        Log.d(TAG, "getImagePath: setting the image to imageview");
        Log.d(TAG, "URI :" + imagePath);
        //qui ricevo URI

        Photo.setImageURI(imagePath);

    }

    @Override
    public void getImageBitmap(Bitmap bitmap) {
        Log.d(TAG, "getImageBitmap: setting the image to imageview");
        //qui ricevo bitmap
        Photo.setImageBitmap(bitmap);

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
            ET_mapsAddress.setAdapter(adapter);
            ET_mapsAddress.showDropDown();
            Log.d(TAG, "maps risult arrayList : " + predictionList);

        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                Log.e(TAG, "Place not found: " + apiException.getStatusCode());
            }
        });


    }

}