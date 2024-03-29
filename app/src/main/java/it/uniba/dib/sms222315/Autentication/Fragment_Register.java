package it.uniba.dib.sms222315.Autentication;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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


public class Fragment_Register extends Fragment {

    private static final String TAG = "TAG_FragRegister";

    CallbackFragment myListnerCall;

    Button button_login , button_register ;
    EditText  ET_password , ET_email  ;

    String  str_email , str_password;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * salva prefrenze su disco
    @Override
    public void onAttach(@NonNull Context context) {
         my_share_preference = context.
                getSharedPreferences("User_file" , Context.MODE_PRIVATE);
         myEditor = my_share_preference.edit();

        super.onAttach(context);
    }
*/




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //cambiamo il layout
        View my_view = inflater.inflate(R.layout.fragm_register, container, false);


        ET_password = my_view.findViewById(R.id.et_password_registr);
        ET_email = my_view.findViewById(R.id.et_mail_registr);

        button_register = my_view.findViewById(R.id.btn_register);




        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //click bottone registrati
                str_password = ET_password.getText().toString();
                str_email = ET_email.getText().toString();

                if( str_email.isEmpty()){
                    ET_email.setError("Email is required");
                }
                if(str_password.isEmpty()){
                    ET_password.setError("Password is required");
                }
                else{
                    //salvataggio dei dati sul db
                    createAccount(str_email , str_password );
                    //todo sostituire con stringa
                    Toast.makeText(getContext() , "ok field register " , Toast.LENGTH_SHORT).show();
                }

                /**
                 * //salva sul disco le preferenze, nel file nominato prima
                myEditor.putString("E-Mail" , str_email);
                myEditor.putString("Username " , str_username );
                myEditor.putString("Password" , str_password );
                 */

               // myEditor.apply();
                //todo sostituire con stringa
                Toast.makeText(getContext() , "Registered" , Toast.LENGTH_SHORT).show();

                // da qui se la registrazione va a buon fine lanciamo user_Activity
                //login automatico


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

    private void createAccount(String email, String password) {

        Fragment_Register my_cont = null;
        my_cont = this;
        myListnerCall.createUSerWithMailPassword(email, password );
       //dobbiamo rimandare dati all activity



    }

    private void updateUI(Object o) {
        Log.d(TAG, "UPDATE UI ");
    }
}//end class
