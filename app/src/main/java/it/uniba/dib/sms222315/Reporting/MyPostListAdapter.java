package it.uniba.dib.sms222315.Reporting;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.InputStream;
import java.util.ArrayList;

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
        ImageView image;
        Button addLike, addComment;
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

        String author = getItem(position).getPrv_authorID();
        String description = getItem(position).getPrv_description();
        int numberLike = getItem(position).getPrv_numberLike();
        String str_numberLike = Integer.toString(numberLike);
        String linkmyPhoto = getItem(position).getPrv_linkImg();


        Report reportObj = new Report(linkmyPhoto, author, description , str_numberLike);

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
           // holder.addComment = ...  Id : Adap_Repo_addComment

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

        Log.d(TAG, "ok animation");



        //TODO DA AGGIUNGERE CONTROLLO FOTO


        //holder.image.set

        if (reportObj.getPrv_linkImg()==(null) ){
            Log.d(TAG, " no profile image");
            holder.image.setImageResource(R.drawable.star);
        }
        else{
            new DownloadImageFromInternet((ImageView)
                    convertView.findViewById(R.id.Adap_Repo_image))
                    .execute(reportObj.getPrv_linkImg());
        }


        holder.authorName.setText(reportObj.getPrv_authorName());
        holder.textPost.setText(reportObj.getPrv_description());

        int intLike = reportObj.getPrv_numberLike();
        String str_Like = Integer.toString(intLike);
        holder.NumberOfLike.setText(str_Like);

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
