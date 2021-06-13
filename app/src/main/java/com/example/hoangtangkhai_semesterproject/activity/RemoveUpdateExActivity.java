package com.example.hoangtangkhai_semesterproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hoangtangkhai_semesterproject.MainActivity;
import com.example.hoangtangkhai_semesterproject.R;
import com.example.hoangtangkhai_semesterproject.model.Account;
import com.example.hoangtangkhai_semesterproject.model.Exercise;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RemoveUpdateExActivity extends AppCompatActivity {

    private EditText editNameReup, editSetsReUp, editRepsReUp;
    private TextView txtId;
    private Button btnRemove;
    private Button btnBackReUp;
    private Button btnUpdate;
    private Spinner spnTypeEx;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_update_ex);
        init();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        // Signed in successfully, show authenticated UI.
        Account acc = new Account();
        acc.setId(user.getUid().toString());
        acc.setName(user.getDisplayName());
        acc.setEmail(user.getEmail());

        Intent i = getIntent();
        Exercise o = (Exercise) i.getSerializableExtra("Exercise");
        txtId.setText(o.getId()+"");
        editNameReup.setText(o.getNameEx());
        editSetsReUp.setText(o.getNumSets()+"");
        editRepsReUp.setText(o.getNumReps()+"");
        switch (o.getTypeEx()){
            case 0:
                spnTypeEx.setSelection(0);
                break;
            case 1:
                spnTypeEx.setSelection(1);
                break;
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exercise o = new Exercise();
                o.setId(Integer.parseInt(txtId.getText().toString()));
                o.setNameEx(editNameReup.getText().toString());
                o.setNumSets(Integer.parseInt(editSetsReUp.getText().toString()));
                o.setNumReps(Integer.parseInt(editRepsReUp.getText().toString()));
                if(spnTypeEx.getSelectedItem().toString().contains("Bodybuilding"))
                    o.setTypeEx(0);
                else o.setTypeEx(1);
                mDatabase.child(acc.getId()).child(String.valueOf(o.getId())).setValue(o);
                Intent i = new Intent(RemoveUpdateExActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(acc.getId()).child(String.valueOf(o.getId())).removeValue();
                Intent i = new Intent(RemoveUpdateExActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnBackReUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RemoveUpdateExActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void init() {
        txtId = findViewById(R.id.txtIdReUp);
        editNameReup = findViewById(R.id.editNameReUp);
        editSetsReUp = findViewById(R.id.editSetsReUp);
        editRepsReUp = findViewById(R.id.editRepsReUp);
        btnRemove = findViewById(R.id.btnRemove);
        btnBackReUp = findViewById(R.id.btnBackReUp);
        btnUpdate = findViewById(R.id.btnUpdate);
        spnTypeEx = findViewById(R.id.spnTypeReUp);
        mAuth = FirebaseAuth.getInstance();
    }
}