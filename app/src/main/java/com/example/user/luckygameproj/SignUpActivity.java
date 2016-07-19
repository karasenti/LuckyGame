package com.example.user.luckygameproj;

import android.content.Intent;

import android.database.sqlite.SQLiteDatabase;
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
import com.google.firebase.auth.UserProfileChangeRequest;



public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button btSave;
    private EditText  etName, etEmail, etPassw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


       btSave =(Button)findViewById(R.id.btSave);

       etName =(EditText) findViewById(R.id.etName);
       etEmail =(EditText) findViewById(R.id.etEmail);
       etPassw=(EditText) findViewById(R.id.etPass);
       auth=FirebaseAuth.getInstance();


       btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=etEmail.getText().toString();
                String passw=etPassw.getText().toString();
                creatAcount(email,passw);

            }  });   }

    private FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user=firebaseAuth.getCurrentUser();
            if(user!=null)
            {
                Toast.makeText(SignUpActivity.this, "user is signed in.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(SignUpActivity.this, "user signed out.", Toast.LENGTH_SHORT).show();
            }
        }    };

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);     }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null)
            auth.removeAuthStateListener(authStateListener);     }


    private void updateUserProfile(FirebaseUser user)
    {
        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
        builder.setDisplayName(etName.getText().toString());
        UserProfileChangeRequest profileUpdate=builder.build();
        user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(SignUpActivity.this, "profileUpdate Successful.",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "profileUpdate failed."+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            task.getException().printStackTrace();
                }
            }
        });
    }

    private void creatAcount(String email, String passw)
    {
        auth.createUserWithEmailAndPassword(email,passw).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //Toast.makeText(SignUpActivity.this, "Authentication Successful.", 											Toast.LENGTH_SHORT).show();
                    updateUserProfile(task.getResult().getUser());
                    Intent i = new Intent("MyCustomIntent");
                    i.putExtra("message", (CharSequence)etName.getText().toString());
                    i.setAction("com.example.user.luckygameproj.A_CUSTOM_INTENT");
                    sendBroadcast(i);
                    Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Authentication failed."+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                }
            }
        });
    }

}
