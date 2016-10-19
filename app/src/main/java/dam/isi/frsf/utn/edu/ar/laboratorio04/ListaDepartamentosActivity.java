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

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.BuscarDepartamentosTask;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.BusquedaFinalizadaListener;
import dam.isi.frsf.utn.edu.ar.laboratorio04.utils.FormBusqueda;

public class ListaDepartamentosActivity extends AppCompatActivity implements BusquedaFinalizadaListener<Departamento>, AdapterView.OnItemClickListener {

    TextView tvEstadoBusqueda;
    ListView listaAlojamientos;
    DepartamentoAdapter departamentosAdapter;
    List<Departamento> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alojamientos);
        lista = new ArrayList<>();
        listaAlojamientos = (ListView) findViewById(R.id.listaAlojamientos);
        tvEstadoBusqueda = (TextView) findViewById(R.id.estadoBusqueda);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Boolean esBusqueda = intent.getExtras().getBoolean("esBusqueda");
        if (esBusqueda && lista.isEmpty()) {
            FormBusqueda fb = (FormBusqueda) intent.getSerializableExtra("frmBusqueda");
            new BuscarDepartamentosTask(ListaDepartamentosActivity.this).execute(fb);
            tvEstadoBusqueda.setText("Buscando....");
            tvEstadoBusqueda.setVisibility(View.VISIBLE);
        } else if(lista.isEmpty()){
            tvEstadoBusqueda.setVisibility(View.GONE);
            lista = Departamento.getAlojamientosDisponibles();
        }
        departamentosAdapter = new DepartamentoAdapter(ListaDepartamentosActivity.this, lista);
        listaAlojamientos.setAdapter(departamentosAdapter);
        listaAlojamientos.setOnItemClickListener(this);
    }

    @Override
    public void busquedaFinalizada(List<Departamento> listaDepartamento) {
        lista.clear();
        lista.addAll(listaDepartamento);
        Toast.makeText(this.getApplicationContext(), lista.size() + " resultados", Toast.LENGTH_SHORT).show();
        tvEstadoBusqueda.setText(getResources().getString(R.string.busqueda_finalizada));
        departamentosAdapter.notifyDataSetChanged();
    }

    @Override
    public void busquedaActualizada(String msg) {
        tvEstadoBusqueda.setText(" Buscando..." + msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Departamento d = (Departamento) listaAlojamientos.getItemAtPosition(position);

        mostrarAltaReservaDialog(d);

    }

    private void mostrarAltaReservaDialog(final Departamento d){
        AlertDialog.Builder altaReservaDialog = new AlertDialog.Builder(this);
        altaReservaDialog.setTitle("Reservar");
        altaReservaDialog.setMessage(d.getDescripcion()+"");
        altaReservaDialog.setCancelable(true);
        altaReservaDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                btnOk(d);
            }
        });
        altaReservaDialog.show();
    }

    public void btnOk(Departamento d) {
        Intent i = new Intent(ListaDepartamentosActivity.this, AltaReservaActivity.class);
        i.putExtra("departamento", d);
        startActivityForResult(i,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((resultCode==0)&&(requestCode==2)){
            if(data != null) {
                setResult(0,data);
                finish();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Ha ocurrido un error en la reserva",Toast.LENGTH_SHORT).show();
        }
    }
}
