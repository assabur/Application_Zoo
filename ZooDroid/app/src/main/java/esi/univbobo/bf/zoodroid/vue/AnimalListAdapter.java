package esi.univbobo.bf.zoodroid.vue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import esi.univbobo.bf.zoodroid.R;
import esi.univbobo.bf.zoodroid.modele.Animal;

//*******************************************************
// implementation de l'adapteur qui permettra
// de gerer l'affichage
//*******************************************************
public class AnimalListAdapter extends BaseAdapter
{

    private Context context;
    private int layout;
    private ArrayList <Animal> animals;

    public AnimalListAdapter(Context context, int layout, ArrayList<Animal> animals) {
        this.context = context;
        this.layout = layout;
        this.animals = animals;
    }

    @Override
    public int getCount() {
        return animals.size();
    }

    @Override
    public Object getItem(int i)
    {
        return animals.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 1;
    }

    private class ViewHolder
    {
        ImageView imageView;
        TextView textViewPseudo;
        TextView textViewEspece;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View row = view;
        ViewHolder holder = new ViewHolder();
        if (row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.textViewPseudo = row.findViewById(R.id.textViewItemPseudo);
            holder.textViewEspece = row.findViewById(R.id.textViewItemEspece);
            holder.imageView = row.findViewById(R.id.imageViewItemPhoto);
            row.setTag(holder);
        } else
        {
            holder = (ViewHolder) row.getTag();
        }

        Animal animal = animals.get(i);

        holder.textViewPseudo.setText(animal.getPseudo());
        holder.textViewEspece.setText(animal.getEspece());

        byte [] animalImage = animal.getIdPhoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(animalImage, 0, animalImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
