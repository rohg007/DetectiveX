package com.example.android.hideseek;

import android.content.Intent;
import android.net.Uri;
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
    private String number,name,email,description,objectType,lostFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_description);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        NameTextView = findViewById(R.id.description_name_text_view);
        ContactTextView = findViewById(R.id.description_number_text_view);
        DescriptionTextView = findViewById(R.id.description_description_text_view);
        EmailTextView = findViewById(R.id.description_email_text_view);
        Button ContactViaPhone = findViewById(R.id.contact_via_phone_button);
        Button ContactViaEmail = findViewById(R.id.contact_via_email_button);

        Intent intent = getIntent();
        Details details = (Details) intent.getExtras().get("DETAILS");

        if(details != null) {
            name = details.getmName();
            number = details.getmContactNumber();
            email = details.getmEmail();
            description = details.getmDescription();
            objectType = details.getmObjectType();
            lostFound = details.getLostFound();

            NameTextView.setText(name);
            ContactTextView.setText(number);
            EmailTextView.setText(email);
            DescriptionTextView.setText(description);
        }

        ContactViaPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialerActivity(number);
            }
        });

        ContactViaEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMailOnClick();
            }
        });
    }
    /*
    Opens the dialer activity when the contactViaPhone Button is clicked
     */
    private void openDialerActivity(String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
    /*
    Opens Mail Activity when contactViaEmail Button is clicked
    */
    private void createMailOnClick(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:"+email));
        String body = "Regards,\n"+name+"\n"+number;
        intent.putExtra(Intent.EXTRA_TEXT,body);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Regarding "+lostFound+" "+objectType);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
