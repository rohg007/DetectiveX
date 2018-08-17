package com.example.android.hideseek;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IssueDescription extends AppCompatActivity {

    private TextView NameTextView, ContactTextView, DescriptionTextView, EmailTextView;
    DatabaseReference databaseReference;
    private String number,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_description);

        //Getting Firebase Database Instance
        databaseReference = FirebaseDatabase.getInstance().getReference("LostFoundDetails");

        //Indexing all elements
        NameTextView = findViewById(R.id.description_name_text_view);
        ContactTextView = findViewById(R.id.description_number_text_view);
        DescriptionTextView = findViewById(R.id.description_description_text_view);
        EmailTextView = findViewById(R.id.description_email_text_view);
        Button ContactViaPhone = findViewById(R.id.contact_via_phone_button);
        Button ContactViaEmail = findViewById(R.id.contact_via_email_button);

        //Getting dataSnapshot to extract the details from Firebase database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot lostFoundSnapshot: dataSnapshot.getChildren()){
                    Details details = lostFoundSnapshot.getValue(Details.class);

                    String name = details.getmName();
                    number = details.getmContactNumber();
                    email = details.getmEmail();
                    String description = details.getmDescription();


                    NameTextView.setText(name);
                    ContactTextView.setText(number);
                    EmailTextView.setText(email);
                    DescriptionTextView.setText(description);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ContactViaPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialPhoneNumber(number);
            }
        });

        ContactViaEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
/*
Dials the phone number
 */
    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
