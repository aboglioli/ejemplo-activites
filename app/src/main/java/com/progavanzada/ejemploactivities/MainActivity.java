package com.progavanzada.ejemploactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.progavanzada.ejemploactivities.db.DB;
import com.progavanzada.ejemploactivities.messages.Messages;
import com.progavanzada.ejemploactivities.models.Usuario;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Laboratorio de Programación Avanzada", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Inicializa la DB
        this.db = new DB(MainActivity.this);

        // Crea usuario por defecto
        boolean existingUser = this.db.checkUsuario("admin@admin.com");
        if(!existingUser) {
            Usuario admin = new Usuario("Admin", "admin@admin.com", "admin");
            this.db.agregarUsuario(admin);
        }

        TextView userCount = (TextView) findViewById(R.id.userCount);

        List<Usuario> users = this.db.getAllUsuario();
        userCount.setText("Existen " + users.size() + " usuarios");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * El usuario se loguea con
     * usuario: user
     * contraseña: passwd
     * @param view
     */
    public void login(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);

        EditText userText = (EditText) findViewById(R.id.emailText);
        EditText passwordText = (EditText) findViewById(R.id.passwordText);

        String user = userText.getText().toString();
        String password = passwordText.getText().toString();

        boolean correctUser = this.db.checkUsuario(user, password);

        if(correctUser) {
            intent.putExtra(Messages.MAIN_MESSAGE, user);
            startActivity(intent);
        } else {
            Snackbar.make(view, "Usuario o contraseña incorrectos", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
