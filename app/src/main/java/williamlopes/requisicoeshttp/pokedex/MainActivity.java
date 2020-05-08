package williamlopes.requisicoeshttp.pokedex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;

import williamlopes.requisicoeshttp.pokedex.Common.Common;
import williamlopes.requisicoeshttp.pokedex.model.Pokemon;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    BroadcastReceiver showDetail = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().toString().equals(Common.KEY_ENABLE_HOME)){
                //Habilita o botão de voltar na Toolbar em ambos os casos
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);

                //Substituir o fragment
                Fragment detailFragment = PokemonDetail.getInstance();
                int position = intent.getIntExtra("position", -1);
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                detailFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.list_pokemon_fragment, detailFragment);
                fragmentTransaction.addToBackStack("detail");
                fragmentTransaction.commit();

                //Setar nome do pokémon na Toolbar
                Pokemon pokemon = Common.commonPokemonList.get(position);
                toolbar.setTitle(pokemon.getName());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("POKEMON LISTA");
        setSupportActionBar(toolbar);

        //Registrando a Broadcast
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showDetail, new IntentFilter(Common.KEY_ENABLE_HOME));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                toolbar.setTitle("POKEMON LIST");
                //clear all detail fragment and return to list fragment
                getSupportFragmentManager().popBackStack("detail", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(false);

                break;
                default:
        }
        return true;
    }
}
