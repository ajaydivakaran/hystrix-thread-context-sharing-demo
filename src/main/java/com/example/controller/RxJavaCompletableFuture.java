package com.example.controller;

import rx.Observable;

import java.util.concurrent.CompletableFuture;

public class RxJavaCompletableFuture<T> extends CompletableFuture<T> {
    public RxJavaCompletableFuture(Observable<T> observable) {
        observable.subscribe(this::complete, this::completeExceptionally);
    }
}
