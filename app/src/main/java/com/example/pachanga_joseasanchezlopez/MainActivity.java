package com.example.pachanga_joseasanchezlopez;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
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
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btRegistrar, btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);*/
        comprobarAutenticacion();
    }

    private final ActivityResultLauncher<Intent> loginLauncher=registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onLoginResult(result);
                }
            }
    );

    private void onLoginResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response=result.getIdpResponse();
        if (result.getResultCode()==RESULT_OK){
            startActivity(new Intent(this, InicioActivity.class));
        }else{
            String msg_error="";
            if (response == null){
                msg_error="Es necesario autenticarse";
            }else if(response.getError().getErrorCode()== ErrorCodes.NO_NETWORK){
                msg_error="No hay red disponible para autenticarse";
            }else{
                msg_error="Error desconocido al autenticarse";
            }
            Toast.makeText(this, msg_error, Toast.LENGTH_LONG).show();
        }
        finish();
    }

    public void createSingInIntent(){
        Intent signIntent=AuthUI.getInstance().createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .build();
        loginLauncher.launch(signIntent);
    }

    private void comprobarAutenticacion(){
        FirebaseAuth auth=FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, InicioActivity.class));
        }else{
            createSingInIntent();
        }
    }
}