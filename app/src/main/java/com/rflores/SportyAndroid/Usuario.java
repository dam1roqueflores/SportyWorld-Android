package com.rflores.SportyAndroid;

public class Usuario {
    //////////////////////////////////
    //ESTADOS
    /////////////////////////////////
    String usuario;
    String passwd;

    ////////////////////////////////
    //COMPORTAMIENTOS
    /////////////////////////////////
    //Constructor
    public Usuario (String serializado){
        if (serializado!=null) {
        String[] listaParametros = serializado.split(";");
        usuario = listaParametros[0];
        passwd = listaParametros[1];}
    }
    ////////////////////////////////
    //Resto de comportamientos
    ///////////////////////////////

    //GETTERS
    public String getUsuario() {
        return usuario;
    }
    public String getPasswd(){
        return passwd;
    }

}
