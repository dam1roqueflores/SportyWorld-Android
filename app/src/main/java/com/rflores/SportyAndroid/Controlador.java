package com.rflores.SportyAndroid;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Controlador {

    ////////////////////////////////////////
    //	CONSTANTES
    ///////////////////////////////////////
    //Fichero con los datos
    static private final String FILE_DATOS = "Data.txt";
    static private final String FILE_LOG = "log.txt";
    static public final String FILE_USUARIOS="users.txt";
    static private final String FILE_LOG_USER = "logusr.txt";
    ////////////////////////////////////////
    //Estados
    ////////////////////////////////////////
    //Colección con los tipos de ejercicios disponibles
    static private TipoEjercicio TEColeccion;
    //Coleccion con las actividades guardadas
    static private ListaActividades COActividad;
    // gestión de Usuarios
    static  private ListaUsuarios miListaUsuarios;
    static private String miUsuario;
    //
    static private MainActivity miMainActivity;
    // instanciado de la clase Singleton, variable estática y privada para asegurar una sola instancia del controlador
    static private Controlador controlador=null;

    ///////////////////////////////////////
    // COMPORTAMIENTOS
    ///////////////////////////////////////
    //constructor
    // constructor privado para asegurar una sola instancia del controlador
    private Controlador(MainActivity mainActivity) {
        miListaUsuarios=new ListaUsuarios();
        miUsuario=null;
        miMainActivity=mainActivity;
        TEColeccion = new TipoEjercicio();
        COActividad=new ListaActividades();
    }
    /////////////////////////////////////
    // resto de comportamientos
    /////////////////////////////////////
    // comportamiento singleton, estático y público para poder acceder al constructor del controlador singleton
    static public Controlador getControlador(MainActivity miMainActivity){
        if (controlador==null) {controlador=new Controlador(miMainActivity);        }
        return controlador;
    }
    static public Controlador getControlador(){
        return controlador;
    }

    //comportamiento para devolver las KCAL de una determinada actividad realizada
    static public String calcularKCal(int minutos, float kilos, String descrEjer) {
        String resultado;
        Ejercicio miEjercicio;

        //dado los kg, minutos y el ejercicio seleccionado, creamos los objetos necesarios
        miEjercicio = TEColeccion.getEjercicioByDescr(descrEjer);

        //y después a esos objetos les pedimos trabajar
        resultado = miEjercicio.calcularKCal(minutos, kilos);

        //el resultado del trabajo lo ponemos en la pantalla, concretamente en el LBResultado
        return resultado;
    }

    // inicia datos
    static public void iniciaDatos (Spinner miCombo) {
        int i=0;
        ArrayList<String> strOpciones = new ArrayList<>();

        try {
            cargarDatos();
            cargarUsuarios();
            COActividad.unSerializaActividades(miMainActivity);

            /////////////////////////////////////////////
            // creamos Toast de fichero guardado
            Toast miT = Toast.makeText(miMainActivity,leeFichero(FILE_USUARIOS),Toast.LENGTH_LONG);
            miT.show();
            /////////////////////////////////////////////////
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        for (i=0;i<TEColeccion.getSize();i++) {
            strOpciones.add(TEColeccion.getEjercicioByIndex(i).getDescripcion());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>((Context) miMainActivity,android.R.layout.simple_spinner_item, strOpciones);
        miCombo.setAdapter(adapter);
    }



    // Carga los Usuarios
    static private void cargarUsuarios()  {
        //variables para fechas
        Date fecha =new Date();
        DateFormat timestamp  = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        // otras variables
        String mensaje="";
        int contador=1;
        Usuario miUsuario;
        String linea;

        // recorremos fichero de lectura
        try {
            // Abrimos el fichero en Assets para lectura
            /*InputStream miFichero = miMainActivity.getAssets().open(FILE_USUARIOS);*/
            FileInputStream miFichero= miMainActivity.openFileInput(FILE_USUARIOS);
            BufferedReader bfIn = new BufferedReader(new InputStreamReader(miFichero));
            linea=bfIn.readLine();

            while (linea!=null) {
                if(comprobarErroresUser(linea)=="") {
                    miUsuario= new Usuario(linea);
                    miListaUsuarios.addUsuario(miUsuario);
                } else {
                    //guarda los mensajes para el log
                    mensaje = mensaje + timestamp.format(fecha)+ " - Error en fichero "+FILE_USUARIOS+ " en la línea "+contador+": "+comprobarErrores(linea)+"\n";
                }
                linea=bfIn.readLine();
                contador++;
            }
            miFichero.close();
        } catch (Exception ex){
            System.out.println("Mensaje de la excepción: " + ex.getMessage());
        }

        //escribimos log Usuario
        if (mensaje != "") {
            try {
                FileOutputStream log = miMainActivity.openFileOutput(FILE_LOG_USER,Context.MODE_APPEND);
                PrintWriter impresor = new PrintWriter(log,true);
                impresor.write(mensaje);
                impresor.close();
                // creamos Toast Usuario
                Toast miT = Toast.makeText(miMainActivity,mensaje,Toast.LENGTH_LONG);
                miT.show();
            } catch (Exception ex) {
                System.out.println("Mensaje de la excepción: " + ex.getMessage());
            }

        }
    }

    // comprobar errores de fichero usuarios
    static private String comprobarErroresUser(String entrada) {
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
                if (listaParametros.length>6) {
                    resultado = "hay más de dos columnas";
                } else {
                    // solo una columna
                    if (listaParametros.length==1 ) {
                        //'solo hay una columna
                        resultado = "Sólo hay una columna";
                    } else {
                        // todo correcto no hacemos nada
                    }
                }
            }

        }
        return resultado;
    }

    // Carga los datos del fichero ejercicios
    static private void cargarDatos()  {
        //variables para fechas
        Date fecha =new Date();
        DateFormat timestamp  = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

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
            //recorremos el fichero
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

        //escribimos log ejercicios
        if (mensaje != "") {
            try {
                FileOutputStream log = miMainActivity.openFileOutput(FILE_LOG,Context.MODE_APPEND);
                PrintWriter impresor = new PrintWriter(log,true);
                impresor.write(mensaje);
                impresor.close();
                // creamos Toast
                Toast miT = Toast.makeText(miMainActivity,mensaje,Toast.LENGTH_LONG);
                miT.show();
            } catch (Exception ex) {
                System.out.println("Mensaje de la excepción: " + ex.getMessage());
            }

        }
    }

    // comprobar errores de fichero actividades
   static private String comprobarErrores(String entrada) {
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
    static private boolean comprobarFloat (String unFloat) {
        try {
            float num=Float.parseFloat(unFloat);
        }
        catch(Exception e){
            return false;
        }
        return true;
    }
    // Comprobar el Login
    public Boolean comprobarLogin (String serializado) {
        String[] listaParametros = serializado.split(";");
        String miNombre = listaParametros[0];
        String miPasswd = listaParametros[1];
        if (existeUsuario(miNombre)){
            //Si el usuario existe
            if (getUserByDescr(miNombre).getPasswd().compareTo(miPasswd)==0) {
                // si la contraseña es correcta
                return true;
            } else {
                // si la contraseña no es correcta
                return false;
            }
        } else {
            //  si no encuentra el usuario
            return false;
        }

    }

    // comprueba si existe el usuario
    public boolean existeUsuario (String miNombre){
        return miListaUsuarios.existeUser(miNombre);
    }

    // devuelve usuario buscando por descripción
    public Usuario getUserByDescr(String miNombreUsuario){
        return miListaUsuarios.getUsuarioByDescr(miNombreUsuario);
    }

    // añadimos el nuevo usuario
    public void addUsuario(Usuario miUsuario) {
        String registro;

        //  Añadimos nuevo usuario a la lista
        miListaUsuarios.addUsuario(miUsuario);
        //Añadimos nuevo usuario al fichero
        registro = miUsuario.serializar();
        try {
            FileOutputStream fos = miMainActivity.openFileOutput(FILE_USUARIOS,miMainActivity.MODE_APPEND);
            OutputStreamWriter ow = new OutputStreamWriter(fos);
            ow.append(registro);
            ow.flush();
            ow.close();

            // creamos Toast de fichero guardado
            Toast miT = Toast.makeText(miMainActivity,leeFichero(FILE_USUARIOS),Toast.LENGTH_LONG);
            miT.show();
        } catch (Exception ex) {
            System.out.println("Mensaje de la excepción: " + ex.getMessage());
        }
    }
    // borra un fichero
    public static void borrarFichero(String nomfile){
        //File fichero = new File (nomfile);
        Toast miToast;


        if (miMainActivity.deleteFile(nomfile)){
            miToast= Toast.makeText(miMainActivity,"El fichero "+nomfile+" ha sido borrado satisfactoriamente",Toast.LENGTH_LONG);
        } else {
            miToast= Toast.makeText(miMainActivity,"Error al borrar el fichero  "+nomfile,Toast.LENGTH_LONG);
        }
        miToast.show();
    }
    //lee un fichero y devuelve un string con su contenido
    public static String leeFichero(String nomfile) {
        int contador=0;
        String mensaje="";
        try {
            // Abrimos el fichero en Assets para lectura
            //File miFichero= new File(nomfile);
            InputStream miIS = miMainActivity.openFileInput(nomfile);
            BufferedReader bfIn = new BufferedReader(new InputStreamReader(miIS));
            String linea = bfIn.readLine();

            while (linea != null) {
               //guarda los mensajes para devolver el resultado
                mensaje = mensaje + contador + ": " + linea + "\n";
                linea = bfIn.readLine();
                contador++;
            }
            miIS.close();
        } catch (Exception ex) {
            System.out.println("Mensaje de la excepción: " + ex.getMessage());
        }
        return mensaje;
    }
    // dispose controlador
    public void dispose(){
        this.dispose();
    }
    public Context getContext() {
        return miMainActivity;
    }
    ////////////////////////////////////////////////////////
    //      GETTERS
    ////////////////////////////////////////////////////////

    public static String getMiUsuario() {
        return miUsuario;
    }
    public ListaActividades getCOActividad() {
        return COActividad;
    }
    /////////////////////////////////////////////////////////////
    //      SETTERS
    ////////////////////////////////////////////////////////
    public void setUsuario(String serializado) {
       miUsuario =serializado;
    }

    public TipoEjercicio getTEColeccion() {
        return TEColeccion;
    }



}
