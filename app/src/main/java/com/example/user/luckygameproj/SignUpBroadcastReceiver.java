package com.example.user.luckygameproj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by User on 16-Jul-16.
 */
public class SignUpBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        CharSequence intentData = intent.getCharSequenceExtra("message");
        Toast.makeText(context, "Welcome "+ intentData +" to Lucky Game ", Toast.LENGTH_LONG).show();
    }
}
