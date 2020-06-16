package esi.univbobo.bf.zoodroid.controleur;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

import esi.univbobo.bf.zoodroid.R;
import esi.univbobo.bf.zoodroid.modele.Citation;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    //***************************************
    // Declaration des elements de la vue
    //***************************************
    private EditText editTextUser;
    private EditText editTextPass;
    private Button buttonConnexion;
    private Button buttonMdpOublie;
    private Button buttonInscription;
    private int aleatoire;
    private String citation;
    //private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //***************************************
        // Initialisation des elements de la vue
        //***************************************
        editTextUser = findViewById(R.id.editTextUser);
        editTextPass = findViewById(R.id.editTextPass);
        buttonConnexion = findViewById(R.id.buttonConnexion);
        buttonMdpOublie = findViewById(R.id.buttonMdpOublie);
        buttonInscription = findViewById(R.id.buttonInscription);
        //progressDialog = new ProgressDialog(this);

        //***************************************
        // Abonnement au cick des bouton
        //***************************************
        buttonConnexion.setOnClickListener(this);
        buttonMdpOublie.setOnClickListener(this);
        buttonInscription.setOnClickListener(this);

        aleatoire = new Random().nextInt(Citation.citations.length);
        citation = Citation.citations[aleatoire];

    }

    @Override
   public void onClick(View view)
    {

        //***************************************
        // Traitement des clicks
        //***************************************

        if (view ==buttonConnexion)
        {



            if (TextUtils.isEmpty(editTextUser.getText().toString()))
            {
                Toast.makeText(this, "Veillez entrer le nom d'utilisateur", Toast.LENGTH_SHORT).show();
            } else
            if (TextUtils.isEmpty(editTextPass.getText().toString()))
            {
                Toast.makeText(this, "Veillez entrer le mot de passe", Toast.LENGTH_SHORT).show();
            } else
            if (editTextUser.getText().toString().equals("zoodroid") && editTextPass.getText().toString().equals("ZooDroid"))
            {
                Intent intent = new Intent(this, DashBoard.class);
                Toast.makeText(this, "Connexion reussi", Toast.LENGTH_SHORT).show();
                editTextUser.setText("");
                editTextPass.setText("");
                // startActivity(intent);
                startActivityForResult(intent,0);
            } else
            {
                Toast.makeText(this, "Nom d'utilisateur ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
            }
        }
        else
        if (view ==buttonMdpOublie)
        {
            Toast.makeText(this, "Veillez contacter un administrateur afin d'acceder a votre compte. Merci!!!", Toast.LENGTH_SHORT).show();
        }
        else
        if (view ==buttonInscription)
        {
            Toast.makeText(this, "Cette option sera disponible dans notre prochaine version. En attendant, Veillez contacter un administrateur", Toast.LENGTH_SHORT).show();
        }
    }

    //*************************************
    // Gestion de l'application a la repris
    //*************************************

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        this.finish();
    }
}
