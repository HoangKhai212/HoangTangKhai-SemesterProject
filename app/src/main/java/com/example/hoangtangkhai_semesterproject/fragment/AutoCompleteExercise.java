package com.example.hoangtangkhai_semesterproject.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hoangtangkhai_semesterproject.R;
import com.example.hoangtangkhai_semesterproject.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteExercise extends ArrayAdapter<Exercise> {
    private List<Exercise> ExListFull;
    private Context context;
    public AutoCompleteExercise(@NonNull Context context) {
        super(context,0);
        ExListFull = new ArrayList<>();
    }
    public void setListFull(List<Exercise> list){
        this.ExListFull = list;
    }

    private Filter ExFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Exercise> suggestion = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                suggestion.addAll(ExListFull);
            }else{
                String filter = constraint.toString().toLowerCase().trim();
                for (Exercise item : ExListFull){
                    if(item.getNameEx().toLowerCase().contains(filter)){
                        suggestion.add(item);
                    }
                }
            }
            results.values = suggestion;
            results.count = suggestion.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List)results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Exercise) resultValue).getNameEx();
        }
    };

    public Filter getExFilter() {
        return ExFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exercise_item, parent,false);
        }
        TextView txtname = convertView.findViewById(R.id.txtNameEx);
        TextView txtSets = convertView.findViewById(R.id.txtSets);
        TextView txtReps = convertView.findViewById(R.id.txtReps);
        TextView txtType = convertView.findViewById(R.id.txtType);

        String set = null;
        String rep = null;
        String type = null;

        Exercise exercise = getItem(position);
        if(exercise != null){
            txtname.setText(exercise.getNameEx());
            if(exercise.getTypeEx() == 0){
                set = "Sets: "+exercise.getNumSets();
                rep = "Reps: "+exercise.getNumReps();
                type = "Bodybuilding";
            }else{
                set = "Distance: "+exercise.getNumSets();
                rep = "Durarion "+exercise.getNumReps();
                type = "Cardio";
            }
            txtSets.setText(set);
            txtReps.setText(rep);
            txtType.setText(type);

        }
        return convertView;
    }
}
