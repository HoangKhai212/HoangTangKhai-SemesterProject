package com.example.hoangtangkhai_semesterproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hoangtangkhai_semesterproject.MainActivity;
import com.example.hoangtangkhai_semesterproject.R;
import com.example.hoangtangkhai_semesterproject.model.Account;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends AppCompatActivity {

    private Button signUpBtn;
    private FirebaseAuth mAuth;
    private Button loginBtn;
    private EditText editUsername;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();

        FirebaseUser account = mAuth.getCurrentUser();

        if(account != null){
            Account acc = new Account();
            acc.setName(account.getDisplayName());
            acc.setEmail(account.getEmail());

            Intent i = new Intent(SignInActivity.this, MainActivity.class);
            i.putExtra("account", acc);
            i.putExtra("msg", "Welcome back " +  acc.getName());
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivityForResult(t, 101);}
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un = editUsername.getText().toString();
                String pw = editPassword.getText().toString();
                if(un.isEmpty() || pw.isEmpty()){
                    Toast.makeText(SignInActivity.this, "Please fill the infomartion!",
                            Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.signInWithEmailAndPassword(un, pw)
                            .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("Normal login", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        // Signed in successfully, show authenticated UI.
                                        Account acc = new Account();
                                        acc.setName(user.getDisplayName());
                                        acc.setEmail(user.getEmail());

                                        Intent i = new Intent(SignInActivity.this, MainActivity.class);
                                        i.putExtra("account", acc);
                                        if (acc.getName() == null) {
                                            i.putExtra("msg", "Welcome User!");
                                        } else {
                                            i.putExtra("msg", "Welcome " + acc.getName());
                                        }
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("Normal login", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(SignInActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }
    public void init(){
        signUpBtn = findViewById(R.id.signUpBtn);
        mAuth = FirebaseAuth.getInstance();
        loginBtn = findViewById(R.id.loginBtn);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            Toast.makeText(SignInActivity.this, "Sign up Successfully", Toast.LENGTH_SHORT).show();
            String email = data.getData().toString();
            editUsername.setText(email);
    }

    }
}