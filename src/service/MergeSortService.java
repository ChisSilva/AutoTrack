package service;

import java.util.Comparator;
import java.util.List;

public class MergeSortService<T> implements Ordenacao<T> {
    @Override
    public void ordenar(List<T> lista, Comparator<T> comparador) {
        if (lista == null || lista.size() <= 1) {
            return;
        }

        mergeSort(lista, comparador);
    }

    private void mergeSort(List<T> lista, Comparator<T> comparador) {
        int n = lista.size();
        if (n < 2) {
            return;
        }

        int meio = n / 2;
        List<T> esquerda = lista.subList(0, meio);
        List<T> direita = lista.subList(meio, n);

        mergeSort(esquerda, comparador);
        mergeSort(direita, comparador);

        merge(lista, esquerda, direita, comparador);
    }

    private void merge(List<T> lista, List<T> esquerda, List<T> direita, Comparator<T> comparador) {
        int i = 0, j = 0, k = 0;

        while (i < esquerda.size() && j < direita.size()) {
            if (comparador.compare(esquerda.get(i), direita.get(j)) <= 0) {
                lista.set(k++, esquerda.get(i++));
            } else {
                lista.set(k++, direita.get(j++));
            }
        }

        while (i < esquerda.size()) {
            lista.set(k++, esquerda.get(i++));
        }

        while (j < direita.size()) {
            lista.set(k++, direita.get(j++));
        }

    
}
