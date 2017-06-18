package com.example.has7.easybmi.handler;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class GetBMI extends AsyncTask<Void, Void, String> {

    private String TAG = getClass().getSimpleName();

    private String serviceUrl;

    private String requestData;

    public GetBMI() {
        super();
    }

    public GetBMI(String url, String jsonData) {
        super();
        serviceUrl = url;
        requestData = jsonData;
    }

    @Override
    protected String doInBackground(Void... arg0) {
        HttpHandler httpHandler = new HttpHandler();
        Log.d(TAG, "ServiceUrl: " + serviceUrl);
        // Making a request to url and getting response
        InputStream inputStream = httpHandler.makeServiceCall(serviceUrl, "POST", requestData);
        String jsonStr = convertStreamToString(inputStream);
        /*String bmiStr = null;
        if (!TextUtils.isEmpty(jsonStr)) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                bmiStr = jsonObj.getString("bmi");
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }*/
        return jsonStr;
    }

    /*@Override
    protected void onPostExecute(String jsonStr) {

    }*/

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}


