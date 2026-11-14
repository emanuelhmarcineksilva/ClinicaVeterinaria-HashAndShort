package Pets;

import java.util.Random;

// Cria bichinhos aleatórios para a Grande Corrida (1.000 e 10.000)
public class GeradorPets {

    private static final String[] NOMES = {
        "Luna", "Thor", "Bob", "Mel", "Nina", "Zeus", "Amora", "Pipoca",
        "Lola", "Frida", "Simba", "Chico", "Fred", "Toddy", "Pandora", "Pacoca"
    };
    private static final String[] ESPECIES = {
        "cao", "gata", "gato", "cachorra", "coelho", "calopsita", "hamster", "papagaio"
    };
    private static final String[] MANIAS = {
        "rouba meias", "late para o aspirador", "mia para a geladeira",
        "dorme em cima do teclado", "come a racao do irmao", "caca o ponteiro laser",
        "tem medo de sacola plastica", "assobia quando alguem chega"
    };

    // Semente fixa: toda execução gera os mesmos pets (bom para repetir medições)
    private static final Random sorteio = new Random(2026);

    // Gera n pets JÁ em ordem crescente de microchip, sem repetir nenhum.
    // Truque: começa em 10.000.000 e soma um "passo" sorteado de 1 a 8.999.
    // Com 10.000 pets, o maior possível é 10.000.000 + 10.000 x 8.999 = 99.990.000 (8 dígitos).
    public static Pet[] gerarOrdenados(int n) {
        Pet[] pets = new Pet[n];
        int microchip = 10_000_000;
        for (int i = 0; i < n; i++) {
            microchip += 1 + sorteio.nextInt(8999);      // nextInt(8999) sorteia 0..8998
            String nome = NOMES[sorteio.nextInt(NOMES.length)];
            String especie = ESPECIES[sorteio.nextInt(ESPECIES.length)];
            String mania = MANIAS[sorteio.nextInt(MANIAS.length)];
            pets[i] = new Pet(microchip, nome, especie, mania);
        }
        return pets;
    }

    // ALEATÓRIO: embaralha uma cópia (algoritmo de Fisher-Yates)
    public static Pet[] embaralhado(Pet[] base) {
        Pet[] v = Ordenacao.copiar(base);
        for (int i = v.length - 1; i > 0; i--) {
            int j = sorteio.nextInt(i + 1);               // posição sorteada entre 0 e i
            Pet aux = v[i];
            v[i] = v[j];
            v[j] = aux;
        }
        return v;
    }

    // ORDEM INVERSA: lê o vetor crescente de trás para frente
    public static Pet[] invertido(Pet[] base) {
        Pet[] v = new Pet[base.length];
        for (int i = 0; i < base.length; i++) {
            v[i] = base[base.length - 1 - i];
        }
        return v;
    }
}
