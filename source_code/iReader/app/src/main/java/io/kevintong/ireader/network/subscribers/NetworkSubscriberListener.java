package io.kevintong.ireader.network.subscribers;


public interface NetworkSubscriberListener<T> {
    void onNext(T t);
    void onError(Throwable e);
}
