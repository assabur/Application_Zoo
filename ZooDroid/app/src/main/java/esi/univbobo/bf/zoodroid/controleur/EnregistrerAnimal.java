package esi.univbobo.bf.zoodroid.controleur;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import esi.univbobo.bf.zoodroid.R;

public class EnregistrerAnimal extends AppCompatActivity implements View.OnClickListener
{
    //***************************************
    // Declaration des elements de la vue
    //***************************************
    EditText editTextPseudo;
    EditText editTextEspece;
    ImageView imageViewPhoto;
    Button btn_Enregistrer;
    Button btn_Annuler;
    Button btn_Ajouter;
    Button btn_Choisir;
    private int REQUEST_CODE_GALLERY = 1;
    private int REQUEST_CODE_PHOTO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrer_animal);

        //***************************************
        // Initialisation des elements de la vue
        //***************************************
        editTextPseudo = findViewById(R.id.editTextPseudo);
        editTextEspece = findViewById(R.id.editTextEspece);
        imageViewPhoto = findViewById(R.id.imageViewPhoto);
        btn_Enregistrer = findViewById(R.id.btn_Enregistrer);
        btn_Annuler = findViewById(R.id.btn_Annuler);
        btn_Ajouter = findViewById(R.id.btn_Ajouter);
        btn_Choisir = findViewById(R.id.btn_Choisir);

        btn_Enregistrer.setOnClickListener(this);
        btn_Annuler.setOnClickListener(this);
        btn_Ajouter.setOnClickListener(this);
        btn_Choisir.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        //***************************************
        // Traitement des clicks
        //***************************************

        if (view == btn_Ajouter)
        {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CODE_PHOTO);
            //onActivityResult(0, 1, intent);

        }
        if (view == btn_Choisir)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }

            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
            }

        }

        if (view == btn_Enregistrer)
        {


            try
            {
                if (TextUtils.isEmpty(editTextPseudo.getText().toString()))
                {
                    Toast.makeText(this, "Veillez entrer un Pseudo", Toast.LENGTH_SHORT).show();
                } else
                if (TextUtils.isEmpty(editTextEspece.getText().toString()))
                {
                    Toast.makeText(this, "Veillez entrer Une Espece", Toast.LENGTH_SHORT).show();
                } else
                {
                    DashBoard.maBaseSQLite.insertAnimal(editTextPseudo.getText().toString(), editTextEspece.getText().toString(), imageViewToByte(imageViewPhoto));
                    Toast.makeText(this, "Enregistré avec succes", Toast.LENGTH_SHORT).show();
                    editTextPseudo.setText("");
                    editTextEspece.setText("");
                    imageViewPhoto.setImageResource(R.drawable.default_img);
                    askContinue();
                }

            }
            catch (Exception e)
            {
                Toast.makeText(this, "Echec de l'enregistrement"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        if (view == btn_Annuler)
        {

            this.finish();
        }
    }


    //***************************************
    // Demande de permisson pour acceder a la gallery
    //***************************************
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == REQUEST_CODE_GALLERY)
        {
            if (grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else
            {
                Toast.makeText(this, "Vous devez autoriser l'application avant de pouvoir choisir une photo", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //***************************************
    // Attacher l'image
    //***************************************
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Bitmap bitmap;

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try
            {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewPhoto.setImageBitmap(bitmap);

            } catch (FileNotFoundException e)
            {
                Toast.makeText(this, "Une erreur est survenue"+e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        else
        if (requestCode == REQUEST_CODE_PHOTO && resultCode == RESULT_OK && data != null)
        {
            bitmap = (Bitmap) data.getExtras().get("data");
            imageViewPhoto.setImageBitmap(bitmap);
        }

        super.onActivityResult(requestCode, resultCode, data);

    }


    //***************************************
    // Conversion de l'mage en tableau de byte
    //***************************************
    private byte [] imageViewToByte(ImageView image)
    {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte [] byteArray = stream.toByteArray();
        return byteArray;
    }

    //***************************************
    // Dialog pour continuer ou pas
    //***************************************
    public void askContinue()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Continuer l'enregistrer");
        builder.setMessage("Voulez vous enregistrer a nouveau un animal ?");

        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                return ;
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                EnregistrerAnimal.super.finish();
            }
        });

        builder.show();
    }

}






































