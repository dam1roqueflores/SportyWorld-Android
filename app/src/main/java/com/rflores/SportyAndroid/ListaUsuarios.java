package com.rflores.SportyAndroid;

import java.util.ArrayList;

public class ListaUsuarios {
    ////////////////
    //Estados
    ///////////////
    //Lista de usuarios disponibles
    private ArrayList<Usuario> listaUsuarios ;
    /////////////////////
    //  COMPORTAMIENTOS
    ////////////////////
    //  CONSTRUCTOR
    ///////////////////
    public ListaUsuarios() {
        listaUsuarios=new ArrayList();
    }
    ///////////////////////////////////
    //  RESTO DE COMPORTAMIENTOS
    //comportamiento para añadir un usuario a la lista
    public void addUsuario(Usuario miUsuario ) {

        listaUsuarios.add(miUsuario);
    }

        // comprobamos que existe el usuario con la misma descripción que nos pasan
    public boolean existeUser(String descr) {
        boolean encontrado=false;
        int contador=0;

        //busco en la lista el que se llame igual. Empiezo por el primero y termino por el final
        while (!encontrado && contador < listaUsuarios.size()) {
            if (listaUsuarios.get(contador).getUsuario().compareTo(descr)==0) {
                encontrado = true;
            }
            contador= contador+1;
        }
        return encontrado;
    }
    //Devolvemos el usuario que se llame igual que la descripción que nos dan
    public Usuario getUsuarioByDescr(String descr) {
        Usuario miUsuario=null;
        boolean encontrado=false;
        int contador=0;

        //busco en la lista el que se llame igual. Empiezo por el primero y termino por el final
        while (!encontrado && contador < listaUsuarios.size()) {
            if (listaUsuarios.get(contador).getUsuario().compareTo(descr)==0) {
                encontrado = true;
                miUsuario = listaUsuarios.get(contador);
            } else {
                contador= contador+1;
            }
        }
        return miUsuario;
    }

   //'Devolvemos el número de ejercicios
    public int getSize(){
        return listaUsuarios.size();
    }

}
