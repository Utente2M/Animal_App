package it.uniba.dib.sms222315.Reporting;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import it.uniba.dib.sms222315.SelectPhotoDialog;
import it.uniba.dib.sms222315.UserProfile.User_Class;


public class Fragment_Report_Add extends Fragment implements SelectPhotoDialog.OnPhotoSelectedListener {
   //xml link
    ImageView photoReportImg;
    Spinner categoryReportSpin;
    String sendCategory;
    EditText descriptionReport;
    Button createReport;

    //DB connection
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    private static final String TAG = "TAG_Fragment_Report_Add";
    private static final int REQUEST_CODE = 1;

    public Fragment_Report_Add() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View my_view = inflater.inflate(R.layout.fragment__report__add, container, false);

        setAllFind(my_view);
        setSpinner();
        setAllClick(my_view);

        // Inflate the layout for this fragment
        return my_view;
    }



    private void setSpinner() {

        ArrayAdapter<CharSequence> adapter_spin = ArrayAdapter.createFromResource(getContext(),
                R.array.CategoryReport, android.R.layout.simple_spinner_item);
        adapter_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryReportSpin.setAdapter(adapter_spin);
        categoryReportSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sendCategory = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void setAllFind(View my_view) {
        photoReportImg = my_view.findViewById(R.id.IV_Add_ReportPhoto);
        categoryReportSpin = my_view.findViewById(R.id.spin_Add_Report);
        descriptionReport = my_view.findViewById(R.id.ET_Add_descriptionReport);
        createReport = my_view.findViewById(R.id.BT_Add_Report);
    }

    private void setAllClick(View my_view) {
        photoReportImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyPermissions()){
                    Log.d(TAG , "Permission ok");
                    Log.d(TAG, "onClick: opening dialog to choose new photo");
                    SelectPhotoDialog dialog = new SelectPhotoDialog();
                    dialog.show(getFragmentManager(), getString(R.string.dialog_select_photo));
                    dialog.setTargetFragment(Fragment_Report_Add.this, 1);

                }
            }
        });

        createReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewReport();
            }
        });
    }


    //check permission for camera or local storage
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

        photoReportImg.setImageURI(imagePath);

    }

    @Override
    public void getImageBitmap(Bitmap bitmap) {
        Log.d(TAG, "getImageBitmap: setting the image to imageview");
        //qui ricevo bitmap
        photoReportImg.setImageBitmap(bitmap);

    }

    private void createNewReport() {
        String linkImg = "uri.toString()" ;
        String category = sendCategory;
        String description = descriptionReport.getText().toString();
        User_Class userData = new User_Class();
        String author_id = userData.getPrv_str_UID();

        SimpleDateFormat format = new SimpleDateFormat("d,MM,yyyy,HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String formatData = format.format(calendar.getTime());


        Report newPost = new Report("", linkImg, category,description,
                0 ,author_id ,formatData );


        db.collection("Post")
                .add(newPost)
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
        String myStringRef = "PostPhoto/"+id_document;
       final StorageReference profileImagesRef = storageRef.child(myStringRef);

        // Get the data from an ImageView as bytes
        photoReportImg.setDrawingCacheEnabled(true);
        photoReportImg.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) photoReportImg.getDrawable()).getBitmap();
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
        photoReportImg.setImageDrawable(getResources().getDrawable(R.drawable.icon_conferme));
        descriptionReport.setText("");
    }

}//end fragment