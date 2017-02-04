package com.example.rxbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rxbus.rxbus.ChangeAnswerEvent;
import com.example.rxbus.rxbus.RxBus;

import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    TextView mResultTv;
    Button mEnterBtn;

    Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResultTv = (TextView) findViewById(R.id.result_tv);
        mEnterBtn = (Button) findViewById(R.id.enter_btn);

        mEnterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RxBusActivity.class));
            }
        });

        mSubscription = RxBus.getDefault().toObserverable(ChangeAnswerEvent.class)
                .subscribe(new Action1<ChangeAnswerEvent>() {
                    @Override
                    public void call(ChangeAnswerEvent changeAnswerEvent) {
                        Toast.makeText(MainActivity.this, "I get your answer ~ ", Toast.LENGTH_SHORT).show();
                        mResultTv.setText(changeAnswerEvent.getAnswer());
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
