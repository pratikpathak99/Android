package com.pratik.api_presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView insert_name;
    EditText name;
    LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        insert_name = findViewById(R.id.insert_show);
        name = findViewById(R.id.editTextEmail);
        mainLayout=(LinearLayout)this.findViewById(R.id.mainLayout);
    }

    public void insert(View view) {
        insert_name.setText("Insert Data Successfully");
        mainLayout.setVisibility(LinearLayout.INVISIBLE);
    }
}