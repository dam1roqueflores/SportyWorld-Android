package com.rflores.SportyAndroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ////////////////////////////////////////////////
    //estados
    ///////////////////////////////////////////
    private static Controlador miControlador;
    private static GUI miGUI;

    //////////////////////////////////////////////////////////////////////
// Main
///////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creamos el controlador através del comportamiento getControlador del método Singleton
         Controlador miControlador = Controlador.getControlador(this);
        // creamos el GUI
        miGUI=new GUI(this);
        // cargamos datos en Spinner
        miControlador.iniciaDatos(miGUI.getCactividad());
    }

    // Añadimos Listener a los botones
    public void btSalir (View view) {
        System.exit(0);
    }
    // Botón calcular resultado
    public void btCalcular(View view) {
        int minutos;
        float kilos;
        if (miGUI.getTminutos().getText().toString().length()==0 || miGUI.getTkilos().getText().toString().length()==0) {
            Toast miToast = Toast.makeText(this,"Minutos y Kilos no pueden ser nulos",Toast.LENGTH_LONG);
            miToast.show();
        } else {
            kilos = Float.parseFloat(String.valueOf(miGUI.getTkilos().getText()));
            minutos = Integer.parseInt(String.valueOf(miGUI.getTminutos().getText()));
            String actividad = (String) miGUI.getCactividad().getSelectedItem();
            miGUI.setLResultado(miControlador.calcularKCal(minutos, kilos, actividad));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        miGUI.setLUser(miControlador.getMiUsuario().getUsuario());
    }

    // Botón Loguearse
    public void BTLogin(View view) {

        Intent miIntent = new Intent(this,LoginActivity.class);
        startActivity(miIntent);
    }

}
