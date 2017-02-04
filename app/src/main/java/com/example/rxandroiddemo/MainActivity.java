package com.example.rxandroiddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gotoSimpleModule();
    }

    // 跳转简单的页面
    public void gotoSimpleModule() {
        startActivity(new Intent(this, SimpleActivity.class));
    }

    // 跳转复杂的页面
    public void gotoMoreModule() {
        startActivity(new Intent(this, MoreActivity.class));
    }

    //跳转其他的页面
    public void gotoOtherModule() {
        startActivity(new Intent(this, OtherActivity.class));
    }

    //跳转测试的页面
    public void gotoTestModule() {
        startActivity(new Intent(this, TestActivity.class));
    }
}
