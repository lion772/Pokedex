package williamlopes.requisicoeshttp.pokedex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnChipClickListener;

import java.util.List;

import williamlopes.requisicoeshttp.pokedex.Common.Common;
import williamlopes.requisicoeshttp.pokedex.Interface.IItemClickListener;
import williamlopes.requisicoeshttp.pokedex.R;

public class PokemonEvolutionAdapter extends RecyclerView.Adapter<PokemonEvolutionAdapter.MyViewHolder> {


    Context context;
    List<String> listaTypes;

    public PokemonEvolutionAdapter(Context context, List<String> listaTypes) {
        this.context = context;
        this.listaTypes = listaTypes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(context).inflate(R.layout.chip_item, parent, false);
        return new MyViewHolder( itemLista );
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonEvolutionAdapter.MyViewHolder holder, int position) {

        holder.chip.setChipText(listaTypes.get(position));
        holder.chip.changeBackgroundColor(Common.getColorByType(listaTypes.get(position)));

    }

    @Override
    public int getItemCount() {
        return listaTypes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        Chip chip;
        IItemClickListener iItemClickListener;

        public void setiItemClickListener(IItemClickListener iItemClickListener) {
            this.iItemClickListener = iItemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = itemView.findViewById(R.id.chip);
            chip.setOnChipClickListener(new OnChipClickListener() {
                @Override
                public void onChipClick(View v) {
                    iItemClickListener.onClick(v, getAdapterPosition());
                }
            });
        }
    }
}