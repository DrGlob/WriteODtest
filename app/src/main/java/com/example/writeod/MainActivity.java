package com.example.writeod;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etText;
    TextView etTextCount;
    Button btnSave, btnLoad, btnWordCount, btnStartTyping;
    SharedPreferences sPref;
    final String SAVED_TEXT = "saved_text";
    long date_start, date_words = 100000000;
    int start_flag = 0, flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etText = (EditText)findViewById(R.id.etText);
        etTextCount = (TextView)findViewById(R.id.etTextCount);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnLoad = (Button)findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(this);
        btnWordCount = (Button)findViewById(R.id.btnWordCount);
        btnWordCount.setOnClickListener(this);
        btnStartTyping = (Button)findViewById(R.id.btnStartTyping);
        btnStartTyping.setOnClickListener(this);
//======================================================================

        etText.addTextChangedListener(new TextWatcher(){

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){

                // действия, когда вводится какой то текст
                // s - то, что вводится, для преобразования в строку - s.toString()
                //Toast.makeText(MainActivity.this, "W "+ s, Toast.LENGTH_SHORT).show();
                countWord();
                if (start_flag == 1) {
                    time();
                    if (flag == 1) {
                        SharedPreferences.Editor ed = sPref.edit();
                        ed.putString(SAVED_TEXT, " ");
                        ed.commit();
                        etText.setText(null);
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        flag = 0;
                        start_flag = 0;
                    }
                }
            }

    

            @Override
            public void afterTextChanged(Editable editable) {

                // действия после того, как что то введено
                // editable - то, что введено. В строку - editable.toString()
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // действия перед тем, как что то введено
            }
        });

//======================================================================
        loadText();
    }
    //===============================================================================
//===============================================================================
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSave) {
            saveText();
        } else if (v.getId() == R.id.btnLoad) {
            loadText();
        } else if (v.getId() == R.id.btnWordCount) {
            countWord();
        } else if (v.getId() == R.id.btnStartTyping) {
            start_time();
        }
    }

    private void countWord() {
        int l = etText.getText().length();
        int count = 0;
        int c = etText.getText().toString().split(" ").length;
        String text = etText.getText().toString();
        for (int i = 0; i < c; i++) {
            if (text.split(" ")[i].length() >= 1) {
                count += 1;
            }
        }
        etTextCount.setText("Words\n"+ count);
        //Toast.makeText(MainActivity.this, "W "+ count, Toast.LENGTH_SHORT).show();
    }

    private void checkLetters() {
        String text = etText.getText().toString();

    }

    private void start_time() {
        Toast.makeText(MainActivity.this, "Start", Toast.LENGTH_SHORT).show();
        date_start =  System.currentTimeMillis();
        date_words = date_start;
        start_flag = 1;
    }

    private void time() {
        long date =  System.currentTimeMillis();
        if (date - date_words > 15000) {
            flag = 1;
            Toast.makeText(MainActivity.this, "Wrong "+ flag, Toast.LENGTH_SHORT).show();
            date_words = date;
        }

    }

    private void saveText() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, etText.getText().toString());
        ed.commit();
        Toast.makeText(MainActivity.this, "Text saved", Toast.LENGTH_SHORT).show();
    }

    private void loadText() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        etText.setText(savedText);
        Toast.makeText(MainActivity.this, "Text loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}