package matheus.bcc.mymusicapp.fragments;

import android.annotation.SuppressLint;
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_generos, container, false);
        lv_generos = view.findViewById(R.id.lv_generos);
        lv_generos.setOnItemLongClickListener((adapterView, view1, i, l) -> {
            Genero genero = (Genero) adapterView.getItemAtPosition(i);

            // Cria o AlertDialog para confirmação
            new AlertDialog.Builder(getContext())
                    .setTitle("Confirmar Exclusão")
                    .setMessage("Tem certeza que deseja apagar o gênero '" + genero.getNome() + "'?") // Mensagem mais específica
                    .setPositiveButton("Sim", (dialog, which) -> {
                        // Este código só roda se o usuário clicar em "Sim"
                        GeneroDAL dal = new GeneroDAL(getContext());
                        dal.apagar(genero.getId());
                        carregarGeneros(view);
                        Toast.makeText(getContext(), "Gênero apagado!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Não", null) // Não faz nada se o usuário clicar em "Não", apenas fecha o diálogo
                    .setIcon(android.R.drawable.ic_dialog_alert) // Ícone de alerta (opcional)
                    .show();

            return true;
            /*GeneroDAL dal = new GeneroDAL(view.getContext());
            Genero genero = (Genero) adapterView.getItemAtPosition(i);
            dal.apagar(genero.getId());
            carregarGeneros(view);
            return true;*/
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