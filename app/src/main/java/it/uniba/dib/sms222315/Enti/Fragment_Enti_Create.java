package it.uniba.dib.sms222315.Enti;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.Reporting.Fragment_Report_Add;
import it.uniba.dib.sms222315.Reporting.Report;
import it.uniba.dib.sms222315.SelectPhotoDialog;
import it.uniba.dib.sms222315.UserProfile.User_Class;


public class Fragment_Enti_Create extends Fragment implements SelectPhotoDialog.OnPhotoSelectedListener{

    ImageView Photo;
    EditText phoneNumber;
    EditText Name;
    String sendCategory;
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
    }

    private void createNewAss() {

    }

    private void createImageIntoDB(String id_document) {

        //save image into db


        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        // Create a storage reference from our app
        String myStringRef = "PostPhoto/"+id_document;
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
        DocumentReference postRef = db.collection("Post").document(id_document);
        postRef
                .update("prv_linkImg", downloadUri.toString())
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
        ET_mapsAddress.setText("");
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

}