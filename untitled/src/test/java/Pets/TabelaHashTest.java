package Pets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// TDD - ciclo 2: os canis da creche (tabela hash com encadeamento)
class TabelaHashTest {

    // Os 20 bichinhos do Apêndice do enunciado
    static Pet[] turminha() {
        return new Pet[] {
            new Pet(60218841, "Luna", "gata", "dorme em cima do teclado"),
            new Pet(31057720, "Thor", "cão", "late para o aspirador"),
            new Pet(48812903, "Pipoca", "cão", "pula quando ouve a palavra passeio"),
            new Pet(22091187, "Mel", "gata", "derruba copos da mesa, um por um"),
            new Pet(70334512, "Zeus", "cão", "tem medo do gato do vizinho"),
            new Pet(51200468, "Amora", "gata", "mia para a geladeira"),
            new Pet(13870255, "Luna", "cachorra", "rouba meias"),
            new Pet(39021746, "Bob", "cão", "come a ração do irmão"),
            new Pet(84460013, "Bob", "gato", "dorme 20 horas por dia"),
            new Pet(27719384, "Lola", "cachorra", "adora tomar banho"),
            new Pet(65502291, "Paçoca", "gato", "caça o ponteiro laser"),
            new Pet(44183067, "Frida", "cachorra", "usa laço e se acha"),
            new Pet(90027758, "Simba", "gato", "acha que é leão"),
            new Pet(18345602, "Nina", "coelha", "rói o fio do carregador"),
            new Pet(76690431, "Pandora", "gata", "abre portas sozinha"),
            new Pet(33208179, "Chico", "papagaio", "imita o toque do celular"),
            new Pet(57741820, "Thor", "gato", "tem nome de cão, mas é gato"),
            new Pet(81196354, "Fred", "hamster", "corre a noite inteira na rodinha"),
            new Pet(29573006, "Luna", "calopsita", "assobia quando alguém chega"),
            new Pet(62015547, "Toddy", "cão", "só come se for na mão")
        };
    }

    static TabelaHash crecheCom(Pet[] pets, int m, int funcao) {
        TabelaHash t = new TabelaHash(m, funcao);
        for (Pet p : pets) {
            t.inserir(p);
        }
        return t;
    }

    // ---------- funções hash ----------
    @Test
    void somaDosCodigosDeLunaCaiNoCanil5ComM13() {
        // L=76 U=85 N=78 A=65 -> 304; 304 % 13 = 5
        assertEquals(5, TabelaHash.hashSoma("LUNA", 13));
    }

    @Test
    void lunaEThorColidemComM13() {
        assertEquals(TabelaHash.hashSoma("LUNA", 13), TabelaHash.hashSoma("THOR", 13));
    }

    @Test
    void fridaEChicoTemAMesmaSomaEColidemEmQualquerM() {
        for (int m : new int[] {13, 17, 31, 97}) {
            assertEquals(TabelaHash.hashSoma("FRIDA", m), TabelaHash.hashSoma("CHICO", m));
        }
    }

    @Test
    void ponderadaLevaEmContaAPosicaoDasLetras() {
        // 76x1 + 85x2 + 78x3 + 65x4 = 740; 740 % 13 = 12
        assertEquals(12, TabelaHash.hashPonderada("LUNA", 13));
        assertNotEquals(TabelaHash.hashPonderada("FRIDA", 13), TabelaHash.hashPonderada("CHICO", 13));
        assertNotEquals(TabelaHash.hashPonderada("AMOR", 13), TabelaHash.hashPonderada("ROMA", 13));
    }

    @Test
    void indiceSempreEntreZeroEMMenos1() {
        for (Pet p : turminha()) {
            for (int m : new int[] {13, 17, 31}) {
                int a = TabelaHash.hashSoma(p.getChaveNome(), m);
                int b = TabelaHash.hashPonderada(p.getChaveNome(), m);
                assertTrue(a >= 0 && a < m);
                assertTrue(b >= 0 && b < m);
            }
        }
    }

    // ---------- inserir e buscar ----------
    @Test
    void buscarPorNomeDevolveTodasAsLunas() {
        TabelaHash t = crecheCom(turminha(), 13, 1);
        assertEquals(3, t.buscarPorNome("Luna").length);
        assertEquals(3, t.buscarPorNome("  luna ").length);   // o nome digitado é normalizado
        assertEquals(1, t.buscarPorNome("Paçoca").length);
        assertEquals(1, t.buscarPorNome("pacoca").length);
    }

