package williamlopes.requisicoeshttp.pokedex.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnChipClickListener;

import java.util.ArrayList;
import java.util.List;

import williamlopes.requisicoeshttp.pokedex.Common.Common;
import williamlopes.requisicoeshttp.pokedex.Interface.IItemClickListener;
import williamlopes.requisicoeshttp.pokedex.R;
import williamlopes.requisicoeshttp.pokedex.model.Evolution;

public class PokemonEvolutionAdapter extends RecyclerView.Adapter<PokemonEvolutionAdapter.MyViewHolder> {


    Context context;
    List<Evolution> listaEvolucoes;

    public PokemonEvolutionAdapter(Context context, List<Evolution> listaEvolucoes) {
        this.context = context;
        if (listaEvolucoes != null )
            this.listaEvolucoes = listaEvolucoes;
        else
            this.listaEvolucoes = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(context).inflate(R.layout.chip_item, parent, false);
        return new MyViewHolder( itemLista );
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonEvolutionAdapter.MyViewHolder holder, int position) {

        holder.chip.setChipText(listaEvolucoes.get(position).getName());
        holder.chip.changeBackgroundColor(
                Common.getColorByType(
                        Common.findPokemonByNum(
                                listaEvolucoes.get(position).getNum()).getType().get(0)));



    }

    @Override
    public int getItemCount() {
        return listaEvolucoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Chip chip;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = itemView.findViewById(R.id.chip);
            chip.setOnChipClickListener(new OnChipClickListener() {
                @Override
                public void onChipClick(View v) {
                    LocalBroadcastManager.getInstance(context)
                            .sendBroadcast(new Intent(Common.KEY_NUM_EVOLUTION)
                                    .putExtra("num", listaEvolucoes.get(getAdapterPosition()).getNum()));
                }
            });
        }
    }
}