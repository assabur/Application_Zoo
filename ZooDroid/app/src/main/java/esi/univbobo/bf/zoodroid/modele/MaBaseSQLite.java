package esi.univbobo.bf.zoodroid.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

public class MaBaseSQLite extends SQLiteOpenHelper
{
    //***************************************
    // Creation de la base de donné SQLite
    //***************************************
    public static final String NOM_BDD ="Animal.db";
    public static final int VERSION_BDD = 1;

    public static final String TABLE_NAME = "Animal";
    public static final String COL_PSEUDO = "Pseudo";
    public static final String COL_ESPECE = "Espece";
    public static final String COL_PHOTO = "Photo";

    public static final String COL_USER = "User";
    public static final String COL_PASS = "Pass";


    //***************************************
    // Création de la base
    //***************************************
    public  String creationTable = "create table Animal ("
            +"ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            +"Pseudo TEXT,"
            +"Espece TEXT );";

    public static final String creerTable ="CREATE TABLE "+TABLE_NAME +" ( ID INTEGER PRIMARY KEY AUTOINCREMENT ,PSEUDO TEXT ,ESPECE TEXT ,PHOTO TEXT)";

    public static final String creerUser ="CREATE TABLE User ( ID INTEGER PRIMARY KEY AUTOINCREMENT ,USER TEXT , PASS TEXT)";

    public MaBaseSQLite(Context context)
    {
        super(context, NOM_BDD, null, VERSION_BDD);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(creerTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        if (i1 < 10 )
        {
            sqLiteDatabase.execSQL("DROP TABLE "+TABLE_NAME+";");
            this.onCreate(sqLiteDatabase);
        }

    }

    //***************************************
    // Insertion des animaux
    // la base de donnée
    //***************************************
    public void insertAnimal(String pseudo, String espece, byte [] photo)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PSEUDO,pseudo);
        contentValues.put(COL_ESPECE,espece);
        contentValues.put(COL_PHOTO,photo);

        long result = db.insert(TABLE_NAME,null,contentValues);

        db.close();
        if (result<=0)
        {
            Log.w("TAG_SQL","Aucune Insertion n'a ete realisé" );
        }
    }

    //***************************************
    // Insertion des Utilisateurs
    // la base de donnée
    //***************************************

    public void insertUser(String user, String pass)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USER,user);
        contentValues.put(COL_PASS,pass);

        long result = db.insert("User",null,contentValues);

        db.close();
        if (result<=0)
        {
            Log.w("TAG_SQL","Aucune Insertion n'a ete realisé" );
        }
    }

    //***************************************
    // Récuperation des animaux enregistré
    // données dans la base
    //***************************************

    public Cursor getData(String sql)
    {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
    public Cursor getAnimal()
    {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery("SELECT * FROM "+TABLE_NAME+";", null);
    }

    //***************************************
    // Récuperation des Utilisateurs enregistré
    // données dans la base
    //***************************************

    public Cursor getUser()
    {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery("SELECT * FROM User;", null);
    }
}
