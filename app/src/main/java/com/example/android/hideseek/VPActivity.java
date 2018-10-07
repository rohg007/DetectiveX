package com.example.android.hideseek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VPActivity extends AppCompatActivity {

    ListView listViewLostFound;
    DatabaseReference databaseReference;
    List<Details> detailsList;
    FirebaseAuth auth;
    public static final int activity = 1003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp);

        databaseReference = FirebaseDatabase.getInstance().getReference("LostFoundDetails");
        listViewLostFound = findViewById(R.id.list_view_lost_found);
        detailsList = new ArrayList<>();
        auth = FirebaseAuth.getInstance();

        TextView empty_case = findViewById(android.R.id.empty);
        listViewLostFound.setEmptyView(empty_case);

        listViewLostFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Gets the current position on List View and sends an intent to open Description Activity
                Details currDetails = detailsList.get(position);
                Intent intent = new Intent(VPActivity.this, IssueDescription.class);
                intent.putExtra("DETAILS", currDetails);
                intent.putExtra("calling-activity",activity);
                startActivity(intent);
            }
        });
        getData();
    }
    private void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                detailsList.clear();
                //Data Snapshot to get the data from Firebase Database
                for (DataSnapshot lostFoundSnapshot : dataSnapshot.getChildren()) {
                    Details details = lostFoundSnapshot.getValue(Details.class);
                    if(details.getmApproved().equals("NO"))
                        detailsList.add(details);
                }
                //Sets the adapter to Details Adapter for our custom list
                DetailsAdapter detailsAdapter = new DetailsAdapter(VPActivity.this, detailsList);
                listViewLostFound.setAdapter(detailsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getCode() + ": " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
