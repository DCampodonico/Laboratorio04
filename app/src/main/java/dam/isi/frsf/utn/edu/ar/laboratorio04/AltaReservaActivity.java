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
import android.widget.Toast;

import java.util.Date;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Reserva;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Usuario;

public class AltaReservaActivity extends AppCompatActivity implements View.OnClickListener {

    private Date fechaInicio;
    private Date fechaFin;
    private FloatingActionButton fabtnConfirmar;
    private Usuario usuario;
    private Departamento departamento;

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
        //fechaInicio = findViewById(R.id.tvFechaInicio);
        //fechaFin = findViewById(R.id.tvFechaFin);
        Intent intent = getIntent();
        departamento = (Departamento) intent.getSerializableExtra("departamento");
        usuario = (Usuario) intent.getSerializableExtra("usuario");
        fechaInicio = new Date();
        fechaFin = new Date();
        fabtnConfirmar = (FloatingActionButton) findViewById(R.id.fabtnConfirmar);
    }

    private void confirmar(){
        Integer id = usuario.getReservas().size()+1;
        Reserva reserva = new Reserva(id,fechaInicio,fechaFin,departamento);
        usuario.setReserva(reserva);
        Toast.makeText(getApplicationContext(),"Se ha realizado la reserva exitosamente! "+usuario.getReservas().size(),Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fabtnConfirmar: {
                confirmar();
            } break;
        }
    }

}
