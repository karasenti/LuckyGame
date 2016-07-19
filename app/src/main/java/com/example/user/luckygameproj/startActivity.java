package com.example.user.luckygameproj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import org.w3c.dom.Text;

import java.util.Calendar;

public class startActivity extends AppCompatActivity {

    private Button btRollNum, btSaveScore, btnListScore;
    private TextView t1,t2,t3, tresul;
    private ImageView img;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        t1=(TextView)findViewById(R.id.tvResNum1) ;
        t2=(TextView)findViewById(R.id.tvResNum2) ;
        t3=(TextView)findViewById(R.id.tvResNum3) ;
        tresul=(TextView)findViewById(R.id.tvResult) ;

        img=(ImageView)findViewById(R.id.imageView);
        img.setImageDrawable(getResources().getDrawable(R.drawable.pic1));

        btRollNum=(Button)findViewById(R.id.btRollNum);
        btSaveScore = (Button)findViewById(R.id.btSaveRes) ;
        btnListScore = (Button)findViewById(R.id.btListScore);


        btnListScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(startActivity.this,ListScoreActivity.class);
                startActivity(intent);
            }
        });


        btSaveScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar  calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                String d = day+"/"+month+"/"+year;
                String t = hour+":"+minute;
                Player p = new Player(d,t,Integer.parseInt(tresul.getText().toString()));

                DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
                String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
                reference.child(email.replace(".","_")).child("Player").push().setValue(p, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError==null)
                        {
                            Toast.makeText(startActivity.this,"Save successful",
                                    Toast.LENGTH_LONG).show();

                        }
                        else
                        {
                            Toast.makeText(startActivity.this,"Save Failed"+databaseError.getMessage(),
                                    Toast.LENGTH_LONG).show();

                        }

                    }
                });
            }
        });


        btRollNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(startActivity.this, HelloService.class));
                Intent intent=new Intent(startActivity.this,StartGameActivity.class);
                startActivityForResult(intent,1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode ==RESULT_OK ){
                t1.setText(data.getStringExtra("n1"));
                t2.setText(data.getStringExtra("n2"));
                t3.setText(data.getStringExtra("n3"));

                tresul.setText(data.getStringExtra("result"));

                int resu=Integer.parseInt(tresul.getText().toString());

                if(resu==-1) {
                    img.setImageDrawable(getResources().getDrawable(R.drawable.pic3));

                }
                if(resu==1){
                    img.setImageDrawable(getResources().getDrawable(R.drawable.pic2));

                }

                if(resu==10)
                {
                    img.setImageDrawable(getResources().getDrawable(R.drawable.pic5));

                }

                if(resu==50){
                    img.setImageDrawable(getResources().getDrawable(R.drawable.pic4));

                }

            }
            if (resultCode ==RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Nothing Returned!", Toast.LENGTH_SHORT).show();            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_start_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mnitem1){
            Intent intent = new Intent(startActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
}
