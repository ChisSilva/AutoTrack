package domain;

import javax.naming.NamingException;
import util.InfoAutor;
import util.NegocioException;

@InfoAutor(nome = "Carlos Alberto de Nobrega", data = "12/03/1936")
public abstract class Veiculo {

    private String placa;
    private String marca;
    private String modelo;
    private int ano;
    private double quilometragem;

    public Veiculo(String placa, String marca, String modelo, int ano, double quilometragem) throws NegocioException {
        // Verifica as regras de negocio
        validarRegras(placa, ano, quilometragem);
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.quilometragem = quilometragem;
    }

    // Get's e Set's
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    } 
    
    public double getQuilometragem() {
        return quilometragem;
    }
    public void setQuilometragem(double quilometragem) {
        this.quilometragem = quilometragem;
    }


    // Regras de negocio
    private void validarRegras(String placa, int ano, double quilometragem) throws NegocioException {
        // Regra 1 - Ano
        if (ano < 1960) {
            throw new NegocioException("Ano invalido");
        }

        // Regra 2 - Quilometragem não negativa
        if (quilometragem < 0) {
            throw new NegocioException("Quilometragem invalida");
        }

        // Regra 3 - Placa no padrão LLL-NNNN
        if (!placa.matches("^[A-Z]{3}-\\d{4}$")) { 
            throw new NegocioException("A placa deve seguir o padrão LLL-NNNN");
        }
    }

    // Muda a depender do tipo de veiculo (carro ou moto)
    public abstract String detalhesAdicionais();

    @Override
    public String toString() {
        return "Placa: " + placa + 
               " | Marca: " + marca + 
               " | Modelo: " + modelo + 
               " | Ano: " + ano + 
               " | Km: " + quilometragem + 
               " | " + detalhesAdicionais();
    }

}
