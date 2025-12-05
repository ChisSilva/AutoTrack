package app;

import domain.*;
import repository.*;
import service.*;
import util.InfoAutor;
import util.NegocioException;
import estructure.*;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;


public class App {

    // Instancia o Repositório Genérico para Veículos
    private static RepositorioHash<Veiculo> repositorioVeiculos = 
        new RepositorioHash<>("placa"); // O campo "placa" é usado para buscar ID

    // Instancia o Serviço de Ordenação
    private static MergeSortService<Veiculo> mergeSorter = new MergeSortService<>();
    
    // Instancia a Árvore AVL
    private static AVL<String, Veiculo> arvoreVeiculos = new AVL<>();

    public static void main(String[] args) {
        System.out.println("====================================================");
        System.out.println("         PROJETO U3 - CADASTRO DE VEÍCULOS          ");
        System.out.println("====================================================");

        // --- 1. DEMONSTRAÇÃO DE REFLECTION E ANOTAÇÕES ---
        demonstrarReflection();
        System.out.println("\n----------------------------------------------------");

        // --- 2. DEMONSTRAÇÃO DE CRUD, EXCEÇÕES E POLIMORFISMO ---
        demonstrarCRUD();
        System.out.println("\n----------------------------------------------------");

        // --- 3. INTEGRAÇÃO COM A ÁRVORE AVL ---
        demonstrarArvore();
        System.out.println("\n----------------------------------------------------");

        // --- 4. DEMONSTRAÇÃO DE ORDENAÇÃO (MERGESORT) ---
        demonstrarOrdenacao();
        System.out.println("\n====================================================");
    }
    
    // -----------------------------------------------------------------
    // Método 1: Reflection
    // -----------------------------------------------------------------

    private static void demonstrarReflection() {
        System.out.println("1. LENDO ANOTAÇÕES @INFOAUTOR (REFLECTION)");
        
        Class<?>[] classesAnotadas = {Veiculo.class, RepositorioHash.class};
        
        for (Class<?> cls : classesAnotadas) {
            if (cls.isAnnotationPresent(InfoAutor.class)) {
                InfoAutor info = cls.getAnnotation(InfoAutor.class);
                System.out.printf("  [Lido em %s]: Autor: %s, Data: %s\n", 
                                  cls.getSimpleName(), info.nome(), info.data());
            }
        }
    }
    
    // -----------------------------------------------------------------
    // Método 2: CRUD, Exceções e Polimorfismo
    // -----------------------------------------------------------------

    private static void demonstrarCRUD() {
        System.out.println("2. CRUD DE VEÍCULOS (Com Polimorfismo e Exceções)");
        
        // --- ADICIONAR (Sucesso) ---
        try {
            Veiculo carro1 = new Carro("ABC-1234", "Honda", "Civic", 2020, 50000.0, 4);
            Veiculo moto1 = new Moto("XYZ-9876", "Kawasaki","Ninja", 2023, 1000.0, 600);
            
            // Adiciona ao Repositório e à Árvore
            repositorioVeiculos.adicionar(carro1);
            arvoreVeiculos.inserir(carro1.getPlaca(), carro1);
            
            repositorioVeiculos.adicionar(moto1);
            arvoreVeiculos.inserir(moto1.getPlaca(), moto1);
            
            System.out.println("  [OK] Veículos cadastrados com sucesso.");

        } catch (NegocioException e) {
            System.err.println("  [ERRO GERAL] " + e.getMessage());
        }
        
        // --- TRATAMENTO DE EXCEÇÃO (Regras de Negócio) ---
        System.out.println("\n  -> Teste de Exceção de Negócio:");
        try {
            // Tentativa de adicionar veículo com ano inválido (Regra: Ano >= 1960)
            Veiculo carroInvalido = new Carro("DEF-5678", "Volkswagen", "Fusca", 1959, 1000.0, 2); 
            repositorioVeiculos.adicionar(carroInvalido);
            
        } catch (NegocioException e) {
            // try/catch no Main com mensagens claras (Requisito)
            System.err.println("  [EXCEÇÃO CAPTURADA] Falha ao adicionar veículo: " + e.getMessage());
        }
        
        // --- BUSCA ---
        System.out.println("\n  -> Busca (Placa: ABC-1234):");
        Veiculo buscado = repositorioVeiculos.buscar("ABC-1234");
        if (buscado != null) {
            // Imprime usando o toString() polimórfico
            System.out.println("    [ENCONTRADO] " + buscado); 
        }

        // --- LISTAR TODOS (Polimorfismo Ativo) ---
        System.out.println("\n  -> Listagem Completa (Polimorfismo Ativo):");
        List<Veiculo> listaAtual = repositorioVeiculos.listar();
        listaAtual.forEach(v -> System.out.println("    " + v)); // Cada subclasse imprime seus Detalhes Adicionais

        // --- REMOVER ---
        System.out.println("\n  -> Remoção (Placa: XYZ-9876):");
        repositorioVeiculos.remover("XYZ-9876");
        arvoreVeiculos.remover("XYZ-9876"); // Remove da Árvore também
        
        // --- VERIFICAÇÃO PÓS REMOÇÃO ---
        System.out.println("\n  -> Listagem após remoção:");
        repositorioVeiculos.listar().forEach(v -> System.out.println("    " + v));
    }

