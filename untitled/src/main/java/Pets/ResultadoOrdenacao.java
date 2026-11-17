package Pets;

// Guarda os números de UMA execução de um algoritmo de ordenação
public class ResultadoOrdenacao {
    String algoritmo;
    long comparacoes = 0;   // quantas vezes comparamos dois microchips
    long trocas = 0;        // trocas de lugar na fila (no Insertion Sort: deslocamentos)
    long tempoNanos = 0;    // medido com System.nanoTime()

    ResultadoOrdenacao(String algoritmo) {
        this.algoritmo = algoritmo;
    }
}
