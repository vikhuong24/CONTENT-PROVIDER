package com.example.contentprovider;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.contentprovider.Model.Contact;
import java.util.ArrayList;
import java.util.Currency;

public class DanhBa extends AppCompatActivity {
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1001;
    ListView lv_danhba;
    ArrayList<Contact> dsDanhBa;
    ArrayAdapter<Contact> adapterDanhBa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_ba);
        addControl();
        requestContactPermission();
    }

    private void requestContactPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_CONTACTS}, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            ShowAllContact();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ShowAllContact();
            }
        }
    }

    private void ShowAllContact() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (cursor != null) {
            dsDanhBa.clear();
            while (cursor.moveToNext()) {
                int vitrinameContact = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int vitriphoneContact = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                if (vitrinameContact >= 0 && vitriphoneContact >= 0) {
                    String name = cursor.getString(vitrinameContact);
                    String phone = cursor.getString(vitriphoneContact);
                    Contact contact = new Contact(name, phone);
                    dsDanhBa.add(contact);
                }
            }
            cursor.close();
            adapterDanhBa.notifyDataSetChanged();
        }
    }

    private void addControl() {
        lv_danhba = findViewById(R.id.lv_DanhBa);
        dsDanhBa = new ArrayList<>();
        adapterDanhBa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dsDanhBa);
        lv_danhba.setAdapter(adapterDanhBa);
    }
}