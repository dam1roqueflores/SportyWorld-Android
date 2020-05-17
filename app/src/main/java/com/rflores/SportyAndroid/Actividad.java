package com.rflores.SportyAndroid;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Actividad {
    private Time hora;
    private Date fecha;
    private String usuario;
    private Ejercicio ejercicio;

    public Actividad (Time miHora, Date miFecha, String miUsuario, Ejercicio miEjercicio){
        hora=miHora;
        fecha=miFecha;
        usuario=miUsuario;
        ejercicio=miEjercicio;
    }

}
