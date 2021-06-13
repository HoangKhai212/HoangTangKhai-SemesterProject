package com.example.hoangtangkhai_semesterproject.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;


import com.example.hoangtangkhai_semesterproject.R;
import com.example.hoangtangkhai_semesterproject.activity.AddExerciseActivity;
import com.example.hoangtangkhai_semesterproject.model.Account;
import com.example.hoangtangkhai_semesterproject.model.Exercise;
import com.example.hoangtangkhai_semesterproject.model.Workout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


public class ExerciseFragment extends Fragment {

    private RecyclerView rev;
    private RecycleViewAdapter adapter;
    private FloatingActionButton floatBtn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private Account acc;
    private List<Exercise> list;



    public ExerciseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        init(view);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();


        // Signed in successfully, show authenticated UI.
        acc = new Account();
        acc.setName(user.getDisplayName());
        acc.setEmail(user.getEmail());
        acc.setId(user.getUid().toString());

        adapter = new RecycleViewAdapter(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rev.setLayoutManager(manager);
        rev.setAdapter(adapter);



        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(getActivity(), AddExerciseActivity.class);
                startActivity(t);
            }
        });

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
        return view;
    }

    public void init(View v){
        rev = v.findViewById(R.id.revViewExercise);
        floatBtn = v.findViewById(R.id.btnAddExercise);
        mAuth = FirebaseAuth.getInstance();
    }
}