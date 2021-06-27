package com.challenge.contacts;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    EditText searchContactTxt,
            contactIdTxt,
            contactNameTxt,
            contactNumberTxt,
            contactEmailTxt,
            contactAddressTxt,
            contactBirthdayTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchContactTxt = findViewById(R.id.searchContact);
        reset();
        db = openOrCreateDatabase("ContactsDB", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS contacts(contact_id INTEGER PRIMARY KEY, contact_name TEXT, contact_number INTEGER, contact_email TEXT, contact_address TEXT, contact_birthday TEXT )");

    }

    public void reset() {
        contactIdTxt = findViewById(R.id.contactIdBtn);
        contactNameTxt = findViewById(R.id.contactName);
        contactNumberTxt = findViewById(R.id.contactNumber);
        contactEmailTxt = findViewById(R.id.contactEmail);
        contactAddressTxt = findViewById(R.id.contactAddress);
        contactBirthdayTxt = findViewById(R.id.contactBirthday);
    }

    public void create(View view) {
        String id = contactIdTxt.getText().toString();
        String name = contactNameTxt.getText().toString();
        String number = contactNumberTxt.getText().toString();
        String email = contactEmailTxt.getText().toString();
        String address = contactAddressTxt.getText().toString();
        String birthday = contactBirthdayTxt.getText().toString();

        db.execSQL("INSERT INTO contacts VALUES(?, ?, ?, ?, ?, ? )", new Object[]{id, name, number, email, address, birthday});

        showMessage("", "Contact saved successfully.");
    }

    public void search(View view) {
        String searchContact = searchContactTxt.getText().toString();

        Cursor cursor = db.rawQuery("SELECT * FROM contacts WHERE contact_name LIKE ?", new String[]{"%" + searchContact + "%"});
        StringBuilder builder = new StringBuilder();

        while (cursor.moveToNext()) {
            builder.append("\nID: ").append(cursor.getInt(0)).append("\n");
            builder.append("Name: ").append(cursor.getString(1)).append("\n");
            builder.append("Number: ").append(cursor.getInt(2)).append("\n");
            builder.append("Email: ").append(cursor.getString(3)).append("\n");
            builder.append("Address: ").append(cursor.getString(4)).append("\n");
            builder.append("Birthday: ").append(cursor.getString(5)).append("\n");
            builder.append("_______________________________________\n");
        }

        if (cursor.moveToFirst()) {
            showMessage("Contacts found: ", builder.toString());
        } else {
            showMessage("No contacts found.", null);
        }

        cursor.close();
    }

    public void saveChanges(View view) {
        reset();
        String id = contactIdTxt.getText().toString();

        String name = contactNameTxt.getText().toString();
        String number = contactNumberTxt.getText().toString();
        String email = contactEmailTxt.getText().toString();
        String address = contactAddressTxt.getText().toString();
        String birthday = contactBirthdayTxt.getText().toString();

        db.execSQL("UPDATE contacts SET contact_Name = ?, contact_number = ?, contact_Email = ?, " +
                        "contact_Address = ?, contact_Birthday = ? WHERE contact_id = ?",
                new Object[]{name, number, email, address, birthday, id});

        showMessage("", "Changes saved successfully.");
    }

    public void delete(View view) {
        reset();
        String id = contactIdTxt.getText().toString();
        db.execSQL("DELETE FROM contacts WHERE contact_id = ?", new Object[]{id});

        showMessage("", "Contact deleted.");
    }

    public void previousPage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void allContacts(View view) {

        Cursor cursor = db.rawQuery("SELECT * FROM contacts", null);
        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            builder.append("\nID: ").append(cursor.getInt(0)).append("\n");
            builder.append("Name: ").append(cursor.getString(1)).append("\n");
            builder.append("Number: ").append(cursor.getInt(2)).append("\n");
            builder.append("Email: ").append(cursor.getString(3)).append("\n");
            builder.append("Address: ").append(cursor.getString(4)).append("\n");
            builder.append("Birthday: ").append(cursor.getString(5)).append("\n");
            builder.append("_______________________________________\n");
        }

        showMessage("All Contacts", builder.toString());
        cursor.close();
    }

    private void showMessage(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .show();

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        break;

                    case DialogInterface.BUTTON_NEUTRAL:
                        setContentView(R.layout.dialog_edit);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        setContentView(R.layout.dialog_delete);
                        break;
                }
            }
        };

        builder.setPositiveButton("Ok", dialogClickListener);
        builder.setNeutralButton("Edit", dialogClickListener);
        builder.setNegativeButton("Delete", dialogClickListener);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}