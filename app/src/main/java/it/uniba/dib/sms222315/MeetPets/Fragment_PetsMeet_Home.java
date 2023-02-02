package it.uniba.dib.sms222315.MeetPets;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.Reporting.MyPostListAdapter;
import it.uniba.dib.sms222315.Reporting.Report;
import it.uniba.dib.sms222315.UserPets.MyPetsListAdapter;
import it.uniba.dib.sms222315.UserPets.Pets;
import it.uniba.dib.sms222315.UserProfile.User_Class;


public class Fragment_PetsMeet_Home extends Fragment {

    ArrayList<Pets> originalList = new ArrayList<>();
    meetAdapter adapter;
    GridView mGridView;
    Button BT_scanQRCode;

    private static final int REQUEST_CODE = 1;


    //DB VARIABLE
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "TAG_Fragment_PetsMeet_Home";

    public Fragment_PetsMeet_Home() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View my_view = inflater.inflate(R.layout.fragment__pets_meet__home, container, false);

        allFind(my_view);
       // setupClick(my_view);
        allOnClick();

        if (adapter ==null){
            originalList.clear();

            popolateList();
            Log.d(TAG , "ok popolateList ");
        }



        return my_view;
    }




    private void allFind(View my_view) {
        BT_scanQRCode = my_view.findViewById(R.id.BT_scanQrcode);
        mGridView = my_view.findViewById(R.id.GV_Pets_Meet);
    }

    private void popolateList() {
        originalList.clear();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        Log.d(TAG, "This is UID " + userID);


        CollectionReference animalRef = db.collection("Animal DB");

        //Query MyPets = animalRef.whereArrayContains("prv_Str_responsabili", userID);

        animalRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());


                        Pets mypet = document.toObject(Pets.class);
                        mypet.setPrv_doc_id(document.getId());
                        originalList.add(mypet);
                        Log.d(TAG, "uid doc : " + mypet.getPrv_doc_id());

                    }//end for

                    adapter = new meetAdapter(getContext(),
                            R.layout.adapter_pets_meet, originalList);

                    mGridView.setAdapter(adapter);


                }//end if
                else {
                    //nessun animale da mostrare
                }//fine else

            }//end on complete

        }); //end Listners
    }


    private void allOnClick() {
        BT_scanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyPermissions()) {
                    scanQrCode();
                }

            }
        });
    }

    private void scanQrCode() {
        ScanOptions myoptions = new ScanOptions();
        myoptions.setPrompt("Volume up to flash on");
        myoptions.setOrientationLocked(true);
        myoptions.setBeepEnabled(false);

        //myoptions.setCaptureActivity(ActivityCapture.class);
        barLauncher.launch(myoptions);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result ->{
        if (result.getContents()!=null){
            Log.d(TAG, "id animale" + result);
        }
    }  );


    //check permission
    private boolean verifyPermissions() {
        Log.d(TAG, "verifyPermissions: asking user for permissions");
        String[] permissions = {Manifest.permission.CAMERA};

        if(ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED){
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


}//end class