package esi.univbobo.bf.zoodroid.controleur;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import esi.univbobo.bf.zoodroid.R;
import esi.univbobo.bf.zoodroid.modele.Animal;
import esi.univbobo.bf.zoodroid.vue.AnimalListAdapter;

//***************************************
// Creation d'une liste d'animaux
//qui sera utilisé dans l'affichage
//***************************************
public class ListerAnimaux extends AppCompatActivity
{
    ListView gridView;
    ArrayList<Animal> animalArrayList;
    AnimalListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister_animaux);

        gridView = findViewById(R.id.gridView);
        animalArrayList = new ArrayList<>();
        adapter = new AnimalListAdapter(this,R.layout.animal_items,animalArrayList);



        gridView.setAdapter(adapter);

        //***************************************
        // Recueillir les donnees provenant
        // de la base de données
        //***************************************

        //Cursor cursor = MainActivity.maBaseSQLite.getData("SELECT * FROM Animal");
        Cursor cursor = DashBoard.maBaseSQLite.getAnimal();
        animalArrayList.clear();

        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String pse = cursor.getString(1);
            String esp = cursor.getString(2);
            byte [] img = cursor.getBlob(3);

            animalArrayList.add( new Animal(id, pse, esp, img));
        }
        adapter.notifyDataSetChanged();
    }
}
