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
            Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");
            Reserva reserva = (Reserva) intent.getSerializableExtra("reserva");
            reserva.setConfirmada(true);
            Log.d(TAG, "reserva: " + reserva.getId());

            Intent intentAlarma = new Intent(context.getApplicationContext(), AlarmReceiver.class);
            PendingIntent piAlarma = PendingIntent.getBroadcast(context.getApplicationContext(), reserva.getId(), intentAlarma, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am =(AlarmManager) context.getApplicationContext().getSystemService(ALARM_SERVICE);
            am.cancel(piAlarma);
            piAlarma.cancel();

            String nombreIntent = "dam.isi.frsf.utn.edu.ar.laboratorio04.notificacion";
            Intent broadcastIntent = new Intent(nombreIntent);
            broadcastIntent.putExtra("usuario", usuario);
            broadcastIntent.putExtra("reserva", reserva);
            context.sendBroadcast(broadcastIntent);
        }
    }
}
