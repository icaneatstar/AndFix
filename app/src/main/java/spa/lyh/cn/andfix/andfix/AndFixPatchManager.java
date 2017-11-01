package spa.lyh.cn.andfix.andfix;

import android.content.Context;
import android.widget.Toast;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.FileNotFoundException;
import java.io.IOException;

import spa.lyh.cn.andfix.util.Utils;

/**
 * Created by liyuhao on 2017/6/22.
 * 管理AndFix所有的API
 */

public class AndFixPatchManager {
    private static AndFixPatchManager instance = null;

    private static PatchManager patchManager;



    public static AndFixPatchManager getInstance(){
        if (instance == null){
            synchronized (AndFixPatchManager.class){
                if (instance == null){
                    instance = new AndFixPatchManager();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化AndFix方法
     * @param context
     */
    public void initPatch(Context context){
        patchManager = new PatchManager(context);
        patchManager.init(Utils.getVersionName(context));
        patchManager.loadPatch();
    }


    /**
     * 加载我们的patch文件
     * @param path
     */
    public String addPatch(String path){
        String info = "";
        try{
            if (patchManager != null){
                patchManager.addPatch(path);
                info = "成功";
            }
        }catch (FileNotFoundException e){
            info = "未找到文件";
        }catch (IOException e){
            e.printStackTrace();
        }
        return info;
    }
}
