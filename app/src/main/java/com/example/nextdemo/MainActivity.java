package com.example.nextdemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button JsonBtn,GetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initClick();

    }
    private void initView(){
        JsonBtn=findViewById(R.id.btn_json);
        GetBtn=findViewById(R.id.btn_get);
    }
    private void initClick(){
        JsonBtn.setOnClickListener(v ->{
            Intent JsonActivity=new Intent(this, JsonActivity.class);
            startActivity(JsonActivity);
                }
        );
        GetBtn.setOnClickListener(v ->{
            Intent GetActivity=new Intent(this,GetActivity.class);
            startActivity(GetActivity);
        });
    }
}