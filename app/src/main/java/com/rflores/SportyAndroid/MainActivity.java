package com.rflores.SportyAndroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

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

        //creamos el controlador
        miControlador=new Controlador(this);
        // creamos el GUI
        miGUI=new GUI(this);

        //
        miControlador.iniciaDatos(miGUI.getCactividad());
    }
    // Añadimos Listener a los botones
    public void btSalir (View view) {
        System.exit(0);
    }

    public void btCalcular(View view) {
        int minutos;
        float kilos;
        kilos=Float.parseFloat(String.valueOf(miGUI.getTkilos().getText()));
        minutos=Integer.parseInt(String.valueOf(miGUI.getTminutos().getText()));
        String actividad=(String) miGUI.getCactividad().getSelectedItem();


        miGUI.setLResultado(miControlador.calcularKCal(minutos, kilos, actividad));
    }

}
