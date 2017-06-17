package com.example.has7.easybmi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadBMILayout(View view) {
        Intent intent = new Intent(this, BMIActivity.class);
        startActivity(intent);
    }
}
