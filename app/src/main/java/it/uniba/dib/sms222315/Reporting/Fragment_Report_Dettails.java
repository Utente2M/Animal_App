package it.uniba.dib.sms222315.Reporting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.UserPets.Pets;
import it.uniba.dib.sms222315.UserProfile.User_Class;

public class Fragment_Report_Dettails extends Fragment {

    ImageView logoImage;
    TextView like , authorName, description, category , address ;

    EditText textComment;
    ImageButton sendComment;


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "TAG_Fragment_Report_Dettails";
    Report receivedReport;

    public Fragment_Report_Dettails() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LOAD DATA FROM FRAG MY EXPENSE HOME
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            receivedReport = bundle.getParcelable("clickRepo"); // Key
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View my_view = inflater.inflate(R.layout.fragment__report__dettails, container, false);


        Log.d(TAG, " oggetto ricevuto : " +receivedReport.getPrv_secretDocID());
        Log.d(TAG, " oggetto ricevuto : " +receivedReport.getPrv_authorName());

        setFind(my_view);
        setTextfromPost(my_view);
        setAllOnClick();

        return my_view;
    }




    private void setFind(View my_view) {
        logoImage = my_view.findViewById(R.id.post_image);
        like = my_view.findViewById(R.id.post_like);
        authorName = my_view.findViewById(R.id.post_author_name);
        description = my_view.findViewById(R.id.post_description);
        category = my_view.findViewById(R.id.post_category);
        address = my_view.findViewById(R.id.post_address);
        sendComment = my_view.findViewById(R.id.IB_sendComment);
        textComment = my_view.findViewById(R.id.ET_textNewComment);

    }

    private void setTextfromPost(View my_view) {
        if (receivedReport.getPrv_linkImg()==(null) ){
            Log.d(TAG, " no profile image");
            logoImage.setImageResource(R.drawable.star);
        }
        else{
            new DownloadImageFromInternet((ImageView)
                    my_view.findViewById(R.id.post_image))
                    .execute(receivedReport.getPrv_linkImg());
        }

        int intLike = (receivedReport.getPrv_numberLike());
        String str_Like = "Piace a "+Integer.toString(intLike) + " persone";
        like.setText(str_Like);

        authorName.setText(receivedReport.getPrv_authorName());
        description.setText(receivedReport.getPrv_description());
        category.setText(receivedReport.getPrv_category());
        address.setText(receivedReport.getAddressReport());
        address.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);


    }

    private void setAllOnClick() {
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchNavigationStep2Step(receivedReport.getAddressReport());
            }
        });

        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createComment();
            }
        });


    }

    public void launchNavigationStep2Step (String newAddress){
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> addresses = new ArrayList<Address>();
        try {
            addresses = geocoder.getFromLocationName(newAddress, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            double latitude = addresses.get(0).getLatitude();
            Log.d(TAG, "latitude :" + latitude);
            double longitude = addresses.get(0).getLongitude();
            Log.d(TAG, "longitude :" + longitude);
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude+"&mode=d");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
                getContext().startActivity(mapIntent);
            }
        }
    }//end launchMap

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;

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


    private void createComment() {
        String str_Comment = textComment.getText().toString();

        SimpleDateFormat format = new SimpleDateFormat("d,MM,yyyy,");
        Calendar calendar = Calendar.getInstance();
        String formatData = format.format(calendar.getTime());

        User_Class myInfo =new User_Class();
        String myUID = myInfo.getPrv_str_UID();
        String myName = myInfo.getPrv_str_nome();


        Comment newComment = new Comment(myUID,myName,formatData,str_Comment);

        db.collection("Post")
                .document(receivedReport.getPrv_secretDocID())
                .collection("Comment")
                .add(newComment)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }

}