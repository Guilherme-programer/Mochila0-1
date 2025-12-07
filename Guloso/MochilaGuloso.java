import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MochilaGuloso {

    
    static class Item {
        int id; 
        int peso;
        int utilidade;
        double razao; 

        public Item(int id, int peso, int utilidade) {
            this.id = id;
            this.peso = peso;
            this.utilidade = utilidade;
            this.razao = (peso > 0) ? (double) utilidade / peso : 0; 
        }
    }

    // Classe auxiliar para encapsular o resultado
    public static class KnapsackAproxSolution {
        public final long totalUtility;
        public final List<Integer> selectedItems; // Lista completa
        public final long totalWeight;

        public KnapsackAproxSolution(long totalUtility, List<Integer> selectedItems, long totalWeight) {
            this.totalUtility = totalUtility;
            this.selectedItems = selectedItems;
            this.totalWeight = totalWeight;
        }
    }

    public static KnapsackAproxSolution knapsackGuloso(int[] pesos, int[] utilidades, int L) {
        int n = pesos.length;
        List<Item> itens = new ArrayList<>();

        // 1. Preparação e Cálculo da Razão (Custo-Benefício)
        for (int i = 0; i < n; i++) {
            itens.add(new Item(i + 1, pesos[i], utilidades[i]));
        }

        // 2. Ordenação dos Itens 
        Collections.sort(itens, Comparator.comparingDouble((Item item) -> item.razao).reversed());

        long pesoAtual = 0;
        long utilidadeTotal = 0;
        List<Integer> itensSelecionados = new ArrayList<>();

        // 3. Seleção dos Itens
        for (Item item : itens) {
            if (pesoAtual + item.peso <= L) {
                pesoAtual += item.peso;
                utilidadeTotal += item.utilidade;
                itensSelecionados.add(item.id);
            }
        }
        
      
        return new KnapsackAproxSolution(utilidadeTotal, itensSelecionados, pesoAtual);
    }
    
    
    public static void gerarDadosMassivos(int N, int L, int[] pesos, int[] utilidades) {

    // Professor, se quiser testar outros valores, pode alterar para uma seed aleatoria aqui e colocar a mesma no PD.
    Random rand = new Random(42); 
    
    for (int i = 0; i < N; i++) {
        // Pesos entre 1 e 50 deram o resultado otimo, mas se mudar para 1000 começa a ficar aproximado
        pesos[i] = rand.nextInt(1000) + 1; 
        utilidades[i] = rand.nextInt(5) + 1; // Utilidades entre 1 e 5
    }
}

    
    public static void gravarDadosEmArquivo(int N, int L, int[] pesos, int[] utilidades, String nomeArquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            writer.println("--- DADOS DE ENTRADA DO PROBLEMA DA MOCHILA (GULOSO) ---");
            writer.println("CAPACIDADE (L): " + L);
            writer.println("TOTAL DE ITENS (N): " + N);
            writer.println("-----------------------------------------------------");
            writer.println("ITEM | PESO (p) | UTILIDADE (u)");
            writer.println("-----------------------------------------------------");
            
            for (int i = 0; i < N; i++) {
                writer.printf("%4d | %8d | %13d\n", i + 1, pesos[i], utilidades[i]);
            }
            System.out.println("✅ Arquivo de dados salvo com sucesso: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        // --- PARÂMETROS DE TESTE ---
        int N_TESTE = 1000; 
        int CAPACIDADE = 100;
        String NOME_ARQUIVO = "guloso_data_N" + N_TESTE + "_L" + CAPACIDADE + ".txt";
        
        int[] PESOS = new int[N_TESTE];
        int[] UTILIDADES = new int[N_TESTE];
        
        gerarDadosMassivos(N_TESTE, CAPACIDADE, PESOS, UTILIDADES);

        // GRAVAÇÃO DOS DADOS DE ENTRADA
        gravarDadosEmArquivo(N_TESTE, CAPACIDADE, PESOS, UTILIDADES, NOME_ARQUIVO);
        
        System.out.println("\n--- Teste de Eficiência Massiva do Algoritmo Guloso ---");
        System.out.println("Capacidade (L): " + CAPACIDADE + " | Total de Itens (n): " + N_TESTE);
        
        long inicio = System.nanoTime();
        KnapsackAproxSolution resultado = knapsackGuloso(PESOS, UTILIDADES, CAPACIDADE);
        long fim = System.nanoTime();
        
        long duracaoMs = TimeUnit.NANOSECONDS.toMillis(fim - inicio);

        System.out.println("\n--- Complexidade Polinomial O(N log N) ---");
        System.out.println("Tempo de Execução para 100.000 itens: " + duracaoMs + " ms");
        System.out.println("Cálculo teórico: O(100.000 * log 100.000) ≈ 1.66 milhões de operações.");

        System.out.println("\n--- Solução Aproximada Encontrada ---");
        System.out.println("Utilidade Total Aproximada: " + resultado.totalUtility);
        System.out.println("Peso Total Ocupado: " + resultado.totalWeight);
        
      
        System.out.println("Itens Selecionados: " + resultado.selectedItems);
    }
}