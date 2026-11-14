import java.util.Random;

public class GeradorPets {
    private static final String[] NOMES = {
        "Luna", "Thor", "Mel", "Bob", "Pipoca", "Amora", "Nina", "Paçoca",
        "Chico", "Toby", "Lola", "Fred"
    };
    private static final String[] ESPECIES = {
        "cão", "gata", "gato", "coelha", "calopsita", "hamster"
    };
    private static final String[] MANIAS = {
        "dorme no sofa", "pede comida na cozinha", "corre atras da propria cauda",
        "esconde os brinquedos", "late para a campainha", "gosta de dormir no colo"
    };

    private GeradorPets() {
    }

    public static Pet[] gerarOrdenados(int quantidade) {
        Pet[] pets = new Pet[quantidade];
        for (int i = 0; i < quantidade; i++) {
            long microchip = 10000000L + i;
            String nome = NOMES[i % NOMES.length];
            String especie = ESPECIES[i % ESPECIES.length];
            String mania = MANIAS[i % MANIAS.length];
            pets[i] = new Pet(microchip, nome, especie, mania);
        }
        return pets;
    }

    public static void embaralhar(Pet[] pets, Random aleatorio) {
        for (int i = pets.length - 1; i > 0; i--) {
            int j = aleatorio.nextInt(i + 1);
            Pet auxiliar = pets[i];
            pets[i] = pets[j];
            pets[j] = auxiliar;
        }
    }

    public static Pet[] ordemInversa(Pet[] crescentes) {
        Pet[] inversos = new Pet[crescentes.length];
        for (int i = 0; i < crescentes.length; i++) {
            inversos[i] = crescentes[crescentes.length - 1 - i];
        }
        return inversos;
    }
}
