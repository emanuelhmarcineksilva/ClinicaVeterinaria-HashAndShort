package Pets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

// TDD - ciclo 5: ler o arquivo de bichinhos e conferir os dados do enunciado
class ArquivoPetsTest {

    @Test
    void leUmPetPorLinhaEIgnoraComentarioVazioEInvalido(@TempDir Path pasta) throws IOException {
        Path arquivo = pasta.resolve("pets.txt");
        Files.writeString(arquivo,
                "\uFEFF# microchip;nome;especie;mania\n"     // BOM do Bloco de Notas + comentário
                + "60218841;Luna;gata;dorme em cima do teclado\n"
                + "\n"                                        // linha vazia
                + "abc;Rex;cão;microchip invalido\n"          // não é número
                + "31057720;Thor;cão\n"                       // falta a mania
                + "65502291;Paçoca;gato;caça o ponteiro; e o laser\n", // ';' dentro da mania
                StandardCharsets.UTF_8);

        Pet[] pets = ArquivoPets.lerPets(arquivo.toString());

        assertEquals(2, pets.length);
        assertEquals(60218841, pets[0].getMicrochip());
        assertEquals("Paçoca", pets[1].getNome());
        assertEquals("caça o ponteiro; e o laser", pets[1].getMania());
    }

    @Test
    void arquivoInexistenteDevolveVetorVazio() {
        assertEquals(0, ArquivoPets.lerPets("nao/existe/pets.txt").length);
    }

    // O arquivo REAL do projeto precisa cumprir o enunciado
    @Test
    void arquivoDoProjetoTemPeloMenos50PetsComMicrochipsUnicosDe8Digitos() {
        Pet[] pets = ArquivoPets.lerPets("dados/pets.txt");
        assertTrue(pets.length >= 50, "pets lidos: " + pets.length);
        for (int i = 0; i < pets.length; i++) {
            int chip = pets[i].getMicrochip();
            assertTrue(chip >= 10_000_000 && chip <= 99_999_999, "8 digitos: " + chip);
            for (int j = i + 1; j < pets.length; j++) {
                assertNotEquals(chip, pets[j].getMicrochip(), "microchip repetido");
            }
            assertFalse(pets[i].getMania().isEmpty(), "toda ficha tem mania");
        }
    }

    @Test
    void arquivoDoProjetoRepeteNomesDeProposito() {
        TabelaHash t = new TabelaHash(13, 1);
        for (Pet p : ArquivoPets.lerPets("dados/pets.txt")) {
            t.inserir(p);
        }
        assertTrue(t.buscarPorNome("Luna").length >= 5);
        assertTrue(t.buscarPorNome("Thor").length >= 3);
        assertTrue(t.buscarPorNome("Bob").length >= 3);
    }
}
