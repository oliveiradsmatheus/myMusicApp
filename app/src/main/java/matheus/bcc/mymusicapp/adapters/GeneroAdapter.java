package matheus.bcc.mymusicapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import matheus.bcc.mymusicapp.R;
import matheus.bcc.mymusicapp.db.bean.Genero;

public class GeneroAdapter extends ArrayAdapter<Genero> {
    private int resource;

    public GeneroAdapter(@NonNull Context context, int resource, @NonNull List<Genero> generoList) {
        super(context, resource, generoList);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resource, parent, false);
        }

        Genero genero = getItem(position);

        TextView tv_genero = convertView.findViewById(R.id.tv_genero);
        if (genero != null)
            tv_genero.setText(genero.getNome());



        return convertView;
    }
}
