package com.fcp.tec.informatec.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class ContractPreguntasTest
{
    public static final String AUTORIDAD = "com.fcp.tec.preguntastest";//La autoridad puede ser la que sea

    public static final Uri URI_CONTENIDO_BASE = Uri.parse("content://" + AUTORIDAD);

    public static final String RECURSO_PREGUNTA = "pregunta"; //El recurso es el nombre de la tabla

    interface ColumnasSincronizacion
    {
        String MODIFICADO = "modificado";
        String INSERTADO = "insertado";
    }

    interface ColumnasPreguntasTest
    {
        String ID_PREGUNTA = "id_pregunta";
        String PREGUNTA = "pregunta";
        String OPCION = "opcion";
        String VERSION = "version";
    }

    public static class ControladorPreguntasTest implements BaseColumns, ColumnasSincronizacion, ColumnasPreguntasTest
    {
        public static final Uri URI_CONTENIDO = URI_CONTENIDO_BASE.buildUpon().appendPath(RECURSO_PREGUNTA).build();

        public final static String MIME_RECURSO = "vnd.android.cursor.item/vnd."+AUTORIDAD+"/"+RECURSO_PREGUNTA;
        public final static String MIME_COLECCION = "vnd.android.cursor.dir/vnd."+AUTORIDAD+"/"+RECURSO_PREGUNTA;


        //Métodos de ayuda de generacion de URIS específicas.
        public static Uri construirUriPreguntas(String idPregunta)
        {
            return URI_CONTENIDO.buildUpon().appendPath(idPregunta).build();
        }

        public static String obtenerIdPregunta(Uri uri)
        {
            return uri.getLastPathSegment();
        }

    }
}