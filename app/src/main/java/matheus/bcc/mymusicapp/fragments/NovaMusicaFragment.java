package matheus.bcc.mymusicapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import matheus.bcc.mymusicapp.R;
import matheus.bcc.mymusicapp.adapters.GeneroAdapter;
import matheus.bcc.mymusicapp.db.bean.Genero;
import matheus.bcc.mymusicapp.db.bean.Musica;
import matheus.bcc.mymusicapp.db.dal.GeneroDAL;
import matheus.bcc.mymusicapp.db.dal.MusicaDAL;

public class NovaMusicaFragment extends Fragment {
    private Button btConfirma;
    public static Musica musica=null;
    private EditText etnDuracao;
    private EditText etnAno;
    private TextInputEditText tiInterprete;
    private TextInputEditText tiTitulo;
    private Spinner sp_generos;

    private MusicaDAL musicaDAL;

    public NovaMusicaFragment() {
        // Requer construtor público vazio
    }
    public static NovaMusicaFragment newInstance(String param1, String param2) {
        NovaMusicaFragment fragment = new NovaMusicaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_nova_musica, container, false);
        sp_generos = view.findViewById(R.id.sp_generos);
        btConfirma= view.findViewById(R.id.btConfirma);
        if(musica!=null)
            btConfirma.setText("Alterar");
        else
            btConfirma.setText("Cadastrar");

        tiInterprete= view.findViewById(R.id.tiInterprete);
        tiTitulo= view.findViewById(R.id.tiTitulo);
        etnDuracao = view.findViewById(R.id.etnDuracao);
        etnAno = view.findViewById(R.id.etnAno);
        musicaDAL= new MusicaDAL(view.getContext());
        btConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String titulo = tiTitulo.getText().toString().trim();
                    String interprete = tiInterprete.getText().toString().trim();

                    int ano = Integer.parseInt(etnAno.getText().toString().trim());
                    double duracao = Double.parseDouble(etnDuracao.getText().toString().trim());
                    Genero genero = (Genero) sp_generos.getSelectedItem();

                    Musica nova = new Musica(ano, titulo, interprete, genero, duracao);

                    if (NovaMusicaFragment.musica != null) {
                        nova.setId(musica.getId());
                        if (musicaDAL.alterar(nova)) {
                            Snackbar snackbar = Snackbar.make(view, "Música alterada com sucesso!", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            NovaMusicaFragment.musica = null;
                            addValores();
                        }
                    } else {
                        if (musicaDAL.salvar(nova)) {
                            Snackbar snackbar = Snackbar.make(view, "Música salva com sucesso!", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            NovaMusicaFragment.musica = null;
                            addValores();
                        }
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        carregarGeneros(view);
        addValores();
        return view;
    }

    private void addValores() {
        if(musica!=null){
            tiTitulo.setText(musica.getTitulo());
            tiInterprete.setText(musica.getInterprete());
            etnAno.setText(""+musica.getAno());
            etnDuracao.setText(""+musica.getDuracao());
            ArrayAdapter<Genero> adapter = (ArrayAdapter<Genero>) sp_generos.getAdapter();

            int i;
            for ( i = 0; i < adapter.getCount()  && adapter.getItem(i).getId() != musica.getGenero().getId(); i++);

            if (i < adapter.getCount()) {
                sp_generos.setSelection(i);
            }
        }
        else{
            tiTitulo.setText("");
            tiInterprete.setText("");
            etnAno.setText("");
            etnDuracao.setText("");
        }
    }

    private void carregarGeneros(View view) {
        GeneroDAL dal = new GeneroDAL(view.getContext());
        List<Genero> generoList =  dal.get("");

        GeneroAdapter adapter = new GeneroAdapter(requireContext(), R.layout.genero_item_layout, generoList);
        adapter.setDropDownViewResource(R.layout.genero_item_layout);
        sp_generos.setAdapter(adapter);
    }


}