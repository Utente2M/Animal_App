package it.uniba.dib.sms222315.UserPets;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.SelectPhotoDialog;
import it.uniba.dib.sms222315.UserProfile.Fragment_UserProfile;
import it.uniba.dib.sms222315.UserProfile.User_Class;


public class Fragment_MyPets_Profile extends Fragment implements SelectPhotoDialog.OnPhotoSelectedListener {

    //FRAGMENT VAR
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;


    TextView nome,data_nasc, sex , specie, razza, mantello , segniPart ;
    ImageView PetImage;
    Button BT_deletePet , BT_modifyPet, BT_newOwner, BT_libretto;
    ImageButton IB_sharePet;

    //ISTANCE DB
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();


    private static final int REQUEST_CODE = 1;
    private static final String TAG = "TAG_Frag_MyPet_PROFILE";
    Pets receivedPet;


    public Fragment_MyPets_Profile() {
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
        Log.d(TAG , "onCreateView ");
        View my_view = inflater.inflate(R.layout.fragment__my_pets__profile, container , false);

        setfind(my_view);
        setTextfromPets(my_view);
        setAllOnClick();


        // Inflate the layout for this fragment
        return my_view;
    }

    private void setAllOnClick() {
        BT_deletePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Attenzione!");
                builder.setMessage("Sei sicuro di eliminare questo animale?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteIntoDB();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

                //qui
                //deleteIntoDB();
            }
        });

        BT_modifyPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                my_fragment = new Fragment_MyPets_Modify();
                my_frag_manager = getActivity().getSupportFragmentManager();
                my_frag_trans = my_frag_manager.beginTransaction();
                Bundle bundle = new Bundle();
                //this is pass
                bundle.putParcelable("modPets", receivedPet);
                my_fragment.setArguments(bundle);
                //si aggiunge il richiamo allo stack
                my_frag_trans.addToBackStack(null);
                //add diventa replace
                my_frag_trans.replace(R.id.Frame_Act_MyPets , my_fragment );
                my_frag_trans.commit();
            }
        });

        BT_newOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_fragment = new Fragment_MyPets_Add_Owner();
                my_frag_manager = getActivity().getSupportFragmentManager();
                my_frag_trans = my_frag_manager.beginTransaction();
                Bundle bundle = new Bundle();
                //this is pass
                bundle.putParcelable("modPets", receivedPet);
                my_fragment.setArguments(bundle);
                //si aggiunge il richiamo allo stack
                my_frag_trans.addToBackStack(null);
                //add diventa replace
                my_frag_trans.replace(R.id.Frame_Act_MyPets , my_fragment );
                my_frag_trans.commit();
            }
        });
        //listview visite
        BT_libretto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_fragment = new Fragment_MyPets_Libretto();
                my_frag_manager = getActivity().getSupportFragmentManager();
                my_frag_trans = my_frag_manager.beginTransaction();
                Bundle bundle = new Bundle();
                //this is pass
                bundle.putParcelable("modPets", receivedPet);
                my_fragment.setArguments(bundle);
                //si aggiunge il richiamo allo stack
                my_frag_trans.addToBackStack(null);
                //add diventa replace
                my_frag_trans.replace(R.id.Frame_Act_MyPets , my_fragment );
                my_frag_trans.commit();
            }
        });
        //change image
        PetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyPermissions()){
                    Log.d(TAG , "Permission ok");
                    Log.d(TAG, "onClick: opening dialog to choose new photo");
                    SelectPhotoDialog dialog = new SelectPhotoDialog();
                    dialog.show(getFragmentManager(), getString(R.string.dialog_select_photo));
                    dialog.setTargetFragment(Fragment_MyPets_Profile.this, 1);

                }
            }
        });

        IB_sharePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePet();
            }
        });
    }




    private void deleteIntoDB() {
        //caricamentodbcollection
        db.collection("Animal DB").document(receivedPet.getPrv_doc_id())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        getActivity().onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    private void setTextfromPets(View my_view) {
        nome.setText(receivedPet.getPrv_str_namePets());
        data_nasc.setText(receivedPet.getPrv_DataNascita());
        sex.setText(receivedPet.getPrv_sex());
        specie.setText(receivedPet.getPrv_specie());
        razza.setText(receivedPet.getPrv_Razza());
        mantello.setText(receivedPet.getPrv_Mantello());
        segniPart.setText(receivedPet.getPrv_SegniParticolari());


        if (!receivedPet.getLinkPhotoPets().isEmpty()){
            new DownloadImageFromInternet((ImageView) my_view.findViewById(R.id.IV_MyPetProfile_picture)).
                    execute(receivedPet.getLinkPhotoPets());

        }else {
            if (specie.equals("Cane")){
                PetImage.setImageResource(R.drawable.icon_dog);

            }else if (specie.equals("Gatto")){
                PetImage.setImageResource(R.drawable.icon_cat);

            }else if (specie.equals("Coniglio")){
                PetImage.setImageResource(R.drawable.icon_rabbit);
            }else{
                Log.d(TAG , "ERROR : specie not found");
            }
        }


    }//END SET TEXT

    //set all find from layout
    private void setfind(View my_view) {

        nome = my_view.findViewById(R.id.TV_MyPetProfile_name);
        data_nasc = my_view.findViewById(R.id.TV_MyPetProfile_data);
        sex = my_view.findViewById(R.id.TV_MyPetProfile_sex);
        specie = my_view.findViewById(R.id.TV_MyPetProfile_specie);
        razza = my_view.findViewById(R.id.TV_MyPetProfile_razza);
        mantello = my_view.findViewById(R.id.TV_MyPetProfile_mantello);
        segniPart = my_view.findViewById(R.id.TV_MyPetProfile_segPartic);
        PetImage = my_view.findViewById(R.id.IV_MyPetProfile_picture);
        BT_deletePet = my_view.findViewById(R.id.BT_DEL_MyPets);
        BT_modifyPet = my_view.findViewById(R.id.BT_MOD_MyPets);
        BT_newOwner = my_view.findViewById(R.id.BT_ADD_RESP_MyPets);
        BT_libretto = my_view.findViewById(R.id.BT_libretto);
        IB_sharePet = my_view.findViewById(R.id.IB_share_MyPets);

    }



    //for load image
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

    //received image
    @Override
    public void getImagePath(Uri imagePath) {
        Log.d(TAG, "getImagePath: setting the image to imageview");
        Log.d(TAG, "URI :" + imagePath);
        //qui ricevo URI

        PetImage.setImageURI(imagePath);
        savePetsPhoto();

    }

    @Override
    public void getImageBitmap(Bitmap bitmap) {
        Log.d(TAG, "getImageBitmap: setting the image to imageview");
        //qui ricevo bitmap
        PetImage.setImageBitmap(bitmap);
        savePetsPhoto();
    }

    private void savePetsPhoto() {
        //id del documento per la foto
        receivedPet.getPrv_doc_id();



        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        // Create a storage reference from our app
        String myStringRef = "imagesPets/"+receivedPet.getPrv_doc_id();;
        StorageReference profileImagesRef = storageRef.child(myStringRef);

        // Get the data from an ImageView as bytes
        PetImage.setDrawingCacheEnabled(true);
        PetImage.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) PetImage.getDrawable()).getBitmap();
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

        DocumentReference publicProfileRef = db.collection("Animal DB").
                document(receivedPet.getPrv_doc_id());

        // Set the "isCapital" field of the city 'DC'
        publicProfileRef
                .update("linkPhotoPets", uri)
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


    private void sharePet() {


        MultiFormatWriter writer = new MultiFormatWriter();
        try{
            BitMatrix matrix = writer.encode(receivedPet.getPrv_doc_id(), BarcodeFormat.QR_CODE,
                    500 , 500);

            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap mBitmap = encoder.createBitmap(matrix);

            PetImage.setImageBitmap(mBitmap);

        }catch (WriterException e)
        {
            e.printStackTrace();
        }

    }

}//end class