package williamlopes.requisicoeshttp.pokedex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import williamlopes.requisicoeshttp.pokedex.Common.Common;
import williamlopes.requisicoeshttp.pokedex.Common.ItemOffsetDecoration;
import williamlopes.requisicoeshttp.pokedex.Retrofit.IPokemon;
import williamlopes.requisicoeshttp.pokedex.Retrofit.RetrofitClient;
import williamlopes.requisicoeshttp.pokedex.adapter.PokemonListAdapter;
import williamlopes.requisicoeshttp.pokedex.model.Pokedex;
import williamlopes.requisicoeshttp.pokedex.model.Pokemon;

/**
 * A simple {@link Fragment} subclass.
 */
public class PokemonList extends Fragment {

    /* CompositeDisposable é apenas uma classe para manter todos os seus descartáveis
     ​ no mesmo local para que você possa dispor de tudo de uma só vez. Todos então são descartados */
    IPokemon iPokemon;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    RecyclerView pokemon_list_recyclerview;

    PokemonListAdapter adapter, search_adapter;
    List<String> last_suggest = new ArrayList<>();

    MaterialSearchBar searchBar;

    static PokemonList instance;

    public static PokemonList getInstance() {
        if (instance == null)
            instance = new PokemonList();
        return instance;
    }

    public PokemonList() {
        Retrofit retrofit = RetrofitClient.getInstance();
        iPokemon = retrofit.create(IPokemon.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);

        pokemon_list_recyclerview = view.findViewById(R.id.pokemon_list_recyclerview);
        pokemon_list_recyclerview.setHasFixedSize(true);
        pokemon_list_recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.spacing);
        pokemon_list_recyclerview.addItemDecoration(itemOffsetDecoration);

        //Configurando a Search Bar
        searchBar = view.findViewById(R.id.search_bar);
        searchBar.setHint("Escreva o nome do Pokémon");
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();
                for (String search: last_suggest){
                    if (search.toLowerCase().contains(searchBar.getText().toLowerCase())){
                        suggest.add(search);
                    }
                }
                searchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled){
                    pokemon_list_recyclerview.setAdapter(adapter); //Retorna default adapter
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        fetchData();

        return view;
    }

    private void startSearch(CharSequence text) {
        if (Common.commonPokemonList.size() > 0){
            List<Pokemon> result = new ArrayList<>();
            for (Pokemon pokemon: Common.commonPokemonList){
                if (pokemon.getName().contains(text)){
                    result.add(pokemon);
                }
            }
            search_adapter = new PokemonListAdapter(getActivity(), result);
            pokemon_list_recyclerview.setAdapter(search_adapter);
        }
    }

    private void fetchData() {

        compositeDisposable.add(iPokemon.getListPokemon()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Pokedex>() {
                    @Override
                    public void accept(Pokedex pokedex) throws Exception {

                        Common.commonPokemonList = pokedex.getPokemon();
                        adapter = new PokemonListAdapter(getActivity(), Common.commonPokemonList);

                        pokemon_list_recyclerview.setAdapter(adapter);
                        last_suggest.clear();

                        for (Pokemon pokemon: Common.commonPokemonList){
                            last_suggest.add(pokemon.getName());
                        }
                        searchBar.setVisibility(View.VISIBLE); // Exibir a search bar depois de carregar todos os pokemons
                        searchBar.setLastSuggestions(last_suggest);
                    }
                })
        );
    }
}
