package io.kevintong.ireader.network.httpMethods;


import io.kevintong.ireader.api.WordAPI;
import io.kevintong.ireader.bean.WordDetails;
import io.kevintong.ireader.network.http.HttpMethods;
import rx.Observable;
import rx.Subscriber;


public class WordHttpMethod extends HttpMethods {
    private WordAPI mWordApi;

    private WordHttpMethod() {
        mWordApi = retrofit.create(WordAPI.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final WordHttpMethod INSTANCE = new WordHttpMethod();
    }

    //获取单例
    public static WordHttpMethod getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void getWordDetails(Subscriber<WordDetails> subscriber,String word) {
        Observable observable = mWordApi.getWordDetails(word).map(new HttpResultFunc<WordDetails>());
        toSubscribe(observable, subscriber);
    }
}
