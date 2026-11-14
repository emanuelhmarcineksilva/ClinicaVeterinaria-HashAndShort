package Pets;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

// TDD - ciclo 6: o Cenário B (Creche dos Bichos)
// Canis = baldes | bichinhos dividindo o canil = lista ligada | lotação = fator de carga | fila da saída = microchip
class CrecheTest {

    // Roda um trecho do programa e devolve tudo o que ele escreveu na tela
    static String capturarTela(Runnable trecho) {
        PrintStream telaOriginal = System.out;
        ByteArrayOutputStream tela = new ByteArrayOutputStream();
        System.setOut(new PrintStream(tela, true, StandardCharsets.UTF_8));
        try {
            trecho.run();
        } finally {
            System.setOut(telaOriginal);
        }
        return tela.toString(StandardCharsets.UTF_8);
    }

    // Simula alguém digitando no teclado e roda o programa inteiro
    static String rodarPrograma(String digitado) {
        InputStream tecladoOriginal = System.in;
        System.setIn(new ByteArrayInputStream(digitado.getBytes(StandardCharsets.UTF_8)));
        try {
            return capturarTela(() -> Main.main(new String[0]));
        } finally {
            System.setIn(tecladoOriginal);
        }
    }

    // Lotação de uma creche com os "quantos" primeiros bichinhos da turminha
    static String lotacao(int quantos, int canis) {
        Pet[] turma = TabelaHashTest.turminha();
        TabelaHash creche = new TabelaHash(canis, 1);
        for (int i = 0; i < quantos; i++) {
            creche.inserir(turma[i]);
        }
        return creche.situacaoDaLotacao();
    }

    // ---------- os canis ----------
    @Test
    void cadaBaldeEhUmCanil() {
        TabelaHash creche = TabelaHashTest.crecheCom(TabelaHashTest.turminha(), 13, 1);
        String canis = creche.desenharCanis();
        assertTrue(canis.startsWith("Creche: 13 canis, 20 bichinhos"), canis);
        assertTrue(canis.contains("Canil 5: Luna (29573006) -> Thor (57741820) -> Luna (13870255)"), canis);
        assertTrue(canis.contains("Canil 6: (vazio)"), canis);
    }

    // ---------- a lotação é o fator de carga ----------
    @Test
    void lotacaoDaCrecheSegueOFatorDeCarga() {
        assertEquals("tranquila", lotacao(0, 17));      // creche vazia
        assertEquals("tranquila", lotacao(12, 17));     // 12/17 = 0,71 (até 0,75: o ideal dos slides)
        assertEquals("no limite", lotacao(13, 17));     // 13/17 = 0,76
        assertEquals("no limite", lotacao(17, 17));     // 17/17 = 1,00 (um bichinho por canil)
        assertEquals("superlotada", lotacao(18, 17));   // 18/17 = 1,06 (mais bichinho que canil)
        assertEquals("superlotada", lotacao(20, 13));   // 20/13 = 1,54
    }

    // ---------- a fila da saída, narrada ----------
    @Test
    void bubbleNarraAFilaDaSaida() {
        String tela = capturarTela(() -> Ordenacao.bubbleSort(OrdenacaoTest.fila(70, 20, 50), true));
        assertTrue(tela.contains("trocam de lugar na fila"), tela);
        assertTrue(tela.contains("fica onde esta"), tela);
        assertTrue(tela.contains("Fila:"), tela);
        assertTrue(tela.contains("a fila da saida esta organizada"), tela);
    }

    @Test
    void insertionNarraAFilaDaSaida() {
        String tela = capturarTela(() -> Ordenacao.insertionSort(OrdenacaoTest.fila(30, 10, 20), true));
        assertTrue(tela.contains("sai da fila"), tela);
        assertTrue(tela.contains("anda um lugar para tras"), tela);
        assertTrue(tela.contains("volta para a fila na posicao"), tela);
    }

    @Test
    void quickNarraAFilaDaSaida() {
        String tela = capturarTela(() -> Ordenacao.quickSort(OrdenacaoTest.fila(30, 10, 40, 20), true));
        assertTrue(tela.contains("troca de lugar com"), tela);
        assertTrue(tela.contains("lado de quem sai depois"), tela);
        assertTrue(tela.contains("(lugar definitivo)"), tela);
    }

    // ---------- o menu e um dia inteiro na creche ----------
    @Test
    void menuContaAHistoriaDaCreche() {
        String menu = capturarTela(Main::mostrarMenu);
        assertTrue(menu.contains("CRECHE DOS BICHOS"), menu);
        assertTrue(menu.contains("Mostrar os canis"), menu);
        assertTrue(menu.contains("Lotacao da creche"), menu);
        assertTrue(menu.contains("Organizar a fila da saida"), menu);
        assertTrue(menu.contains("9 - Mudar os canis (M e funcao hash)"), menu);
        assertTrue(menu.contains("Fechar a creche"), menu);
    }

    // ---------- TDD - ciclo 7 (auditoria): as duas ressalvas ----------
    @Test
    void mudarOsCanisParaM17ComAFuncaoDoGrupo() {
        String tela = rodarPrograma(
                "9\n0\n"           // M = 0: inválido
                + "9\n17\n5\n"     // função 5: inválida
                + "9\n17\n2\n"     // M = 17 com a função ponderada
                + "4\n"            // mostrar os canis
                + "0\n");
        assertTrue(tela.contains("O numero de canis (M) precisa ser pelo menos 1."), tela);
        assertTrue(tela.contains("Funcao invalida (use 1 ou 2)."), tela);
        assertTrue(tela.contains("Creche reorganizada: 17 canis, funcao hash 2"), tela);
        assertTrue(tela.contains("Creche: 17 canis, 50 bichinhos, funcao hash 2"), tela);
        assertTrue(tela.contains("Canil 16: "), tela);
    }

    @Test
    void grandeCorridaUsaOs50BichinhosDoArquivo() {
        String tela = rodarPrograma("8\n0\n");
        assertTrue(tela.contains("corrida com 50 bichinhos do grupo concluida"), tela);
        assertTrue(tela.contains("corrida com 1000 bichinhos gerados concluida"), tela);
        assertTrue(tela.contains("corrida com 10000 bichinhos gerados concluida"), tela);
        assertTrue(tela.contains("COMPARACOES"), tela);
    }

    @Test
    void umDiaNaCrecheDoComecoAoFim() {
        String tela = rodarPrograma(
                "4\n"              // mostrar os canis
                + "2\nluna\n"      // procurar as Lunas
                + "3\n13870255\n"  // a Luna cachorra foi para casa
                + "5\n"            // lotação da creche
                + "abc\n"          // digitou errado
                + "0\n");          // fechar a creche
        assertTrue(tela.contains("50 bichinhos chegaram na creche"), tela);
        assertTrue(tela.contains("Canil 5: "), tela);
        assertTrue(tela.contains("6 bichinho(s) encontrado(s)"), tela);
        assertTrue(tela.contains("Tchau, Luna! Saiu do canil 5."), tela);
        assertTrue(tela.contains("superlotada"), tela);
        assertTrue(tela.contains("Digite apenas numeros."), tela);
        assertTrue(tela.contains("Creche fechada"), tela);
    }
}
