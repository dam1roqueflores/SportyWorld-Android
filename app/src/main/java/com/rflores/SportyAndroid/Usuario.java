package com.rflores.SportyAndroid;

public class Usuario {
    //////////////////////////////////
    //ESTADOS
    /////////////////////////////////
    String usuario;
    String passwd;
    String nombre;
    String apellidos;
    String dni;
    String email;
    ////////////////////////////////
    //COMPORTAMIENTOS
    /////////////////////////////////
    //Constructor
    public Usuario (String serializado){
        if (serializado!=null) {
        String[] listaParametros = serializado.split(";");
        usuario = listaParametros[0];
        passwd = listaParametros[1];
        nombre = listaParametros[2];
        apellidos = listaParametros[3];
        dni = listaParametros[4];
        email=listaParametros[5];
        }
    }

    public String serializar (){
        return usuario +";"+ passwd +";"+nombre +";"+apellidos +";"+dni +";"+email+"\n";
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
