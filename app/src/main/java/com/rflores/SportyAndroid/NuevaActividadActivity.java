package com.rflores.SportyAndroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Date;

public class NuevaActividadActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String CERO = "0";
    private static final String BARRA = "/";
    private static final String DOS_PUNTOS = ":";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int year = c.get(Calendar.YEAR);

    //Widgets de fecha
    EditText etFecha;
    ImageButton ibObtenerFecha;

    //Variables para obtener la hora hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    // Descripción del ejercicio y usuario para crear nuestro ejercicio asociado a la actividad
    private String strEjercicio=null;
    private String strUser=null;

    //Widgets de hora
    EditText etHora;
    ImageButton ibObtenerHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_actividad);

    // carga los labels con los editext de la actividad principal con parámetros de android
        // getExtras y setExtras
        TextView miLAMinutosNA = findViewById(R.id.LAMinutosNA);
        miLAMinutosNA.setText(getIntent().getExtras().getString("minutos"));

        TextView miLAKgrNA = findViewById(R.id.LAKgrNA);
        miLAKgrNA.setText(getIntent().getExtras().getString("Kgr"));



       strEjercicio=getIntent().getExtras().getString("strEjer");
       strUser=getIntent().getExtras().getString("user");

    //Widget EditText donde se mostrara la fecha obtenida
    etFecha = (EditText) findViewById(R.id.et_mostrar_fecha_picker);
    //Widget ImageButton del cual usaremos el evento clic para obtener la fecha
    ibObtenerFecha = (ImageButton) findViewById(R.id.ib_obtener_fecha);
    //Evento setOnClickListener - clic
    ibObtenerFecha.setOnClickListener(this);

    //Widget EditText donde se mostrara la hora obtenida
    etHora = (EditText) findViewById(R.id.et_mostrar_hora_picker);
    //Widget ImageButton del cual usaremos el evento clic para obtener la hora
    ibObtenerHora = (ImageButton) findViewById(R.id.ib_obtener_hora);
    //Evento setOnClickListener - clic
    ibObtenerHora.setOnClickListener(this);
    }

    /////////////////// listener
    @Override
    public void onClick(View v) {
        AlertDialog.Builder bldr = new AlertDialog.Builder(this);
        bldr.setTitle("Error");
        bldr.setPositiveButton("Aceptar",null);
        bldr.setMessage("Sin mensaje");
        AlertDialog dialog=bldr.create();

        switch (v.getId()){
            case R.id.ib_obtener_fecha:
                obtenerFecha();
                break;
            case R.id.ib_obtener_hora:
                obtenerHora();
                break;
            case R.id.btOk:
                btOk();
                break;
            default:
                bldr.setMessage("No existe ese id");
                dialog.show();
        }
    }
/////////////////////////////////   Botones
    private void btOk ( ){
        // variables de la GUI
        TextView miLAMinutos = findViewById(R.id.LAMinutosNA);
        TextView miLAKgr = findViewById(R.id.LAKgrNA);
        TextView miFechaPicker = findViewById(R.id.et_mostrar_fecha_picker);
        EditText miHoraPicker = findViewById(R.id.et_mostrar_hora_picker);

        // variables otras
        Controlador miControlador = Controlador.getControlador();
        Ejercicio miEjercicio;
        miEjercicio = miControlador.getTEColeccion().getEjercicioByDescr(strEjercicio);
        String miHora= String.valueOf(miHoraPicker.getText());
        String miFecha = String.valueOf(miFechaPicker.getText());
        String miUser= strUser;
        // creamos la actividad
        Actividad miActividad=new Actividad(miHora,miFecha,miUser,miEjercicio);;
        // guardamos la actividad
        miControlador.getCOActividad().addActividad(miActividad);
        //Comprobar campos nulos
        if (miFechaPicker.getText().toString().length() == 0 || miHoraPicker.getText().toString().length() == 0) {
            Toast miToast = Toast.makeText(this, "Hay que rellenar todos los campos", Toast.LENGTH_LONG);
            miToast.show();
            // los campos obligatorios no son nulos
        } else {
            // obtenemos la lista del controlador
            ListaActividades miListaAct = miControlador.getCOActividad();
            // creamos actividad
            miActividad=new Actividad(String.valueOf(miHoraPicker.getText()), String.valueOf(miFechaPicker.getText()),(String) strUser, miEjercicio);
            // añadimos actividad
            miListaAct.addActividad(miActividad);
            //serializamos la lista
            miListaAct.serializaActividades(miControlador.getContext());
            // el login es correcto hay que pasar los datos al controlador
            miControlador.setUsuario(String.valueOf(strUser));
            // volvemos a la activity anterior
            finish();
            }

    }
/////////////////////////////////    obtenemos la hora
    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                etFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },year, mes, dia);
        //Muestro el widget
        recogerFecha.show();
    }

    private void obtenerHora(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                etHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);

        recogerHora.show();
    }

}
