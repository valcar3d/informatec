package com.fcp.tec.informatec.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.fcp.tec.informatec.R;
import com.fcp.tec.informatec.utilidades.UToasts;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment_Inicio extends Fragment
{
    public Button  btnCarreras, btnIngenierias, btnProcesoAdmiciones, btnPreguntas, btnCalendario, btnContactos, btnTest;
    public FloatingActionButton FloatingActionButton;

    private ImageButton btntec;
    private int numClicks = 0;

    public OneFragment_Inicio()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //Infla el fragment con la vista.
        View view = inflater.inflate(R.layout.fragment_one_item, container, false);
        UToasts.showToast(getContext(),"Comprobando actualizaciones. . .");
        //region Instancia los IDs necesarios


        btnCarreras = (view).findViewById(R.id.button_carreras);
        btnIngenierias = (view).findViewById(R.id.button_ingenierias);
        btnProcesoAdmiciones = (view).findViewById(R.id.button_procesoAdmiciones);
        btnPreguntas = (view).findViewById(R.id.button_preguntas);
        btnCalendario = (view).findViewById(R.id.button_calendario);
        btnContactos = (view).findViewById(R.id.button_contactos);
        FloatingActionButton = (view).findViewById(R.id.fab_inicio);
        btntec = (view).findViewById(R.id.btntec);


        //endregion

        //Obtiene el contexto de este fragment
        final Context context = this.getContext();


        //region Enlace de los botones en las vistas


        btnCarreras.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), OneFragmentItem_Test.class);
                startActivity(intent);
            }
        });
        btnIngenierias.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), OneFragmentItem_Carreras.class);
                startActivity(intent);
            }
        });
        btnPreguntas.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Snackbar.make(v, "Preguntas", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(v.getContext(), OneFragmentItem_Preguntas.class);
                startActivity(intent);
            }
        });

        btnCalendario.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), OneFragmentItem_Calendario.class);
                startActivity(intent);
            }
        });
        btnProcesoAdmiciones.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), OneFragmentItem_Padmin.class);
                startActivity(intent);
            }
        });
        btnContactos.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), OneFragmentItem_Contacto.class);
                startActivity(intent);
            }
        });
        FloatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), OneFragmentItem_Beneficios.class);
                startActivity(intent);
            }
        });


        btntec.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                numClicks += 1;

                if (numClicks == 1)
                {
                    UToasts.showToast(getContext(), "Estas a 2 pasos de conocernos");
                }
                if (numClicks == 2)
                {
                    UToasts.showToast(getContext(), "Estas a 1 paso de conocernos");
                }

                if (numClicks == 3)
                {
                    UToasts.showToast(getContext(), "¿Conocernos?");
                }
                if (numClicks > 3)
                {
                    Log.i("OneFragment_Inicio", "Click en logo TEC!");
                    Intent intent = new Intent(v.getContext(), Acerca_De.class);
                    startActivity(intent);
                    numClicks = 0;
                }


            }
        });
        //endregion

        //Importante el return
        return view;
    }

}
