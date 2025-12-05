package estructure;

import java.util.ArrayList;
import java.util.List;

public class AVL<K extends Comparable<K>, V> implements Arvore<K, V> {

    private No<K, V> raiz;

    public AVL() {
        this.raiz = null;
    }

    private int altura(No<K, V> n) {
        return (n == null) ? 0 : n.getAltura();
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    private int fatorBalanceamento(No<K, V> n) {
        return (n == null) ? 0 : altura(n.esquerda) - altura(n.direita);
    }

    private void atualizarAltura(No<K, V> n) {
        if (n != null) {
            n.altura = 1 + max(altura(n.esquerda), altura(n.direita));
        }
    }


    // Rotações

    private No<K, V> rotacaoDireita(No<K, V> y) {
        No<K, V> x = y.esquerda;
        No<K, V> T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        atualizarAltura(y);
        atualizarAltura(x);

        return x;
    }

    private No<K, V> rotacaoEsquerda(No<K, V> x) {
        No<K, V> y = x.direita;
        No<K, V> T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        atualizarAltura(x);
        atualizarAltura(y);

        return y;
    }

    private No<K, V> balancear(No<K, V> no) {
        atualizarAltura(no);
        int balance = fatorBalanceamento(no);

        // Caso LL
        if (balance > 1 && fatorBalanceamento(no.esquerda) >= 0) {
            return rotacaoDireita(no);
        }

        // Caso RR
        if (balance < -1 && fatorBalanceamento(no.direita) <= 0) {
            return rotacaoEsquerda(no);
        }

        // Caso LR
        if (balance > 1 && fatorBalanceamento(no.esquerda) < 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        // Caso RL
        if (balance < -1 && fatorBalanceamento(no.direita) > 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    @Override
    public void inserir(K chave, V valor) {
        raiz = inserirRecursivo(raiz, chave, valor);
    }

    private No<K, V> inserirRecursivo(No<K, V> no, K chave, V valor) {
        if (no == null) {
            return new No<>(chave, valor);
        }

        int comparacao = chave.compareTo(no.chave);

        if (comparacao < 0) {
            no.esquerda = inserirRecursivo(no.esquerda, chave, valor);
        } else if (comparacao > 0) {
            no.direita = inserirRecursivo(no.direita, chave, valor);
        } else {
            // Atualiza o valor se a chave já existir
            no.valor = valor; 
            return no;
        }

        return balancear(no);
    }

    @Override
    public V buscar(K chave) {
        No<K, V> no = buscarRecursivo(this.raiz, chave);
        return (no == null) ? null : no.valor;
    }

    private No<K, V> buscarRecursivo(No<K, V> no, K chave) {
        if (no == null) {
            return null;
        }

        int comparacao = chave.compareTo(no.chave);

        if (comparacao < 0) {
            return buscarRecursivo(no.esquerda, chave);
        } else if (comparacao > 0) {
            return buscarRecursivo(no.direita, chave);
        } else {
            return no;
        }

        @Override
        public List<V> emOrdem() {
            List<V> lista = new ArrayList<>();
            emOrdemRecursivo(this.raiz, lista);
            return lista;
        }

        private void emOrdemRecursivo(No<K, V> no, List<V> lista) {
            if (no != null) {
                emOrdemRecursivo(no.esquerda, lista);
                lista.add(no.valor);
                emOrdemRecursivo(no.direita, lista);
            }
        }

        private No<K, V> minValorNo(No<K, V> no) {
            No<K, V> atual = no;
            while (atual.esquerda != null) {
                atual = atual.esquerda;
            }
            return atual;
        }

        @Override
        public V remover(K chave) {
            if (raiz == null) {
                return null;
            }

            V valorRemovido = buscar(chave);
            if (valorRemovido != null) {
                this.raiz = removerRecursivo(this.raiz, chave);
            }

            return valorRemovido;
        }

        private No<K, V> removerRecursivo(No<K, V> no, K chave) {
            if (no == null) {
                return no;
            }

            int comparacao = chave.compareTo(no.chave);

            if (comparacao < 0) {
                no.esquerda = removerRecursivo(no.esquerda, chave);
            } else if (comparacao > 0) {
                no.direita = removerRecursivo(no.direita, chave);
            } else {
                // Nó com apenas um filho ou nenhum filho
                if ((no.esquerda == null) || (no.direita == null)) {
                    No<K, V> temp = (no.esquerda != null) ? no.esquerda : no.direita;

                    // Sem filhos
                    if (temp == null) {
                        no = null;
                    } else { 
                        // Um filho
                        no = temp;
                    }
                } else {
                    // Nó com dois filhos: obter o sucessor inorder (menor na subárvore direita)
                    No<K, V> temp = minValorNo(no.direita);

                    // Copiar o valor do sucessor emordem para este nó
                    no.chave = temp.chave;
                    no.valor = temp.valor;

                    // Remover o sucessor emordem
                    no.direita = removerRecursivo(no.direita, temp.chave);
                }
            }

            // Se o nó só tinha um filho ou nenhum filho
            if (no == null) {
                return no;
            }

            return balancear(no);
        }
}