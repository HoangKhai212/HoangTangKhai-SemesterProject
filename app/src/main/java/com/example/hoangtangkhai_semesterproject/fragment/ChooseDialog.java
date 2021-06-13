package com.example.hoangtangkhai_semesterproject.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoangtangkhai_semesterproject.R;
import com.example.hoangtangkhai_semesterproject.model.Account;
import com.example.hoangtangkhai_semesterproject.model.Exercise;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChooseDialog extends Dialog implements android.view.View.OnClickListener {

    private Activity c;
    private Dialog d;
    private RecyclerView rev;
    private ChooseAdapter adapter;
    private Account acc;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    public ChooseDialog(Activity a) {
        super(a);
        this.c = a;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Choose Excercise"); //ChronometerLabel
        setContentView(R.layout.choose_dialog);
        this.setCanceledOnTouchOutside(false); // make it modal
        init();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        acc = new Account();
        acc.setName(user.getDisplayName());
        acc.setEmail(user.getEmail());
        acc.setId(user.getUid().toString());


        // Signed in successfully, show authenticated UI.


        adapter = new ChooseAdapter(c);
        LinearLayoutManager manager = new LinearLayoutManager(c);
        rev.setLayoutManager(manager);
        rev.setAdapter(adapter);


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Exercise> list = new ArrayList<>();
                GenericTypeIndicator<Map<String, Exercise>> t = new GenericTypeIndicator<Map<String, Exercise>>() {};
                Map<String, Exercise> map =  dataSnapshot.child(acc.getId()).getValue(t);
                if(map != null){
                    for (Map.Entry<String, Exercise> entry : map.entrySet()) {
                        list.add(entry.getValue());
                    }
                }
                adapter.setListExercise(list);
                rev.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.addValueEventListener(postListener);
    }
    private void init(){
        rev = findViewById(R.id.revViewChoose);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {

    }
}

