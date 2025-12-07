import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit; 


public class MochilaForcaBruta {

   
    public static class KnapsackResult {
        public final int maxUtilidade;
        public final List<Integer> itensEscolhidos;

        public KnapsackResult(int maxUtilidade, List<Integer> itensEscolhidos) {
            this.maxUtilidade = maxUtilidade;
            this.itensEscolhidos = itensEscolhidos;
        }
    }

    /**
     * Resolve o Problema da Mochila 0/1 por enumeração exaustiva (Força Bruta).
     *
     * @param pesos Lista de pesos dos itens.
     * @param utilidades Lista de utilidades dos itens.
     * @param L Capacidade máxima da mochila.
     * @return Um objeto KnapsackResult com a solução ótima.
     */
    public static KnapsackResult knapsack01ForcaBruta(int[] pesos, int[] utilidades, int L) {
        int n = pesos.length;
        long numCombinacoes = 1L << n; 
        int maxUtilidade = 0;
        List<Integer> melhorSubconjunto = new ArrayList<>();

        // Loop principal que itera sobre todas as 2^n combinações (máscaras de bits)
        for (long i = 0; i < numCombinacoes; i++) {
            int pesoAtual = 0;
            int utilidadeAtual = 0;
            List<Integer> subconjuntoAtual = new ArrayList<>();

           
            for (int j = 0; j < n; j++) {
                
                if ((i >> j) % 2 == 1) { 
                    pesoAtual += pesos[j];
                    utilidadeAtual += utilidades[j];
                    subconjuntoAtual.add(j + 1); 
                }
            }

            if (pesoAtual <= L) {
                
                
                if (utilidadeAtual > maxUtilidade) {
                    maxUtilidade = utilidadeAtual;
                    melhorSubconjunto = subconjuntoAtual;
                }
            }
        }

        return new KnapsackResult(maxUtilidade, melhorSubconjunto);
    }

    public static void main(String[] args) {
        // Exemplo 1: N pequeno, solução rápida.
        int[] pesos1 = {2, 3, 4, 5};
        int[] utilidades1 = {3, 4, 5, 8};
        int capacidade1 = 5;
        
        System.out.println("--- Teste 1: N Pequeno (N=" + pesos1.length + ") ---");
        long inicio1 = System.nanoTime();
        KnapsackResult resultado1 = knapsack01ForcaBruta(pesos1, utilidades1, capacidade1);
        long fim1 = System.nanoTime();

        System.out.println("Utilidade Máxima: " + resultado1.maxUtilidade);
        System.out.println("Itens Escolhidos: " + resultado1.itensEscolhidos);
        long duracao1 = TimeUnit.NANOSECONDS.toMillis(fim1 - inicio1);
        System.out.println("Tempo de Execução: " + duracao1 + " ms");
        System.out.println("----------------------------------------\n");


       
       
        int N_LIMIT = 28;
        int[] pesos_limit = new int[N_LIMIT];
        int[] utilidades_limit = new int[N_LIMIT];
        int capacidade_limit = 1000;
        
        
        for (int i = 0; i < N_LIMIT; i++) {
            pesos_limit[i] = (i % 10) + 1; // Pesos entre 1 e 10
            utilidades_limit[i] = (i % 15) + 5; // Utilidades entre 5 e 20
        }

        System.out.println("--- Teste 2: N no Limite Prático (N=" + N_LIMIT + ") ---");
        System.out.println("EXECUTANDO... Pode levar um bom tempo para N>25  (N = " + N_LIMIT + ")...");
        
        long inicio2 = System.nanoTime();
       
        knapsack01ForcaBruta(pesos_limit, utilidades_limit, capacidade_limit);
        long fim2 = System.nanoTime();
        
        long duracao2 = TimeUnit.NANOSECONDS.toSeconds(fim2 - inicio2);
        System.out.println("\nTempo de Execução (N=" + N_LIMIT + "): " + duracao2 + " segundos");
        System.out.println("----------------------------------------");
        
    }
}