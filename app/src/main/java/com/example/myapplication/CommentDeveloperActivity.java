package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class CommentDeveloperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_developer);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    public void newIntent(View view) {
        CharSequence contentDescription = view.getContentDescription();
        Intent i = new Intent(Intent.ACTION_VIEW);
        String url = "";
        if ("github".equals(contentDescription)) {
            url = "https://github.com/Zhandos-Hello-World";
        } else if ("telegram".equals(contentDescription)) {
            url = "https://t.me/CatManHero";
        } else if ("gmail".equals(contentDescription)) {
            url = "https://mail.google.com/mail/u/0/#inbox?compose=GTvVlcRwRdsBZSxDVggLQWLcqrLWcqsnXbTVcJzWSFDjkNchzprVxfcwsHJbGCDQpkxzqDPmMRXfn";
        } else {
            url = "https://www.instagram.com/dzhbaimurat/";
        }
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
