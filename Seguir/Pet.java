import java.text.Normalizer;
import java.util.Locale;

public class Pet {
    private final long microchip;
    private final String nome;
    private final String especie;
    private final String mania;

    public Pet(long microchip, String nome, String especie, String mania) {
        if (microchip < 10000000L || microchip > 99999999L) {
            throw new IllegalArgumentException("O microchip deve ter exatamente 8 digitos.");
        }
        this.microchip = microchip;
        this.nome = campoObrigatorio(nome, "nome");
        this.especie = campoObrigatorio(especie, "especie");
        this.mania = campoObrigatorio(mania, "mania");
    }

    private static String campoObrigatorio(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo " + campo + " nao pode ficar vazio.");
        }
        return valor.trim();
    }

    public long getMicrochip() {
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

    public static String normalizarNome(String nome) {
        String maiusculo = nome.trim().toUpperCase(Locale.ROOT);
        return Normalizer.normalize(maiusculo, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
    }

    @Override
    public String toString() {
        return "[" + microchip + "] " + nome + " (" + especie + ") - Mania: " + mania;
    }
}
