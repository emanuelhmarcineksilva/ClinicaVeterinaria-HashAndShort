package Pets;

import java.text.Normalizer;
import java.util.Locale;

// Ficha de um bichinho da creche: cada objeto Pet é uma ficha preenchida.
public class Pet {
    private int microchip;   // 8 dígitos, nunca se repete (é por ele que ordenamos)
    private String nome;     // como foi digitado: "Paçoca"
    private String especie;
    private String mania;

    public Pet(int microchip, String nome, String especie, String mania) {
        this.microchip = microchip;
        this.nome = nome;
        this.especie = especie;
        this.mania = mania;
    }

    public int getMicrochip() {
        return microchip;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecie() {
        return especie;
    }

    public String getMania() {
        return mania;
    }

    // Chave da tabela hash: "Paçoca" -> "PACOCA"
    public String getChaveNome() {
        return normalizar(nome);
    }

    // static: a busca também precisa normalizar o nome DIGITADO, sem ter um Pet
    public static String normalizar(String texto) {
        String semAcentos = Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");               // apaga os acentos soltos
        return semAcentos.toUpperCase(Locale.ROOT);
    }

    // Usado automaticamente em System.out.println(pet)
    @Override
    public String toString() {
        return nome + " (" + especie + ") - microchip " + microchip + " - mania: " + mania;
    }
}
