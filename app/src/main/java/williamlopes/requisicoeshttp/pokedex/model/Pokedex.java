package williamlopes.requisicoeshttp.pokedex.model;

import java.util.List;

public class Pokedex {
    private List<Pokemon> pokemon;

    public Pokedex() { //Construtor vazio
    }

    public Pokedex(List<Pokemon> pokemon) { //Construtor com a lista de pokémons
        this.pokemon = pokemon;
    }

    public List<Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }
}
