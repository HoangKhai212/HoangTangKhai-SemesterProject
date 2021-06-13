package com.example.hoangtangkhai_semesterproject.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoangtangkhai_semesterproject.CountDownDialog;
import com.example.hoangtangkhai_semesterproject.MainActivity;
import com.example.hoangtangkhai_semesterproject.R;
import com.example.hoangtangkhai_semesterproject.activity.AddExerciseActivity;
import com.example.hoangtangkhai_semesterproject.activity.SignInActivity;
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
import java.util.Date;
import java.util.List;
import java.util.Map;


public class WorkoutFragment extends Fragment {

    private RecyclerView rev;
    private RecycleViewAdapter ExAdapter;
    private AutoCompleteExercise autoAdapter;
    private WorkoutAdapter adapter;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private Account acc;
    private Exercise e = null;
    private List<Workout> wlist;
    private AutoCompleteTextView searchEx;
    private EditText editSetsWk, editRepsWk, editCountDown;
    private TextView editDateWk;
    private ImageView imSearchEx;
    private Button startCD;
    private Spinner spTypeEx;
    private List<Exercise> list = null;


    public WorkoutFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);
        init(view);
        e = ((MainActivity)this.getActivity()).getExercise();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();

        // Signed in successfully, show authenticated UI.
        acc = new Account();
        acc.setName(user.getDisplayName());
        acc.setEmail(user.getEmail());
        acc.setId(user.getUid().toString());

//        adapter = new WorkoutAdapter(getActivity());
//        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
//        rev.setLayoutManager(manager);
//        rev.setAdapter(adapter);

        if (e != null){
            searchEx.setText(e.getNameEx().toString());
            editSetsWk.setText(e.getNumSets()+"");
            editRepsWk.setText(e.getNumReps()+"");
            if(e.getTypeEx() == 0){
                spTypeEx.setSelection(0);
            }
            else {
                spTypeEx.setSelection(1);
            }
        }

        imSearchEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseDialog chooseDialog = new ChooseDialog(getActivity());
                chooseDialog.show();
            }
        });

        editDateWk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editDateWk.setText(dayOfMonth+"/"+(month+1)+"/"+(year));
                    }
                },y,m,d);
                dialog.show();
            }
        });
        startCD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int time = 0;
                if(editCountDown.getText().toString().contains("Second")){
                    time = 0;
                }
                else{
                    time = Integer.parseInt(editCountDown.getText().toString());
                }
                String sdate = editDateWk.getText().toString();
                if(e != null && time >0) {
                    CountDownDialog countDownDialog = new CountDownDialog(getActivity(), e, time, sdate);
                    countDownDialog.show();
                }else{
                    Toast.makeText(getActivity(), "Please fill the infomartion!", Toast.LENGTH_SHORT).show();
                }

            }
        });

//        addEx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int id = (int) (new Date().getTime()/1000);
//                Workout w = new Workout();
//                w.setId(id);
//                w.setDate(editDateWk.getText().toString());
//                w.setExercise(searchEx.getText().toString());
//                mDatabase.child(acc.getId()).child(String.valueOf(id)).setValue(w);
//            }
//        });

//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                wlist = new ArrayList<>();
//                GenericTypeIndicator<Map<String, Workout>> t = new GenericTypeIndicator<Map<String, Workout>>() {};
//                Map<String, Workout> map =  dataSnapshot.child(acc.getId()).getValue(t);
//                if(map != null){
//                    for (Map.Entry<String, Workout> entry : map.entrySet()) {
//                        System.out.println(entry.getValue());
//                        wlist.add(entry.getValue());
//                    }
//                }
//                adapter.setListWorkout(wlist);
//                rev.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        };
//        mDatabase.addValueEventListener(postListener);

        return view;

    }
    private void init(View view){
//        rev = view.findViewById(R.id.revViewWorkout);
        editCountDown = view.findViewById(R.id.editCountDown);
        editSetsWk = view.findViewById(R.id.editSetsWk);
        editRepsWk = view.findViewById(R.id.editRepsWk);
        editDateWk = view.findViewById(R.id.editDateWK);
        searchEx = view.findViewById(R.id.searchExAuto);
        imSearchEx = view.findViewById(R.id.searchEx);
        startCD = view.findViewById(R.id.btnStartWk);
        spTypeEx = view.findViewById(R.id.spnTypeWk);
        mAuth = FirebaseAuth.getInstance();
    }
}