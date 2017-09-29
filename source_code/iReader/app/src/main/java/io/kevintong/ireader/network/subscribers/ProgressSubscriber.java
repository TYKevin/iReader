package io.kevintong.ireader.network.subscribers;

import android.content.Context;

import io.kevintong.ireader.network.progress.ProgressCancelListener;
import io.kevintong.ireader.network.progress.ProgressDialogHandler;


/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public class ProgressSubscriber<T> extends NetworkSubscriber<T> implements ProgressCancelListener {

    private ProgressDialogHandler mProgressDialogHandler;

    public ProgressSubscriber(NetworkSubscriberListener mNetworkSubscriberListener, Context context) {
        super( mNetworkSubscriberListener, context);
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    public ProgressSubscriber(NetworkSubscriberListener mNetworkSubscriberListener, Context context, String showTitle) {
        super( mNetworkSubscriberListener, context);
        mProgressDialogHandler = new ProgressDialogHandler(context,showTitle, this, true);
    }

    private void showProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        super.onCompleted();
        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        super.onError(e);
        dismissProgressDialog();
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}