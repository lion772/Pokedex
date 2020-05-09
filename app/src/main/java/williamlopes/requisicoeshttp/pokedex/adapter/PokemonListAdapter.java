package williamlopes.requisicoeshttp.pokedex.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import williamlopes.requisicoeshttp.pokedex.Common.Common;
import williamlopes.requisicoeshttp.pokedex.Interface.IItemClickListener;
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

        //evento
        holder.setiItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(context, "Click at Pokémon: " + listaPokemons.get(position).getName(), Toast.LENGTH_LONG).show();

                LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(new Intent(Common.KEY_ENABLE_HOME).putExtra("num", listaPokemons.get(position).getNum()));
            }

            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPokemons.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView pokemon_image;
        TextView pokemon_name;
        IItemClickListener iItemClickListener;
        public void setiItemClickListener(IItemClickListener iItemClickListener) {
            this.iItemClickListener = iItemClickListener;
        }
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemon_image = itemView.findViewById(R.id.pokemon_image);
            pokemon_name = itemView.findViewById(R.id.text_pokemon_name);

            itemView.setOnClickListener(this); // make "MyViewHolder" implement "...View.OnClickListener"
        }
        @Override
        public void onClick(View view) {
            iItemClickListener.onClick(view, getAdapterPosition());
        }

    }
}
