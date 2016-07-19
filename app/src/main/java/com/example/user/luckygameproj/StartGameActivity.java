package com.example.user.luckygameproj;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class StartGameActivity extends AppCompatActivity {

    private TextView tv1,tv2,tv3;
    private Button  btStop;
    private Timer timer;
    private Game game;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);


         tv1 = (TextView) findViewById(R.id.tvNum1);
         tv2 = (TextView) findViewById(R.id.tvNum2);
         tv3 = (TextView) findViewById(R.id.tvNum3);
         timer = new Timer();
         game = new Game();


        btStop = (Button) findViewById(R.id.btStop);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        game.Roll();

                        tv1.setText(String.valueOf(game.getNum1()));
                        tv2.setText(String.valueOf(game.getNum2()));
                        tv3.setText(String.valueOf(game.getNum3()));


                    }
                });

            }
        }, 0, 100);



        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timer.cancel();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("n1", tv1.getText().toString());
                resultIntent.putExtra("n2", tv2.getText().toString());
                resultIntent.putExtra("n3", tv3.getText().toString());
                resultIntent.putExtra("result",String.valueOf(game.Result()));
                setResult(RESULT_OK, resultIntent);


                String email= FirebaseAuth.getInstance().getCurrentUser().getEmail().replace('.','_');
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference(email);

                reference.child("Player").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int sum=0;
                        for (DataSnapshot dst:dataSnapshot.getChildren())
                        {
                            Player p = dst.getValue(Player.class);
                            p.setPlayKey(dst.getKey());
                            sum=sum+p.getScore();
                        }
                        sum = sum +game.Result();
                        Intent intent = new Intent();
                        PendingIntent pendingIntent = PendingIntent.getActivity(StartGameActivity.this,0,intent,0);
                        Notification notification=new  Notification.Builder(StartGameActivity.this)//build notification
                                .setTicker("Lucky Game") // Ticker title
                                .setContentTitle("Notification Content Titile") //Title
                                .setContentText("Your score Before Save is : " + sum) // Content Title
                                .setSmallIcon(R.drawable.ic_stat_name) // icon
                                .setContentIntent(pendingIntent) //
                                .getNotification();
                        notification.flags = Notification.FLAG_AUTO_CANCEL;
                        NotificationManager notificationManager= (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.notify(0,notification);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                finish();
            }
        });
    }
}


