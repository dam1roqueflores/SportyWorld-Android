package com.rflores.sportyworld;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.AccessMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class Controlador {

    ////////////////////////////////////////
    //	CONSTANTES
    ///////////////////////////////////////
    //Fichero con los datos
    private final String FILE_DATOS = "Data.txt";
    private final String FILE_LOG = "log.txt";
    ////////////////////////////////////////
    //Estados
    ////////////////////////////////////////

    //Colección con los tipos de ejercicios disponibles
    private TipoEjercicio TEColeccion;
    private MainActivity miMainActivity;

    ///////////////////////////////////////
    // Comportamientos
    ///////////////////////////////////////

    //constructor
    public Controlador(MainActivity mainActivity) {
        TEColeccion = new TipoEjercicio();
        miMainActivity=mainActivity;
    }
    /////////////////////////////////////
    // resto de comportamientos
    /////////////////////////////////////
    //comportamiento para devolver las KCAL de una determinada actividad realizada
    public String calcularKCal(int minutos, float kilos, String descrEjer) {

        String resultado;
        Ejercicio miEjercicio;


        //dado los kg, minutos y el ejercicio seleccionado, creamos los objetos necesarios
        miEjercicio = TEColeccion.getEjercicioByDescr(descrEjer);

        //y después a esos objetos les pedimos trabajar
        resultado = miEjercicio.calcularKCal(minutos, kilos);


        //el resultado del trabajo lo ponemos en la pantalla, concretamente en el LBResultado
        return resultado;
    }

    // inicia datos de spinner
    public void iniciaDatos (Spinner miCombo) {
        int i=0;
        ArrayList<String> strOpciones = new ArrayList<>();

        try {
            cargarDatos();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        for (i=0;i<TEColeccion.getSize();i++) {
            strOpciones.add(TEColeccion.getEjercicioByIndex(i).getDescripcion());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>((Context) miMainActivity,android.R.layout.simple_spinner_item, strOpciones);
        miCombo.setAdapter(adapter);
    }

    // Carga los datos del fichero
    private void cargarDatos()  {


        //variables para fechas
        Date fecha =new Date();
        DateFormat timestamp  = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        // variables para ficghero log

        // otras variables
        String mensaje="";
        int contador=1;
        Ejercicio miEjercicio;
        String linea;
        // recorremos fichero de lectura
        try {
            // Abrimos el fichero en Assets para lectura
            InputStream miFichero = miMainActivity.getAssets().open(FILE_DATOS);
            BufferedReader bfIn = new BufferedReader(new InputStreamReader(miFichero));
            linea=bfIn.readLine();
            while (linea!=null) {
                if(comprobarErrores(linea)=="") {
                    miEjercicio= new Ejercicio(linea);
                    TEColeccion.addEjercicio(miEjercicio);
                } else {
                    //guarda los mensajes para el log
                    mensaje = mensaje + timestamp.format(fecha)+ " - Error en fichero "+FILE_DATOS+ " en la línea "+contador+": "+comprobarErrores(linea)+"\n";
                }
                linea=bfIn.readLine();
                contador++;
            }
            miFichero.close();
        } catch (Exception ex){
            System.out.println("Mensaje de la excepción: " + ex.getMessage());
        }


        //escribimos log
        if (mensaje != "") {
            try {
                FileOutputStream log = miMainActivity.openFileOutput(FILE_LOG,Context.MODE_APPEND);
                PrintWriter impresor = new PrintWriter(log,true);
                impresor.write(mensaje);
                impresor.close();

            } catch (Exception ex) {
                System.out.println("Mensaje de la excepción: " + ex.getMessage());
            }

        }
    }


    // comprobar errores
    private String comprobarErrores(String entrada) {

        String resultado;
        String[] listaParametros = entrada.split(";");

        resultado = "";
        //'linea vacia
        if(entrada.isEmpty()) {
            resultado = "Línea vacia";
        } else {
            //'hay al menos uno ;
            if (listaParametros.length > 0) {
                //'Hay más de dos ;
                if (listaParametros.length>2) {
                    resultado = "hay más de dos columnas";
                } else {
                    if (listaParametros.length==1 ) {
                        //'solo hay una columna
                        resultado = "Sólo hay una columna";
                    } else {
                        if (comprobarFloat(listaParametros[1])){
                            // todo correcto no hacemos nada
                        }else{
                            resultado = "La segunda columna no es un número decimal";
                        }
                    }
                }
            }

        }
        return resultado;
    }
// comprueba el float
    private boolean comprobarFloat (String unFloat) {
        try {
            float num=Float.parseFloat(unFloat);
        }
        catch(Exception e){
            return false;
        }
        return true;
    }

    // dispose controlador
    public void dispose(){
        this.dispose();
    }
    // escribimos el log de errores
    private void escribeLog(String error, int numLinea) {




    }
}
