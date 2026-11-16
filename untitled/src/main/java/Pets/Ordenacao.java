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

        // Começamos em 1, pois a posição 0 sozinha já está "ordenada".
        for (int i = 1; i < v.length; i++) {
            Pet chave = v[i];                             // o bichinho que sai da fila
            int j = i - 1;                                // último lugar da parte já organizada
            if (narrar) System.out.println("\n== Insertion Sort - " + ficha(chave)
                    + " sai da fila (posicao " + i + ") ==");

            // Enquanto houver bichinho à esquerda com microchip MAIOR que a chave,
            // ele anda uma casa para trás (como nos slides: j >= 0 e v[j] > chave)
            while (j >= 0 && v[j].getMicrochip() > chave.getMicrochip()) {
                r.comparacoes++;
                if (narrar) System.out.println("  " + ficha(v[j]) + " > " + ficha(chave)
                        + "? SIM -> " + v[j].getNome() + " anda um lugar para tras (posicao "
                        + (j + 1) + ")");
                v[j + 1] = v[j];                          // DESLOCAMENTO
                r.trocas++;
                j--;
            }
            if (j >= 0) {                                 // a comparação que fez o while parar
                r.comparacoes++;
                if (narrar) System.out.println("  " + ficha(v[j]) + " > " + ficha(chave)
                        + "? NAO -> achou o lugar.");
            }
            // Quando o laço para, j+1 é o lugar certo da chave.
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
        quickSort(v, 0, v.length - 1, r, narrar);
        return r;
    }

    private static void quickSort(Pet[] v, int inicio, int fim, ResultadoOrdenacao r, boolean narrar) {
        // Caso base: com 0 ou 1 bichinho não há nada a organizar.
        if (inicio < fim) {
            int posPivot = particionar(v, inicio, fim, r, narrar);
            quickSort(v, inicio, posPivot - 1, r, narrar);    // ordena quem sai antes (menores)
            quickSort(v, posPivot + 1, fim, r, narrar);       // ordena quem sai depois (maiores)
        }
    }

    // Organiza o trecho v[inicio..fim] em torno do pivot e devolve a posição final do pivot.
    private static int particionar(Pet[] v, int inicio, int fim, ResultadoOrdenacao r, boolean narrar) {
        Pet pivot = v[fim];                               // pivot = último elemento
        int i = inicio - 1;                               // fim da "partição dos menores"
        if (narrar) System.out.println("\n== Quick Sort - posicoes " + inicio + " a " + fim
                + " da fila | pivot: " + ficha(pivot) + " ==");

        for (int j = inicio; j < fim; j++) {
            r.comparacoes++;
            if (v[j].getMicrochip() < pivot.getMicrochip()) {
                i++;                                      // abre espaço na partição dos menores
                if (narrar) System.out.println("  " + ficha(v[j]) + " < pivot? SIM -> "
                        + trocaNarrada(v, i, j));
                trocar(v, i, j);                          // e leva v[j] para lá
                r.trocas++;
            } else if (narrar) {
                System.out.println("  " + ficha(v[j]) + " < pivot? NAO -> fica do lado de quem sai depois");
            }
        }
        // A próxima posição disponível (i+1) é o lugar certo do pivot.
        if (narrar) System.out.println("  pivot -> " + trocaNarrada(v, i + 1, fim));
        trocar(v, i + 1, fim);
        r.trocas++;
        if (narrar) {
            System.out.println("  " + pivot.getNome() + " vai para a posicao " + (i + 1)
                    + " (lugar definitivo) | total -> comparacoes: " + r.comparacoes
                    + " | trocas: " + r.trocas);
            mostrarFila(v);
        }
        return i + 1;
    }

    // Como nos slides, o algoritmo troca SEMPRE, até um bichinho com ele mesmo (😄)
    private static String trocaNarrada(Pet[] v, int a, int b) {
        if (a == b) {
            return v[b].getNome() + " troca de lugar com ele mesmo";
        }
        return v[b].getNome() + " troca de lugar com " + ficha(v[a]);
    }
}
