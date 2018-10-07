package com.example.android.hideseek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ImageView rohan = findViewById(R.id.rohan);
        ImageView shubham = findViewById(R.id.shubham);
        ImageView divyansh = findViewById(R.id.divyansh);

        Glide.with(this).load(R.drawable.rohan_gupta).into(rohan);
        Glide.with(this).load(R.drawable.shubham_buccha).into(shubham);
        Glide.with(this).load(R.drawable.divyansh_singh).into(divyansh);
    }
}
