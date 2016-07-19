package com.example.user.luckygameproj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ListScoreActivity extends AppCompatActivity {

    private String[] arr;
    private ListView listView;
    private DatabaseReference reference;
    private Player p;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_score);

        listView=(ListView)findViewById(R.id.listView);

        String email= FirebaseAuth.getInstance().getCurrentUser().getEmail().replace('.','_');
        reference= FirebaseDatabase.getInstance().getReference(email);

        reference.child("Player").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arr=new String[(int) dataSnapshot.getChildrenCount()];
                int i=0;
                for (DataSnapshot dst:dataSnapshot.getChildren())
                {
                    p = dst.getValue(Player.class);
                    p.setPlayKey(dst.getKey());
                    arr[i]=p.toString();
                    i++;
                }
                adapter = new ArrayAdapter<String>(ListScoreActivity.this,android.R.layout.simple_list_item_1,arr);
                listView.setAdapter(adapter);
               // Toast.makeText(ListScoreActivity.this,i+" ",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        }
}
