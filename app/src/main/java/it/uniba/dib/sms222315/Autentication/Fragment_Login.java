package it.uniba.dib.sms222315.Autentication;

import android.content.Context;
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

import it.uniba.dib.sms222315.R;


public class Fragment_Login extends Fragment {


    CallbackFragment myListnerCall;
    Button button_login , button_register ;
    EditText ET_mail , ET_password ;

    CallbackFragment my_callbackFragment;

    String str_mail , str_password;

    //SharedPreferences my_share_preference;
    //SharedPreferences.Editor myEditor;

   

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View my_view = inflater.inflate(R.layout.fragm_login, container, false);

        ET_mail = my_view.findViewById(R.id.et_mail_login);
        ET_password = my_view.findViewById(R.id.et_password_login);
        button_login = my_view.findViewById(R.id.btn_login);
        button_register = my_view.findViewById(R.id.btn_register);


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //BOTTONE LOGIN
                //queste due variabili contengono username e password in formato Stringa
                str_mail = ET_mail.getText().toString();
                str_password = ET_password.getText().toString();

                if( str_mail.isEmpty()){
                    ET_mail.setError("Email is required");
                }
                if(str_password.isEmpty()){
                    ET_password.setError("Password is required");
                }
                else{
                    loginAccount (str_mail , str_password);
                    //todo sostituire con stringa
                    Toast.makeText(getContext() , "ok field Login " , Toast.LENGTH_SHORT).show();
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //listner di Class Interface per scambio dati
        myListnerCall = (CallbackFragment) context;

    }
    

    private void loginAccount(String str_mail, String str_password) {
        
        myListnerCall.logAccountWithMailePass (str_mail , str_password);
    }


    //potenziale errore
    public void setMy_callbackFragment (CallbackFragment my_callFrag ){
        this.my_callbackFragment = my_callFrag;
    }

}//end classFragment login
