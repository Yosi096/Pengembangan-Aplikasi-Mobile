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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    TextView txtR;
    Button btn_regis;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBarR;
    EditText first_nameR, emailR, pwdR, rety_passR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        first_nameR = findViewById(R.id.fisrt_nameR);
        emailR = findViewById(R.id.emailR);
        pwdR = findViewById(R.id.pwdR);
        rety_passR = findViewById(R.id.rety_pwdR);
        btn_regis = findViewById(R.id.btn_regis);
        txtR = findViewById(R.id.txtR);
        progressBarR = findViewById(R.id.progressBarR);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailR.getText().toString().trim();
                String password = pwdR.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    emailR.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    pwdR.setError("Password is Required");
                    return;
                }

                if (password.length()<6){
                    pwdR.setError("Password Must be >= 6 Character");
                    return;
                }
                progressBarR.setVisibility(View.VISIBLE);

                //register di firebase
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Created",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        }else {
                            Toast.makeText(RegisterActivity.this, "Error"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        txtR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }
}
