package service;

import java.util.Comparator;
import java.util.List;

public interface Ordenacao<T> {
    void ordenar(List<T> lista, Comparator<T> comparador);
}
