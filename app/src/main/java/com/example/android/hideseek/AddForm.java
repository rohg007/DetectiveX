package com.example.android.hideseek;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddForm extends AppCompatActivity {

    private RadioButton LostRadioButton, FoundRadioButton;
    private EditText NameEditText, ContactNumberEditText, EmailEditText, ObjectTypeEditText, DescriptionEditText;
    private DatabaseReference databaseReference;
    private static final int PICK_IMAGE_REQUEST = 2;
    private static final int REQUEST_CODE = 500;
    private ImageView browseImageView;
    private Uri mImageUri;
    private StorageReference storageReference;
    private ProgressBar imageProgressBar;
    private String imageUrl;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);


        //findViewByIding the elements

        LostRadioButton = findViewById(R.id.lost_radio_button);
        FoundRadioButton = findViewById(R.id.found_radio_button);
        Button UploadImageButton = findViewById(R.id.upload_button);
        Button SubmitButton = findViewById(R.id.submit_button);
        Button CreateMailButton = findViewById(R.id.create_mail_button);
        TextView BrowseTextView = findViewById(R.id.browse_text_view);
        NameEditText = findViewById(R.id.name_edit_text);
        ContactNumberEditText = findViewById(R.id.contact_number_edit_text);
        EmailEditText = findViewById(R.id.email_edit_text);
        ObjectTypeEditText = findViewById(R.id.object_type_edit_text);
        DescriptionEditText = findViewById(R.id.description_edit_text);
        TextView ResetTextView = findViewById(R.id.reset_text_view);
        browseImageView = findViewById(R.id.optional_image_view);
        imageProgressBar = findViewById(R.id.image_upload_progress_bar);

        auth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("LostFoundDetails");
        storageReference = FirebaseStorage.getInstance().getReference("LostFoundDetails");

        EmailEditText.setText(auth.getCurrentUser().getEmail());
        String email = auth.getCurrentUser().getEmail();
        final String domain = email.substring(email.indexOf('@')+1);

        //When Upload Button is clicked
        UploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        //When Browse Image is clicked
        BrowseTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        //When Submit Button is clicked
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(domain.equals("lnmiit.ac.in"))
                    addDetailsToFirebase();
                else
                    Toast.makeText(getApplicationContext(),"You're not logged in with LNMIIT domain\n Please login or signup using LNMIIT Id",Toast.LENGTH_LONG).show();
            }
        });
        //When Create Mail Button is clicked
        CreateMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderMail();
            }
        });

        ResetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetForm();
            }
        });
    }

    /*
    A function to get all details in appropriate format
     */
    private Details getDetails() {
        Details details;
        String name = NameEditText.getText().toString().trim();
        String contactNumber = ContactNumberEditText.getText().toString().trim();
        String email = EmailEditText.getText().toString().trim();
        String objectType = ObjectTypeEditText.getText().toString().trim();
        String description = DescriptionEditText.getText().toString().trim();
        RadioButtonStatus();
        //Binding all details
        details = new Details(RadioButtonStatus(), name, contactNumber, objectType, description, email);
        return details;
    }

    /*
    Adds the Details of form to firebase database
     */
    private void addDetailsToFirebase() {
        try {
            if (!TextUtils.isEmpty(getDetails().getmName()) && !TextUtils.isEmpty(getDetails().getmContactNumber()) && !TextUtils.isEmpty(getDetails().getmDescription()) && !TextUtils.isEmpty(getDetails().getmObjectType()) && !TextUtils.isEmpty(getDetails().getmEmail()) && !TextUtils.isEmpty(getDetails().getmDescription()) && browseImageView.getDrawable() == null) {
                //Storing the Id
                String id = databaseReference.push().getKey();
                Details details1 = new Details(id, getDetails().getLostFound(), getDetails().getmName(), getDetails().getmContactNumber(), getDetails().getmObjectType(), getDetails().getmDescription(), getDetails().getmEmail(), "NO","NO","NO");
                //Now saving the details in Real time Database under id node
                databaseReference.child(id).setValue(details1);
                //if task is successful display toast message
                Toast.makeText(getApplicationContext(), "Details Successfully Uploaded", Toast.LENGTH_SHORT).show();
            } else if (!TextUtils.isEmpty(getDetails().getmName()) && !TextUtils.isEmpty(getDetails().getmContactNumber()) && !TextUtils.isEmpty(getDetails().getmDescription()) && !TextUtils.isEmpty(getDetails().getmObjectType()) && !TextUtils.isEmpty(getDetails().getmEmail()) && !TextUtils.isEmpty(getDetails().getmDescription()) && browseImageView.getDrawable() != null) {
                //Storing the Id
                String id = databaseReference.push().getKey();
                Details details1 = new Details(id, getDetails().getLostFound(), getDetails().getmName(), getDetails().getmContactNumber(), getDetails().getmObjectType(), getDetails().getmDescription(), getDetails().getmEmail(), imageUrl, "NO","NO","NO");
                //Now saving the details in Real time Database under id node
                databaseReference.child(id).setValue(details1);
                //if task is successful display toast message
                Toast.makeText(getApplicationContext(), "Details Successfully Uploaded", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Fill all the mandatory fields\n One or more mandatory field is blank", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception exception) {
            //if task fails catch the exception and display in toast message
            Toast.makeText(getApplicationContext(), "Upload Unsuccessful" + exception.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /*
    This method Resets the form to initial state
     */

    private void ResetForm() {
        LostRadioButton.setChecked(false);
        FoundRadioButton.setChecked(false);
        NameEditText.setText("");
        ContactNumberEditText.setText("");
        ObjectTypeEditText.setText("");
        DescriptionEditText.setText("");
        browseImageView.setImageResource(0);
    }

    /*
    Gives the Status of which Radio Button is selected
     */
    private String RadioButtonStatus() {
        String LostFound = "";
        //on Radio Button Selected Status
        if (LostRadioButton.isChecked()) {
            LostFound = "Lost";
        } else if (FoundRadioButton.isChecked()) {
            LostFound = "Found";
        }
        Details details = new Details();
        details.setLostFound(LostFound);
        return LostFound;
    }

    /*
    Creates and Email Intent when Create Email Button is clicked
     */
    private void orderMail() {
        String body = "";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:"));
        if (RadioButtonStatus().equals("Lost")) {
            body = "Sir,\n Please forward this to all UG & PG Groups.\n\nI've lost my " + ObjectTypeEditText.getText().toString().trim() + ". It is a " + DescriptionEditText.getText().toString().trim() + "\n\n Anyone who finds it may contact the undersigned\n\n" + NameEditText.getText().toString().trim() + "\n" + ContactNumberEditText.getText().toString().trim();
        } else if (RadioButtonStatus().equals("Found")) {
            body = "Sir,\n Please forward this to all UG & PG Groups.\n\nI've Found a " + ObjectTypeEditText.getText().toString().trim() + ". It is a " + DescriptionEditText.getText().toString().trim() + "\n\n To collect it you may contact the undersigned\n\n" + NameEditText.getText().toString().trim() + "\n" + ContactNumberEditText.getText().toString().trim();
        }
        //Setting the subject of mail
        intent.putExtra(Intent.EXTRA_SUBJECT, ObjectTypeEditText.getText().toString().trim() + " " + RadioButtonStatus());
        //Setting the body of mail
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /*
    Shows Images in the device when Browse text view is clicked
     */
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST) {
            mImageUri = data.getData();
            browseImageView.setImageURI(mImageUri);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        if (mImageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    imageProgressBar.setProgress(0);
                                    imageProgressBar.setVisibility(View.GONE);
                                }
                            }, 5000);
                            Toast.makeText(getApplicationContext(), "Image Upload Successful", Toast.LENGTH_SHORT).show();
                            Details details = new Details();
                            imageUrl = taskSnapshot.getDownloadUrl().toString();
                            details.setmImageUrl(imageUrl);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            imageProgressBar.setVisibility(View.VISIBLE);
                            double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            imageProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }
}
