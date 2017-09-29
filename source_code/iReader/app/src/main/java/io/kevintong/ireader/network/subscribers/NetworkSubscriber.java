package io.kevintong.ireader.network.subscribers;

import android.content.Context;
import android.util.Log;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public class NetworkSubscriber<T> extends Subscriber<T>{

    protected NetworkSubscriberListener mNetworkSubscriberListener;

    protected Context context;

    public NetworkSubscriber(NetworkSubscriberListener mNetworkSubscriberListener, Context context) {
        this.mNetworkSubscriberListener = mNetworkSubscriberListener;
        this.context = context;
    }


    /**
     * 完成
     */
    @Override
    public void onCompleted() {
        Log.d("Network",  "成功获得网络数据");
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
           Log.d("NetworkExecption"
                    , "连接超时");
        } else if (e instanceof ConnectException) {
            Log.d("NetworkExecption"
                    , "网络中断，请检查您的网络状态");
        }else {
            Log.d("NetworkExecption"
                    , "网络调用错误： 出现未知错误");
        }
        e.printStackTrace();

        if (mNetworkSubscriberListener != null) {
            mNetworkSubscriberListener.onError(e);
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mNetworkSubscriberListener != null) {
            mNetworkSubscriberListener.onNext(t);
        }
    }
}