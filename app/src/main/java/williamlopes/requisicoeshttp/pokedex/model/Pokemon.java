package williamlopes.requisicoeshttp.pokedex.model;

import java.util.List;

class Pokemon {

    public int id;
    public String num;
    public String name;
    public String img;
    public List<String> type;
    public String height;
    public String weight;
    public String candy;
    public int candy_count;
    public String egg;
    public double spawn_chance;
    public double avg_spawns;
    public String spawn_time;
    public List<Double> multipliers;
    public List<String> weaknesses;
    public List<NextEvolution> next_evolution;
    public List<PrevEvolution> prev_evolution;

}
