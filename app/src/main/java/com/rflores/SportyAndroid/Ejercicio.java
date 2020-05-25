package com.rflores.SportyAndroid;

import java.io.Serializable;

public class Ejercicio implements Serializable {
    //Estado
    String descripcion;
    float met;

    //Comportamiento
    //Constructor
    public Ejercicio (String serializado){
        String[] listaParametros = serializado.split(";");
        descripcion = listaParametros[0];
        met = Float.parseFloat(listaParametros[1]);

    }

    //Resto de comportamientos
    //Devolver la descripcion
    public String getDescripcion() {
        return descripcion;
    }

    //Calcular las Kcal
    public String calcularKCal(int minutos, float kilos) {

        String resultado;

        resultado = (met * kilos * minutos / 60.0)+"";

        return resultado;
    }
}
