package com.example.hoangtangkhai_semesterproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoangtangkhai_semesterproject.MainActivity;
import com.example.hoangtangkhai_semesterproject.R;
import com.example.hoangtangkhai_semesterproject.activity.RemoveUpdateExActivity;
import com.example.hoangtangkhai_semesterproject.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ChooseViewHolder>{
    private List<Exercise> mList;
    private Context context;

    public ChooseAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
    }

    public void setListExercise(List<Exercise> list){
        this.mList = list;
    }

    @NonNull
    @Override
    public ChooseAdapter.ChooseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ChooseAdapter.ChooseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseAdapter.ChooseViewHolder holder, int position) {
        Exercise e = mList.get(position);
        String set = null;
        String rep = null;
        String type = null;
        if(e != null){
//            holder.txtId.setText(e.getId());
            holder.txtNameEx.setText(e.getNameEx());
            if(e.getTypeEx() == 0){
                set = "Sets: "+e.getNumSets();
                rep = "Reps: "+e.getNumReps();
                type = "Bodybuilding";
            }else{
                set = "Distance: "+e.getNumSets();
                rep = "Durarion "+e.getNumReps();
                type = "Cardio";
            }
            holder.txtSets.setText(set);
            holder.txtReps.setText(rep);
            holder.txtTypeEx.setText(type);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, MainActivity.class);
                    i.putExtra("Exercise", e);
                    context.startActivity(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ChooseViewHolder extends RecyclerView.ViewHolder {
        TextView txtId, txtNameEx, txtSets, txtReps, txtTypeEx;

        public ChooseViewHolder(@NonNull View itemView) {
            super(itemView);
//            txtId = itemView.findViewById(R.id.txtIdEx);
            txtNameEx = itemView.findViewById(R.id.txtNameEx);
            txtSets = itemView.findViewById(R.id.txtSets);
            txtReps = itemView.findViewById(R.id.txtReps);
            txtTypeEx = itemView.findViewById(R.id.txtType);
        }
    }
}
