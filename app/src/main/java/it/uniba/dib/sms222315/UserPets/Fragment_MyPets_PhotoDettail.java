package it.uniba.dib.sms222315.UserPets;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.InputStream;

import it.uniba.dib.sms222315.R;


public class Fragment_MyPets_PhotoDettail extends Fragment {


    private static final String TAG = "TAG_Fragment_MyPets_PhotoDettail";
    Pets receivedPet;
    MyPhoto receivedPhoto;

    ImageButton But_share , But_delete;
    ImageView photoOpenend;


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
        setAllClick();


        return my_view;
    }

    private void setAllClick() {
        But_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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