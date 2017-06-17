package com.example.has7.easybmi.handler;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.has7.easybmi.BMIActivity;
import com.example.has7.easybmi.MainActivity;
import com.example.has7.easybmi.domain.BMIVO;

import org.json.JSONException;
import org.json.JSONObject;


public class GetBMI extends AsyncTask<Void, Void, Void> {

    private TextView bmiTextView;

    private String TAG = getClass().getSimpleName();

    private BMIVO bmivo;

    private String serviceUrl;

    private String requestData;

    public GetBMI() {
        super();
    }

    public GetBMI(String url, String jsonData, TextView view) {
        serviceUrl = url;
        requestData = jsonData;
        bmiTextView = view;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        HttpHandler httpHandler = new HttpHandler();

        Log.d(TAG, "ServiceUrl: " + serviceUrl);
        // Making a request to url and getting response
        String jsonStr = httpHandler.makeServiceCall(serviceUrl, "POST", requestData);

        Log.d(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);


                String name = jsonObj.getString("name");
                String height = jsonObj.getString("height");
                String weight = jsonObj.getString("weight");
                String bmi = jsonObj.getString("bmi");

                Log.d(TAG, name + ", " + height + ", " + weight + ", " + bmi);
                setBmivo(new BMIVO(name, height, weight, bmi));


            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());


            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");


        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (getBmivo() != null) {
            Log.d(TAG, getBmivo().getBmi());
            BMIActivity.printMessage(getBmivo(), bmiTextView);
        } else {
            BMIActivity.printMessage(getBmivo(), bmiTextView);
        }

    }

    public BMIVO getBmivo() {
        return bmivo;
    }

    public void setBmivo(BMIVO bmivo) {
        this.bmivo = bmivo;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}


