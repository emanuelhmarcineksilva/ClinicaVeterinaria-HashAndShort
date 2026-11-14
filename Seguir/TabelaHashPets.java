public class TabelaHashPets {
    private static class No {
        private final Pet pet;
        private No proximo;

        private No(Pet pet) {
            this.pet = pet;
        }
    }

    public static class Estatisticas {
        private final int quantidade;
        private final int capacidade;
        private final int baldesVazios;
        private final int maiorLista;
        private final int colisoes;

        private Estatisticas(int quantidade, int capacidade, int baldesVazios,
                int maiorLista, int colisoes) {
            this.quantidade = quantidade;
            this.capacidade = capacidade;
            this.baldesVazios = baldesVazios;
            this.maiorLista = maiorLista;
            this.colisoes = colisoes;
        }

        public void exibir(String identificacao, int funcaoHash) {
            System.out.println(identificacao + " — M = " + capacidade
                    + ", funcao hash " + funcaoHash);
            System.out.println("  Pets: " + quantidade);
            System.out.printf("  Fator de carga: %.2f%n", (double) quantidade / capacidade);
            System.out.println("  Baldes vazios: " + baldesVazios);
            System.out.println("  Maior lista: " + maiorLista);
            System.out.println("  Colisoes: " + colisoes);
        }
    }

    private final No[] tabela;
    private final int capacidade;
    private final int funcaoHash;
    private int quantidade;

    public TabelaHashPets(int capacidade) {
        this(capacidade, 1);
    }

    public TabelaHashPets(int capacidade, int funcaoHash) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException("A capacidade deve ser maior que zero.");
        }
        if (funcaoHash != 1 && funcaoHash != 2) {
            throw new IllegalArgumentException("A funcao hash deve ser 1 ou 2.");
        }
        this.capacidade = capacidade;
        this.funcaoHash = funcaoHash;
        this.tabela = new No[capacidade];
    }

    public int hash1(String chave) {
        String normalizada = Pet.normalizarNome(chave);
        long soma = 0;
        for (int i = 0; i < normalizada.length(); i++) {
            soma += normalizada.charAt(i);
        }
        return (int) (soma % capacidade);
    }

    public int hash2(String chave) {
        String normalizada = Pet.normalizarNome(chave);
        long valor = 0;
        for (int i = 0; i < normalizada.length(); i++) {
            valor = (valor + (long) normalizada.charAt(i) * (i + 1)) % capacidade;
        }
        return (int) valor;
    }

    private int indice(String nome) {
        return funcaoHash == 1 ? hash1(nome) : hash2(nome);
    }

    public boolean inserir(Pet pet) {
        if (contemMicrochip(pet.getMicrochip())) {
            return false;
        }
        int posicao = indice(pet.getNome());
        No novo = new No(pet);
        novo.proximo = tabela[posicao];
        tabela[posicao] = novo;
        quantidade++;
        return true;
    }

    public boolean contemMicrochip(long microchip) {
        for (No inicio : tabela) {
            for (No atual = inicio; atual != null; atual = atual.proximo) {
                if (atual.pet.getMicrochip() == microchip) {
                    return true;
                }
            }
        }
        return false;
    }

    public Pet[] buscarPorNome(String nome) {
        int posicao = indice(nome);
        int encontrados = 0;
        String chave = Pet.normalizarNome(nome);

        for (No atual = tabela[posicao]; atual != null; atual = atual.proximo) {
            if (Pet.normalizarNome(atual.pet.getNome()).equals(chave)) {
                encontrados++;
            }
        }

        Pet[] resultado = new Pet[encontrados];
        int indiceResultado = 0;
        for (No atual = tabela[posicao]; atual != null; atual = atual.proximo) {
            if (Pet.normalizarNome(atual.pet.getNome()).equals(chave)) {
                resultado[indiceResultado++] = atual.pet;
            }
        }
        return resultado;
    }

    public Pet removerPorMicrochip(long microchip) {
        for (int i = 0; i < capacidade; i++) {
            No anterior = null;
            No atual = tabela[i];
            while (atual != null) {
                if (atual.pet.getMicrochip() == microchip) {
                    if (anterior == null) {
                        tabela[i] = atual.proximo;
                    } else {
                        anterior.proximo = atual.proximo;
                    }
                    quantidade--;
                    return atual.pet;
                }
                anterior = atual;
                atual = atual.proximo;
            }
        }
        return null;
    }

    public Pet[] listarTodos() {
        Pet[] pets = new Pet[quantidade];
        int indice = 0;
        for (No inicio : tabela) {
            for (No atual = inicio; atual != null; atual = atual.proximo) {
                pets[indice++] = atual.pet;
            }
        }
        return pets;
    }

    public void exibir() {
        System.out.println("\n--- FICHARIO (M = " + capacidade + ") ---");
        for (int i = 0; i < capacidade; i++) {
            System.out.print("[" + i + "] ");
            No atual = tabela[i];
            if (atual == null) {
                System.out.println("vazio");
                continue;
            }
            while (atual != null) {
                System.out.print(atual.pet.getNome() + " (" + atual.pet.getMicrochip() + ")");
                atual = atual.proximo;
                System.out.print(atual == null ? " -> null" : " -> ");
            }
            System.out.println();
        }
    }

    public Estatisticas estatisticas() {
        int baldesVazios = 0;
        int maiorLista = 0;
        int colisoes = 0;

        for (No inicio : tabela) {
            int tamanhoLista = 0;
            for (No atual = inicio; atual != null; atual = atual.proximo) {
                tamanhoLista++;
            }
            if (tamanhoLista == 0) {
                baldesVazios++;
            } else {
                // Cada registro depois do primeiro no balde conta como uma colisao.
                colisoes += tamanhoLista - 1;
                if (tamanhoLista > maiorLista) {
                    maiorLista = tamanhoLista;
                }
            }
        }
        return new Estatisticas(quantidade, capacidade, baldesVazios, maiorLista, colisoes);
    }

    public static void exibirComparacao(Pet[] pets) {
        int[] capacidades = {13, 17, 23};
        System.out.println("\n--- COMPARACAO DAS FUNCOES HASH ---");
        System.out.println("Funcao 1: soma dos codigos dos caracteres normalizados.");
        System.out.println("Funcao 2: soma ponderada pelo indice do caractere (i + 1).");
        for (int capacidade : capacidades) {
            for (int funcao = 1; funcao <= 2; funcao++) {
                TabelaHashPets teste = new TabelaHashPets(capacidade, funcao);
                for (Pet pet : pets) {
                    if (!teste.inserir(pet)) {
                        throw new IllegalArgumentException("Microchip duplicado na comparacao.");
                    }
                }
                teste.estatisticas().exibir("Resultado", funcao);
            }
        }
    }
}
