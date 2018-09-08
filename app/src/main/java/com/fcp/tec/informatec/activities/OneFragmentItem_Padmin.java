package com.fcp.tec.informatec.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.fcp.tec.informatec.DatabaseHelper;
import com.fcp.tec.informatec.R;
import com.fcp.tec.informatec.provider.ContractReqExaAdmision;
import com.fcp.tec.informatec.provider.ContractReqInscripcion;
import com.fcp.tec.informatec.utilidades.USincronizacion;

public class OneFragmentItem_Padmin extends AppCompatActivity
{

    Button btnInscriExani, btnInscripcion;
    Cursor inscriExani, inscripcion;
    TextView card_text;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ini_var_padmin);


        USincronizacion uSincronizacion = new USincronizacion();
        uSincronizacion.sincronizarAutomaticamente(this, ContractReqExaAdmision.AUTORIDAD, 1);
        uSincronizacion.sincronizarAutomaticamente(this, ContractReqInscripcion.AUTORIDAD, 1);

        card_text = (TextView) findViewById(R.id.card_text_descripcion_logros);
        card_text.setMovementMethod(new ScrollingMovementMethod());

                btnInscriExani = (Button) findViewById(R.id.btnReq1);
                btnInscripcion = (Button) findViewById(R.id.btnReq2);

                DatabaseHelper databaseHelper = new DatabaseHelper(OneFragmentItem_Padmin.this);
                final SQLiteDatabase database = databaseHelper.getReadableDatabase();




                inscriExani = database.rawQuery("SELECT * FROM requisitos_exa_admision", null);
                String texto = "";

                if (inscriExani.moveToFirst())
                {

                    do
                    {
                        texto += "\u2022 " + inscriExani.getString(1) +"\n" + "\n";
                    }

                    while (inscriExani.moveToNext());
                    card_text.setText(texto);
                    btnInscriExani.setBackgroundResource(R.drawable.toggle_button_left_on);

                }


                btnInscriExani.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {

                inscriExani = database.rawQuery("SELECT * FROM requisitos_exa_admision", null);
                String texto = "";

                if (inscriExani.moveToFirst())
                {

                    do
                    {
                        texto += "\u2022 " + inscriExani.getString(1) + "\n" + "\n";
                    }

                    while (inscriExani.moveToNext());
                    card_text.setText(texto);
                    btnInscriExani.setBackgroundResource(R.drawable.toggle_button_left_on);
                    btnInscripcion.setBackgroundResource(R.drawable.toggle_button_right);


                }
            }
        });


        btnInscripcion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                inscripcion = database.rawQuery("SELECT * FROM requisitos_inscripcion", null);
                String texto = "";

                if (inscripcion.moveToFirst())
                {

                    do
                    {
                        texto += "\u2022 " + inscripcion.getString(1) + "\n" + "\n";
                    }

                    while (inscripcion.moveToNext());
                    card_text.setText(texto);
                    btnInscriExani.setBackgroundResource(R.drawable.toggle_button_left);
                    btnInscripcion.setBackgroundResource(R.drawable.toggle_button_right_on);

                }

            }
        });



    }


}
