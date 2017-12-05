package com.example.has7.easybmi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.has7.easybmi.handler.HttpHandler;

public class BMIActivity extends AppCompatActivity {

    //private static String easyBMI = "https://easybmi.herokuapp.com/bmi";
    //private static String easyBMIServiceURL = "https://easybmi.herokuapp.com/bmi/add/user";
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

            float bmi = Utility.calculateBmi(heightStr, weightStr);
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


        }
    }

}
