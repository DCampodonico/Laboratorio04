/*
 * Copyright (c) 2016 Daniel Campodonico; Emiliano Gioria; Lucas Moretti.
 * This file is part of Laboratorio04.
 *
 * Laboratorio04 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Laboratorio04 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Laboratorio04.  If not, see <http://www.gnu.org/licenses/>.
 */

package dam.isi.frsf.utn.edu.ar.laboratorio04;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Reserva;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Usuario;

public class AltaReservaActivity extends AppCompatActivity implements View.OnClickListener {

    DatePicker dpFechaInicio, dpFechaFin;
    Date fechaInicio, fechaFin;
    TextView tvDescripcion, tvFechaInicio, tvFechaFin, tvPrecio;
    FloatingActionButton fabtnConfirmar;
    Departamento departamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_reserva);
        setParametros();
        fabtnConfirmar.setOnClickListener(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    private void setParametros(){
        Intent intent = getIntent();
        departamento = (Departamento) intent.getSerializableExtra("departamento");

        tvDescripcion = (TextView) findViewById(R.id.tvDescripcion);
        tvDescripcion.setText(departamento.getDescripcion());
        tvFechaInicio = (TextView) findViewById(R.id.tvFechaInicio);
        tvFechaInicio.setText(R.string.tv_fecha_inicio);
        dpFechaInicio = (DatePicker) findViewById(R.id.dpFechaInicio);
        tvFechaFin = (TextView) findViewById(R.id.tvFechaFin);
        tvFechaFin.setText(R.string.tv_fecha_fin);
        dpFechaFin = (DatePicker) findViewById(R.id.dpFechaFin);
        tvPrecio = (TextView) findViewById(R.id.tvPrecio);
        tvPrecio.setText("$ "+departamento.getPrecio());
        fabtnConfirmar = (FloatingActionButton) findViewById(R.id.fabtnConfirmar);
    }

    private void confirmar(){
        fechaInicio = getDateFromDatePicker(dpFechaInicio);
        fechaFin = getDateFromDatePicker(dpFechaFin);

        String msjError = validarReserva();

        if(msjError==null) {
            //Toast.makeText(getApplicationContext(),msjError,Toast.LENGTH_LONG).show();
            Integer id = 1; //TODO:
            Reserva reserva = new Reserva(id, fechaInicio, fechaFin, departamento);
            Intent i = new Intent();
            i.putExtra("reserva", reserva);
            setResult(0, i);
            finish();
        }else{
            Toast.makeText(getApplicationContext(),msjError,Toast.LENGTH_LONG).show();
        }
    }

    //TODO: Implementar
    private String validarReserva() {
        if(!fechaInicio.before(fechaFin)){
            return "Verifique que la fecha de inicio sea antes que la de finalización";
        }else {
            List<Reserva> reservas = departamento.getReservas();
            for (Reserva r : reservas) {
                if (isOverhead(r.getFechaInicio(), r.getFechaFin(), fechaInicio, fechaFin)) {
                    return "Existe una reserva desde el " + formatFecha(r.getFechaInicio()) + " al " + formatFecha(r.getFechaFin());
                }
            }
        }
        return null;
    }

    private boolean isOverhead(Date inicio1, Date fin1, Date inicio2, Date fin2){
        if(inicio1.before(inicio2)){
            if(fin1.before(inicio2)){
                return false;
            }
        }else if(inicio2.before(inicio1)){
            if (fin2.before(inicio1)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fabtnConfirmar: {
                confirmar();
            } break;
        }
    }

    //Convertir DatePicker a Date
    private Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    //TODO ver
    private String formatFecha(Date fecha){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR);
    }

}
