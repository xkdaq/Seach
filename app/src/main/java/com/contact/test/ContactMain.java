package com.contact.test;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dalipan.search.R;

//联系人测试
public class ContactMain extends AppCompatActivity {

    private TextView tvContact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        findViewById(R.id.btn_get1).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
            startActivityForResult(intent, 1000);
        });
        tvContact = findViewById(R.id.tv_contact);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode != Activity.RESULT_OK || data == null){
                Log.e("xxxxx1", "========1");
            }else {
                Uri contactUri = data.getData();
                String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                if (cursor == null) {
                    Log.e("xxxxx3", "========3");
                    return;
                }

                if (cursor.moveToFirst()) {
                    int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String number = cursor.getString(numberIndex);

                    int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    String name = cursor.getString(nameIndex);

                    Log.e("xxxxx2", "========2" + name + "=========" + number);
                    tvContact.setText("========" + name + "=========" + number);
                }

                cursor.close();
            }
        }
    }
}
