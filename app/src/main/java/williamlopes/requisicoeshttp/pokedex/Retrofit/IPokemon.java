package williamlopes.requisicoeshttp.pokedex.Retrofit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import williamlopes.requisicoeshttp.pokedex.model.Pokedex;

public interface IPokemon {

    @GET("pokedex.json")
    Observable<Pokedex> getListPokemon();
}
