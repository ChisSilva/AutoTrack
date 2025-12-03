package domain;

import util.NegocioException;

public class Moto extends Veiculo {
    
    private int cilindradas;

    public Moto(String placa, String marca, String modelo, int ano, double quilometragem, int cilindradas) throws NegocioException {
        super(placa, marca, modelo, ano, quilometragem);
        this.cilindradas = cilindradas;
    }

    @Override
    public String detalhesAdicionais() {
        return "Tipo: Moto | Cilindradas: " + cilindradas + "cc";
    }
}
