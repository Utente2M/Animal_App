package it.uniba.dib.sms222315.Reporting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.UserPets.MyPetsListAdapter;
import it.uniba.dib.sms222315.UserPets.Pets;

public class CommentAdapter extends ArrayAdapter<Comment> {
    private static final String TAG = "TAG_MyPostListAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView textComment;
        TextView authorName;
        TextView date;

    }
    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public CommentAdapter(Context context, int resource,
                             ArrayList<Comment> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


//get the pets information
        String str_name = getItem(position).getPrv_authorName();
        String str_message = getItem(position).getPrv_CommentText();
        String str_date = getItem(position).getPrv_Date();


        //Create the pets object with the information
        Comment newComment = new Comment(str_name,str_date, str_message );


        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        CommentAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new CommentAdapter.ViewHolder();
            //cambiare id
            holder.authorName = (TextView) convertView.findViewById(R.id.tV_ListV_my_pets_sex);
            holder.textComment = (TextView) convertView.findViewById(R.id.TV_ListV_Comment_Text);
            holder.date = (TextView) convertView.findViewById(R.id.TV_ListV_Comment_Date);



            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (CommentAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.authorName.setText(newComment.getPrv_authorName());
        holder.textComment.setText(newComment.getPrv_CommentText());
        holder.date.setText(newComment.getPrv_Date());




        return convertView;
    }
}
