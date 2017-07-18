package com.example.has7.easybmi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.has7.easybmi.domain.BMIVO;
import com.example.has7.easybmi.handler.GetBMI;
import com.example.has7.easybmi.handler.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class BMIActivity extends AppCompatActivity {

    //private static String easyBMI = "https://easybmi.herokuapp.com/bmi";
    private static String easyBMIServiceURL = "https://easybmi.herokuapp.com/bmi/add/user";
    //private static String easyBMIServiceURL = "http://192.168.1.102:9999/bmi/add/user";
    private String TAG = getClass().getSimpleName();

    HttpHandler httpHandler = new HttpHandler();

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        button = (Button)findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                calculateBMI(view);
            }
        });
    }

    protected void calculateBMI(View view) {

        EditText height = (EditText) findViewById(R.id.height);
        EditText weight = (EditText) findViewById(R.id.weight);
        EditText name = (EditText) findViewById(R.id.name);
        EditText email = (EditText) findViewById(R.id.email);

        String heightStr = height.getText().toString();
        String weightStr = weight.getText().toString();
        String nameStr = name.getText().toString();
        String emailStr = email.getText().toString();

        Log.d(this.getClass().getName(), "height:" + heightStr + " weight:" + weightStr + " name:" + nameStr + " email:" + emailStr);

        JSONObject postData = new JSONObject();
        try {
            postData.put("name", nameStr);
            postData.put("email", emailStr);
            postData.put("weight", weightStr);
            postData.put("height", heightStr);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(this.getClass().getSimpleName(),e.getMessage());
        }
        TextView bmiTextView = (TextView) findViewById(R.id.bmi);
        String bmiStr = null;

        try {
            String jsonStr = (String) new GetBMI(easyBMIServiceURL, postData.toString()).execute().get();

            Log.d(TAG, "result string: " + jsonStr);
            if (!TextUtils.isEmpty(jsonStr)) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    bmiStr = jsonObj.getString("bmi");
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            Log.d(TAG, "result BMI: " + bmiStr);
            if (!TextUtils.isEmpty(bmiStr)) {
                bmiTextView.setText(bmiStr);
            }

        } catch (InterruptedException e) {
            Log.e(TAG, e.getMessage());
        } catch (ExecutionException e) {
            Log.e(TAG, e.getMessage());
        }

    }

}
