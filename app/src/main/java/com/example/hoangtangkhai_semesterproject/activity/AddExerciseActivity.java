package com.example.hoangtangkhai_semesterproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import com.example.hoangtangkhai_semesterproject.MainActivity;
import com.example.hoangtangkhai_semesterproject.R;
import com.example.hoangtangkhai_semesterproject.model.Account;
import com.example.hoangtangkhai_semesterproject.model.Exercise;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class AddExerciseActivity extends AppCompatActivity {
    private EditText editNameAdd, editSets, editReps;
    private Button btnAddAdd;
    private Button btnBackAdd;
    private Spinner spnTypeEx;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        init();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        // Signed in successfully, show authenticated UI.
        Account acc = new Account();
        acc.setId(user.getUid().toString());
        acc.setName(user.getDisplayName());
        acc.setEmail(user.getEmail());


        btnAddAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = (int) (new Date().getTime()/1000);
                Exercise o = new Exercise();
                o.setId(id);
                o.setNameEx(editNameAdd.getText().toString());
                o.setNumSets(Integer.parseInt(editSets.getText().toString()));
                o.setNumReps(Integer.parseInt(editReps.getText().toString()));
                if(spnTypeEx.getSelectedItem().toString().contains("Bodybuilding"))
                    o.setTypeEx(0);
                else o.setTypeEx(1);
                mDatabase.child(acc.getId()).child(String.valueOf(id)).setValue(o);
                Intent i = new Intent(AddExerciseActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnBackAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddExerciseActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void init() {
        editNameAdd = findViewById(R.id.editNameAdd);
        editSets = findViewById(R.id.editSets);
        editReps = findViewById(R.id.editReps);
        btnAddAdd = findViewById(R.id.btnAddAdd);
        btnBackAdd = findViewById(R.id.btnBackAdd);
        spnTypeEx = findViewById(R.id.spnTypeAdd);
        mAuth = FirebaseAuth.getInstance();
    }
}