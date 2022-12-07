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

import javax.security.auth.callback.Callback;


public class Fragment_Login extends Fragment {

    Button button_login , button_register ;
    EditText ET_username , ET_password ;

    CallbackFragment my_callbackFragment;

    String str_username , str_password;

    SharedPreferences my_share_preference;
    SharedPreferences.Editor myEditor;

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

        View my_view = inflater.inflate(R.layout.fragm_login, container, false);

        ET_username = my_view.findViewById(R.id.et_username_login);
        ET_password = my_view.findViewById(R.id.et_password_login);
        button_login = my_view.findViewById(R.id.btn_login);
        button_register = my_view.findViewById(R.id.btn_register);


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //BOTTONE LOGIN
                //queste due variabili contengono username e password in formato Stringa
                str_username = ET_username.getText().toString();
                str_password = ET_password.getText().toString();

                String u_name , u_password ;
                u_name = my_share_preference.getString("Username" , null);
                u_password = my_share_preference.getString("Password" , null);

                if (str_username.equals(u_name) && str_password.equals(u_password)){
                    //TODO sistemare messaggi
                    Toast.makeText(getContext() , "Login MIAO" , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext() , "Error Login" , Toast.LENGTH_SHORT).show();
                }


            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                if ( my_callbackFragment != null ) {
                    my_callbackFragment.changeFragment();
                }
            }
        });



        return my_view;
    }


    //potenziale errore
    public void setMy_callbackFragment (CallbackFragment my_callFrag ){
        this.my_callbackFragment = my_callFrag;
    }

}//end classFragment login
