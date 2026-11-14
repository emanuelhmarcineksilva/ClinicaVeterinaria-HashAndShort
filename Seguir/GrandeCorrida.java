import java.util.Random;

public class GrandeCorrida {
    private static final int[] QUANTIDADES = {50, 1000, 10000};

    private GrandeCorrida() {
    }

    public static void executar() {
        Random aleatorio = new Random();
        System.out.println("\n--- A GRANDE CORRIDA ---");
        System.out.println("Tempo em nanossegundos; cada algoritmo recebe uma copia independente.");

        for (int quantidade : QUANTIDADES) {
            Pet[] crescente = GeradorPets.gerarOrdenados(quantidade);
            Pet[] aleatorioPets = Ordenacao.copiar(crescente);
            GeradorPets.embaralhar(aleatorioPets, aleatorio);
            Pet[] inverso = GeradorPets.ordemInversa(crescente);

            executarCenario(quantidade, "Aleatorio", aleatorioPets);
            executarCenario(quantidade, "Ja ordenado", crescente);
            executarCenario(quantidade, "Ordem inversa", inverso);
        }
    }

    private static void executarCenario(int quantidade, String cenario, Pet[] origem) {
        System.out.println("\nn = " + quantidade + " — " + cenario);

        Pet[] bubble = Ordenacao.copiar(origem);
        ResultadoOrdenacao resultadoBubble = Ordenacao.bubbleSort(bubble, false);
        exibir("Bubble Sort", resultadoBubble, bubble);

        Pet[] insertion = Ordenacao.copiar(origem);
        ResultadoOrdenacao resultadoInsertion = Ordenacao.insertionSort(insertion, false);
        exibir("Insertion Sort", resultadoInsertion, insertion);

        Pet[] quick = Ordenacao.copiar(origem);
        ResultadoOrdenacao resultadoQuick = Ordenacao.quickSort(quick, false);
        exibir("Quick Sort", resultadoQuick, quick);
    }

    private static void exibir(String algoritmo, ResultadoOrdenacao resultado, Pet[] ordenados) {
        System.out.printf("%-14s | tempo: %10d ns | comparacoes: %10d | trocas/deslocamentos: %10d | ordenado: %s%n",
                algoritmo, resultado.getTempoNanosegundos(), resultado.getComparacoes(),
                resultado.getTrocasOuDeslocamentos(), Ordenacao.estaOrdenado(ordenados));
    }
}
