package domain;

import java.util.Comparator;

public class VeiculoKmComparador implements Comparator<Veiculo> {
    @Override
    public int compare(Veiculo v1, Veiculo v2) {
        
        return Double.compare(v1.getQuilometragem(), v2.getQuilometragem());
    }
    
}
