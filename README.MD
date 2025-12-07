# 🎒 Projeto do Problema da Mochila (0/1 Knapsack Problem)

Este repositório contém a implementação e análise comparativa de **três abordagens clássicas** para resolver o Problema da Mochila 0/1, um dos problemas fundamentais de otimização combinatória e classificado como **NP-Difícil**.

## 🎯 Objetivo do Projeto

Demonstrar, na prática, o **trade-off entre otimalidade e eficiência**, comparando:

- 🔵 **Força Bruta (Exata)** — garante a solução ótima, mas é exponencial  
- 🟢 **Programação Dinâmica (Exata)** — solução ótima com complexidade pseudo-polinomial  
- 🟡 **Guloso (Aproximado)** — extremamente eficiente, mas não garante otimalidade

---

# 💻 Algoritmos e Complexidade

| Algoritmo              | Tipo de Solução | Complexidade de Tempo | Melhor Uso |
|------------------------|-----------------|------------------------|------------|
| **Força Bruta (FB)**  | Exata (Ótima)   | 𝑂(N · 2ⁿ)             | Apenas para N ≤ 25 |
| **Programação Dinâmica (PD)** | Exata (Ótima) | 𝑂(N · L) | N e L pequenos |
| **Guloso (Greedy)**   | Aproximada      | 𝑂(N log N)            | N ou L muito grandes |

---

# ⚙️ Estrutura do Código

O projeto contém três implementações principais em Java:

| Arquivo / Classe | Descrição | Principais Métodos |
|------------------|-----------|---------------------|
| **MochilaForcaBruta.java** | Testa todas as 2ⁿ combinações | `knapsackForcaBruta()` |
| **MochilaPDTesteAltoN.java** | Programação Dinâmica com matriz (N+1 × L+1) | `knapsackPD()` |
| **MochilaGuloso.java** | Heurística baseada em razão u/p | `knapsackGuloso()` |
| **Utilitários** | Geração e salvamento de instâncias | `gerarDados()`, `gravarDadosEmArquivo()` |

---

# 🚀 Como Executar o Projeto

### ✔ Pré-requisitos
- **JDK 17+**
- VS Code, IntelliJ IDEA ou terminal

---


# 📦 Dados de Teste
Todas as abordagens usam gerarDados() com seed unificada, garantindo a mesma instância para comparação.

Instâncias usadas nos testes:
N = 1000, L = 100

N = 1000, L = 1000

Ao rodar, um arquivo .txt com todos os pesos e utilidades é gerado automaticamente.

# 📊 Resultados e Conclusões
Resultados Empíricos
| Algoritmo  | Instância (N, L) | Tempo Médio | Qualidade da Solução | Observações                    |
| ---------- | ---------------- | ----------- | -------------------- | ------------------------------ |
| **Guloso** | (1000, 100)      | 6 ms        | **100%**             | Peso <= 50                     |
| **Guloso** | (1000, 100)      | 6 ms        | **97.22%**           | Peso <=1000                    |
| **PD**     | (1000, 100)      | 5 ms        | **100%**             | Matriz pequena (N·L = 100k)    |


Conclusão
O Guloso mostrou excelente desempenho e surpreendeu ao atingir 100% de acerto quando a capacidade é pequena e os pesos são aleatórios pequenos → cenário ideal para heurísticas de razão.

O FB é inviável para N > 25, confirmando sua natureza exponencial.

👥 Contribuição
Desenvolvido por: [Guilherme Eduardo Matos Drumond] e [Robson Duarte Vicente]
Data: Dezembro de 2025
