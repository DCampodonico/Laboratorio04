package dam.isi.frsf.utn.edu.ar.laboratorio04;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Locale;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Reserva;
import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Usuario;

import static android.content.ContentValues.TAG;
import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificacionReserva extends BroadcastReceiver {

    public NotificacionReserva() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "notificar");

        Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");
        Reserva reserva = (Reserva) intent.getSerializableExtra("reserva");
        Departamento alojamiento = reserva.getAlojamiento();
        Intent intentReservas = new Intent(context, ReservasActivity.class);
        intentReservas.putExtra("usuario", usuario);
        PendingIntent piReservas = PendingIntent.getActivity(context, 2, intentReservas, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(context.getResources().getString(R.string.titulo_reserva_aceptada));

        String text = context.getResources().getString(R.string.texto_reserva_aceptada);
        text = String.format(Locale.getDefault(), text, alojamiento.getDescripcion(), alojamiento.getCiudad(), reserva.getFechaInicio(), reserva.getFechaFin());

        builder
                 .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                 .setAutoCancel(true)
                 .setContentIntent(piReservas);

        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(context);
        String strRingtonePreference = preferencias.getString("opcionRingtone", "DEFAULT_SOUND");
        Uri defaultSoundUri = Uri.parse(strRingtonePreference);
        builder.setSound(defaultSoundUri);

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
