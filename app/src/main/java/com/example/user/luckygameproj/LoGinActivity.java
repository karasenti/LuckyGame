package com.example.user.luckygameproj;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoGinActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText    etEmail, etPass;
    private Button btStart, btReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lo_gin);

        auth=FirebaseAuth.getInstance();
        etEmail=(EditText)findViewById(R.id.etEmail);
        etPass=(EditText)findViewById(R.id.etPassword);
        btReturn=(Button)findViewById(R.id.btReturnLogin);
        btStart=(Button)findViewById(R.id.btnStart);


        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoGinActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=etEmail.getText().toString();
                String passw=etPass.getText().toString();
                signIn(email,passw);

            }
        });
    }
    private FirebaseAuth.AuthStateListener authStateListener=new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user=firebaseAuth.getCurrentUser();
            if(user!=null)
            {
               // Toast.makeText(LoGinActivity.this, "user is signed in.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(LoGinActivity.this, "user signed out.", Toast.LENGTH_SHORT).show();
            }         }
    };



    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null)
            auth.removeAuthStateListener(authStateListener);
    }
    private void signIn(String email, String passw) {
        auth.signInWithEmailAndPassword(email,passw).addOnCompleteListener(LoGinActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                     //Toast.makeText(LoGinActivity.this, "signIn Successful.", Toast.LENGTH_SHORT).show();
                     startService(new Intent(LoGinActivity.this, HelloService.class));
                     Intent intent=new Intent(LoGinActivity.this,startActivity.class);
                     startActivity(intent);

                }
                else
                {
                     Toast.makeText(LoGinActivity.this, "signIn failed."+task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                }
            }
        });
    }

}

