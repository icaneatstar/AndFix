package spa.lyh.cn.andfix.application;

import android.app.Application;

import spa.lyh.cn.andfix.andfix.AndFixPatchManager;

/**
 * Created by liyuhao on 2017/6/22.
 */

public class ImoocApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //完成andFix模块的初始化
        initAndFix();
    }

    private void initAndFix(){
        AndFixPatchManager.getInstance().initPatch(this);
    }
}
