package com.rflores.sportyworld;

import java.util.ArrayList;

public class TipoEjercicio {
//Estados

    //Lista de ejercicios disponibles
    //ArrayList<String> nombreArrayList = new ArrayList<String>();
    private ArrayList<Ejercicio> listaEjercicios ;

    public TipoEjercicio() {
        listaEjercicios=new ArrayList();
    }

    //comportamiento para añadir un ejercicio a la lista
    public void addEjercicio(Ejercicio miEjercicio ) {
        listaEjercicios.add(miEjercicio);
    }

    //Devolvemos los ejercicios que nos pidan

    public Ejercicio getEjercicioByIndex(int indice) {

        //miramos en la lista el ejercicio que necesitamos
        return listaEjercicios.get(indice);


    }


    //Devolvemos el ejercicio que se llame igual que la descripción que nos dan
    public Ejercicio getEjercicioByDescr(String descr) {
        Ejercicio miEjercicio=null;
        boolean encontrado;
        int n;

        encontrado = false;
        n = 0;

        //busco en la lista el que se llame igual. Empiezo por el primero y termino por el final
        while (!encontrado && n < listaEjercicios.size()) {   //es un if encubierto
            //busco
            if (listaEjercicios.get(n).getDescripcion() == descr) {
                encontrado = true;
                miEjercicio = listaEjercicios.get(n);
            } else {
                n = n+1;

            }

        }
        return miEjercicio;
    }

    //'Devolvemos el número de ejercicios
    public int getSize(){

        return listaEjercicios.size();

    }

}
