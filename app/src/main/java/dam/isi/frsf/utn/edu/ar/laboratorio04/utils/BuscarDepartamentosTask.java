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

package dam.isi.frsf.utn.edu.ar.laboratorio04.utils;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Ciudad;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Departamento;

/**
 * Created by martdominguez on 22/09/2016.
 */
public class BuscarDepartamentosTask extends AsyncTask<FormBusqueda,Integer,List<Departamento>> {

    private BusquedaFinalizadaListener<Departamento> listener;

    public BuscarDepartamentosTask(BusquedaFinalizadaListener<Departamento> dListener){
        this.listener = dListener;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Departamento> departamentos) {
        listener.busquedaFinalizada(departamentos);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        listener.busquedaActualizada("departamento "+values[0]+"/"+values[1]);

    }

    @Override
    protected List<Departamento> doInBackground(FormBusqueda... busqueda) {
        List<Departamento> todos = Departamento.getAlojamientosDisponibles();
        List<Departamento> resultado = new ArrayList<>();
        int contador = 0;
        Ciudad ciudadBuscada = busqueda[0].getCiudad();
        Double precioMinimo = busqueda[0].getPrecioMinimo();
        Double precioMaximo = busqueda[0].getPrecioMaximo();
        Boolean permiteFumar = busqueda[0].getPermiteFumar();
        Integer huespedes = busqueda[0].getHuespedes();

        for (Departamento d: todos) {
            if(ciudadBuscada.equals(d.getCiudad())){
                if(precioMinimo <= d.getPrecio() && precioMaximo >= d.getPrecio()){
                    if(permiteFumar.equals(d.getNoFumador())){
                        if(huespedes <= d.getCantidadHabitaciones()){
                            resultado.add(d);
                        }
                    }
                }
            }

            publishProgress(contador++,todos.size());
            try {
                Thread.sleep(10);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return resultado;
    }
}
