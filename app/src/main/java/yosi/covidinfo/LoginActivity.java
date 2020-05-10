package yosi.covidinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText unameL, pwdL;
    ProgressBar progressBarL;
    Button btn_login, btn_signup, btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unameL = findViewById(R.id.unameL);
        pwdL = findViewById(R.id.pwdL);
        progressBarL = findViewById(R.id.progressBarL);
        firebaseAuth = FirebaseAuth.getInstance();
        btn_close = findViewById(R.id.btn_close);
        btn_signup = findViewById(R.id.btn_signup);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = unameL.getText().toString().trim();
                String pass = pwdL.getText().toString().trim();

                if (TextUtils.isEmpty(uname)){
                    unameL.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(pass)){
                    pwdL.setError("Password is Required");
                    return;
                }

                if (pass.length()<6){
                    pwdL.setError("Password Must be >= 6 Character");
                    return;
                }
                progressBarL.setVisibility(View.VISIBLE);

                //authentification
                firebaseAuth.signInWithEmailAndPassword(uname,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),IntroActivity.class));
                        }else {
                            Toast.makeText(LoginActivity.this, "Error"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            progressBarL.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
