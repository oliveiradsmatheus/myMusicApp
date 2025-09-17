package matheus.bcc.mymusicapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import matheus.bcc.mymusicapp.MainActivity;
import matheus.bcc.mymusicapp.R;
import matheus.bcc.mymusicapp.adapters.GeneroAdapter;
import matheus.bcc.mymusicapp.db.bean.Genero;
import matheus.bcc.mymusicapp.db.dal.GeneroDAL;

public class GenerosFragment extends Fragment {
    private ListView lv_generos;
    private MainActivity mainActivity;

    public GenerosFragment() {
        // Requer construtor público vazio
    }

    public static GenerosFragment newInstance() {
        GenerosFragment fragment = new GenerosFragment();
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
        View view =  inflater.inflate(R.layout.fragment_generos, container, false);
        lv_generos = view.findViewById(R.id.lv_generos);
        lv_generos.setOnItemLongClickListener((adapterView, view1, i, l) -> {
            Genero genero = (Genero) adapterView.getItemAtPosition(i);

            new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Confirmar Exclusão")
                    .setMessage("Tem certeza que deseja apagar o gênero '" + genero.getNome() + "'?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        GeneroDAL dal = new GeneroDAL(view.getContext());
                        dal.apagar(genero.getId());
                        carregarGeneros(view);
                        Toast.makeText(getContext(), "Gênero apagado!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Não", null)
                    .show();
            return true;
        });
        lv_generos.setOnItemClickListener((parent, view2, position, id) -> {
            //mainActivity.cadastrarMusicas((Genero) adapterView.getItemAtPosition(position));
            mainActivity.cadastrarMusicas(null);
        });
        carregarGeneros(view);
        return view;
    }

    private void carregarGeneros(View view) {
        GeneroDAL dal = new GeneroDAL(view.getContext());
        List<Genero> generoList =  dal.get("");
        lv_generos.setAdapter(new GeneroAdapter(view.getContext(), R.layout.genero_item_layout, generoList));
    }
}