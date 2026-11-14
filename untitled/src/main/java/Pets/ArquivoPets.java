package Pets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

// Lê o arquivo de dados (uma linha por pet: microchip;nome;especie;mania)
public class ArquivoPets {

    public static Pet[] lerPets(String caminho) {
        List<String> linhas;
        try {
            linhas = Files.readAllLines(Path.of(caminho), StandardCharsets.UTF_8);
        } catch (IOException e) {
            // Mostra ONDE o Java procurou: ajuda muito a descobrir o problema
            System.out.println("Arquivo nao encontrado: " + Path.of(caminho).toAbsolutePath());
            return new Pet[0];
        }

        Pet[] temporario = new Pet[linhas.size()];   // no máximo 1 pet por linha
        int quantidade = 0;

        for (String linha : linhas) {
            linha = linha.replace("\uFEFF", "").trim();   // tira o BOM do Bloco de Notas
            if (linha.isEmpty() || linha.startsWith("#")) {
                continue;                                  // pula linha vazia ou comentário
            }
            String[] campos = linha.split(";", 4);
            if (campos.length < 4) {
                System.out.println("Linha ignorada (faltam campos): " + linha);
                continue;
            }
            try {
                int microchip = Integer.parseInt(campos[0].trim());
                temporario[quantidade] = new Pet(microchip, campos[1].trim(),
                                                 campos[2].trim(), campos[3].trim());
                quantidade++;
            } catch (NumberFormatException e) {
                System.out.println("Linha ignorada (microchip invalido): " + linha);
            }
        }

        // Copia só as posições preenchidas para um vetor do tamanho exato
        Pet[] pets = new Pet[quantidade];
        for (int i = 0; i < quantidade; i++) {
            pets[i] = temporario[i];
        }
        return pets;
    }
}
