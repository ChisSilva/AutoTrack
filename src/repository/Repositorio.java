package repository;

import util.NegocioException;
import java.util.List;

public interface Repositorio<T> {
    
    void adicionar(T item) throws NegocioException;

    T buscar(String id);

    void remover(String id);

    List<T> listar();
}
