package com.rflores.SportyAndroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HistorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        cargarActividades();
    }

    // Carga los datos del fichero ejercicios
    private void cargarActividades() {
        // variables de GUI
        ListView miListView = (ListView) findViewById(R.id.lvHistorial);
        //Otras variables
        Controlador miControlador = Controlador.getControlador();


        // desserializamos para conseguir la lista de actividades
        miControlador.getCOActividad().unSerializaActividades(miControlador.getContext());
        // array de strong para cargar el adapter
        ArrayList<String> strOpciones=null;
        // primera línea del listview
        strOpciones.add("\tUsuario\tEjercicio\tFecha\tHora\n");
        //cargamos los strings en variable temporal
        for (int i=0;i<miControlador.getCOActividad().getSize();i++) {
            strOpciones.add(miControlador.getCOActividad().getActiviadadByIndex(i).getUsuario()+"\t"+
                    miControlador.getCOActividad().getActiviadadByIndex(i).getEjercicio().getDescripcion()+"\t"+
                    miControlador.getCOActividad().getActiviadadByIndex(i).getFecha()+"\t"+
                    miControlador.getCOActividad().getActiviadadByIndex(i).getHora()+"\n");
        }
        // creamos el arrayadapter y el lisview
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(miControlador.getContext(),android.R.layout.simple_list_item_1,strOpciones);
        miListView.setAdapter(adapter);

    }
}
