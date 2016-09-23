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
