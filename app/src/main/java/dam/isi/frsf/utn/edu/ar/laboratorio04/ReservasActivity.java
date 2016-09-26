package dam.isi.frsf.utn.edu.ar.laboratorio04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Reserva;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Usuario;

public class ReservasActivity extends AppCompatActivity {


    private ListView lvReservas;
    private List<Reserva> listaReservas;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);
        lvReservas = (ListView) findViewById(R.id.listaReservas);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        usuario = (Usuario) intent.getSerializableExtra("usuario");
        listaReservas = usuario.getReservas();
        Toast.makeText(getApplicationContext(), listaReservas.size()+"",Toast.LENGTH_SHORT).show(); ;
        ReservaAdapter  reservaAdapter= new ReservaAdapter(ReservasActivity.this, listaReservas);
        lvReservas.setAdapter(reservaAdapter);
    }
}
