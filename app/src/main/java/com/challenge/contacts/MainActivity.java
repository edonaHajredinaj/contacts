package com.challenge.contacts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        contactIdTxt = findViewById(R.id.contactId);
        contactNameTxt = findViewById(R.id.contactName);
        contactNumberTxt = findViewById(R.id.contactNumber);
        contactEmailTxt = findViewById(R.id.contactEmail);
        contactAddressTxt = findViewById(R.id.contactAddress);
        contactBirthdayTxt = findViewById(R.id.contactBirthday);
        db = openOrCreateDatabase("ContactsDB", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS contacts(contact_id INTEGER PRIMARY KEY, contact_name TEXT, contact_number INTEGER, contact_email TEXT, contact_address TEXT, contact_birthday TEXT )");

    }

    public void create(View view) {
        String id = contactIdTxt.getText().toString();
        String name = contactNameTxt.getText().toString();
        String number = contactNumberTxt.getText().toString();
        String email = contactEmailTxt.getText().toString();
        String address = contactAddressTxt.getText().toString();
        String birthday = contactBirthdayTxt.getText().toString();
        //db.execSQL("INSERT INTO contacts VALUES(1, 'Edona Hajredinaj', 38344555247, 'edona@gmail.com', 'Prishtine', '2000/03/27' ) "); // hard-coded
        //db.execSQL("INSERT INTO contacts VALUES(" + id.toString() + ", '" + name + "', " + number.toString() + ", '" + email + "', '" + address + "', '" + birthday + "' ) "); // e merr prej edit text
        //parameterized version >>>
        db.execSQL("INSERT INTO contacts VALUES(?, ?, ?, ?, ?, ? )", new Object[]{id, name, number, email, address, birthday});

        showMessage("", "Contact saved successfully.");
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
            builder.append("_____________________________________\n");
        }

        showMessage("All Contacts", builder.toString());
        cursor.close();

    }

    public void search(View view) {
        String searchContact = searchContactTxt.getText().toString();

        Cursor cursor = db.rawQuery("SELECT * FROM contacts WHERE contact_name LIKE ?", new String[]{"%"+ searchContact+ "%"});
        StringBuilder builder = new StringBuilder();

        while (cursor.moveToNext()) {
            builder.append("\nID: ").append(cursor.getInt(0)).append("\n");
            builder.append("Name: ").append(cursor.getString(1)).append("\n");
            builder.append("Number: ").append(cursor.getInt(2)).append("\n");
            builder.append("Email: ").append(cursor.getString(3)).append("\n");
            builder.append("Address: ").append(cursor.getString(4)).append("\n");
            builder.append("Birthday: ").append(cursor.getString(5)).append("\n");
            builder.append("_____________________________________\n");
        }

        if (cursor.moveToFirst()) {
            showMessage("Contacts found: ", builder.toString());
        } else {
            showMessage("No contacts found.", null);
        }

        cursor.close();

    }

    private void showMessage(String title, String message) {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
}