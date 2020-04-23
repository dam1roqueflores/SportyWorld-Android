package com.rflores.SportyAndroid;


import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class GUI {
    /////////////////////////////////////////////
// ESTADOS
// Parte del GUI


//labels
    private TextView LResultado;
    private TextView LUser;

    // TextViews
    private EditText Tminutos;
    private EditText Tkilos;
// spinner
    private Spinner Cactividad;


    private MainActivity miMainActivity;

//private SpringLayout SprLayout;
///////////////////////////////////////////
// Comportamientos
//////////////////////////////////////////

// Constructor

    public GUI(MainActivity miContexto) {
        // definimos contexto
        miMainActivity= miContexto;

        //definimos Labels
        LResultado=miContexto.findViewById(R.id.LAResultado);
        LUser=miContexto.findViewById(R.id.LAUser);

        //Definimos los textfield
        Tminutos=miContexto.findViewById(R.id.TEMinutos);
        Tkilos=miContexto.findViewById(R.id.TEKgr);

        //definimos el combobox
        Cactividad=miContexto.findViewById(R.id.CBEjercicio);;

    }
///////////////////////////////////////////////////////////
    //GETTERS
///////////////////////////////////////////////////////////
    public EditText getTminutos() {
        return Tminutos;
    }
    public EditText getTkilos() {
        return Tkilos;
    }
    public Spinner getCactividad() {
        return Cactividad;
    }
    public TextView getLResultado() {
        return LResultado;
    }
///////////////////////////////////////////////////////////
    //SETTERS
///////////////////////////////////////////////////////////
    public void setLResultado(String resultado) {
    LResultado.setText(resultado);
    }

    public void setLUser(String user) {
        this.LUser.setText(user);
    }
}
