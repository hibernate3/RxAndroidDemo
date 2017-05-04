package com.example.rx2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AdvancedActivity extends AppCompatActivity {
    private static final String TAG = "myLog";
    @BindView(R.id.requestBtn)
    Button requestBtn;

    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);
        butterknife.ButterKnife.bind(this);

        bufferTest();
    }

    private void bufferTest() {
        Flowable upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "before emit, requested = " + emitter.requested());

                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "after emit 1, requested = " + emitter.requested());

                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "after emit 2, requested = " + emitter.requested());

                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "after emit 3, requested = " + emitter.requested());

                Log.d(TAG, "emit complete");
                emitter.onComplete();

                Log.d(TAG, "after emit complete, requested = " + emitter.requested());
            }
        }, BackpressureStrategy.ERROR);

        Subscriber downstream = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "onSubscribe");
                mSubscription = s;
//                s.request(10);  //request 10
                s.request(Long.MAX_VALUE);  //注意这句代码
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.w(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };

        upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(downstream);
    }

    @butterknife.OnClick(R.id.requestBtn)
    public void onViewClicked() {
        mSubscription.request(128);
    }
}
