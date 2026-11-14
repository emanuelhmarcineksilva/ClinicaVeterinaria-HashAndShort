package Pets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// TDD - ciclo 3: a fila da saída (Bubble, Insertion e Quick Sort por microchip)
class OrdenacaoTest {

    static final int BUBBLE = 0, INSERTION = 1, QUICK = 2;

    // Cria pets só com o microchip que interessa para o teste
    static Pet[] fila(int... microchips) {
        Pet[] v = new Pet[microchips.length];
        for (int i = 0; i < microchips.length; i++) {
            v[i] = new Pet(microchips[i], "P" + microchips[i], "cao", "teste");
        }
        return v;
    }

    static Pet[] crescente(int n) {
        int[] m = new int[n];
        for (int i = 0; i < n; i++) m[i] = 10_000_000 + i;
        return fila(m);
    }

    static Pet[] decrescente(int n) {
        int[] m = new int[n];
        for (int i = 0; i < n; i++) m[i] = 10_000_000 + n - i;
        return fila(m);
    }

    static int[] microchips(Pet[] v) {
        int[] m = new int[v.length];
        for (int i = 0; i < v.length; i++) m[i] = v[i].getMicrochip();
        return m;
    }

    // ---------- rastreios dos slides ----------
    @Test
    void bubbleNoVetorDosSlides() {
        Pet[] v = fila(7, 2, 12, 8, 3);
        ResultadoOrdenacao r = Ordenacao.bubbleSort(v, false);
        assertArrayEquals(new int[] {2, 3, 7, 8, 12}, microchips(v));
        assertEquals(10, r.comparacoes);   // passagens: 4 + 3 + 2 + 1
        assertEquals(5, r.trocas);         // as 5 inversões do vetor
    }

    @Test
    void insertionNoVetorDosSlides() {
        Pet[] v = fila(7, 2, 12, 8, 3);
        ResultadoOrdenacao r = Ordenacao.insertionSort(v, false);
        assertArrayEquals(new int[] {2, 3, 7, 8, 12}, microchips(v));
        assertEquals(8, r.comparacoes);
        assertEquals(5, r.trocas);         // deslocamentos
    }

    @Test
    void quickNoVetorDosSlides() {
        Pet[] v = fila(7, 1, 3, 5, 2, 6, 4);
        ResultadoOrdenacao r = Ordenacao.quickSort(v, false);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5, 6, 7}, microchips(v));
        assertEquals(11, r.comparacoes);   // 6 + 2 + 2 + 1
        assertEquals(6, r.trocas);         // 4 + 1 + 1 (trocas de um elemento com ele mesmo não contam)
    }

    // ---------- melhor e pior caso (fórmulas exatas, n = 50) ----------
    @Test
    void jaOrdenado() {
        int n = 50;
        ResultadoOrdenacao b = Ordenacao.bubbleSort(crescente(n), false);
        ResultadoOrdenacao i = Ordenacao.insertionSort(crescente(n), false);
        ResultadoOrdenacao q = Ordenacao.quickSort(crescente(n), false);
        assertEquals(n - 1, b.comparacoes);              // uma passagem e para
        assertEquals(0, b.trocas);
        assertEquals(n - 1, i.comparacoes);              // cada chave compara 1 vez
        assertEquals(0, i.trocas);
        assertEquals(n * (n - 1) / 2, q.comparacoes);    // pior caso do Quick!
        assertEquals(0, q.trocas);
    }

    @Test
    void ordemInversa() {
        int n = 50;
        ResultadoOrdenacao b = Ordenacao.bubbleSort(decrescente(n), false);
        ResultadoOrdenacao i = Ordenacao.insertionSort(decrescente(n), false);
        ResultadoOrdenacao q = Ordenacao.quickSort(decrescente(n), false);
        assertEquals(n * (n - 1) / 2, b.comparacoes);
        assertEquals(n * (n - 1) / 2, b.trocas);
        assertEquals(n * (n - 1) / 2, i.comparacoes);
        assertEquals(n * (n - 1) / 2, i.trocas);
        assertEquals(n * (n - 1) / 2, q.comparacoes);
        assertEquals(n / 2, q.trocas);
    }

    @Test
    void trocasDoBubbleIgualADeslocamentosDoInsertion() {
        // os dois contam as inversões (pares fora de ordem)
        Pet[] base = fila(50, 3, 77, 12, 9, 64, 1, 38, 25, 90, 41, 7);
        ResultadoOrdenacao b = Ordenacao.bubbleSort(Ordenacao.copiar(base), false);
        ResultadoOrdenacao i = Ordenacao.insertionSort(Ordenacao.copiar(base), false);
        assertEquals(b.trocas, i.trocas);
    }

    // ---------- os três organizam qualquer fila sem perder ninguém ----------
    @Test
    void osTresOrdenamOsBichinhosDaTurminha() {
        Pet[] original = TabelaHashTest.turminha();
        for (int a = BUBBLE; a <= QUICK; a++) {
            Pet[] copia = Ordenacao.copiar(original);
            Ordenacao.ordenar(a, copia, false);
            assertTrue(Ordenacao.estaOrdenado(copia), Ordenacao.NOMES[a]);
            assertEquals(13870255, copia[0].getMicrochip());          // Luna cachorra
            assertEquals(90027758, copia[19].getMicrochip());         // Simba
        }
    }

    @Test
    void copiarCriaUmVetorNovo() {
        Pet[] original = fila(3, 1, 2);
        Pet[] copia = Ordenacao.copiar(original);
        Ordenacao.bubbleSort(copia, false);
        assertArrayEquals(new int[] {3, 1, 2}, microchips(original));  // o original não mudou
        assertArrayEquals(new int[] {1, 2, 3}, microchips(copia));
    }

    @Test
    void estaOrdenadoConfereVizinhos() {
        assertTrue(Ordenacao.estaOrdenado(fila(1, 2, 3)));
        assertFalse(Ordenacao.estaOrdenado(fila(1, 3, 2)));
        assertTrue(Ordenacao.estaOrdenado(fila()));
    }
}
