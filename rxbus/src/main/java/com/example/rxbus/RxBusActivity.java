package com.example.rxbus;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rxbus.rxbus.ChangeAnswerEvent;
import com.example.rxbus.rxbus.RxBus;

public class RxBusActivity extends AppCompatActivity {
    Button mYesBtn, mNoBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);

        mYesBtn = (Button) findViewById(R.id.yes_btn);
        mNoBtn = (Button) findViewById(R.id.no_btn);

        mYesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeAnswerEvent changeAnswerEvent = new ChangeAnswerEvent();
                changeAnswerEvent.setAnswer(mYesBtn.getText().toString());
                RxBus.getDefault().post(changeAnswerEvent);
            }
        });

        mNoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeAnswerEvent changeAnswerEvent = new ChangeAnswerEvent();
                changeAnswerEvent.setAnswer(mNoBtn.getText().toString());
                RxBus.getDefault().post(changeAnswerEvent);
            }
        });
    }
}
