import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ArquivoPets {
    public static Pet[] ler(String caminho) throws IOException {
        List<String> linhas = Files.readAllLines(Paths.get(caminho), StandardCharsets.UTF_8);
        List<Pet> pets = new ArrayList<>();

        for (int i = 0; i < linhas.size(); i++) {
            String linha = linhas.get(i).trim();
            if (linha.isEmpty()) {
                continue;
            }

            String[] campos = linha.split(";", 4);
            if (campos.length != 4) {
                throw new IOException("Linha " + (i + 1) + " invalida: sao necessarios 4 campos.");
            }

            try {
                Pet pet = new Pet(Long.parseLong(campos[0].trim()),
                        campos[1], campos[2], campos[3]);
                for (Pet existente : pets) {
                    if (existente.getMicrochip() == pet.getMicrochip()) {
                        throw new IOException("Microchip repetido na linha " + (i + 1) + ".");
                    }
                }
                pets.add(pet);
            } catch (NumberFormatException e) {
                throw new IOException("Microchip invalido na linha " + (i + 1) + ".", e);
            } catch (IllegalArgumentException e) {
                throw new IOException("Dados invalidos na linha " + (i + 1) + ": " + e.getMessage(), e);
            }
        }

        return pets.toArray(new Pet[pets.size()]);
    }
}
