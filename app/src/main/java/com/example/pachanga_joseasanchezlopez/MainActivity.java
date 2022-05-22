package com.example.pachanga_joseasanchezlopez;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btRegistrar, btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btAcceder);
        btRegistrar = findViewById(R.id.btRegistrar);

        /**
         * En este botón solamente compruebo si existe un usario o no para que realice el registro
         */
        btRegistrar.setOnClickListener(v -> {
            /*if (!etEmail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        etEmail.getText().toString(), etPassword.getText().toString());
            }*/
            comprobarAutenticacion();
        });

        /**
         * Comprueba que existe y se logea
         */
        btLogin.setOnClickListener(v -> {
            if (!etEmail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        etEmail.getText().toString(), etPassword.getText().toString());
                comprobarAutenticacion();
            }
        });
    }

    private final ActivityResultLauncher<Intent> loginLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    //onLoginResult(result);
                }
            }
    );


    public void createSingInIntent() {
        Intent signIntent = AuthUI.getInstance().createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .build();
        loginLauncher.launch(signIntent);
    }

    private void comprobarAutenticacion() {
        /*FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
//            if (auth.getCurrentUser().getEmail().equals(etEmail.getText().toString())) {
            finish();
            startActivity(new Intent(this, InicioActivity.class));
//            }
        } else {
            Toast.makeText(this, "Usuario no Registrado", Toast.LENGTH_LONG).show();
            createSingInIntent();
        }*/
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(MainActivity.this, InicioActivity.class));
                        }else{
                            Toast.makeText(MainActivity.this, "Usuario no Registrado", Toast.LENGTH_LONG).show();
                            createSingInIntent();
                        }
                    }
                });
    }
}