import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MochilaPDTesteAltoN {


    public static class KnapsackSolution {
        public final int maxUtility;
        public final List<Integer> selectedItems;

        public KnapsackSolution(int maxUtility, List<Integer> selectedItems) {
            this.maxUtility = maxUtility;
            this.selectedItems = selectedItems;
        }
    }

    // --- Algoritmo de Programação Dinâmica ---

    public static KnapsackSolution solveKnapsack(int[] pesos, int[] utilidades, int L) {
        int n = pesos.length;
        // Matriz DP: DP[capacidade][item]
        int[][] DP = new int[L + 1][n + 1]; 

        // Fase 1: Preenchimento da Matriz 
        for (int j = 1; j <= n; j++) { 
            int pj = pesos[j - 1];   
            int uj = utilidades[j - 1]; 
            
            for (int i = 0; i <= L; i++) { 
                DP[i][j] = DP[i][j - 1];
                
                if (i >= pj) {
                    
                    int valorAoIncluir = uj + DP[i - pj][j - 1];
                    DP[i][j] = Math.max(DP[i][j], valorAoIncluir);
                }
            }
        }
        
        int maxUtility = DP[L][n]; 
        
        // Fase 2: Reconstrução da Solução (Backtracking)
        List<Integer> selectedItems = new ArrayList<>();
        int i = L; 
        int j = n; 

        while (j > 0 && i >= 0) {
            
            if (DP[i][j] != DP[i][j - 1]) {
                selectedItems.add(j); 
                i = i - pesos[j - 1]; 
                j = j - 1;
            } else {
                j = j - 1; 
            }
        }
        
        Collections.reverse(selectedItems); 
        return new KnapsackSolution(maxUtility, selectedItems);
    }
    
   
    public static void gerarDados(int N, int L, int[] pesos, int[] utilidades) {
    //Se quiser testar com outros valores é so alterar a seed aqui e no Guloso professor.
    Random rand = new Random(42); 
    
    for (int i = 0; i < N; i++) {
        // Pesos entre 1 e 50 geram 100% ou quase no guloso, se quiser deixar mais complexo, defina como 1000 nos dois.
        pesos[i] = rand.nextInt(1000) + 1; 
        utilidades[i] = rand.nextInt(5) + 1; 
    }
}
    
    
    public static void gravarDadosEmArquivo(int N, int L, int[] pesos, int[] utilidades, String nomeArquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            writer.println("--- DADOS DE ENTRADA DO PROBLEMA DA MOCHILA (PD) ---");
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
    
    
    public static void imprimirDados(int[] pesos, int[] utilidades) {
        System.out.println("\n--- Amostra dos Dados de Entrada ---");
        System.out.println("Item | Peso (p) | Utilidade (u)");
        System.out.println("--------------------------------");
        int N_amostra = Math.min(pesos.length, 10); 
        for (int i = 0; i < N_amostra; i++) {
            System.out.printf("%4d | %8d | %13d\n", i + 1, pesos[i], utilidades[i]);
        }
        if (pesos.length > N_amostra) {
            System.out.println("... e mais " + (pesos.length - N_amostra) + " itens.");
        }
        System.out.println("--------------------------------\n");
    }

 

    public static void main(String[] args) {
        // PARÂMETROS DE TESTE
        int N_TESTE = 1000; 
        int CAPACIDADE = 100;
        String NOME_ARQUIVO = "knapsack_data_N" + N_TESTE + "_L" + CAPACIDADE + ".txt";
        
        int[] PESOS = new int[N_TESTE];
        int[] UTILIDADES = new int[N_TESTE];
        
      
        gerarDados(N_TESTE, CAPACIDADE, PESOS, UTILIDADES);
        
       
        gravarDadosEmArquivo(N_TESTE, CAPACIDADE, PESOS, UTILIDADES, NOME_ARQUIVO);
        
        System.out.println("\n--- Teste de Eficiência da Programação Dinâmica ---");
        System.out.println("Capacidade (L): " + CAPACIDADE + " | Total de Itens (n): " + N_TESTE);

        
        imprimirDados(PESOS, UTILIDADES);
        
        // 4. Execução do Algoritmo PD
        long inicio = System.currentTimeMillis();
        KnapsackSolution resultado = solveKnapsack(PESOS, UTILIDADES, CAPACIDADE);
        long fim = System.currentTimeMillis();
        
        System.out.println("--- Complexidade Pseudo-Polinomial ---");
        System.out.println("Tempo de Execução (O(n * L)): " + (fim - inicio) + " ms");
        System.out.println("Cálculo envolve (n * L) = " + (N_TESTE * CAPACIDADE) + " operações básicas."); // 100.000 ops

        System.out.println("\n--- Resultado Ótimo ---");
        System.out.println("Valor Máximo de Utilidade: " + resultado.maxUtility);
        System.out.println("Itens Selecionados (Alguns): " + resultado.selectedItems.subList(0, Math.min(10, resultado.selectedItems.size())) + "...");
    }
}