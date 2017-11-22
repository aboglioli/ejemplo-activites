package com.progavanzada.ejemploactivities.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.progavanzada.ejemploactivities.models.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aboglioli on 11/22/17.
 */
public class DB extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DB_NOMBRE = "Usuarios.db";

    // Usuario table name
    private static final String TABLA_USUARIO = "Usuario";

    // Usuario Table Columns names
    private static final String COLUMNA_USUARIO_ID = "UsuarioID";
    private static final String COLUMNA_USUARIO_NOMBRE = "UsuarioNombre";
    private static final String COLUMNA_USUARIO_EMAIL = "UsuarioEmail";
    private static final String COLUMNA_USUARIO_CLAVE = "UsuarioClave";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLA_USUARIO + "("
            + COLUMNA_USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMNA_USUARIO_NOMBRE + " TEXT,"
            + COLUMNA_USUARIO_EMAIL + " TEXT," + COLUMNA_USUARIO_CLAVE + " TEXT" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLA_USUARIO;

    /**
     * Constructor
     *
     * @param context
     */
    public DB(Context context) {
        super(context, DB_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop Usuario Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create usuario record
     *
     * @param usuario
     */
    public void agregarUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMNA_USUARIO_NOMBRE, usuario.getNombre());
        values.put(COLUMNA_USUARIO_EMAIL, usuario.getEmail());
        values.put(COLUMNA_USUARIO_CLAVE, usuario.getClave());

        // Inserting Row
        db.insert(TABLA_USUARIO, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<Usuario> getAllUsuario() {
        // array of columns to fetch
        String[] columns = {
                COLUMNA_USUARIO_ID,
                COLUMNA_USUARIO_EMAIL,
                COLUMNA_USUARIO_NOMBRE,
                COLUMNA_USUARIO_CLAVE
        };
        // sorting orders
        String sortOrder =
                COLUMNA_USUARIO_NOMBRE + " ASC";
        List<Usuario> usuarioList = new ArrayList<Usuario>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLA_USUARIO, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Usuario usuario = new Usuario();
                usuario.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMNA_USUARIO_ID))));
                usuario.setNombre(cursor.getString(cursor.getColumnIndex(COLUMNA_USUARIO_NOMBRE)));
                usuario.setEmail(cursor.getString(cursor.getColumnIndex(COLUMNA_USUARIO_EMAIL)));
                usuario.setClave(cursor.getString(cursor.getColumnIndex(COLUMNA_USUARIO_CLAVE)));
                // Adding usuario record to list
                usuarioList.add(usuario);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return usuarioList;
    }

    /**
     * This method to update usuario record
     *
     * @param usuario
     */
    public void updateUser(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMNA_USUARIO_NOMBRE, usuario.getNombre());
        values.put(COLUMNA_USUARIO_EMAIL, usuario.getEmail());
        values.put(COLUMNA_USUARIO_CLAVE, usuario.getClave());

        // updating row
        db.update(TABLA_USUARIO, values, COLUMNA_USUARIO_ID + " = ?",
                new String[]{String.valueOf(usuario.getId())});
        db.close();
    }

    /**
     * This method is to delete usuario record
     *
     * @param usuario
     */
    public void deleteUser(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete usuario record by id
        db.delete(TABLA_USUARIO, COLUMNA_USUARIO_ID + " = ?",
                new String[]{String.valueOf(usuario.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUsuario(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMNA_USUARIO_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMNA_USUARIO_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLA_USUARIO, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public Usuario getUserByEmail(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMNA_USUARIO_ID,
                COLUMNA_USUARIO_EMAIL,
                COLUMNA_USUARIO_NOMBRE,
                COLUMNA_USUARIO_CLAVE
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMNA_USUARIO_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLA_USUARIO, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        Usuario usuario = new Usuario();
        if (cursor.moveToFirst()) {
            usuario.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMNA_USUARIO_ID))));
            usuario.setNombre(cursor.getString(cursor.getColumnIndex(COLUMNA_USUARIO_NOMBRE)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndex(COLUMNA_USUARIO_EMAIL)));
            usuario.setClave(cursor.getString(cursor.getColumnIndex(COLUMNA_USUARIO_CLAVE)));
        }

        return usuario;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUsuario(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMNA_USUARIO_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMNA_USUARIO_EMAIL + " = ?" + " AND " + COLUMNA_USUARIO_CLAVE + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLA_USUARIO, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}
