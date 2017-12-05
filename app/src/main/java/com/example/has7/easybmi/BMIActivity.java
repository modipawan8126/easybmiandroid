package com.example.has7.easybmi;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.has7.easybmi.handler.HttpHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class BMIActivity extends AppCompatActivity {

    //private static String easyBMI = "https://easybmi.herokuapp.com/bmi";
    //private static String easyBMIServiceURL = "https://easybmi.herokuapp.com/bmi/add/user";
    //private static String easyBMIServiceURL = "http://192.168.1.102:9999/bmi/add/user";
    private String TAG = getClass().getSimpleName();

    HttpHandler httpHandler = new HttpHandler();

    Button button;

    Button share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        button = (Button)findViewById(R.id.button2);

        share = (Button) findViewById(R.id.socialshare);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenshot();
            }
        });

        share.setVisibility(View.GONE);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                calculateBMI(view);
            }
        });
    }

    protected void calculateBMI(View view) {

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.sex);
        int selectedId = radioGroup.getCheckedRadioButtonId();

        Log.d(this.getClass().getSimpleName(), "selectedId: " + selectedId);


        TextView bmiTextView = (TextView) findViewById(R.id.bmi);
        TextView bmiMessageTextView = (TextView) findViewById(R.id.bmimessage);
        ImageView imageView = (ImageView) findViewById(R.id.bmiuser);


        EditText height = (EditText) findViewById(R.id.height);
        EditText weight = (EditText) findViewById(R.id.weight);
        String heightStr = height.getText().toString();
        String weightStr = weight.getText().toString();

        if ((heightStr == null || heightStr.length() == 0 )|| (weightStr == null || weightStr.length() == 0) || selectedId == -1) {
            bmiTextView.setText("Invalid Input..");
        } else {
            RadioButton radioButton = (RadioButton) findViewById(selectedId);
            String gender = (String) radioButton.getText();

            double bmi = Utility.calculateBmi(heightStr, weightStr);
            Log.d(this.getClass().getName(), "height:" + heightStr + " weight:" + weightStr + " Bmi: " + bmi);
            bmiTextView.setText(String.valueOf(bmi));

            String bmiMsg = Utility.bmiMessage(bmi);
            bmiMessageTextView.setText(bmiMsg);

            if (gender.equalsIgnoreCase("male")) {
                if (bmi <= 18) {
                    imageView.setImageResource(R.mipmap.boy1);
                } else if ( bmi > 18 && bmi <= 25) {
                    imageView.setImageResource(R.mipmap.boy2);
                } else {
                    imageView.setImageResource(R.mipmap.boy3);
                }
            } else {
                if (bmi <= 18) {
                    imageView.setImageResource(R.mipmap.girl1);
                } else if ( bmi > 18 && bmi <= 25) {
                    imageView.setImageResource(R.mipmap.girl2);
                } else {
                    imageView.setImageResource(R.mipmap.girl3);
                }
            }


            share.setVisibility(View.VISIBLE);
            hideSoftKeyboard();
        }
    }


    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        StringBuilder sb = new StringBuilder();
        sb.append("easybmi-").append(now).append(".jpg");

        try {
            // image naming and path  to include sd card  appending name you choose for file
            //String mPath = Environment.getExternalStorageDirectory().toString() + "/easybmi-" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("easybmi", this.MODE_PRIVATE);
            if (!directory.exists()) {
                directory.mkdir();
            }

            File imageFile = new File(directory, sb.toString());
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
