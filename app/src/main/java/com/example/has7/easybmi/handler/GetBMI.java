package com.example.has7.easybmi.handler;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


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
        String jsonStr = httpHandler.makeServiceCall(serviceUrl, "POST", requestData);
        Log.d(TAG, "Response from url: " + jsonStr);
        String bmiStr = null;
        if (!TextUtils.isEmpty(jsonStr)) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                bmiStr = jsonObj.getString("bmi");
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return bmiStr;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG, "In PostMethod: " + result);
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}


