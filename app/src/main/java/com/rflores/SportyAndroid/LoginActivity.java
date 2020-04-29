package com.rflores.SportyAndroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    ////////////////////////////////////////////////
    //estados
    ///////////////////////////////////////////
    private Controlador miControlador;

    //////////////////////////////////////////////////////////////////////
    // Oncreate
    ///////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        miControlador = Controlador.getControlador();
    }

    public void BTLogin(View view) {
        EditText miTEPasswd = findViewById(R.id.TEPasswd);
        EditText miTEUsuario = findViewById(R.id.TEUsuario);
        TextView miLAError = findViewById(R.id.LAError);
        String serializado;

        // comprobamos que los campos no sean nulos
        if (miTEUsuario.getText().toString().length()==0 || miTEPasswd.getText().toString().length()==0) {
            Toast miToast = Toast.makeText(this,"Usuario y Contraseña no pueden ser nulos",Toast.LENGTH_LONG);
            miToast.show();
            miLAError.setText("Usuario y Contraseña no pueden ser nulos");
        } else {
            // comprobamos login en fichero

            serializado=miTEUsuario.getText()+";"+miTEPasswd.getText();

            if (miControlador.comprobarLogin (serializado)){
                // el login es correcto hay que pasar los datos al controlador
                miControlador.setUsuario(String.valueOf(miTEUsuario.getText()));
                //volvemos a Mainactivity
                finish();
            } else {
                // el login es incorrecto registramos el error
                Toast miToast = Toast.makeText(this,"El Usuario no existe o la contraseña es incorrecta",Toast.LENGTH_LONG);
                miToast.show();
                miLAError.setText("El Usuario no existe o la contraseña es incorrecta");
            }

        }

        //volvemos a la anterior activity si el login es correcto o informamos del error si no lo es

    }

    // Botón Registrarse
    public void BTReg(View view) {

        Intent miIntent = new Intent(this, NewUserActivity.class);
        startActivity(miIntent);
        finish();
    }
}
