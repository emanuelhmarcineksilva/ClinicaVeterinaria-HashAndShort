public class ResultadoOrdenacao {
    private final long comparacoes;
    private final long trocasOuDeslocamentos;
    private final long tempoNanosegundos;

    public ResultadoOrdenacao(long comparacoes, long trocasOuDeslocamentos, long tempoNanosegundos) {
        this.comparacoes = comparacoes;
        this.trocasOuDeslocamentos = trocasOuDeslocamentos;
        this.tempoNanosegundos = tempoNanosegundos;
    }

    public long getComparacoes() {
        return comparacoes;
    }

    public long getTrocasOuDeslocamentos() {
        return trocasOuDeslocamentos;
    }

    public long getTempoNanosegundos() {
        return tempoNanosegundos;
    }
}
