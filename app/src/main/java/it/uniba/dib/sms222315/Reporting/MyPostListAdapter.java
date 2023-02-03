package it.uniba.dib.sms222315.Reporting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222315.Friends.MyFriends;
import it.uniba.dib.sms222315.Friends.MyFriendsListAdapter;
import it.uniba.dib.sms222315.R;
import it.uniba.dib.sms222315.UserPets.Fragment_MyPets_Profile;
import it.uniba.dib.sms222315.UserPets.Pets;

public class MyPostListAdapter extends ArrayAdapter<Report> {

    //FRAGMENT VAR
    Fragment my_fragment;
    FragmentManager my_frag_manager;
    FragmentTransaction my_frag_trans;
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
        ImageButton addLike, addComment , share;

        String UID;
        TextView category;
    }
    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public MyPostListAdapter(Context context, int resource,
                             ArrayList<Report> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        String author = getItem(position).getPrv_authorName();
        String description = getItem(position).getPrv_description();
        int numberLike = getItem(position).getPrv_numberLike();
        String str_numberLike = Integer.toString(numberLike);
        String linkmyPhoto = getItem(position).getPrv_linkImg();
        String address = getItem(position).getAddressReport();

        String categoryPost = getItem(position).getPrv_category();
        String uidDocument = getItem(position).getPrv_secretDocID();


        Report reportObj = new Report(linkmyPhoto, author, description , str_numberLike,
                address, categoryPost, uidDocument);

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

            holder.category = (TextView) convertView.findViewById(R.id.Adap_Repo_Category);

            holder.addComment = (ImageButton) convertView.findViewById(R.id.Adap_Repo_addComment);
            holder.share = (ImageButton) convertView.findViewById(R.id.Adap_Repo_share);


            Log.d(TAG , " ok find");

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            Log.d(TAG , " Convert view not null");
            holder = (MyPostListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        //scrool animation
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

        holder.category.setText(reportObj.getPrv_category());
        //qusta è solo una stringa
        holder.UID = reportObj.getPrv_secretDocID();

        holder.street.setText(reportObj.getAddressReport());
        holder.street.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent per maps

                launchNavigationStep2Step(reportObj.getAddressReport());
            }
        });
        holder.addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG , "add comment to post number : " +reportObj.getPrv_description());
                Log.d(TAG , "UDI Document : " +reportObj.getPrv_secretDocID());
                Log.d(TAG , "Category : " + reportObj.getPrv_category());
                openDetailReport(reportObj);
            }
        });

        String checkcategoria = reportObj.getPrv_category();

        Log.d(TAG, "checkcategoria :" + checkcategoria);
        if (checkcategoria.equals("General")){
            holder.category.setTextColor(Color.BLACK);

        }else if (checkcategoria.equals("Pets accommodation")){
            holder.category.setTextColor(Color.BLUE);
        }else if (checkcategoria.equals("Talk")){
            holder.category.setTextColor(Color.GREEN);
        }else if (checkcategoria.equals("For Fun")){
            holder.category.setTextColor(Color.GREEN);
        }else if (checkcategoria.equals("Advisory")){
            holder.category.setTextColor(Color.GREEN);
        }else if (checkcategoria.equals("Danger")){
            holder.category.setTextColor(Color.RED);
        }else if (checkcategoria.equals("Abandonment")){
            holder.category.setTextColor(Color.BLUE);
        }else if (checkcategoria.equals("Find")){
            holder.category.setTextColor(Color.BLUE);
        }else if (checkcategoria.equals("Lost")){
            holder.category.setTextColor(Color.BLUE);
        }else if (checkcategoria.equals("Adoption")){
            holder.category.setTextColor(Color.BLUE);
        }else{
            Log.d(TAG , "specie not found");
        }

        View finalConvertView = convertView;
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG , "add comment to post number : " +reportObj.getPrv_description());
                Log.d(TAG , "UDI Document : " +reportObj.getPrv_secretDocID());
                Log.d(TAG , "Category : " + reportObj.getPrv_category());
                shareProfile(reportObj, finalConvertView);
            }
        });


        return convertView;
    }

    private void shareProfile(Report reportObj, View view) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, reportObj.getPrv_category()+": "+"\n"+
                reportObj.getPrv_description()+": "+"\n"+reportObj.getAddressReport());
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
        ImageView imageView = view.findViewById(R.id.Adap_Repo_image);
        Bitmap originalImage = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
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

    private void openDetailReport(Report clickRepo) {
        my_fragment = new Fragment_Report_Dettails();
        my_frag_manager = ((FragmentActivity) mContext).getSupportFragmentManager();
        my_frag_trans = my_frag_manager.beginTransaction();
        Bundle bundle = new Bundle();
        //this is pass
        bundle.putParcelable("clickRepo", clickRepo);
        my_fragment.setArguments(bundle);
        //si aggiunge il richiamo allo stack
        my_frag_trans.addToBackStack(null);
        //add diventa replace
        my_frag_trans.replace(R.id.Frame_Act_Main , my_fragment );
        my_frag_trans.commit();

    }


}
