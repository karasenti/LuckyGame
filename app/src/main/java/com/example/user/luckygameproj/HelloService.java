package com.example.user.luckygameproj;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by User on 16-Jul-16.
 */
public class HelloService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public HelloService() {
    }

    @Override
    public void onCreate() {
        //super.onCreate();
        Toast.makeText(this, "Welcome To Lucky Game", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        //super.onDestroy();
        Toast.makeText(this, "Successfully", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Try Your Luck", Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }
}