    // -----------------------------------------------------------------
    // Método 3: Integração com a Árvore AVL
    // -----------------------------------------------------------------

    private static void demonstrarArvore() {
        System.out.println("3. DEMONSTRAÇÃO DA ÁRVORE AVL");
        System.out.println("  (A chave é a Placa, a travessia Em-Ordem retorna valores ordenados pela Placa)");
        
        // Adiciona mais um item para ver o balanceamento
        try {
            Veiculo carro2 = new Carro("MMM-0000", "Fiat","Fiorino", 2022, 12000.0, 5);
            repositorioVeiculos.adicionar(carro2);
            arvoreVeiculos.inserir(carro2.getPlaca(), carro2);
        } catch (NegocioException e) {
             System.err.println("  [ERRO AVL] " + e.getMessage());
        }

        // Travessia em-ordem (Retorna valores ordenados pela chave - Placa)
        List<Veiculo> veiculosOrdenadosArvore = arvoreVeiculos.travessiaEmOrdem();
        
        System.out.println("\n  -> Travessia Em-Ordem da AVL (Ordenado por Placa):");
        veiculosOrdenadosArvore.forEach(v -> System.out.println("    Placa: " + v.getPlaca() + " | Modelo: " + v.getModelo()));
    }
    
    // -----------------------------------------------------------------
    // Método 4: Ordenação MergeSort
    // -----------------------------------------------------------------

    private static void demonstrarOrdenacao() {
        System.out.println("4. DEMONSTRAÇÃO DE ORDENAÇÃO (MergeSort com Comparator)");
        
        // Pega a lista atual do Repositório
        List<Veiculo> listaParaOrdenar = repositorioVeiculos.listar();
        
        // --- ORDENAÇÃO POR ANO (Usando VeiculoAnoComparator) ---
        System.out.println("\n  -> Ordenando por ANO (Mais Antigo para Mais Novo):");
        
        // 1. Instancia o Comparator
        Comparator<Veiculo> compAno = new VeiculoAnoComparador(); 
        
        // 2. Aplica o MergeSort
        long startTime = System.currentTimeMillis();
        mergeSorter.ordenar(listaParaOrdenar, compAno);
        long endTime = System.currentTimeMillis();
        
        // Requisito: medir tempo de ordenar
        System.out.printf("  [LOG] Tempo de ordenação: %d ms\n", (endTime - startTime));
        
        // Imprime o resultado
        listaParaOrdenar.forEach(v -> System.out.println("    Ano: " + v.getAno() + " | Placa: " + v.getPlaca()));
        
        // --- ORDENAÇÃO POR QUILOMETRAGEM (Usando VeiculoKmComparator) ---
        System.out.println("\n  -> Ordenando por QUILOMETRAGEM (Menor para Maior):");
        
        // 1. Instancia o Comparator
        Comparator<Veiculo> compKm = new VeiculoKmComparador(); 
        
        // 2. Aplica o MergeSort
        mergeSorter.ordenar(listaParaOrdenar, compKm);
        
        // Imprime o resultado
        listaParaOrdenar.forEach(v -> System.out.println("    Km: " + v.getQuilometragem() + " | Placa: " + v.getPlaca()));
    }
}