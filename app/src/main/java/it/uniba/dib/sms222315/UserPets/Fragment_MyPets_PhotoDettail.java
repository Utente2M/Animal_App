package it.uniba.dib.sms222315.UserPets;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.Reporting.Report;


public class Fragment_MyPets_PhotoDettail extends Fragment {


    private static final String TAG = "TAG_Fragment_MyPets_PhotoDettail";
    Pets receivedPet;
    MyPhoto receivedPhoto;

    ImageButton But_share , But_delete;
    ImageView photoOpenend;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public Fragment_MyPets_PhotoDettail() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LOAD DATA FROM FRAG MY EXPENSE HOME
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            receivedPet = bundle.getParcelable("modPets"); // Key
            receivedPhoto = bundle.getParcelable("modPhoto");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View my_view = inflater.inflate(R.layout.fragment__my_pets__photo_dettail, container, false);

        setAllFind(my_view);
        openPhoto(my_view);
        setAllClick(my_view);


        return my_view;
    }

    private void setAllClick(View view) {
        But_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareProfile(receivedPhoto, receivedPet, view);
            }
        });
        But_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePhoto();
            }
        });
    }

    private void deletePhoto() {

        DocumentReference animalRef = db.collection("Animal DB")
                .document(receivedPet.getPrv_doc_id())
                .collection("Pet Photo")
                .document(receivedPhoto.getPrv_DocID());

        animalRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getActivity().onBackPressed();
            }
        });
    }

    private void shareProfile(MyPhoto onePhoto,Pets onePet, View view) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "HI !"+"\n"+"Look at this photo of "+
                receivedPet.getPrv_str_namePets());
        searchBitmap(view);

        try {
            File cachePath = new File(getContext().getCacheDir(), "images");
            File imagePath = new File(cachePath, "compressedImage.jpeg");
            Uri contentUri = FileProvider.getUriForFile(getContext(), "it.uniba.dib.sms222315.fileprovider", imagePath);
            sendIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            sendIntent.setType("image/*");
            sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(Intent.createChooser(sendIntent, null));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void searchBitmap(View view) {
        //ImageView imageView = view.findViewById(R.id.Adap_Repo_image);
        Bitmap originalImage = ((BitmapDrawable) photoOpenend.getDrawable()).getBitmap();
        int maxSize = 1024;
        if (originalImage.getHeight() > maxSize || originalImage.getWidth() > maxSize) {
            originalImage = Bitmap.createScaledBitmap(originalImage, maxSize, maxSize, false);
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        originalImage.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        byte[] compressedImage = stream.toByteArray();

        try {
            File cachePath = new File(getContext().getCacheDir(), "images");
            cachePath.mkdirs();
            FileOutputStream s = new FileOutputStream(cachePath + "/compressedImage.jpeg");
            s.write(compressedImage);
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void openPhoto(View my_view) {

        if (receivedPhoto.getPhotoLink()==null ){
            Log.d(TAG, " no profile image");

        }
        else{
            new DownloadImageFromInternet((ImageView)
                    my_view.findViewById(R.id.IV_photoOpen))
                    .execute(receivedPhoto.getPhotoLink());
        }
    }


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

    private void setAllFind(View my_view) {
        But_share = my_view.findViewById(R.id.IB_photoShare);
        But_delete = my_view.findViewById(R.id.IB_photoDelete);
        photoOpenend = my_view.findViewById(R.id.IV_photoOpen);
    }
}