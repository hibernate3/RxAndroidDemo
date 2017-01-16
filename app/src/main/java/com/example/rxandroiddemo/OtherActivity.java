package com.example.rxandroiddemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OtherActivity extends AppCompatActivity {
    @Bind(R.id.activity_other)
    RelativeLayout mRootView;

    @Bind(R.id.main_thread)
    Button mThreadButton;

    @Bind(R.id.main_async)
    Button mAsyncButton;

    @Bind(R.id.main_rx)
    Button mRxButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        ButterKnife.bind(this);

        Log.i("myLog", "Main Thread: " + Thread.currentThread().getName());

        //线程运行
        mThreadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThreadButton.setEnabled(false);
                longRunningOperation();
                Toast.makeText(OtherActivity.this, longRunningOperation(), Toast.LENGTH_SHORT).show();
                mThreadButton.setEnabled(true);
            }
        });

        //异步运行
        mAsyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAsyncButton.setEnabled(false);
                new MyAsyncTasks().execute();
            }
        });

        //使用新线程处理，主线程响应
        final Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(longRunningOperation());
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());

        //响应式运行
        mRxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRxButton.setEnabled(false);
                observable.subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        mRxButton.setEnabled(true);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(OtherActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //异步线程
    private class MyAsyncTasks extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(OtherActivity.this, s, Toast.LENGTH_SHORT).show();
            mAsyncButton.setEnabled(true);
        }

        @Override
        protected String doInBackground(Void... params) {
            return longRunningOperation();
        }
    }

    //长时间运行的任务
    private String longRunningOperation() {
        try {
            Log.i("myLog", "Task Thread: " + Thread.currentThread().getName());
            Thread.sleep(5000);
        } catch (Exception e) {
            Log.i("myLog", e.toString());
        }

        return "Complete!";
    }
}
