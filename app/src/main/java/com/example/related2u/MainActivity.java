package com.example.related2u;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText title;
    Button next;
    Spinner spinner;
    DatabaseReference databaseReference;
    String item;
    LocalList localList;

    String[] locals = {"시/도", "서울특별시", "인천광역시", "강원도", "충청남도", "충청북도"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //mainActivity 화면을 불러옴

        title = findViewById(R.id.mainActivity_edittext_title); //mainActivity에 있는 edittext 요소를 id로 불러옴.
        next = findViewById(R.id.mainActivity_button_next); //mainActivity에 있는 button 요소를 id로 불러옴.
        spinner = findViewById(R.id.mainActivity_spinner1);

        databaseReference = FirebaseDatabase.getInstance().getReference("schedule");

        spinner.setOnItemSelectedListener(this);

        localList = new LocalList();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, locals);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Firebase에 입력값 저장.
                databaseReference.child("title").setValue(title.getText().toString());
                SaveValue(item);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = spinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    void SaveValue(String item) {
        if (item == "시/도") {
            Toast.makeText(this, "가고 싶은 도시 중 하나를 선택해주세요.", Toast.LENGTH_SHORT).show();
        }
        else{
            localList.setLocal(item);
            databaseReference.child("city").setValue(localList);
        }
    }
}