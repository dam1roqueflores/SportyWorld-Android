package com.rflores.SportyAndroid;

import android.content.Context;

import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class ListaActividades implements Serializable {
    //Estados

    private final String FILE_ACT="actividades.dat";
    //Lista de actividades disponibles
    private ArrayList<Actividad> listaActividades;

    public ListaActividades() {
        listaActividades=new ArrayList();
    }

    //comportamiento para añadir una actividad a la lista
    public void addActividad(Actividad miActividad ) {
        listaActividades.add(miActividad);
    }

    //Devolvemos las actividades que nos pidan
    public Actividad getActiviadadByIndex(int indice) {

        //miramos en la lista el ejercicio que necesitamos
        return listaActividades.get(indice);
    }

    //'Devolvemos el número de actividades
    public int getSize(){
        return listaActividades.size();
    }

    //serializamos la lista
    public void serializaActividades (Context myContext){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(myContext.openFileOutput(FILE_ACT, myContext.MODE_PRIVATE));
            oos.writeObject(listaActividades);
            oos.close();
        } catch (Exception ex) {
            System.out.println("Mensaje de la excepción: " + ex.getMessage());
        }
    }

    //desserializamos la lista y devolvemos el objeto
    public void unSerializaActividades (Context myContext){

        try {
            ObjectInputStream ois = new ObjectInputStream(myContext.openFileInput(FILE_ACT));
            listaActividades = (ArrayList<Actividad>) ois.readObject();
            ois.close();
        } catch (Exception ex) {
            System.out.println("Mensaje de la excepción: " + ex.getMessage());
        }
    }
}
