package estructure;

import java.util.List;

public interface Arvore<K extends Comparable<K>, V> {
    
    void inserir(K chave, V valor);
    V buscar(K chave);
    void remover(K chave);

    List<V> travessiaEmOrdem();
}
