package it.uniba.dib.sms222315.Friends;

import android.content.Context;
import android.graphics.Color;
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

import java.util.ArrayList;

import it.uniba.dib.sms222315.R;


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



        //Create the expense object with the information
        MyFriends FriendObj = new MyFriends(nameText, mailExp, likeExchange);

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
        holder.image.setImageResource(R.drawable.icon_my_friends);


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
}
