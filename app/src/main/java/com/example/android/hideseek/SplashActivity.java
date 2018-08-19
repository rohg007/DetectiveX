package com.example.android.hideseek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.widget.ListView;
import android.widget.Toast;



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME= 4000;

    ListView listViewLostFound;
    DatabaseReference databaseReference;
    List<Details> detailsList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        databaseReference = FirebaseDatabase.getInstance().getReference("LostFoundDetails");
        listViewLostFound = findViewById(R.id.list_view_lost_found);
        detailsList = new ArrayList<>();

        //Code to start timer and take action after the timer ends
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do any action here. Now we are moving to next page
                Intent mySuperIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mySuperIntent);
                /* This 'finish()' is for exiting the app when back button pressed
                 *  from Home page which is MainActivity
                 */
                finish();
            }
        }, SPLASH_TIME);
    }



    private void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                detailsList.clear();
                //Data Snapshot to get the data from Firebase Database
                for(DataSnapshot lostFoundSnapshot: dataSnapshot.getChildren()){
                    Details details = lostFoundSnapshot.getValue(Details.class);
                    detailsList.add(details);
                }
                //Sets the adapter to Details Adapter for our custom list
                DetailsAdapter detailsAdapter = new DetailsAdapter(SplashActivity.this, detailsList);
                listViewLostFound.setAdapter(detailsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getCode()+": "+databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
