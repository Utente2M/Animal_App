package it.uniba.dib.sms222315.Friends;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.UserProfile.Fragment_UserProfile;


public class MyFriendsListAdapter extends ArrayAdapter<MyFriends> {

    private static final String TAG = "TAG_MyFriendAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView PublicName;
        TextView PublicMail;
        TextView NumberOfLike;
        ImageView image; //forse rimane

    }


    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public MyFriendsListAdapter(Context context, int resource, ArrayList<MyFriends> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(TAG , " in getView adapter expanse");

        String nameText = getItem(position).getNameFriend();
        String mailExp = getItem(position).getMailFriend();
        String likeExchange = getItem(position).getNumberOfLike();

        //new
        String linkmyPhoto = getItem(position).getUrlPhotoProfile();



        //Create the expense object with the information
        MyFriends FriendObj = new MyFriends(nameText, mailExp, likeExchange , linkmyPhoto);

        Log.d(TAG , " ok constructor");

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        MyFriendsListAdapter.ViewHolder holder;


        if(convertView == null){
            Log.d(TAG , " converView is NULL");
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new MyFriendsListAdapter.ViewHolder();
            Log.d(TAG , " try find");

            holder.PublicName = (TextView) convertView.findViewById(R.id.tV_ListV_myFriend_PublicName);
            holder.PublicMail = (TextView) convertView.findViewById(R.id.tV_ListV_myFriend_PublicMail);
            holder.NumberOfLike = (TextView) convertView.findViewById(R.id.tV_ListV_myFriend_numberOfLike);
            holder.image = (ImageView) convertView.findViewById(R.id.image_PublicFriend);

            Log.d(TAG , " ok find");

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            Log.d(TAG , " Convert view not null");
            holder = (MyFriendsListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        Log.d(TAG, "ok animation");



        //TODO DA AGGIUNGERE CONTROLLO FOTO


        //holder.image.set

        if (FriendObj.getUrlPhotoProfile()==(null) ){
            Log.d(TAG, " no profile image");
            holder.image.setImageResource(R.drawable.icon_my_friends);
        }
        else{
            new DownloadImageFromInternet((ImageView)
                    convertView.findViewById(R.id.image_PublicFriend))
                    .execute(FriendObj.getUrlPhotoProfile());
        }


        //se ci sono like scambiati mostrali
        Integer like = 0 ;  //likeExchange
        if (like > 0){
            holder.NumberOfLike.setText(FriendObj.getNumberOfLike() + "Like");

        }else {
            holder.NumberOfLike.setText("");
        }


        holder.PublicName.setText(FriendObj.getNameFriend());
        holder.PublicMail.setText(FriendObj.getMailFriend());

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
