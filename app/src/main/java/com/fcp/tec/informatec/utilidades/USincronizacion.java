package com.fcp.tec.informatec.utilidades;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;


public final class USincronizacion
{
    private long FRECUENCIA_SINCRONIZACION = 256000;

    public void sincronizarAutomaticamente(Context context, String autoridad)
    {


        Account cuentaActiva = UCuentas.obtenerCuentaActiva(context);


        //verificación para evitar iniciar más de una sincronización a la vez.
        if (ContentResolver.isSyncActive(cuentaActiva, autoridad))
        {
            Log.i("Sincronizacion", "Ya existe una sincronización en curso");
            return;
        }

        if (UWeb.hayConexion(context))
        {
            ContentResolver.setSyncAutomatically(cuentaActiva, autoridad, true);
            ContentResolver.addPeriodicSync(cuentaActiva, autoridad, new Bundle(), FRECUENCIA_SINCRONIZACION);
        }

    }

}
