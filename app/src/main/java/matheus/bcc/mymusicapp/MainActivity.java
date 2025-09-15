package matheus.bcc.mymusicapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import matheus.bcc.mymusicapp.db.bean.Musica;
import matheus.bcc.mymusicapp.fragments.GenerosFragment;
import matheus.bcc.mymusicapp.fragments.NovaMusicaFragment;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawer_layout;
    private View frame_layout;
    private NavigationView navigation_view;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private GenerosFragment generosFragment = new GenerosFragment();
    private NovaMusicaFragment novaMusicaFragment = new NovaMusicaFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        drawer_layout = findViewById(R.id.drawerLayout);
        navigation_view = findViewById(R.id.navigationView);
        frame_layout = findViewById(R.id.frame_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout, R.string.open_menu, R.string.close_menu);
        drawer_layout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentManager = getSupportFragmentManager();
        // Opções para os itens do menu lateral
        navigation_view.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.it_nmusica) {
                cadastrarMusicas(null);
            } else if (item.getItemId() == R.id.it_lmusicas) {
            } else if (item.getItemId() == R.id.it_lgeneros) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(frame_layout.getId(), generosFragment);
                fragmentTransaction.commit();
            } else if (item.getItemId() == R.id.it_fechar)
                finish();
            drawer_layout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    public void cadastrarMusicas(Musica musica) {
        novaMusicaFragment.musica=musica;
        // Caso música seja null, suponha a inserção de uma nova.
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frame_layout.getId(), novaMusicaFragment);
        fragmentTransaction.commit();
    }
}