package Pets;

import java.text.Normalizer;
import java.util.Locale;

public class Pet {
    private int microchip;
    private String nome;
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

    public String getChaveNome() {
        String nomeSemAcentos = Normalizer.normalize(nome, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return nomeSemAcentos.toUpperCase(Locale.ROOT);
    }
}
