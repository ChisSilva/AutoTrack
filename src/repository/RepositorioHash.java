package repository;

import java.util.*;
import util.InfoAutor;
import util.NegocioException;

@InfoAutor(nome = "Bruno Henrique", data = "30/12/1990")
public class RepositorioHash<T> implements Repositorio<T>{
    
    private final Map<String, T> dados;

    private final String idFieldName;

    public RepositorioHash(String idFieldName) {
        this.dados = new HashMap<>();
        this.idFieldName = idFieldName;
    }


    
    @Override
    public void adicionar(T item) throws NegocioException {
        
        try {
            
            String id = (String) item.getClass().getMethod("getPlaca").invoke(item);

            if (dados.containsKey(id)) {
                throw new NegocioException("Veiculo com a placa " + id + " ja cadastrado");
            }
            dados.put(id, item);

        } catch (Exception e) {
            System.err.println("Erro ao obter ID do item (verifique o método getPlaca): " + e.getMessage());
        }
    }

    @Override
    public T buscar(String id) {
        return dados.get(id);
    }

    @Override
    public void remover(String id) {
        if (dados.remove(id) == null) {
            System.out.println("Veiculo com a placa " + id + " nao encontrado");
        } else {
            System.out.println("Veiculo com a placa " + id + " removido com sucesso");
        }
    }

    @Override
    public List<T> listar() {
        return new ArrayList<>(dados.values());
    }

}
