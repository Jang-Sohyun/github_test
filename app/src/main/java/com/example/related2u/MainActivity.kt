package com.example.related2u

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var title: EditText? = null
    var next: Button? = null
    var spinner: Spinner? = null
    var databaseReference: DatabaseReference? = null
    var item: String? = null
    var localList: LocalList? = null
    var locals = arrayOf<String?>("시/도", "서울특별시", "인천광역시", "강원도", "충청남도", "충청북도")
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //mainActivity 화면을 불러옴
        title = findViewById(R.id.mainActivity_edittext_title) //mainActivity에 있는 edittext 요소를 id로 불러옴.
        next = findViewById(R.id.mainActivity_button_next) //mainActivity에 있는 button 요소를 id로 불러옴.
        spinner = findViewById(R.id.mainActivity_spinner1)
        databaseReference = FirebaseDatabase.getInstance().getReference("schedule")
        spinner?.setOnItemSelectedListener(this)
        localList = LocalList()
        val arrayAdapter: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_dropdown_item, locals)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.setAdapter(arrayAdapter)
        next?.setOnClickListener(View.OnClickListener { //Firebase에 입력값 저장.
            databaseReference!!.child("title").setValue(title?.getText().toString())
            SaveValue(item)
        })
    }

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
        item = spinner!!.selectedItem.toString()
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) {}
    fun SaveValue(item: String?) {
        if (item === "시/도") {
            Toast.makeText(this, "가고 싶은 도시 중 하나를 선택해주세요.", Toast.LENGTH_SHORT).show()
        } else {
            localList!!.local = item
            databaseReference!!.child("city").setValue(localList)
        }
    }
}