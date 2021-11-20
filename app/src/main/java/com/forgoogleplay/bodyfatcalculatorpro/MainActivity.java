package com.forgoogleplay.bodyfatcalculatorpro;

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


    public void goToBasicActivity(View view) {
        Intent intent = new Intent(this, BasicActivity.class);
        startActivity(intent);
    }

    public void goToProActivity(View view) {
        Intent intent = new Intent(this, ProActivity.class);
        startActivity(intent);
    }

}