package matheus.bcc.mymusicapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import matheus.bcc.mymusicapp.MainActivity;
import matheus.bcc.mymusicapp.R;
import matheus.bcc.mymusicapp.adapters.MusicaAdapter;
import matheus.bcc.mymusicapp.db.bean.Musica;
import matheus.bcc.mymusicapp.db.dal.MusicaDAL;

public class MusicasFragment extends Fragment {
    private ListView lv_musicas;
    private MainActivity mainActivity;

    public MusicasFragment() {
        // Requer construtor público vazio
    }

    public static MusicasFragment newInstance() {
        MusicasFragment fragment = new MusicasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_musicas, container, false);
        lv_musicas = view.findViewById(R.id.lv_musicas);
        lv_musicas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainActivity.cadastrarMusicas((Musica) parent.getItemAtPosition(position));
            }
        });
        lv_musicas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Musica musica = (Musica) parent.getItemAtPosition(position);
                new MaterialAlertDialogBuilder(getContext() , R.style.AlertaCustomizado)
                        .setTitle("Confirmar Exclusão")
                        .setMessage("Tem certeza que deseja apagar a musica '" + musica.getTitulo() + "'?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            MusicaDAL dal = new MusicaDAL(view.getContext());
                            dal.apagar(musica.getId());
                            carregarMusicas(view);
                            Toast.makeText(getContext(), "Música apagada!", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Não", null)
                        .show();
                return true;
            }
        });
        carregarMusicas(view);
        return view;
    }

    private void carregarMusicas(View view) {
        MusicaDAL dal = new MusicaDAL(view.getContext());
        List<Musica> musicaList =  dal.get("");
        lv_musicas.setAdapter(new MusicaAdapter(view.getContext(), R.layout.musica_item_layout, musicaList));
    }


}