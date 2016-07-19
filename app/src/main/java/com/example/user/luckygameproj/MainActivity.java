package com.example.user.luckygameproj;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RadioButton rdAbout, rdLogin, rdSignup;
    private Button btExit;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = (TextView)findViewById(R.id.tvTitle);
        rdAbout = (RadioButton)findViewById(R.id.rdAbout);
        rdLogin = (RadioButton)findViewById(R.id.rdLogin);
        rdSignup = (RadioButton)findViewById(R.id.rdSignUp);
        btExit = (Button)findViewById(R.id.btExitGame);

        Animation translatebu= AnimationUtils.loadAnimation(this, R.anim.rotate);
        tvTitle.startAnimation(translatebu);

        rdAbout.setChecked(false);
        rdLogin.setChecked(false);
        rdSignup.setChecked(false);


        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setMessage("Are you sure you want to Exit !!! ");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });


        rdAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });

        rdSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });


        rdLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoGinActivity.class);
                startActivity(intent);
            }
        });

        }

    @Override
    protected void onResume() {
        super.onResume();
        rdAbout.setChecked(false);
        rdLogin.setChecked(false);
        rdSignup.setChecked(false);

    }


}
