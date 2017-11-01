package spa.lyh.cn.andfix.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import spa.lyh.cn.andfix.R;
import spa.lyh.cn.andfix.andfix.AndFixPatchManager;
import spa.lyh.cn.andfix.andfix.AndFixService;
import spa.lyh.cn.andfix.util.Utils;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_END = ".apatch";
    private String mPatchDir = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /*mPatchDir = getExternalCacheDir().getAbsolutePath()+"/apatch/";
        //是为了创建我们的文件夹
        File file = new File(mPatchDir);
        if (!file.exists()){
            file.mkdir();
        }*/

        startPatchService();
    }

    private void startPatchService(){
        Intent intent = new Intent(this, AndFixService.class);
        startService(intent);
    }


    public void createBug(View v) {
        Utils.printLog();
    }

    public void fixBug(View v) {
        String path = getPatchName();
        if (!(path.equals(""))){
            String info = AndFixPatchManager.getInstance().addPatch(getPatchName());
            Toast.makeText(this,info,Toast.LENGTH_SHORT).show();
        }
    }

    private String getPatchName() {
        if (mPatchDir.equals("")){
            return "";
        }else {
            return mPatchDir.concat("imooc").concat(FILE_END);
        }
    }
}
