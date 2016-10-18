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

import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

public class Preferencias extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    EditTextPreference opcionUsuario;
    EditTextPreference opcionCorreo;
    RingtonePreference opcionRingtone;
    SharedPreferences preferencias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);

        PreferenceManager.setDefaultValues(this, R.xml.preferencias, false);
        preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        opcionUsuario = (EditTextPreference) findPreference("opcionUsuario");
        opcionCorreo = (EditTextPreference) findPreference("opcionCorreo");
        opcionRingtone = (RingtonePreference) findPreference("opcionRingtone");

        opcionCorreo.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (!TextUtils.isEmpty(newValue.toString()) && Patterns.EMAIL_ADDRESS.matcher(newValue.toString()).matches()) {
                    return true;
                }
                else{
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.opcionCorreo_error), Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });
        updatePreferences();

        preferencias.registerOnSharedPreferenceChangeListener(this);
    }

    private void updatePreferences() {


        opcionUsuario.setSummary(preferencias.getString("opcionUsuario", getResources().getString(R.string.opcionUsuario_summary)));
        opcionCorreo.setSummary(preferencias.getString("opcionCorreo", getResources().getString(R.string.opcionCorreo_summary)));

        String strRingtonePreference = preferencias.getString("opcionRingtone", getResources().getString(R.string.opcionRingtone_default));
        Uri ringtoneUri = Uri.parse(strRingtonePreference);
        Ringtone ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
        opcionRingtone.setSummary(ringtone.getTitle(this));
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updatePreferences();
    }
}
