package com.example.hoangtangkhai_semesterproject.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class BottomNavigationAdapter extends FragmentStatePagerAdapter {
    private int numPage = 3;

    public BottomNavigationAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: return new WorkoutFragment();
            case 1: return new ExerciseFragment();
            case 2: return new WeightFragment();
            default: return new WorkoutFragment();
        }
    }

    @Override
    public int getCount() {
        return numPage;
    }
}
