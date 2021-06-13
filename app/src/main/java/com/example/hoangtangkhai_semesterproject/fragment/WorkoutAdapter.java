package com.example.hoangtangkhai_semesterproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoangtangkhai_semesterproject.R;
import com.example.hoangtangkhai_semesterproject.activity.RemoveUpdateExActivity;
import com.example.hoangtangkhai_semesterproject.model.Account;
import com.example.hoangtangkhai_semesterproject.model.Exercise;
import com.example.hoangtangkhai_semesterproject.model.Workout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>{
    private List<Workout> mList;
    private Exercise e = null;
    private Context context;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Account acc = new Account();

    public WorkoutAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
    }


    public void setListWorkout(List<Workout> list){
        this.mList = list;
    }

    @NonNull
    @Override
    public WorkoutAdapter.WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wokout_item, parent, false);
        return new WorkoutAdapter.WorkoutViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout w = mList.get(position);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        // Signed in successfully, show authenticated UI.
        Account acc = new Account();
        acc.setId(user.getUid().toString());
        acc.setName(user.getDisplayName());
        acc.setEmail(user.getEmail());
        if (w != null){
            holder.txtNameEx.setText(w.getExercise());
            holder.txtDate.setText(w.getDate());
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabase.child(acc.getId()).child(String.valueOf(w.getId())).removeValue();
                }
            });
            }

    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView txtId, txtNameEx, txtSets, txtReps, txtDate;
        Button remove;
        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameEx = itemView.findViewById(R.id.txtNameWk);
            txtDate = itemView.findViewById(R.id.txtDate);
            remove = itemView.findViewById(R.id.btnRemoveWk);
        }
    }
}