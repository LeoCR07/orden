package com.example.orden.Login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orden.Principal.MainActivity;
import com.example.orden.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    protected Window window;
    protected Button _BtnAceptar;
    protected TextInputLayout _EditCorreo;
    protected TextInputLayout _EditPassword;
    protected ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ApplyToolBar();

        //Iniciarlizar el auth
        firebaseAuth = FirebaseAuth.getInstance();
        _BtnAceptar = findViewById(R.id.BtnLogin);
        _EditCorreo = findViewById(R.id.EditCorreo);
        _EditPassword = findViewById(R.id.EditContra);

       progressDialog = new ProgressDialog(this);
       progressDialog.setCancelable(false);
       progressDialog.setMessage("Realizando validación en linea...");
       //progressDialog.show();


    }

    private void ApplyToolBar() {

        this.window = getWindow();
        window.setStatusBarColor(Color.parseColor("#154360"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable( Color.parseColor("#1F618D")));

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.logo_toolbar);


    }


    public void OnClickLogin(View view) {

        String Correo = _EditCorreo.getEditText().getText().toString().trim();
        String Password = _EditPassword.getEditText().getText().toString().trim();

        Boolean r1 = ValidarPassword(Password);
        Boolean r2 = ValidarCorreo(Correo);

        if(r1&&r2){
            ValidarAuth(Correo,Password);
        }


    }

    private void ValidarAuth(String correo, String password) {
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(correo,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(LoginActivity.this, MainActivity.class));

                        }else{
                            Toast.makeText(LoginActivity.this, "Datos invalidos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private  boolean ValidarPassword(String password) {
        if(password.isEmpty()){
            _EditPassword.setError("Campo no puede estar vacio");
            return false;
        }else{
            _EditPassword.setError(null);
            return true;
        }

    }

    private  boolean ValidarCorreo(String correo) {

        if( (!correo.isEmpty())&& (Patterns.EMAIL_ADDRESS.matcher(correo).matches())){
            _EditCorreo.setError(null);
            return true;
        }else{
            _EditCorreo.setError("Correo Invalido");
            return false;
        }

    }

}