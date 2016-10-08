package dam.isi.frsf.utn.edu.ar.laboratorio04;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Reserva;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Usuario;

import static android.content.ContentValues.TAG;
import static android.content.Context.ALARM_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        long tiempo = System.currentTimeMillis();
        Log.d(TAG, "alarma: " + tiempo);
        if(tiempo % 3 == 0){
            Intent intentAlarma = new Intent(context.getApplicationContext(), AlarmReceiver.class);
            PendingIntent piAlarma = PendingIntent.getBroadcast(context.getApplicationContext(), 1, intentAlarma, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am =(AlarmManager) context.getApplicationContext().getSystemService(ALARM_SERVICE);
            am.cancel(piAlarma);
            piAlarma.cancel();

            Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");
            Reserva reserva = (Reserva) intent.getSerializableExtra("reserva");
            reserva.setConfirmada(true);

            String nombreIntent = "dam.isi.frsf.utn.edu.ar.laboratorio04.notificacion";
            Intent broadcastIntent = new Intent(nombreIntent);
            broadcastIntent.putExtra("usuario", usuario);
            broadcastIntent.putExtra("reserva", reserva);
            context.sendBroadcast(broadcastIntent);
        }
    }
}
