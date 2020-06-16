package esi.univbobo.bf.zoodroid.controleur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import esi.univbobo.bf.zoodroid.R;
import esi.univbobo.bf.zoodroid.modele.Citation;
import esi.univbobo.bf.zoodroid.modele.MaBaseSQLite;

public class DashBoard extends AppCompatActivity implements View.OnClickListener
{
    //***************************************
    // Declaration des elements de la vue
    //***************************************

    Button btn_Enregistrer;
    Button btn_Lister;
    Button btn_Apropos;
    Button btn_Aide;
    private int aleatoire;
    private String citation;
    public static MaBaseSQLite maBaseSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        //***************************************
        // Initialisation des elements de la vue
        //***************************************
        btn_Enregistrer = findViewById(R.id.btn_Enregistrer);
        btn_Lister = findViewById(R.id.btn_Lister);
        btn_Apropos = findViewById(R.id.btn_Apropos);
        btn_Aide = findViewById(R.id.btn_Aide);


        //***************************************
        // Abonnement au cick des bouton
        //***************************************
        btn_Enregistrer.setOnClickListener(this);
        btn_Lister.setOnClickListener(this);
        btn_Apropos.setOnClickListener(this);
        btn_Aide.setOnClickListener(this);

        maBaseSQLite = new MaBaseSQLite(this);

        aleatoire = new Random().nextInt(Citation.citations.length);
        citation = Citation.citations[aleatoire];

    }

    @Override
    public void onClick(View view)
    {
        //***************************************
        // Traitement des clicks
        //***************************************
        if (view == btn_Enregistrer)
        {
            Intent intent = new Intent(this, EnregistrerAnimal.class);
            startActivity(intent);
        }
        if (view == btn_Lister)
        {
            Intent intent = new Intent(this, ListerAnimaux.class);
            startActivity(intent);
        }
        if (view == btn_Apropos)
        {
            Intent intent = new Intent(this, Apropos.class);
            startActivity(intent);
        }
        if (view == btn_Aide)
        {
            Intent intent = new Intent(this, Aide.class);
            startActivity(intent);
        }


    }
    //***************************************
    // Confirmation pour quitter l'application
    //***************************************
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                askContinue();

        }
        return super.onKeyDown(keyCode, event);
    }

    //***************************************
    // Menu de Dialog
    //***************************************
    public void askContinue()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Quitter ZOODROID");
        builder.setMessage("Voulez vous vraiment quitter ?");

        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                AlertDialog.Builder build = new AlertDialog.Builder(DashBoard.this);
                build.setTitle("Citation");
                build.setMessage(citation);
                build.setCancelable(false);
                build.setIcon(R.mipmap.diag);
                build.setPositiveButton("Partager", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Intent myItentent = new Intent(Intent.ACTION_SEND);
                        myItentent.setType("text/plain");
                        myItentent.putExtra(Intent.EXTRA_SUBJECT, "Citation");
                        myItentent.putExtra(Intent.EXTRA_TEXT, citation);
                        startActivity(Intent.createChooser(myItentent, "Partager via"));
                    }
                });
                build.setNegativeButton("Fermer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        DashBoard.super.finish();
                    }
                });
                build.show();

                new CountDownTimer(5000, 1000) {

                    public void onTick(long millisUntilFinished)
                    {

                    }

                    public void onFinish()
                    {
                        DashBoard.super.finish();
                    }
                }.start();

                //System.exit(0);
            }


        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                return;
            }
        });

        builder.show();


    }
}
