package it.uniba.dib.sms222315.UserProfile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import it.uniba.dib.sms222315.MainActivity;
import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.SelectPhotoDialog;

public class Fragment_UserProfile extends Fragment implements SelectPhotoDialog.OnPhotoSelectedListener {

    //vars
    private Bitmap mSelectedBitmap;
    private Uri mSelectedUri;
    private byte[] mUploadBytes;
    private double mProgress = 0;

    //TV_UID , but_logout ,
    TextView modifyInformation;
    TextView TV_email , TV_name , TV_phone , TV_street;
    Button  but_menu ;
    ImageView Img_profileUser , but_back ;

    Interf_UserProfile myCallBackFrag;
    User_Class my_User;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private static final String TAG = "TAG_Frag_UserProfile";
    private static final int REQUEST_CODE = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG , "onCreateView , try create class");
        View my_view = inflater.inflate(R.layout.fragm_userprofile_basic , container , false);

        allfind(my_view);


        //this class is an interface for Firebase Authentication
        my_User = new User_Class();

        Log.d(TAG , "onCreateView , OK create class");

        allSetClick();
        setTextandImage(my_view);


        return my_view;
    }

    private void setTextandImage(View my_view) {

        DocumentReference userRef = db.collection("User Basic Info").
                document(my_User.getPrv_str_UID());
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> data = document.getData();
                        if(data.containsKey("Phone")){
                            TV_phone.setText(data.get("Phone").toString());
                        }
                        if(data.containsKey("address")){
                            TV_street.setText(data.get("address").toString());
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                }
            }});

        TV_name.setText(my_User.getPrv_str_nome());
        TV_email.setText(my_User.getPrv_str_email());

        //TV_UID.setText(my_User.getPrv_str_UID());

        Uri UriImgProfile = null;
        UriImgProfile = my_User.getUri_ProfImg();
        Log.d(TAG, "URI :" + UriImgProfile);
        if (UriImgProfile==(null) ){
            Log.d(TAG, " no profile image");
        }
        else{
            new DownloadImageFromInternet((ImageView) my_view.findViewById(R.id.frag_userbasic_imageView_UserProfile)).
                    execute(my_User.getUri_ProfImg().toString());


        }
    }

    private void allSetClick() {

        //back home
        but_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back_home=new Intent(getActivity(),MainActivity.class);
                startActivity(back_home);
            }
        });

        //button menu
        but_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "BUTTON menu OK ");
                if (myCallBackFrag != null ){
                    myCallBackFrag.changeFragment();
                }

            }
        });
        //button logout
        /*
        but_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "BUTTON LOGOUT OK ");
                FirebaseAuth.getInstance().signOut();
                //Richiama la main activity
                Intent okLogut = new Intent (getActivity(), MainActivity.class);
                startActivity(okLogut);

            }
        }); */

        //Change foto profile
        Img_profileUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyPermissions()){
                    Log.d(TAG , "Permission ok");
                    Log.d(TAG, "onClick: opening dialog to choose new photo");
                    SelectPhotoDialog dialog = new SelectPhotoDialog();
                    dialog.show(getFragmentManager(), getString(R.string.dialog_select_photo));
                    dialog.setTargetFragment(Fragment_UserProfile.this, 1);

                }

            }
        });

        modifyInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //modifca informazioni utente
            }
        });
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


    private void allfind(View my_view) {
        TV_name = my_view.findViewById(R.id.show_profil_name);
        TV_email = my_view.findViewById(R.id.show_profil_mail);
        TV_phone = my_view.findViewById(R.id.idem_info_phone);
        TV_street = my_view.findViewById(R.id.idem_add_street);
        //TV_UID = my_view.findViewById(R.id.show_profil_uid);
        //but_logout = my_view.findViewById(R.id.logout_profile);
        but_menu = my_view.findViewById(R.id.button_menu);
        but_back = my_view.findViewById(R.id.back_button);
        Img_profileUser = my_view.findViewById(R.id.frag_userbasic_imageView_UserProfile);
        modifyInformation = my_view.findViewById(R.id.TV_changeInformationUser);
    }



    public void setMyCallBackFrag (Interf_UserProfile varCallback ){
        this.myCallBackFrag = varCallback;
    }


    @Override
    public void getImagePath(Uri imagePath) {
        Log.d(TAG, "getImagePath: setting the image to imageview");
        Log.d(TAG, "URI :" + imagePath);
        //qui ricevo URI

        Img_profileUser.setImageURI(imagePath);
        //forse non serve
        //mSelectedBitmap = null;
        mSelectedUri = imagePath;
        addInformationToProfile();
    }

    @Override
    public void getImageBitmap(Bitmap bitmap) {
        Log.d(TAG, "getImageBitmap: setting the image to imageview");
        //qui ricevo bitmap
        Img_profileUser.setImageBitmap(bitmap);
        //Convert Into URI
        mSelectedUri = getImageUri(getContext() , bitmap);
        //forse non serve
        //mSelectedBitmap = bitmap;
        addInformationToProfile();
    }

    public void addInformationToProfile() {


        //this is for UID
        User_Class thisuser = new User_Class();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        // Create a storage reference from our app
        String myStringRef = "imagesProfile/"+thisuser.getPrv_str_UID();
        StorageReference profileImagesRef = storageRef.child(myStringRef);

        // Get the data from an ImageView as bytes
        Img_profileUser.setDrawingCacheEnabled(true);
        Img_profileUser.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) Img_profileUser.getDrawable()).getBitmap();
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

                Log.d(TAG, "old URI : " + mSelectedUri);
                searchUriImage(myStringRef);
                Log.d(TAG, "new URI : " + mSelectedUri);

            }
        });


    }

    private void searchUriImage(String myStringRef ) {

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        storageRef.child(myStringRef).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d(TAG , "download URI : " + uri);

                addImageToAuthProfile(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors

            }
        });

    }

    private void addImageToAuthProfile(Uri uri) {
        //= Uri.parse("gs://animal-app-31090.appspot.com/imagesProfile/" + thisuser.getPrv_str_UID());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                //.setDisplayName(name)
                .setPhotoUri(uri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                            updateImageInPublicProfile();

                        }
                    }
                });

    }

    private void updateImageInPublicProfile() {

        User_Class myUserData = new User_Class();

        //query for search correct document
        db.collection("Public User")
                .whereEqualTo("secretId", myUserData.getPrv_str_UID())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                //aggiorna immagina - aspettiamo sempre un solo risultato
                                updateLink(document.getId());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    private void updateLink(String id) {

        User_Class myUserData = new User_Class();
        //aggiorna il link nel profilo pubblico
        DocumentReference publicProfileRef = db.collection("Public User").
                document(id);

// Set the "isCapital" field of the city 'DC'
        publicProfileRef
                .update("urlPhotoProfile", myUserData.getUri_ProfImg())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "ImageProfile", null);
        return Uri.parse(path);
    }


    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;
            Toast.makeText(getActivity().getApplicationContext(), "Please wait, it may take a few minute...",Toast.LENGTH_SHORT).show();
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {
                InputStream in=new java.net.URL(imageURL).openStream();
                bimage= BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }




}//END CLASS
