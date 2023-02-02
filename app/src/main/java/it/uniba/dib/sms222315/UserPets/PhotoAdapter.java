package it.uniba.dib.sms222315.UserPets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.InputStream;
import java.util.ArrayList;

import it.uniba.dib.sms222315.MeetPets.meetAdapter;
import it.uniba.dib.sms222315.R;

public class PhotoAdapter extends ArrayAdapter<MyPhoto> {

    private static final String TAG = "TAG_PhotoAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    /**
     * Holds variables in a View
     */
    private static class ViewHolder {

        ImageView image;

    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public PhotoAdapter(Context context, int resource, ArrayList<MyPhoto> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


//get the pets information

        String linkimage = getItem(position).getPhotoLink();


        //Create the pets object with the information
        MyPhoto onePhoto = new MyPhoto(linkimage);


        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        PhotoAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new PhotoAdapter.ViewHolder();
            //cambiare id

            holder.image =(ImageView) convertView.findViewById(R.id.IV_adap_photo);


            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (PhotoAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;


        Log.d(TAG, "image: " +onePhoto.getPhotoLink());
        if (onePhoto.getPhotoLink()==null ){
            Log.d(TAG, " no profile image");

        }
        else{
            new DownloadImageFromInternet((ImageView)
                    convertView.findViewById(R.id.IV_adap_photo))
                    .execute(onePhoto.getPhotoLink());
        }
        return convertView;
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

}
