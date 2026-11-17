package Pets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// TDD - ciclo 1: a ficha do bichinho
class PetTest {

    @Test
    void guardaOsQuatroDadosDaFicha() {
        Pet luna = new Pet(60218841, "Luna", "gata", "dorme em cima do teclado");
        assertEquals(60218841, luna.getMicrochip());
        assertEquals("Luna", luna.getNome());
        assertEquals("gata", luna.getEspecie());
        assertEquals("dorme em cima do teclado", luna.getMania());
    }

    @Test
    void chaveFicaEmMaiusculasESemAcento() {
        Pet pacoca = new Pet(65502291, "Paçoca", "gato", "caça o ponteiro laser");
        assertEquals("PACOCA", pacoca.getChaveNome());
        assertEquals("Paçoca", pacoca.getNome());          // o nome exibido não muda
    }

    @Test
    void normalizarServeParaONomeDigitadoNaBusca() {
        assertEquals("LUNA", Pet.normalizar("luna"));
        assertEquals("LUNA", Pet.normalizar("  Luna "));     // tira espaços das pontas
        assertEquals("JOAO", Pet.normalizar("João"));
    }

    @Test
    void toStringMostraAFichaLegivel() {
        Pet thor = new Pet(31057720, "Thor", "cão", "late para o aspirador");
        assertEquals("Thor (cão) - microchip 31057720 - mania: late para o aspirador", thor.toString());
    }
}
