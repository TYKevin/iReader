package io.kevintong.ireader.api;

import io.kevintong.ireader.bean.WordDetails;
import io.kevintong.ireader.network.entity.HttpResult;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by kevin on 2017/9/29.
 */

public interface WordAPI {
    /**
     * 获取单词解释
     * @return
     */
    @GET("bdc/search/")
    Observable<HttpResult<WordDetails>> getWordDetails(@Query("word") String word);
}