    @Test
    void buscarNomeQueNaoExisteDevolveVetorVazio() {
        TabelaHash t = crecheCom(turminha(), 13, 1);
        assertEquals(0, t.buscarPorNome("Rex").length);   // REX cai no canil 5, mas não está lá
    }

    @Test
    void naoAceitaMicrochipRepetido() {
        TabelaHash t = crecheCom(turminha(), 13, 1);
        assertFalse(t.inserir(new Pet(60218841, "Outra", "gata", "copia")));
        assertEquals(20, t.getQuantidade());
    }

    @Test
    void funcionaIgualComAFuncaoDoGrupo() {
        TabelaHash t = crecheCom(turminha(), 17, 2);
        assertEquals(20, t.getQuantidade());
        assertEquals(3, t.buscarPorNome("Luna").length);
        assertEquals(2, t.buscarPorNome("Bob").length);
    }

    // ---------- remover ----------
    @Test
    void removePrimeiroMeioEInexistente() {
        TabelaHash t = crecheCom(turminha(), 13, 1);
        // canil 5 (mais recente primeiro): Luna 29573006 -> Thor -> Luna 13870255 -> Thor -> Luna 60218841
        assertEquals("Luna", t.removerPorMicrochip(29573006).getNome());   // primeira do canil
        assertEquals("Luna", t.removerPorMicrochip(13870255).getNome());   // meio da lista
        assertEquals("Luna", t.removerPorMicrochip(60218841).getNome());   // última da lista
        assertNull(t.removerPorMicrochip(11111111));                      // não existe
        assertEquals(17, t.getQuantidade());
        assertEquals(0, t.buscarPorNome("Luna").length);
        assertEquals(2, t.buscarPorNome("Thor").length);                  // os vizinhos continuam lá
    }

    // ---------- estatísticas ----------
    @Test
    void estatisticasDaTurminhaComM13() {
        TabelaHash t = crecheCom(turminha(), 13, 1);
        assertEquals(20.0 / 13, t.fatorDeCarga(), 1e-9);
        assertEquals(3, t.baldesVazios());                 // canis 6, 9 e 12
        assertEquals(5, t.maiorLista());                   // canil 5: 3 Lunas + 2 Thors
        assertEquals(10, t.colisoes());                    // soma de (tamanho - 1)
        assertEquals(6, t.colisoesEntreNomesDiferentes()); // só nomes diferentes contam
    }

    // ---------- TDD - ciclo 7 (auditoria): trocar M e a função sem perder ninguém ----------
    @Test
    void reorganizarRecalculaOCanilDeTodos() {
        TabelaHash t = crecheCom(turminha(), 13, 1);
        t.reorganizar(17, 2);
        TabelaHash nova = crecheCom(turminha(), 17, 2);    // como se a creche já nascesse assim
        assertEquals(17, t.getM());
        assertEquals(20, t.getQuantidade());
        assertEquals(3, t.buscarPorNome("Luna").length);
        assertEquals(nova.baldesVazios(), t.baldesVazios());
        assertEquals(nova.maiorLista(), t.maiorLista());
        assertEquals(nova.colisoes(), t.colisoes());
        assertEquals(TabelaHash.hashPonderada("LUNA", 17), t.canilDoNome("Luna"));
        assertTrue(t.desenharCanis().startsWith("Creche: 17 canis, 20 bichinhos, funcao hash 2"));
    }

    @Test
    void crecheVazia() {
        TabelaHash t = new TabelaHash(17, 1);
        assertEquals(0.0, t.fatorDeCarga());
        assertEquals(17, t.baldesVazios());
        assertEquals(0, t.maiorLista());
        assertEquals(0, t.colisoes());
        assertEquals(0, t.todosOsPets().length);
    }

    @Test
    void todosOsPetsDevolveCadaBichinhoUmaVez() {
        TabelaHash t = crecheCom(turminha(), 13, 1);
        Pet[] todos = t.todosOsPets();
        assertEquals(20, todos.length);
        for (Pet p : turminha()) {
            int vezes = 0;
            for (Pet q : todos) {
                if (q.getMicrochip() == p.getMicrochip()) vezes++;
            }
            assertEquals(1, vezes);
        }
    }
}
