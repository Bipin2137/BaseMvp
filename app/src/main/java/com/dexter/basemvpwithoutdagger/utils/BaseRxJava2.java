package com.dexter.basemvpwithoutdagger.utils;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Admin on 17-01-2018.
 */

public class BaseRxJava2<T> {

    private Flowable<T> getObservable(Flowable<T> observable) {
        return observable;
    }

    public Disposable subscribe(Flowable<T> observable, DataInterface<T> dataInterface) {
        return getObservable(observable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataInterface::onNext, throwable -> dataInterface.onError(NetworkUtils.getStringError(throwable)), dataInterface::onComplete);
    }

    public interface DataInterface<T> {
        void onNext(T response);

        void onError(String error);

        void onComplete();
    }
}
