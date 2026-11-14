package Pets;

// A FILA DA SAÍDA: no fim da tarde, os bichinhos vão para casa em ordem de microchip
public class Ordenacao {

    public static final String[] NOMES = {"Bubble Sort", "Insertion Sort", "Quick Sort"};

    // Chama o algoritmo escolhido (0 = Bubble, 1 = Insertion, 2 = Quick)
    public static ResultadoOrdenacao ordenar(int algoritmo, Pet[] v, boolean narrar) {
        if (algoritmo == 0) return bubbleSort(v, narrar);
        if (algoritmo == 1) return insertionSort(v, narrar);
        return quickSort(v, narrar);
    }

    // Cópia nova do vetor: cada algoritmo recebe os MESMOS dados, sem estragar o original
    public static Pet[] copiar(Pet[] original) {
        Pet[] copia = new Pet[original.length];
        for (int i = 0; i < original.length; i++) {
            copia[i] = original[i];
        }
        return copia;
    }

    // Confere o resultado: cada microchip tem que ser menor que o seguinte
    public static boolean estaOrdenado(Pet[] v) {
        for (int i = 0; i < v.length - 1; i++) {
            if (v[i].getMicrochip() > v[i + 1].getMicrochip()) {
                return false;
            }
        }
        return true;
    }

    private static void trocar(Pet[] v, int a, int b) {
        Pet aux = v[a];
        v[a] = v[b];
        v[b] = aux;
    }

    // "Luna (60218841)" - usado na narração
    private static String ficha(Pet p) {
        return p.getNome() + " (" + p.getMicrochip() + ")";
    }

    public static void mostrarFila(Pet[] v) {
        System.out.print("  Fila:");
        for (int i = 0; i < v.length; i++) {
            System.out.print(" " + i + ":" + v[i].getNome() + "(" + v[i].getMicrochip() + ")");
        }
        System.out.println();
    }

    // ---------------- BUBBLE SORT ----------------
    public static ResultadoOrdenacao bubbleSort(Pet[] v, boolean narrar) {
        ResultadoOrdenacao r = new ResultadoOrdenacao("Bubble Sort");
        int n = v.length;

        for (int i = 0; i < n - 1; i++) {                 // cada passagem...
            boolean houveTroca = false;
            long compAntes = r.comparacoes, trocasAntes = r.trocas;
            if (narrar) System.out.println("\n== Bubble Sort - passagem " + (i + 1) + " ==");

            for (int j = 0; j < n - 1 - i; j++) {         // ...compara vizinhos
                r.comparacoes++;
                if (v[j].getMicrochip() > v[j + 1].getMicrochip()) {
                    if (narrar) System.out.println("  " + ficha(v[j]) + " > " + ficha(v[j + 1])
                            + "? SIM -> trocam de lugar na fila!");
                    trocar(v, j, j + 1);
                    r.trocas++;
                    houveTroca = true;
                } else if (narrar) {
                    System.out.println("  " + ficha(v[j]) + " > " + ficha(v[j + 1])
                            + "? NAO -> " + v[j].getNome() + " fica onde esta.");
                }
            }

            if (narrar) {
                System.out.println("  fim da passagem " + (i + 1) + " -> comparacoes: "
                        + (r.comparacoes - compAntes) + " | trocas: " + (r.trocas - trocasAntes));
                mostrarFila(v);
            }
            if (!houveTroca) {                            // passagem sem troca = já ordenado
                if (narrar) System.out.println("  Ninguem trocou de lugar: a fila da saida esta organizada!");
                break;
            }
        }
        return r;
    }

    // ---------------- INSERTION SORT ----------------
    public static ResultadoOrdenacao insertionSort(Pet[] v, boolean narrar) {
        ResultadoOrdenacao r = new ResultadoOrdenacao("Insertion Sort");

        for (int i = 1; i < v.length; i++) {
            Pet chave = v[i];                             // o bichinho que sai da fila
            int j = i - 1;
            if (narrar) System.out.println("\n== Insertion Sort - " + ficha(chave)
                    + " sai da fila (posicao " + i + ") ==");

            while (j >= 0) {
                r.comparacoes++;
                if (v[j].getMicrochip() > chave.getMicrochip()) {
                    if (narrar) System.out.println("  " + ficha(v[j]) + " > " + ficha(chave)
                            + "? SIM -> " + v[j].getNome() + " anda um lugar para tras (posicao "
                            + (j + 1) + ")");
                    v[j + 1] = v[j];                      // DESLOCAMENTO
                    r.trocas++;
                    j--;
                } else {
                    if (narrar) System.out.println("  " + ficha(v[j]) + " > " + ficha(chave)
                            + "? NAO -> achou o lugar.");
                    break;
                }
            }
            v[j + 1] = chave;
            if (narrar) {
                System.out.println("  " + chave.getNome() + " volta para a fila na posicao " + (j + 1)
                        + " | total ate agora -> comparacoes: " + r.comparacoes
                        + " | deslocamentos: " + r.trocas);
                mostrarFila(v);
            }
        }
        return r;
    }

    // ---------------- QUICK SORT ----------------
    public static ResultadoOrdenacao quickSort(Pet[] v, boolean narrar) {
        ResultadoOrdenacao r = new ResultadoOrdenacao("Quick Sort");
        quickSortRecursivo(v, 0, v.length - 1, r, narrar);
        return r;
    }

    private static void quickSortRecursivo(Pet[] v, int inicio, int fim,
                                           ResultadoOrdenacao r, boolean narrar) {
        if (inicio < fim) {                               // 0 ou 1 bichinho: nada a fazer
            int p = particionar(v, inicio, fim, r, narrar);
            quickSortRecursivo(v, inicio, p - 1, r, narrar);   // quem sai antes
            quickSortRecursivo(v, p + 1, fim, r, narrar);      // quem sai depois
        }
    }

    private static int particionar(Pet[] v, int inicio, int fim,
                                   ResultadoOrdenacao r, boolean narrar) {
        Pet pivo = v[fim];                                // pivô = último do trecho
        int i = inicio - 1;                               // fim da área de "quem sai antes"
        if (narrar) System.out.println("\n== Quick Sort - posicoes " + inicio + " a " + fim
                + " da fila | pivo: " + ficha(pivo) + " ==");

        for (int j = inicio; j < fim; j++) {
            r.comparacoes++;
            if (v[j].getMicrochip() < pivo.getMicrochip()) {
                i++;
                if (i != j) {                             // só conta troca de verdade
                    if (narrar) System.out.println("  " + ficha(v[j])
                            + " < pivo? SIM -> troca de lugar com " + ficha(v[i]));
                    trocar(v, i, j);
                    r.trocas++;
                } else if (narrar) {
                    System.out.println("  " + ficha(v[j])
                            + " < pivo? SIM -> ja esta do lado de quem sai antes");
                }
            } else if (narrar) {
                System.out.println("  " + ficha(v[j]) + " < pivo? NAO -> fica do lado de quem sai depois");
            }
        }
        if (i + 1 != fim) {                               // pivô vai para o lugar definitivo
            trocar(v, i + 1, fim);
            r.trocas++;
        }
        if (narrar) {
            System.out.println("  " + pivo.getNome() + " vai para a posicao " + (i + 1)
                    + " (lugar definitivo) | total -> comparacoes: " + r.comparacoes
                    + " | trocas: " + r.trocas);
            mostrarFila(v);
        }
        return i + 1;
    }
}
