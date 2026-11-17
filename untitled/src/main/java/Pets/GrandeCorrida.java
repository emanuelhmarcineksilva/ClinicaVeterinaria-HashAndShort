package Pets;

// A GRANDE CORRIDA: 3 algoritmos x 3 situações x 3 tamanhos = 27 medições
public class GrandeCorrida {

    static final int[] TAMANHOS = {50, 1000, 10000};
    static final String[] SITUACOES = {"Aleatorio", "Ja ordenado", "Ordem inversa"};
    static final int REPETICOES = 5;   // cada medição roda 5 vezes; usamos a média do tempo

    // Chamado pelo menu (opção 8) com os bichinhos do arquivo: aquece, corre e imprime as tabelas
    public static void executar(Pet[] doGrupo) {
        System.out.println("Aquecendo os motores...");
        aquecer();
        ResultadoOrdenacao[][][] res = correr(doGrupo, TAMANHOS, REPETICOES);
        imprimirTabela("TEMPO MEDIO (ms)", res, 0);
        imprimirTabela("COMPARACOES", res, 1);
        imprimirTabela("TROCAS (no Insertion Sort: deslocamentos)", res, 2);
    }

    // Sem bichinhos do grupo: todos os tamanhos são gerados
    public static ResultadoOrdenacao[][][] correr(int[] tamanhos, int repeticoes) {
        return correr(null, tamanhos, repeticoes);
    }

    // Faz todas as medições e devolve res[algoritmo][situacao][tamanho] (os testes usam este)
    public static ResultadoOrdenacao[][][] correr(Pet[] doGrupo, int[] tamanhos, int repeticoes) {
        ResultadoOrdenacao[][][] res = new ResultadoOrdenacao[3][3][tamanhos.length];

        for (int t = 0; t < tamanhos.length; t++) {
            Pet[][] entradas = montarEntradas(doGrupo, tamanhos[t]);
            for (int s = 0; s < 3; s++) {
                for (int a = 0; a < 3; a++) {
                    res[a][s][t] = medir(a, entradas[s], repeticoes);
                }
            }
            String origem = "gerados";
            if (usaOGrupo(doGrupo, tamanhos[t])) {
                origem = "do grupo";
            }
            System.out.println("  corrida com " + tamanhos[t] + " bichinhos " + origem + " concluida");
        }
        return res;
    }

    // As 3 situações a partir da mesma base: {aleatório, já ordenado, ordem inversa}.
    // n = 50: os bichinhos do grupo (arquivo de dados). 1.000 e 10.000: o gerador.
    public static Pet[][] montarEntradas(Pet[] doGrupo, int tamanho) {
        Pet[] crescente;
        if (usaOGrupo(doGrupo, tamanho)) {
            crescente = new Pet[tamanho];
            for (int i = 0; i < tamanho; i++) {
                crescente[i] = doGrupo[i];            // cópia: o vetor do grupo não muda
            }
            Ordenacao.insertionSort(crescente, false);   // a NOSSA ordenação, fora do cronômetro
        } else {
            crescente = GeradorPets.gerarOrdenados(tamanho);   // já sai em ordem crescente
        }
        return new Pet[][] {
            GeradorPets.embaralhado(crescente),   // s = 0: aleatório
            crescente,                            // s = 1: já ordenado
            GeradorPets.invertido(crescente)      // s = 2: ordem inversa
        };
    }

    // O grupo tem bichinhos suficientes para este tamanho? (50 sim; 1.000 e 10.000 não)
    private static boolean usaOGrupo(Pet[] doGrupo, int tamanho) {
        return doGrupo != null && doGrupo.length >= tamanho;
    }

    // Ordena "repeticoes" cópias da mesma entrada e guarda a média do tempo
    private static ResultadoOrdenacao medir(int algoritmo, Pet[] entrada, int repeticoes) {
        ResultadoOrdenacao r = null;
        long somaTempos = 0;
        for (int rep = 0; rep < repeticoes; rep++) {
            Pet[] copia = Ordenacao.copiar(entrada);       // fora do cronômetro

            long inicio = System.nanoTime();
            r = Ordenacao.ordenar(algoritmo, copia, false); // narrar = false!
            long fim = System.nanoTime();

            somaTempos += fim - inicio;
            if (!Ordenacao.estaOrdenado(copia)) {
                System.out.println("ERRO: " + Ordenacao.NOMES[algoritmo] + " nao ordenou!");
            }
        }
        r.tempoNanos = somaTempos / repeticoes;
        return r;
    }

    // O Java fica mais rápido depois de rodar um trecho algumas vezes (compilador JIT).
    // Rodamos tudo uma vez "de mentira" para a primeira medição não sair prejudicada.
    private static void aquecer() {
        for (int k = 0; k < 3; k++) {
            Pet[] treino = GeradorPets.embaralhado(GeradorPets.gerarOrdenados(2000));
            for (int a = 0; a < 3; a++) {
                Ordenacao.ordenar(a, Ordenacao.copiar(treino), false);
            }
        }
    }

    // qual: 0 = tempo, 1 = comparações, 2 = trocas
    private static void imprimirTabela(String titulo, ResultadoOrdenacao[][][] res, int qual) {
        System.out.println("\n" + titulo);
        System.out.printf("%-15s %-14s %13s %13s %13s%n",
                "Algoritmo", "Situacao", "n = 50", "n = 1.000", "n = 10.000");
        for (int a = 0; a < 3; a++) {
            for (int s = 0; s < 3; s++) {
                System.out.printf("%-15s %-14s", Ordenacao.NOMES[a], SITUACOES[s]);
                for (int t = 0; t < 3; t++) {
                    ResultadoOrdenacao r = res[a][s][t];
                    if (qual == 0) {
                        System.out.printf(" %13.3f", r.tempoNanos / 1_000_000.0);
                    } else if (qual == 1) {
                        System.out.printf(" %13d", r.comparacoes);
                    } else {
                        System.out.printf(" %13d", r.trocas);
                    }
                }
                System.out.println();
            }
        }
    }
}
