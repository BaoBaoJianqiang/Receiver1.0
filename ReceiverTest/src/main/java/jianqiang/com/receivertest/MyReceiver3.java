package jianqiang.com.receivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver3 extends BroadcastReceiver {

    public MyReceiver3() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,
                "接收到300万" + intent.getAction() + intent.getStringExtra("msg"),
                Toast.LENGTH_LONG).show();
    }
}
