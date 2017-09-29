package io.kevintong.ireader.boot;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kevin on 2017/9/29.
 */
public class BaseApplication extends Application {
    private static final int MAX_RETRY_COUNT = 5;

    @Override
    public void onCreate() {
        super.onCreate();

        setupPicasso();
    }

    /**
     * 设置 picasso 缓存路径
     * 增加 图片读取失败重试机制
     */
    private void setupPicasso() {
        //缓存文件夹
        String cacheDirPath = getInnerSDCardPath() + "/Andorid/data/" + getPackageName() + "/Images ";
        File cacheDir = new File(cacheDirPath);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        long maxSize = Runtime.getRuntime().maxMemory() / 8;
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(new Cache(cacheDir, maxSize))//修改缓存文件夹
                .retryOnConnectionFailure(true) //添加重试机制
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Response response = chain.proceed(request);
                        int tryCount = 0;
                        while (!response.isSuccessful() && tryCount < MAX_RETRY_COUNT) {
                            Log.d("Picasso Request", " 请求失败：正在重试请求，" + tryCount);
                            tryCount++;
                            // 重新请求
                            response = chain.proceed(request);
                        }
                        return response;
                    }
                })
                .build();

        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(client))
                .build();
        Picasso.setSingletonInstance(picasso);
    }

    /**
     * 获取内置SD卡路径
     *
     * @return
     */
    public String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }
}
