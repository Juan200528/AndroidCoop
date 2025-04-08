package com.kevin.basesdedatoslocalesk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kevin.basesdedatoslocalesk.controller.Registro_participantes;
import com.kevin.basesdedatoslocalesk.model.ManagerDB;

public class LoginUsuarios extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private ManagerDB managerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuarios);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        managerDB = new ManagerDB(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginUsuarios.this, "Por favor, complete ambos(2) campos", Toast.LENGTH_SHORT).show();
                } else if (!email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    Toast.makeText(LoginUsuarios.this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isValid = managerDB.validarLogin(email, password);

                    if (isValid) {
                        Toast.makeText(LoginUsuarios.this, "Bienvenido", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginUsuarios.this, Registro_participantes.class);
                        startActivity(intent);
                        finish();  // Opcional, cierra la actividad de login
                    } else {
                        Toast.makeText(LoginUsuarios.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
