package jianqiang.com.receiverhook;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import jianqiang.com.receiverhook.classloder_hook.BaseDexClassLoaderHookHelper;

public class MainActivity extends Activity {

    static final String apkName = "receivertest.apk";
    static final String dexName = "receivertest.dex";

    BroadcastReceiver receiver3 = null;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);

        //解压到本地
        Utils.extractAssets(this, apkName);

        File dexFile = getFileStreamPath(apkName);
        File optDexFile = getFileStreamPath(dexName);

        try {
            BaseDexClassLoaderHookHelper.patchClassLoader(getClassLoader(), dexFile, optDexFile);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationInfo();
        Button t = new Button(this);
        setContentView(t);
        t.setText("send broadcast to plugin: demo");
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "插件插件!收到请回答!!", Toast.LENGTH_SHORT).show();
                sendBroadcast(new Intent("baobao3"));
            }
        });

        // 注册插件收到我们发送的广播之后, 回传的广播
        receiver3 = (BroadcastReceiver)RefInvoke.createObject("jianqiang.com.receivertest.MyReceiver3");
        registerReceiver(receiver3, new IntentFilter("baobao3"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver3);
    }
}
