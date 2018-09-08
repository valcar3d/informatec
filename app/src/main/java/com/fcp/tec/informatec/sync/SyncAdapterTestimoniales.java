package com.fcp.tec.informatec.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fcp.tec.informatec.modelos.JsonAModeloTestimonios;
import com.fcp.tec.informatec.provider.ContractTestimoniales;
import com.fcp.tec.informatec.utilidades.UToasts;
import com.fcp.tec.informatec.utilidades.UURLS;
import com.fcp.tec.informatec.web.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SyncAdapterTestimoniales extends AbstractThreadedSyncAdapter
{
    public SyncAdapterTestimoniales(Context context, boolean autoInitialize)
    {
        super(context, autoInitialize);
        cResolver = context.getContentResolver();
    }


    private final String TAG = SyncAdapterTestimoniales.class.getSimpleName();
    private final ContentResolver cResolver;



    /**
     * Este método es invocado cuando se sincroniza de manera manual o automática
     */
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult)
    {
        Log.i(TAG, "Entrando a onPerformSync() con la cuenta" + account);
        Log.i(TAG, "Sincronizando desde la URL_TESTIMONIOS: " + UURLS.URL_TESTIMONIOS);

        //Al iniciar la sincronizacion manual o automática se procede a leer la respuesta
        //GET o POST enviada por el servidor desde la URL_TESTIMONIOS dada
        lecturaGET();

    }

    /**
     * Este metodo envia una <b>petición URL_INSTALACIONES</b> para despues leer y almacenar un <B>Objeto de tipo JSON</B>
     * y lo almacena en la variable "response" para su uso posterior.
     * Son obligatorios en una petición URL_INSTALACIONES 2 @Overrides onResponse() y onErrorResponse()
     */
    private void lecturaGET()
    {

        Log.i(TAG, "Ejecucion de método para sincronizar localmente");
        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, UURLS.URL_TESTIMONIOS, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.i(TAG, "onResponse: JSON remoto: " + response.toString());
                        procesarGET(response);
                    }

                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.i(TAG, "onErrorResponse: ", error);
                        //UToasts.showToast(getContext(), "Sin conexión web o muy lenta");
                    }
                });

        //Al llamar el setRetryPolicy controlamos el umbral de tiempo de volley antes de lanzar un error
        //de tiempo y por consiguiente mandar un mensaje al usuario sobre conexión lenta.
        getRequest.setRetryPolicy(new DefaultRetryPolicy(3500, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        //Agregar el request a la fila de procesamiento mediante el singleton de Volley
        VolleySingleton.getInstance(getContext()).addToRequestQueue(getRequest);
    }

    /**
     * este método procesa el json que acaba de obtenerse en la petición URL_CALENDARIO
     * usando el parametro "response" al ser un objeto JSON podemos hacer las operaciones
     * necesarias, de comparación, conversion.
     */
    private void procesarGET(JSONObject response)
    {
        try
        {
            //Se crea una lista de referencia a las operaciones de un content provider
            ArrayList<ContentProviderOperation> cpOperaciones = new ArrayList<>();

            //CONVERTIR la respuesta JSON al modelo correspondiente
            //en este caso se convertirá {calendario[{JSON Objects}]}
            JsonAModeloTestimonios jsonAModeloTestimonios = new JsonAModeloTestimonios();
            //Se procesa la respuesta JSON y se convierte a objeto Carreras.java
            jsonAModeloTestimonios.procesar(response.getJSONArray("testimonios"));


            //Se procesar las operaciones correspondienes al CRUD y comparaciones
            jsonAModeloTestimonios.procesarOperaciones(cpOperaciones, cResolver);


            //Hay operaciones por realizar? EN ESTA SECCION SE INDICAN LOS CAMBIOS ENCONTRADOS
            if (cpOperaciones.size() > 0)
            {

                Log.i(TAG, "Cambios en \'testimonios\': " + cpOperaciones.size() + " operaciones");


                //UToasts.showToast(getContext(), "Se encontraron " + cpOperaciones.size() + " cambios para sincronizar");
                //APLICAR batch de operaciones
                cResolver.applyBatch(ContractTestimoniales.AUTORIDAD, cpOperaciones);

                //NOTIFICAR cambio al Content Provider
                cResolver.notifyChange(ContractTestimoniales.URI_CONTENIDO_BASE, null, false);


            } else
            {
                //Log.i(TAG, "Sin cambios remotos");
                //UToasts.showToast(getContext(), "No hay datos nuevos por sincronizar");
            }

            //Log.i(TAG, "Respuesta JSON a procesar: "+response.getJSONArray("becas"));
        }


        catch (RemoteException | OperationApplicationException | JSONException e)
        {
            e.printStackTrace();
        }


    }
}
