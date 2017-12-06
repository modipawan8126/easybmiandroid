package com.example.has7.easybmi;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class BMIActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 999;
    private String TAG = getClass().getSimpleName();

    Button button;

    ImageView share;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        button = (Button)findViewById(R.id.button2);

        share = (ImageView) findViewById(R.id.socialshare);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRequiredPermissions();
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

    private void checkRequiredPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(BMIActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(BMIActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d(this.getClass().getSimpleName(), requestCode + "  " + grantResults[0] + "  " + permissions);
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //populateFriendList();
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(BMIActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(BMIActivity.this);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Request to Write is Necessary");
                    alertBuilder.setMessage("Write to storage permission is necessary for to save your BMI screens. You can share BMI screens with friends.");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            checkRequiredPermissions();
                        }
                    });

                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    Toast.makeText(this, "Until you grant the permission, we canot display the friends", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the friends", Toast.LENGTH_LONG).show();
            }
        }

    }
    protected void calculateBMI(View view) {

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.sex);
        int selectedId = radioGroup.getCheckedRadioButtonId();

        Log.d(this.getClass().getSimpleName(), "selectedId: " + selectedId);


        TextView bmiTextView = (TextView) findViewById(R.id.bmi);
        TextView bmiMessageTextView = (TextView) findViewById(R.id.bmimessage);
        ImageView imageView = (ImageView) findViewById(R.id.bmiuser);
        TextView timestamp = (TextView) findViewById(R.id.timestamp);

        String date = (String) android.text.format.DateFormat.format("dd-MMM-yy hh:mm:ss a", new Date());
        timestamp.setText(date);

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
                    imageView.setImageResource(R.drawable.boy1resize);
                } else if ( bmi > 18 && bmi <= 25) {
                    imageView.setImageResource(R.drawable.boy2resize);
                } else {
                    imageView.setImageResource(R.drawable.boy3resize);
                }
            } else {
                if (bmi <= 18) {
                    imageView.setImageResource(R.drawable.girl1resize);
                } else if ( bmi > 18 && bmi <= 25) {
                    imageView.setImageResource(R.drawable.girl2resize);
                } else {
                    imageView.setImageResource(R.drawable.girl3resize);
                }
            }

            //imageView.setImageResource(R.drawable.boy1resize);
           /* ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) imageView.getLayoutParams();
            params.width = 120;*/
            // existing height is ok as is, no need to edit it
            //imageView.setLayoutParams(params);

            share.setVisibility(View.VISIBLE);
            hideSoftKeyboard();
        }
    }


    private void takeScreenshot() {
        StringBuilder sb = new StringBuilder();
        sb.append("/easybmi-").append(System.currentTimeMillis()).append(".jpg");

        try {

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            String root = Environment.getExternalStorageDirectory().toString();
            File directory = new File(root + "/easybmi/screen");
            if (!directory.exists()) {
                directory.mkdirs();
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
