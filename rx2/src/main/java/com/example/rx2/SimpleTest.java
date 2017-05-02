package com.example.rx2;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by wangyuhang on 17-5-2.
 */

public class SimpleTest {
    public static void main(String[] args) {
        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);

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

        observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer value) throws Exception {
                System.out.println(value);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("error");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("complete");
            }
        }, new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                System.out.println("subscribe");
            }
        });
    }
}
