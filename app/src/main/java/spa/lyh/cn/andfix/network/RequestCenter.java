package spa.lyh.cn.andfix.network;


import spa.lyh.cn.andfix.andfix.module.BasePatch;
import spa.lyh.cn.andfix.network.listener.DisposeDataHandle;
import spa.lyh.cn.andfix.network.listener.DisposeDataListener;
import spa.lyh.cn.andfix.network.listener.DisposeDownloadListener;
import spa.lyh.cn.andfix.network.request.CommonRequest;
import spa.lyh.cn.andfix.network.request.RequestParams;

/**
 * Created by renzhiqiang on 17/4/24.
 *
 * @function 请求发送中心
 */
public class RequestCenter {

    //根据参数发送所有post请求
    public static void postRequest(String url, RequestParams params, DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url, params), new DisposeDataHandle(listener, clazz));
    }


    /**
     * 询问是否有patch可更新
     *
     * @param listener
     */
    public static void requestPatchUpdateInfo(DisposeDataListener listener) {
        RequestCenter.postRequest(HttpConstant.UPDATE_PATCH_URL, null, listener,
                BasePatch.class);

    }

    /**
     * 文件下载
     * @param url
     * @param path
     * @param listener
     */
    public static void downloadFile(String url, String path, DisposeDownloadListener listener) {
        CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest(url, null),
                new DisposeDataHandle(listener, path));
    }
}
