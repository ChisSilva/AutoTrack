package estructure;

public class No<K extends Comparable<K>, V> {
    K chave;
    V valor;
    int altura;

    No<K, V> esquerda;
    No<K, V> direita;

    public No(K chave, V valor) {

        this.chave = chave;
        this.valor = valor;
        this.altura = 1;
        this.esquerda = null;
        this.direita = null;
    }

    public K getChave() {
        return chave;
    }
    public V getValor() {
        return valor;
    }
    public int getAltura() {
        return altura;
    }
}