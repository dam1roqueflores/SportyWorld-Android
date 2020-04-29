package com.rflores.SportyAndroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserActivity extends AppCompatActivity {
    Controlador miControlador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

       miControlador= Controlador.getControlador();
    }
    // Botón Registrarse
    public void BTRegistrar(View view) {
        EditText miTEUsuario = findViewById(R.id.TENusuarioR);
        EditText miTEPasswd = findViewById(R.id.TEpasswdR);
        EditText miTEnombre = findViewById(R.id.TENombre);
        EditText miTEapellidos = findViewById(R.id.TEApellidos);
        EditText miTEDNI = findViewById(R.id.TEDNI);
        EditText miTEEmail=findViewById(R.id.TEemail);

        String serializado = miTEUsuario.getText()+";"+miTEPasswd.getText()+";"+miTEnombre.getText()+";"+miTEapellidos.getText()+";"+miTEDNI.getText()+";"+miTEEmail.getText();


        //Comprobar campos nulos
        if (miTEUsuario.getText().toString().length() == 0 || miTEPasswd.getText().toString().length() == 0) {
            Toast miToast = Toast.makeText(this, "Usuario y Contraseña no pueden ser nulos", Toast.LENGTH_LONG);
            miToast.show();
            // los campos obligatorios no son nulos
        } else {
            // comprobar usuario
              if (miControlador.existeUsuario(String.valueOf(miTEUsuario.getText()))) {
                // error si ya existe el usuario
                Toast miToast = Toast.makeText(this, "El usuario " + miTEUsuario.getText() +" ya existe", Toast.LENGTH_LONG);
                miToast.show();
            } else {
                  Usuario miUsuario= new Usuario(serializado);
                // registrar usuario si NO existe el usuario
                miControlador.addUsuario(miUsuario);
                // volvemos a la activity anterior
                finish();
            }

        }
    }
}
