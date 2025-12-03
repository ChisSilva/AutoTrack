package domain;

import util.NegocioException;

public class Carro extends Veiculo{
    
    private int numeroPortas;

    public Carro(String placa, String marca, String modelo, int ano, double quilometragem, int numeroPortas) throws NegocioException {
        super(placa, marca, modelo, ano, quilometragem);
        this.numeroPortas = numeroPortas;
    }

    @Override
    public String detalhesAdicionais() {
        return "Tipo: Carro | Numero de portas: " + numeroPortas;
    }

}
