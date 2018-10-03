package com.lap0895.testfirebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class YourFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("sendRegistraToServer",token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // send FCM token to server
        JSONObject person = new JSONObject();
        JSONObject tokenFCM = new JSONObject();
        try {
            tokenFCM.put("token", token);
            person.put("name", "Franck");
            person.put("tokenFCM", tokenFCM);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        executePost("http://192.168.211.247:8080/person", person);

    }

    private static Boolean executePost(String targetURL, JSONObject jsonParam)
    {
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.connect();

            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream ());
            wr.writeBytes(jsonParam.toString());
            wr.flush();
            wr.close ();

            int response = connection.getResponseCode();
            if (response >= 200 && response <=399){
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            Log.e("API REQUEST", e.getMessage());
            return false;

        } finally {

            if(connection != null) {
                connection.disconnect();
            }
        }
    }
}
