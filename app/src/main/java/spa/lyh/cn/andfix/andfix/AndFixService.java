package spa.lyh.cn.andfix.andfix;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;


import java.io.File;

import spa.lyh.cn.andfix.andfix.module.BasePatch;
import spa.lyh.cn.andfix.network.RequestCenter;
import spa.lyh.cn.andfix.network.listener.DisposeDataListener;
import spa.lyh.cn.andfix.network.listener.DisposeDownloadListener;

/**
 * Created by renzhiqiang on 17/4/24.
 *
 * @function: 1.检查patch文件 2.下载patch文件 3.加载下载好的patch文件
 */
public class AndFixService extends Service {
    private static final String TAG = AndFixService.class.getSimpleName();
    private static final String FILE_END = ".apatch";
    private static final int UPDATE_PATCH = 0x02;
    private static final int DOWNLOAD_PATCH = 0x01;

    private String mPatchFileDir;
    private String mPatchFile;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PATCH:
                    checkPatchUpdate();
                    break;
                case DOWNLOAD_PATCH:
                    downloadPatch();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler.sendEmptyMessage(UPDATE_PATCH);
        return START_NOT_STICKY;
    }

    //完成文件目录的构造
    private void init() {

        mPatchFileDir = getExternalCacheDir().getAbsolutePath() + "/apatch/";
        File patchDir = new File(mPatchFileDir);

        try {
            if (!patchDir.exists()) {
                patchDir.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
            stopSelf();
        }
    }

    private BasePatch mBasePatchInfo;

    //检查服务器是否有patch文件
    private void checkPatchUpdate() {
        RequestCenter.requestPatchUpdateInfo(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                mBasePatchInfo = (BasePatch) responseObj;
                if (!TextUtils.isEmpty(mBasePatchInfo.data.downloadUrl)) {
                    //下载patch文件
                    mHandler.sendEmptyMessage(DOWNLOAD_PATCH);
                } else {
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
                stopSelf();
            }
        });
    }

    //完成patch文件的下载
    private void downloadPatch() {
        //初始化patch文件下载路径
        mPatchFile = mPatchFileDir.concat(String.valueOf(System.currentTimeMillis())).
                concat(FILE_END);

        RequestCenter.downloadFile(mBasePatchInfo.data.downloadUrl, mPatchFile,
                new DisposeDownloadListener() {
                    @Override
                    public void onProgress(int progrss) {
                        Log.d(TAG, "current progedss: " + progrss);
                    }

                    @Override
                    public void onSuccess(Object responseObj) {
                        //将我们下载好的patch文件添加到我们的andfix中
                        AndFixPatchManager.getInstance().addPatch(mPatchFile);
                    }

                    @Override
                    public void onFailure(Object reasonObj) {
                        stopSelf();
                    }
                });
    }
}







