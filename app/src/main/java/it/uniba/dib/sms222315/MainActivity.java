package it.uniba.dib.sms222315;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void lunchLogin(View view) {
        Intent login_prova = new Intent(this, EmailPasswordActivity.class);
        startActivity(login_prova);
    }
}