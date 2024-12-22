package com.example.nextdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonActivity extends AppCompatActivity {
    private TextView mTextView ,BackTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        initView();
        String jsonString = "{\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"desc\": \"我们支持订阅啦~\",\n" +
                "            \"id\": 30,\n" +
                "            \"imagePath\": \"https://www.wanandroid.com/blogimgs/42da12d8-de56-4439-b40c-eab66c227a4b.png\",\n" +
                "            \"isVisible\": 1,\n" +
                "            \"order\": 2,\n" +
                "            \"title\": \"我们支持订阅啦~\",\n" +
                "            \"type\": 0,\n" +
                "            \"url\": \"https://www.wanandroid.com/blog/show/3352\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"desc\": \"\",\n" +
                "            \"id\": 6,\n" +
                "            \"imagePath\": \"https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png\",\n" +
                "            \"isVisible\": 1,\n" +
                "            \"order\": 1,\n" +
                "            \"title\": \"我们新增了一个常用导航Tab~\",\n" +
                "            \"type\": 1,\n" +
                "            \"url\": \"https://www.wanandroid.com/navi\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"desc\": \"一起来做个App吧\",\n" +
                "            \"id\": 10,\n" +
                "            \"imagePath\": \"https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png\",\n" +
                "            \"isVisible\": 1,\n" +
                "            \"order\": 1,\n" +
                "            \"title\": \"一起来做个App吧\",\n" +
                "            \"type\": 1,\n" +
                "            \"url\": \"https://www.wanandroid.com/blog/show/2\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"errorCode\": 0,\n" +
                "    \"errorMsg\": \"\"\n" +
                "}";
        JsonData jsonData =  decodeJson(jsonString);
        setData(jsonData);
        BackTextView.setOnClickListener(v -> finish());
    }
    private void setData(JsonData jsonData){
        mTextView.setText(jsonData.data.get(0).title);
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

    private void initView() {
        mTextView = findViewById(R.id.jsonText);
        BackTextView = findViewById(R.id.backTextView);

    }
}