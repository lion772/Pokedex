package williamlopes.requisicoeshttp.pokedex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import williamlopes.requisicoeshttp.pokedex.R;
import williamlopes.requisicoeshttp.pokedex.model.Pokemon;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.MyViewHolder> {

    Context context;
    List<Pokemon> listaPokemons;

    public PokemonListAdapter(Context context, List<Pokemon> listaPokemons) {
        this.context = context;
        this.listaPokemons = listaPokemons;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pokemon_list_item, parent, false);
        return new MyViewHolder( itemLista );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.pokemon_name.setText(listaPokemons.get(position).getName());
        Glide.with(context).load(listaPokemons.get(position).getImg()).into(holder.pokemon_image);

    }

    @Override
    public int getItemCount() {
        return listaPokemons.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView pokemon_image;
        TextView pokemon_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pokemon_image = itemView.findViewById(R.id.pokemon_image);
            pokemon_name = itemView.findViewById(R.id.text_pokemon_name);
        }
    }
}
