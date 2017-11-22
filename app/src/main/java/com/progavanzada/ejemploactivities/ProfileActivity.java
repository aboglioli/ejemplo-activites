package com.progavanzada.ejemploactivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.progavanzada.ejemploactivities.config.Config;
import com.progavanzada.ejemploactivities.db.DB;
import com.progavanzada.ejemploactivities.messages.Messages;
import com.progavanzada.ejemploactivities.models.Usuario;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private DB db;
    private SharedPreferences settings;

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

        this.db = new DB(ProfileActivity.this);
        settings = getSharedPreferences(Config.PREFS_FILE, 0);

        // Muestra el usuario (dato que proviene de MainActivity) y un nombre
        Intent intent = getIntent();
        boolean hasMainMessage = intent.hasExtra(Messages.MAIN_MESSAGE);
        boolean hasCreateUserMessage = intent.hasExtra(Messages.CREATE_USER_MESSAGE);

        if(hasMainMessage) {
            String userEmail = intent.getStringExtra(Messages.MAIN_MESSAGE);
            Usuario user = this.db.getUserByEmail(userEmail);

            Log.d("ProfileActivity", "Mostrando " + user.getEmail());

            this.showProfile(user);

            // Guarda email para poder volver a este Activity luego de crear
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("email", user.getEmail());
            editor.commit();
        } else {
            String email = settings.getString("email", "");

            Log.d("ProfileActivity", "Email desde SharedPreferences: " + email);

            if(!email.isEmpty()) {
                Usuario user = this.db.getUserByEmail(email);
                this.showProfile(user);
            }
        }

        // Usuario creado
        if(hasCreateUserMessage) {
            String createdUser = intent.getStringExtra(Messages.CREATE_USER_MESSAGE);

            if(!createdUser.isEmpty()) {
                TextView lastActionTextView = findViewById(R.id.lastActionTextView);
                lastActionTextView.setText("Se creó un nuevo usuario: " + createdUser);
            }
        }

    }

    public void createUser(View view) {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }

    private void showProfile(Usuario user) {
        TextView emailTextView = findViewById(R.id.emailTextView);
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView idTextView = findViewById(R.id.idTextView);

        emailTextView.setText(user.getEmail());
        nameTextView.setText(user.getNombre());
        idTextView.setText(String.valueOf(user.getId()));
    }

}
