package com.rflores.SportyAndroid;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Actividad implements  Serializable{
    private String hora;
    private String fecha;
    private String usuario;
    private Ejercicio ejercicio;

    public Actividad  (String miHora, String miFecha, String miUsuario, Ejercicio miEjercicio){
        hora=miHora;
        fecha=miFecha;
        usuario=miUsuario;
        ejercicio=miEjercicio;
    }
//////////////////////////// GETTERS

    public String getHora() {
        return hora;
    }

    public String getFecha() {
        return fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }
}
