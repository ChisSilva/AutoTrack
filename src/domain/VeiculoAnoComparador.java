package domain;

import java.util.Comparator;

public class VeiculoAnoComparador implements Comparator<Veiculo>{
    @Override
    public int compare(Veiculo v1, Veiculo v2) {

        return Integer.compare(v1.getAno(), v2.getAno());
    }
}
