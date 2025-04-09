package com.kevin.basesdedatoslocalesk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.juan.proyectcoop.R;

import androidx.appcompat.app.AppCompatActivity;

import com.kevin.basesdedatoslocalesk.model.ManagerDB;

public class RegistroUsuarios extends AppCompatActivity {

    private EditText etNombre, etEmail, etPassword, etConfirmPassword;
    private Button btnRegistrarse;
    private TextView tvLogin;
    private ManagerDB managerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        tvLogin = findViewById(R.id.tvLogin);

        managerDB = new ManagerDB(this);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroUsuarios.this, LoginUsuarios.class);
                startActivity(intent);
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegistroUsuarios.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegistroUsuarios.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                } else if (!email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    Toast.makeText(RegistroUsuarios.this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show();
                } else {
                    long result = managerDB.insertUsuario(nombre, email, password);

                    if (result == -1) {
                        Toast.makeText(RegistroUsuarios.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegistroUsuarios.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                        etNombre.setText("");
                        etEmail.setText("");
                        etPassword.setText("");
                        etConfirmPassword.setText("");
                        Intent intent = new Intent(RegistroUsuarios.this, LoginUsuarios.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
