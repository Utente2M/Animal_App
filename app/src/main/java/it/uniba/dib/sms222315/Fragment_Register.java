package it.uniba.dib.sms222315;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_Register extends Fragment {

    Button button_login , button_register ;
    EditText ET_username , ET_password , ET_email  ;

    String str_username , str_email , str_password;
    SharedPreferences my_share_preference;
    SharedPreferences.Editor myEditor;



    //questo viene copiato da Frag_login per semplicità

    @Override
    public void onAttach(@NonNull Context context) {
         my_share_preference = context.
                getSharedPreferences("User_file" , Context.MODE_PRIVATE);
         myEditor = my_share_preference.edit();

        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //cambiamo il layout
        View my_view = inflater.inflate(R.layout.fragm_register, container, false);

        ET_username = my_view.findViewById(R.id.et_username_login);
        ET_password = my_view.findViewById(R.id.et_password_login);
        ET_email = my_view.findViewById(R.id.et_mail_login);

        button_register = my_view.findViewById(R.id.btn_register);




        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //click bottone registrati
                str_password = ET_password.getText().toString();
                str_username = ET_username.getText().toString();
                str_email = ET_email.getText().toString();

                myEditor.putString("E-Mail" , str_email);
                myEditor.putString("Username " , str_username );
                myEditor.putString("Password" , str_password );

               // myEditor.apply();
                //todo sostituire con stringa
                Toast.makeText(getContext() , "Registered" , Toast.LENGTH_SHORT).show();


            }
        });



        return my_view;
    }
}//end class
