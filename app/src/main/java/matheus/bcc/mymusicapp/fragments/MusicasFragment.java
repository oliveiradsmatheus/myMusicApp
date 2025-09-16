package matheus.bcc.mymusicapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_musicas, container, false);
        lv_musicas = view.findViewById(R.id.lv_musicas);
        
        carregarMusicas(view);
        return view;
    }

    private void carregarMusicas(View view) {
        MusicaDAL dal = new MusicaDAL(view.getContext());
        List<Musica> musicaList =  dal.get("");
        lv_musicas.setAdapter(new MusicaAdapter(view.getContext(), R.layout.musica_item_layout, musicaList));
    }
}