package com.pocketassist.qinnig;

/**
 * Created by qinnig on 2015/5/31.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent i = new Intent(context, MainService.class);
            context.startService(i);
        }
    }
}
