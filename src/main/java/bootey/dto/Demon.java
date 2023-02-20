package bootey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Demon {
    private Integer Id;
    private Integer Sc;
    private Integer Tr;
    private Integer Sr;
    private Integer Na;
    private List<Integer> fragments;
    private Double weight;

    public Demon(Integer id, Integer sc, Integer tr, Integer sr, Integer na, List<Integer> fragments) {
        this.Id = id;
        this.Sc = sc;
        this.Tr = tr;
        this.Sr = sr;
        this.Na = na;
        this.fragments = fragments;

        this.weight = computeWeight();
    }

    public void setWeight(int n, int S) {
        weight = computeWeight3(n, S);
    }

    public Double computeWeight() {
        Integer sumFragments = 0;
        // calcola sum fragments
        for (Integer i : this.fragments) {
            sumFragments += i;
        }
        this.weight = 1.0 * this.Sr * sumFragments;
        // calcola weight
        return this.weight;// / this.Tr;
    }

    public Double computeWeight2(int n) {
        Integer sumFragments = 0;
        Integer sumFragmentsTot = 0;
        // calcola sum fragments
        for (int i = 0; i < fragments.size(); i++) {
            if (i < n) {
                sumFragments += fragments.get(i);
            }
            sumFragmentsTot += fragments.get(i);
        }
        // calcola weight

        return this.weight = 1.0 * sumFragments;// / this.Tr;
    }

    public Double computeWeight3(int n, int S) {
        Integer sumFragments = 0;
        // calcola sum fragments
        for (int i = 0; i < fragments.size(); i++) {
            if (i < n) {
                sumFragments += fragments.get(i);
            }
        }
        // calcola weight
        if (S < 15) {
            return this.weight = 1.0 * (this.Sr - this.Sc) / this.Sc;
        } else {
            return this.weight = 1.0 * sumFragments;
        }
    }
}
