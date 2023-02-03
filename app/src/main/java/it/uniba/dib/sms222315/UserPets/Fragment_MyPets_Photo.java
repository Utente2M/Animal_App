package it.uniba.dib.sms222315.UserPets;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.SelectPhotoDialog;


public class Fragment_MyPets_Photo extends Fragment implements SelectPhotoDialog.OnPhotoSelectedListener {


    Pets receivedPet;

    ArrayList<MyPhoto> originalList = new ArrayList<>();
    PhotoAdapter adapter;
    GridView mGridView;
    Button BT_addOnePhoto;
    //ISTANCE DB
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    private static final int REQUEST_CODE = 1;

    private static final String TAG = "TAG_Fragment_MyPets_Photo";

    public Fragment_MyPets_Photo() {
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

        View my_view = inflater.inflate(R.layout.fragment__my_pets__photo, container, false);

        setfind(my_view);
        setAllOnClick();

        if (adapter ==null){
            originalList.clear();

            popolateList();
            Log.d(TAG , "ok popolateList ");
        }


        return my_view;
    }



    private void setfind(View my_view) {
        BT_addOnePhoto = my_view.findViewById(R.id.BT_addOnePhoto);
        mGridView =my_view.findViewById(R.id.GV_PhotoPetsDettail);
    }

    private void setAllOnClick() {
        BT_addOnePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyPermissions()){
                    Log.d(TAG , "Permission ok");
                    Log.d(TAG, "onClick: opening dialog to choose new photo");
                    SelectPhotoDialog dialog = new SelectPhotoDialog();
                    dialog.show(getFragmentManager(), getString(R.string.dialog_select_photo));
                    dialog.setTargetFragment(Fragment_MyPets_Photo.this, 1);


                }
            }
        });
    }

    //check permission
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

            requestPermissions(permissions, REQUEST_CODE);
        }
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verifyPermissions();
    }


    private void savePetsPhoto(Bitmap bitmap) {
//id del documento per la foto
        receivedPet.getPrv_doc_id();


        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        // Create a storage reference from our app
        String myStringRef = "photoPets/"+receivedPet.getPrv_doc_id()+originalList.size();
        StorageReference profileImagesRef = storageRef.child(myStringRef);

        // Get the data from an ImageView as bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = profileImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // carica immagine e ricavane uri

                searchUriImage(myStringRef);


            }
        });
    }


    @Override
    public void getImagePath(Uri imagePath) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imagePath);
            savePetsPhoto(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    @Override
    public void getImageBitmap(Bitmap bitmap) {
        savePetsPhoto(bitmap);

    }

    private void searchUriImage(String myStringRef) {
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        storageRef.child(myStringRef).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d(TAG , "download URI : " + uri);

                addImageToPetsProfile(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors

            }
        });
    }

    private void addImageToPetsProfile(Uri uri) {




        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Log.d(TAG,"This is UID " + userID);

        MyPhoto onePhoto = new MyPhoto(uri.toString(), "");


        CollectionReference animalRef = db.collection("Animal DB")
                .document(receivedPet.getPrv_doc_id())
                .collection("Pet Photo");

        animalRef.add(onePhoto).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

                popolateList();
            }
        });

    }


    private void popolateList() {

        originalList.clear();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Log.d(TAG,"This is UID " + userID);


        CollectionReference animalRef = db.collection("Animal DB")
                .document(receivedPet.getPrv_doc_id())
                .collection("Pet Photo");


        animalRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());


                        MyPhoto onePhoto = document.toObject(MyPhoto.class);
                        onePhoto.setPrv_DocID(document.getId());
                        originalList.add(onePhoto);


                    }//end for

                    adapter = new PhotoAdapter(getContext(),
                            R.layout.adapter_photo, originalList);

                    mGridView.setAdapter(adapter);


                }//end if
                else {
                    //nessun animale da mostrare
                }//fine else

            }//end on complete

        }); //end Listners

    }


}