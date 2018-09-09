package com.example.android.hideseek;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IssueDescription extends AppCompatActivity {


    DatabaseReference databaseReference;
    private String number,name,email,objectType,lostFound,image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_description);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        TextView NameTextView = findViewById(R.id.description_name_text_view);
        TextView ContactTextView = findViewById(R.id.description_number_text_view);
        TextView DescriptionTextView = findViewById(R.id.description_description_text_view);
        TextView EmailTextView = findViewById(R.id.description_email_text_view);
        Button ContactViaPhone = findViewById(R.id.contact_via_phone_button);
        Button ContactViaEmail = findViewById(R.id.contact_via_email_button);
        ImageView descriptionImageView = findViewById(R.id.description_image_view);
        Button resolveButton = findViewById(R.id.resolve_button);

        Intent intent = getIntent();
        final Details details = (Details) intent.getExtras().get("DETAILS");

        if(details != null) {
            name = details.getmName();
            number = details.getmContactNumber();
            email = details.getmEmail();
            String description = details.getmDescription();
            objectType = details.getmObjectType();
            lostFound = details.getLostFound();
            image = details.getmImageUrl();

            NameTextView.setText(name);
            ContactTextView.setText(number);
            EmailTextView.setText(email);
            DescriptionTextView.setText(description);
            /*
                Setting Images to the Image View
            */
            if(image!=null)
            {
                Glide.with(this).load(image).into(descriptionImageView);
            }
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

        resolveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                                databaseReference.child("LostFoundDetails").child(details.getmId()).child("mVisibililty").setValue("NO");
                                TextView resolvedTextView = findViewById(R.id.issue_resolved_text_view);
                                resolvedTextView.setText("RESOLVED");
                                resolvedTextView.setVisibility(View.VISIBLE);
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
//