package com.example.nextdemo;

import android.os.Bundle;
import android.os.Message;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class GetActivity extends AppCompatActivity {
    private TextView mNetTextView,mBackTextView;
    private Button mNetButtton;
    private Handler mHandler;


    private String mUrl = "https://www.wanandroid.com/banner/json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
        mHandler=new MyHandler();
        initView();
        initClick();
    }
    private void sendGetRequest(String mUrl){
        new Thread(
                () -> {
                    try {
                        URL url = new URL(mUrl);
                        HttpURLConnection connection = (HttpURLConnection)
                                url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        connection.setRequestProperty("Accept-Language",
                                "zh-CN,zh;q=0.9");
                        connection.setRequestProperty("Accept-Encoding",
                                "gzip,deflate");
                        connection.connect();
                        InputStream in = connection.getInputStream();
                        String responseData = StreamToString(in);
                        Message message = new Message();
                        message.obj = responseData;
                        mHandler.sendMessage(message);
                        Log.d("lx", "sendGetNetRequest: "+responseData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            setText(decodeJson(result));
        }
    }
    private String StreamToString(InputStream in) {
        StringBuilder sb = new StringBuilder();
        String oneLine;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            while ((oneLine = reader.readLine()) != null) {
                sb.append(oneLine).append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    private JsonData decodeJson(String data) {
        JsonData jsonData = new JsonData();
        jsonData.data = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            jsonData.errorCode = jsonObject.getInt("errorCode");
            jsonData.errorMsg = jsonObject.getString("errorMsg");
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            JsonData.DetailData detailData;
            for (int i = 0; i < jsonArray.length(); i++) {
                detailData = new JsonData.DetailData();
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                detailData.desc = jsonObject1.getString("desc");
                detailData.id = jsonObject1.getInt("id");
                detailData.imagePath = jsonObject1.getString("imagePath");
                detailData.isVisible = jsonObject1.getInt("isVisible");
                detailData.order = jsonObject1.getInt("order");
                detailData.title = jsonObject1.getString("title");
                detailData.type = jsonObject1.getInt("type");
                detailData.url = jsonObject1.getString("url");
                jsonData.data.add(detailData);
            }
        } catch (Exception e) {
            Log.e("tag", "decodeJson: ", e);
        }
        return jsonData;
    }
    private void initClick(){
        mNetButtton.setOnClickListener(v -> {
            sendGetRequest(mUrl);
        });
        mBackTextView.setOnClickListener(v -> finish());
    }
    private void initView() {
        mNetTextView=findViewById(R.id.netText);
        mBackTextView=findViewById(R.id.backText);
        mNetButtton=findViewById(R.id.netButton);
    }
    private void setText(JsonData jsonData){
        mNetTextView.setText(jsonData.data.get(0).title);
    }
}