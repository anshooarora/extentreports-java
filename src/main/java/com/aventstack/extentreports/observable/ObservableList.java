package com.aventstack.extentreports.observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.aventstack.extentreports.model.BaseEntity;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.Getter;

@Getter
public class ObservableList<T extends BaseEntity> {
    private final List<T> list = Collections.synchronizedList(new ArrayList<>());
    private final PublishSubject<T> observable = PublishSubject.create();

    public void subscribe(Observer<T> observer) {
        observable.subscribe(observer);
    }

    public void add(T value) {
        list.add(value);
        observable.onNext(value);
    }

    public void remove(T value) {
        
    }

    public Observable<T> getObservable() {
        return observable;
    }
}
