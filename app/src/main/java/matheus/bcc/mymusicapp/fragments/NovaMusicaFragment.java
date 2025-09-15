package matheus.bcc.mymusicapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import matheus.bcc.mymusicapp.R;
import matheus.bcc.mymusicapp.db.bean.Genero;
import matheus.bcc.mymusicapp.db.bean.Musica;
import matheus.bcc.mymusicapp.db.dal.GeneroDAL;
import matheus.bcc.mymusicapp.db.dal.MusicaDAL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NovaMusicaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NovaMusicaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btConfirma;
    public static Musica musica=null;
    private EditText etnDuracao;
    private EditText etnAno;
    private TextInputEditText tiInterprete;
    private TextInputEditText tiTitulo;
    private Spinner sp_generos;

    private MusicaDAL musicaDAL;

    public NovaMusicaFragment() {
        // Required empty public constructor
    }
    public static NovaMusicaFragment newInstance(String param1, String param2) {
        NovaMusicaFragment fragment = new NovaMusicaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

                    Musica musica = new Musica(ano, titulo, interprete, genero, duracao);

                    if (NovaMusicaFragment.musica != null) {
                        if (musicaDAL.alterar(musica))
                            NovaMusicaFragment.musica = null;
                    } else {
                        if (musicaDAL.salvar(musica))
                            NovaMusicaFragment.musica = null;
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });


        carregarGeneros(view);
        return view;
    }

    private void carregarGeneros(View view) {
        GeneroDAL dal = new GeneroDAL(view.getContext());
        List<Genero> generoList =  dal.get("");
        ArrayAdapter<Genero> adapter = new ArrayAdapter<>(
                view.getContext(),
                android.R.layout.simple_spinner_item,
                generoList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_generos.setAdapter(adapter);
    }
}