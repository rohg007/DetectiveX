package com.example.android.hideseek;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.hideseek.AccountActivity.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ListView listViewLostFound;
    DatabaseReference databaseReference;
    public List<Details> detailsList;
    FirebaseAuth auth;
    String lfStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseReference = FirebaseDatabase.getInstance().getReference("LostFoundDetails");
        listViewLostFound = findViewById(R.id.list_view_lost_found);
        detailsList = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        Toast.makeText(getApplicationContext(), "Logged In as " + auth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

        //If the list view is empty
        TextView empty_case = findViewById(android.R.id.empty);
        listViewLostFound.setEmptyView(empty_case);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //When Fab is clicked the Add Form is called
                Intent intent = new Intent(MainActivity.this, AddForm.class);
                startActivity(intent);
            }
        });

        listViewLostFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Gets the current position on List View and sends an intent to open Description Activity
                Details currDetails = detailsList.get(position);
                Intent intent = new Intent(MainActivity.this, IssueDescription.class);
                intent.putExtra("DETAILS", currDetails);
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
                    if (details.getmVisibililty().equals("YES")) {
                        detailsList.add(details);
                        lfStatus="Lost";
                    }
                    else if(details.getmVisibililty().equals("YES")) {
                        detailsList.add(details);
                        lfStatus="Found";
                    }
                }
                //Sets the adapter to Details Adapter for our custom list
                    DetailsAdapter detailsAdapter = new DetailsAdapter(MainActivity.this, detailsList);
                    listViewLostFound.setAdapter(detailsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getCode() + ": " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
            Toast.makeText(getApplicationContext(), "Successfully Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.my_issues) {
            Intent intent = new Intent(MainActivity.this, Personal_Activity.class);
            startActivity(intent);
        } else if (id==R.id.dev_info){
            Intent intent = new Intent(MainActivity.this,InfoActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
