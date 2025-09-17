package matheus.bcc.mymusicapp.adapters;

import android.annotation.SuppressLint;
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
import matheus.bcc.mymusicapp.db.bean.Musica;

public class MusicaAdapter extends ArrayAdapter<Musica> {
    private int resource;

    public MusicaAdapter(@NonNull Context context, int resource, @NonNull List<Musica> musicaList) {
        super(context, resource, musicaList);
        this.resource = resource;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resource, parent, false);
        }

        Musica musica = getItem(position);

        TextView tv_titulo = convertView.findViewById(R.id.tv_titulo);
        TextView tv_interprete = convertView.findViewById(R.id.tv_interprete);
        TextView tv_genero = convertView.findViewById(R.id.tv_genero);
        TextView tv_duracao = convertView.findViewById(R.id.tv_duracao);
        TextView tv_ano = convertView.findViewById(R.id.tv_ano);

        // Preenche os TextViews com os dados do objeto Usuario
        if (musica != null) {
            tv_titulo.setText(musica.getTitulo());
            tv_interprete.setText(musica.getInterprete());
            tv_ano.setText("" + musica.getAno());
            tv_genero.setText(musica.getGenero().getNome());
            int duracao = (int) musica.getDuracao();
            int minutos = (int) Math.round((musica.getDuracao() - duracao) * 100);
            tv_duracao.setText(duracao + ":" + (minutos > 9 ? minutos : "0" + minutos));
        }

        return convertView;
    }
}
