package it.uniba.dib.sms222315.Reporting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222315.Friends.MyFriends;
import it.uniba.dib.sms222315.Friends.MyFriendsListAdapter;
import it.uniba.dib.sms222315.R;

public class MyPostListAdapter extends ArrayAdapter<Report> {

    private static final String TAG = "TAG_MyPostListAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView authorName;
        TextView textPost;
        TextView NumberOfLike;
        TextView street;
        ImageView image;
        ImageButton addLike, addComment;
    }
    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public MyPostListAdapter(Context context, int resource, ArrayList<Report> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        Log.d(TAG , " in getView adapter expanse");

        String author = getItem(position).getPrv_authorName();
        String description = getItem(position).getPrv_description();
        int numberLike = getItem(position).getPrv_numberLike();
        String str_numberLike = Integer.toString(numberLike);
        String linkmyPhoto = getItem(position).getPrv_linkImg();
        String address = getItem(position).getAddressReport();


        Report reportObj = new Report(linkmyPhoto, author, description , str_numberLike, address);

        Log.d(TAG , " ok constructor");

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        MyPostListAdapter.ViewHolder holder;


        if(convertView == null){
            Log.d(TAG , " converView is NULL");
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new MyPostListAdapter.ViewHolder();
            Log.d(TAG , " try find");


            holder.authorName = (TextView) convertView.findViewById(R.id.Adap_Repo_authorName);
            holder.textPost = (TextView) convertView.findViewById(R.id.Adap_Repo_description);
            holder.NumberOfLike = (TextView) convertView.findViewById(R.id.Adap_Repo_numberLike);
            holder.image = (ImageView) convertView.findViewById(R.id.Adap_Repo_image);
            //holder.addLike = ..
            holder.street =(TextView) convertView.findViewById(R.id.Adap_Repo_street);
            holder.street.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

           holder.addComment = (ImageButton) convertView.findViewById(R.id.Adap_Repo_addComment);
           holder.addComment.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Log.d(TAG , "add comment to post number : " +getItem(position).getPrv_description());
               }
           });

            Log.d(TAG , " ok find");

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            Log.d(TAG , " Convert view not null");
            holder = (MyPostListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;


        if (reportObj.getPrv_linkImg()==(null) ){
            Log.d(TAG, " no profile image");
            holder.image.setImageResource(R.drawable.star);
        }
        else{
            new DownloadImageFromInternet((ImageView)
                    convertView.findViewById(R.id.Adap_Repo_image))
                    .execute(reportObj.getPrv_linkImg());
        }


        holder.authorName.setText("Author : "+reportObj.getPrv_authorName());
        holder.textPost.setText(reportObj.getPrv_description());

        int intLike = reportObj.getPrv_numberLike();
        String str_Like = "Piace a "+Integer.toString(intLike) + " persone";
        holder.NumberOfLike.setText(str_Like);

        holder.street.setText(reportObj.getAddressReport());
        holder.street.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent per maps

                Geocoder geocoder = new Geocoder(getContext());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocationName("Via Edoardo Orabona, Bari, BA, Italia", 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addresses.size() > 0) {
                    double latitude = addresses.get(0).getLatitude();
                    double longitude = addresses.get(0).getLongitude();
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    if (mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
                        getContext().startActivity(mapIntent);
                    }
                }





                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + holder.street.toString());

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if(mapIntent.resolveActivity(getContext().getPackageManager())!=null){
                    getContext().startActivity(mapIntent);
                }


            }
        });



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
