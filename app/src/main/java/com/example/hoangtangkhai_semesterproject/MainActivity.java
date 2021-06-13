package com.example.hoangtangkhai_semesterproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hoangtangkhai_semesterproject.activity.SignInActivity;
import com.example.hoangtangkhai_semesterproject.fragment.BottomNavigationAdapter;
import com.example.hoangtangkhai_semesterproject.model.Account;
import com.example.hoangtangkhai_semesterproject.model.Exercise;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private BottomNavigationAdapter adapter;
    private Exercise exercise;

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {

        this.exercise = exercise;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chrono_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_chrono:
                ChronometerDialog cd = new ChronometerDialog(MainActivity.this);
                cd.show();
                break;
            case R.id.workout:
                viewPager.setCurrentItem(0);
                break;
            case R.id.exit:
                finish();
                System.exit(0);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent t = new Intent(getApplicationContext(), SignInActivity.class);
                                t.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(t);
                                finish();
                            }
                        });
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Intent t = getIntent();
        Account acc = (Account) t.getSerializableExtra("account");
        String str = (String) t.getSerializableExtra("msg");
        if(acc != null){
            Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
        }

        exercise = (Exercise) t.getSerializableExtra("Exercise");
        this.setExercise(exercise);

        adapter = new BottomNavigationAdapter(getSupportFragmentManager(), BottomNavigationAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mWorkout:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.mExercise:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.mWeight:
                        viewPager.setCurrentItem(2);
                        break;
                }

                return true;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: bottomNavigationView.getMenu().findItem(R.id.mWorkout).setChecked(true);
                        break;
                    case 1: bottomNavigationView.getMenu().findItem(R.id.mExercise).setChecked(true);
                        break;
                    case 2: bottomNavigationView.getMenu().findItem(R.id.mWeight).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void init(){
        viewPager = findViewById(R.id.viewPagerHome);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }
}