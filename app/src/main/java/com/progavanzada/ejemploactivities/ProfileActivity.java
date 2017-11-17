package com.progavanzada.ejemploactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Logueado", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Muestra el usuario (dato que proviene de MainActivity) y un nombre
        Intent intent = getIntent();
        String user = intent.getStringExtra(MainActivity.USER_MESSAGE);

        TextView userTextView = findViewById(R.id.userTextView);
        TextView nameTextView = findViewById(R.id.nameTextView);

        userTextView.setText(user);
        nameTextView.setText("Alan Boglioli");
    }

}
