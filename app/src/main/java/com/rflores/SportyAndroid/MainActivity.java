package com.rflores.SportyAndroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
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
        Button miButton =(Button) this.findViewById(R.id.btCalcular);

        if (miGUI.getTminutos().getText().toString().length()==0 || miGUI.getTkilos().getText().toString().length()==0) {
            Toast miToast = Toast.makeText(this,"Minutos y Kilos no pueden ser nulos",Toast.LENGTH_LONG);
            miToast.show();
        } else {
            // calculamos calorias gastadas
            kilos = Float.parseFloat(String.valueOf(miGUI.getTkilos().getText()));
            minutos = Integer.parseInt(String.valueOf(miGUI.getTminutos().getText()));
            String actividad = (String) miGUI.getCactividad().getSelectedItem();
            miGUI.setLResultado(miControlador.calcularKCal(minutos, kilos, actividad));


            // es cliente vip
            if (miButton.getText()=="Registrar actividad"){
                TextView aminutos = findViewById(R.id.TEMinutos);
                TextView user = findViewById(R.id.LAUser);
                TextView Kgr = findViewById(R.id.TEKgr);
                Spinner ejer = findViewById(R.id.CBEjercicio);

                Intent miIntent = new Intent(this,NuevaActividadActivity.class);
                miIntent.putExtra("minutos",  aminutos.getText());
                miIntent.putExtra("Kgr",  Kgr.getText());
                miIntent.putExtra("user", user.getText());
                miIntent.putExtra("strEjer", (String) miGUI.getCactividad().getSelectedItem());
                startActivity(miIntent);
            }
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        if (miControlador.getMiUsuario()!=null) {
            miGUI.setLUser(miControlador.getMiUsuario());
            Button miButton =(Button) this.findViewById(R.id.btCalcular);
            miButton.setText("Registrar actividad");
            Button miButton1 =(Button) this.findViewById(R.id.btHistorial);
            miButton1.setEnabled(true);
        }
        miControlador.iniciaDatos((Spinner) findViewById(R.id.CBEjercicio));
    }

    // Botón Loguearse
    public void BTLogin(View view) {

        Intent miIntent = new Intent(this,LoginActivity.class);
        startActivity(miIntent);
    }
    ///////////////////////////////////////
    // botonera
    ///////////////////////////////////////
    // Botón ver historial
    public void btHistorial(View view) {

        Intent miIntent = new Intent(this,HistorialActivity.class);
        startActivity(miIntent);
    }

    // Botón ver Usuarios
    public void BTVerUsuarios(View view) {
        // creamos Toast de fichero guardado
        verFichero ("users.txt");
    }

    //  Botón borrar usuario
    public void BTBorrarUsuarios(View view) {
        borrarFichero("users.txt");
    }
    // Botón ver Log
    public void BTVerLog(View view) {
        // creamos Toast de fichero guardado
        verFichero ("log.txt");
    }

    //  Botón borrar log
    public void BTBorrarLog(View view) {
        borrarFichero("log.txt");
    }
    // Botón ver Log Carga Usuarios
    public void BTVerLogU(View view) {
        // creamos Toast de fichero guardado
        verFichero ("logusr.txt");
    }

    //  Botón borrar log Carga Usuarios
    public void BTBorrarLogU(View view) {
        borrarFichero("logusr.txt");
    }
    /////////////////////////////////////////
    //  otros comportamientos
    ////////////////////////////////////////
    // Ver fichero
    private void verFichero (String miFichero){
        Toast miT = Toast.makeText(this,miControlador.leeFichero(miFichero),Toast.LENGTH_LONG);
        miT.show();
    }

    // Borrar fichero
    private void borrarFichero(String miFichero){
        miControlador.borrarFichero(miFichero);
    }
}
