package Pets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// TDD - ciclo 4: gerador de pets aleatórios e a Grande Corrida
class GrandeCorridaTest {

    // ---------- gerador ----------
    @Test
    void geraMicrochipsUnicosDe8DigitosEmOrdemCrescente() {
        Pet[] pets = GeradorPets.gerarOrdenados(10_000);
        assertEquals(10_000, pets.length);
        for (int i = 0; i < pets.length; i++) {
            int chip = pets[i].getMicrochip();
            assertTrue(chip >= 10_000_000 && chip <= 99_999_999, "8 digitos: " + chip);
            if (i > 0) {
                assertTrue(chip > pets[i - 1].getMicrochip(), "crescente e sem repetir");
            }
        }
    }

    @Test
    void geradorRepeteNomesDePropositoParaProvocarColisoes() {
        Pet[] pets = GeradorPets.gerarOrdenados(1000);
        int lunas = 0;
        for (Pet p : pets) {
            if (p.getChaveNome().equals("LUNA")) lunas++;
        }
        assertTrue(lunas > 1);
    }

    @Test
    void embaralhadoTemOsMesmosPetsForaDeOrdem() {
        Pet[] base = GeradorPets.gerarOrdenados(1000);
        Pet[] mistura = GeradorPets.embaralhado(base);
        assertEquals(base.length, mistura.length);
        long somaBase = 0, somaMistura = 0;
        for (int i = 0; i < base.length; i++) {
            somaBase += base[i].getMicrochip();
            somaMistura += mistura[i].getMicrochip();
        }
        assertEquals(somaBase, somaMistura);                  // ninguém sumiu nem duplicou
        assertFalse(Ordenacao.estaOrdenado(mistura));
        assertTrue(Ordenacao.estaOrdenado(base));             // a base não foi alterada
    }

    @Test
    void invertidoEhOCrescenteDeTrasParaFrente() {
        Pet[] base = GeradorPets.gerarOrdenados(50);
        Pet[] inv = GeradorPets.invertido(base);
        assertSame(base[49], inv[0]);
        assertSame(base[0], inv[49]);
        for (int i = 1; i < inv.length; i++) {
            assertTrue(inv[i].getMicrochip() < inv[i - 1].getMicrochip());
        }
    }

    // ---------- corrida ----------
    @Test
    void corridaMede3Algoritmosx3SituacoesComContagensCertas() {
        int n = 50;
        ResultadoOrdenacao[][][] res = GrandeCorrida.correr(new int[] {n}, 1);
        int todos = n * (n - 1) / 2;
        // [algoritmo][situacao][tamanho]  situacao: 0 aleatório, 1 já ordenado, 2 inversa
        assertEquals(n - 1, res[0][1][0].comparacoes);   // Bubble, já ordenado
        assertEquals(todos, res[0][2][0].trocas);        // Bubble, inversa
        assertEquals(0, res[1][1][0].trocas);            // Insertion, já ordenado
        assertEquals(todos, res[1][2][0].comparacoes);   // Insertion, inversa
        assertEquals(todos, res[2][1][0].comparacoes);   // Quick, já ordenado (pior caso)
        assertEquals(todos, res[2][2][0].comparacoes);   // Quick, inversa (pior caso)
        assertEquals(res[0][0][0].trocas, res[1][0][0].trocas);   // inversões no aleatório
        for (int a = 0; a < 3; a++) {
            for (int s = 0; s < 3; s++) {
                assertTrue(res[a][s][0].tempoNanos >= 0);
            }
        }
    }

    // ---------- TDD - ciclo 7 (auditoria): n = 50 usa os bichinhos do grupo ----------
    @Test
    void entradasUsamOsBichinhosDoGrupoQuandoCabem() {
        Pet[] grupo = TabelaHashTest.turminha();                   // 20 bichinhos
        Pet[][] e = GrandeCorrida.montarEntradas(grupo, 20);
        Pet[] aleatorio = e[0], crescente = e[1], inverso = e[2];
        assertEquals(20, crescente.length);
        assertTrue(Ordenacao.estaOrdenado(crescente));
        assertEquals(13870255, crescente[0].getMicrochip());      // Luna cachorra: menor microchip
        assertEquals(90027758, crescente[19].getMicrochip());     // Simba: maior microchip
        assertSame(crescente[0], inverso[19]);
        assertFalse(Ordenacao.estaOrdenado(aleatorio));
        long soma = 0, somaAleatorio = 0;
        for (int i = 0; i < 20; i++) {
            soma += crescente[i].getMicrochip();
            somaAleatorio += aleatorio[i].getMicrochip();
        }
        assertEquals(soma, somaAleatorio);                         // os mesmos bichinhos, só embaralhados
        assertEquals(60218841, grupo[0].getMicrochip());           // o vetor do grupo não foi mexido
    }

    @Test
    void tamanhosMaioresQueOGrupoSaoGerados() {
        Pet[][] e = GrandeCorrida.montarEntradas(TabelaHashTest.turminha(), 1000);
        assertEquals(1000, e[1].length);
        assertTrue(Ordenacao.estaOrdenado(e[1]));
    }

    @Test
    void corridaComOGrupoTemAsContagensCertas() {
        ResultadoOrdenacao[][][] res = GrandeCorrida.correr(TabelaHashTest.turminha(), new int[] {20}, 1);
        assertEquals(19, res[0][1][0].comparacoes);     // Bubble, já ordenado: n - 1
        assertEquals(190, res[2][1][0].comparacoes);    // Quick, já ordenado: n(n-1)/2
        assertEquals(190, res[1][2][0].trocas);         // Insertion, inversa: n(n-1)/2 deslocamentos
    }
}
