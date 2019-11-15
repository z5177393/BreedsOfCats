package com.cat.www;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectUtil {

    private static final ConnectUtil Instance = new ConnectUtil();

    public static ConnectUtil getInstance() {
        return Instance;
    }

    public void getdata(String url, final Callback callback) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {

                try {
                    URL url1 = new URL(strings[0]);
                    HttpURLConnection hurlc = (HttpURLConnection) url1.openConnection();
                    hurlc.setReadTimeout(10000);
                    hurlc.setRequestMethod("GET");
                    hurlc.setReadTimeout(10000);
                    hurlc.setConnectTimeout(10000);

                    InputStream inputStream = hurlc.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));//写入reader
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    return response.toString();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (!TextUtils.isEmpty(s)) {
                    callback.onSucceedData(s);
                }

            }
        }.execute(url);
    }

}
