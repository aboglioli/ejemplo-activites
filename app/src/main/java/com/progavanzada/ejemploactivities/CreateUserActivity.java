package com.progavanzada.ejemploactivities;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.progavanzada.ejemploactivities.db.DB;
import com.progavanzada.ejemploactivities.messages.Messages;
import com.progavanzada.ejemploactivities.models.Usuario;

public class CreateUserActivity extends AppCompatActivity {
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        this.db = new DB(CreateUserActivity.this);
    }

    public void createUser(View view) {
        EditText emailText = (EditText) findViewById(R.id.newUserEmail);
        EditText nameText = (EditText) findViewById(R.id.newUserName);
        EditText passwordText = (EditText) findViewById(R.id.newUserPassword);

        String email = emailText.getText().toString();
        String name = nameText.getText().toString();
        String password = passwordText.getText().toString();

        boolean existingUser = this.db.checkUsuario(email);

        if(email.isEmpty() || name.isEmpty() || password.isEmpty()) {
            Snackbar.make(view, "Hay campos en blanco", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else if(existingUser) {
            Snackbar.make(view, "El usuario ya existe", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Usuario newUser = new Usuario(name, email, password);
            this.db.agregarUsuario(newUser);

            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra(Messages.CREATE_USER_MESSAGE, newUser.getNombre());
            startActivity(intent);
        }
    }
}
