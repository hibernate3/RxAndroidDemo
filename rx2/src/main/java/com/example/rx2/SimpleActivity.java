package com.example.rx2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SimpleActivity extends AppCompatActivity {
    private static final String TAG = "myLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "Observable thread is : " + Thread.currentThread().getName());

                Log.d(TAG, "emit 1");
                emitter.onNext(1);

                Log.d(TAG, "emit 2");
                emitter.onNext(2);

                Log.d(TAG, "emit 3");
                emitter.onNext(3);

                Log.d(TAG, "emit 4");
                emitter.onNext(4);

                Log.d(TAG, "emit complete");
                emitter.onComplete();
            }
        });

//        observable.subscribe(new Observer<Integer>() {
//            private Disposable disposable;
//
//            @Override
//            public void onSubscribe(Disposable d) {
//                disposable = d;
//                System.out.println("subscribe");
//            }
//
//            @Override
//            public void onNext(Integer value) {
//                System.out.println(value);
//                if (value == 2) {
//                    disposable.dispose();
//                    System.out.println("isDisposed : " + disposable.isDisposed());
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("error");
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("complete");
//            }
//        });

        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer value) throws Exception {
                        Log.d(TAG, "accept: " + value);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "onError");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "onComplete");
                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d(TAG, "subscribe");
                        Log.d(TAG, "Observer thread is :" + Thread.currentThread().getName());
                    }
                });
    }
}
