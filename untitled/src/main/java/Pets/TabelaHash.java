package Pets;

// OS CANIS DA CRECHE: tabela hash com encadeamento.
// Cada balde é um canil; bichinhos que caem no mesmo canil dividem o espaço (lista ligada).
public class TabelaHash {

    // Um bichinho dentro do canil: guarda o pet e aponta para o próximo do mesmo canil
    private static class No {
        Pet pet;
        No proximo;

        No(Pet pet) {
            this.pet = pet;
            this.proximo = null;
        }
    }

    private No[] baldes;      // baldes[i] é o canil i: aponta para o PRIMEIRO bichinho dele
    private int quantidade;  // quantos bichinhos estão na creche
    private int funcaoHash;  // 1 = soma dos códigos | 2 = função do grupo

    public TabelaHash(int m, int funcaoHash) {
        this.baldes = new No[m];     // todos os canis começam vazios (null)
        this.quantidade = 0;
        this.funcaoHash = funcaoHash;
    }

    public int getM()          { return baldes.length; }
    public int getQuantidade() { return quantidade; }

    // FUNÇÃO HASH 1 (material de apoio): soma dos códigos dos caracteres, resto por M
    public static int hashSoma(String chave, int m) {
        int soma = 0;
        for (int i = 0; i < chave.length(); i++) {
            soma += chave.charAt(i);              // 'L' vale 76, 'U' vale 85...
        }
        return soma % m;
    }

    // FUNÇÃO HASH 2 (do grupo): soma PONDERADA - cada letra pesa conforme a posição
    public static int hashPonderada(String chave, int m) {
        int soma = 0;
        for (int i = 0; i < chave.length(); i++) {
            soma += chave.charAt(i) * (i + 1);    // 1a letra x1, 2a x2, 3a x3...
        }
        return soma % m;
    }

    // Escolhe a função configurada. É SEMPRE a mesma conta na inserção e na busca.
    private int indice(String chave) {
        if (funcaoHash == 1) {
            return hashSoma(chave, baldes.length);
        }
        return hashPonderada(chave, baldes.length);
    }

    // Em qual canil fica este nome? (o menu usa para mostrar a conta)
    public int canilDoNome(String nome) {
        return indice(Pet.normalizar(nome));
    }

    // INSERIR (matricular): calcula o canil pelo nome e coloca o bichinho no INÍCIO da lista
    public boolean inserir(Pet pet) {
        if (buscarPorMicrochip(pet.getMicrochip()) != null) {
            return false;                         // microchip repetido: recusa
        }
        int i = indice(pet.getChaveNome());
        No novo = new No(pet);
        novo.proximo = baldes[i];                 // o novo aponta para o antigo 1o do canil
        baldes[i] = novo;                         // e passa a ser o 1o do canil
        quantidade++;
        return true;
    }

    // BUSCAR POR NOME: vai direto ao canil e devolve TODOS os bichinhos com aquele nome
    public Pet[] buscarPorNome(String nome) {
        String chave = Pet.normalizar(nome);
        No inicio = baldes[indice(chave)];

        // 1a volta no canil: contar quantos bichinhos têm essa chave
        int encontrados = 0;
        No atual = inicio;
        while (atual != null) {
            if (atual.pet.getChaveNome().equals(chave)) {
                encontrados++;
            }
            atual = atual.proximo;
        }

        // 2a volta: copiar esses bichinhos para um vetor do tamanho certo
        Pet[] resultado = new Pet[encontrados];
        int k = 0;
        atual = inicio;
        while (atual != null) {
            if (atual.pet.getChaveNome().equals(chave)) {
                resultado[k] = atual.pet;
                k++;
            }
            atual = atual.proximo;
        }
        return resultado;
    }

    // O microchip NÃO é a chave, então não dá para calcular o canil: olhamos todos
    public Pet buscarPorMicrochip(int microchip) {
        for (int i = 0; i < baldes.length; i++) {
            No atual = baldes[i];
            while (atual != null) {
                if (atual.pet.getMicrochip() == microchip) {
                    return atual.pet;
                }
                atual = atual.proximo;
            }
        }
        return null;                              // não existe
    }

    // REMOVER POR MICROCHIP (foi para casa): acha o bichinho e "costura" a lista por cima dele
    public Pet removerPorMicrochip(int microchip) {
        for (int i = 0; i < baldes.length; i++) {
            No anterior = null;
            No atual = baldes[i];
            while (atual != null) {
                if (atual.pet.getMicrochip() == microchip) {
                    if (anterior == null) {
                        baldes[i] = atual.proximo;          // era o 1o do canil
                    } else {
                        anterior.proximo = atual.proximo;   // estava no meio ou no fim
                    }
                    quantidade--;
                    return atual.pet;
                }
                anterior = atual;
                atual = atual.proximo;
            }
        }
        return null;                                        // não encontrado
    }

    // REORGANIZAR (rehashing): muda o número de canis e/ou a função hash.
    // Todo bichinho é reinserido, porque o canil depende de M e da função.
    public void reorganizar(int novoM, int novaFuncao) {
        Pet[] todos = todosOsPets();
        baldes = new No[novoM];
        funcaoHash = novaFuncao;
        quantidade = 0;
        for (Pet p : todos) {
            inserir(p);                           // recalcula o canil com o novo M
        }
    }

    // DESENHAR OS CANIS: monta o texto da creche, canil por canil.
    // StringBuilder é um "rascunho" onde vamos juntando pedaços de texto com append.
    public String desenharCanis() {
        StringBuilder sb = new StringBuilder();
        sb.append("Creche: " + baldes.length + " canis, " + quantidade
                + " bichinhos, funcao hash " + funcaoHash + "\n");
        for (int i = 0; i < baldes.length; i++) {
            sb.append("Canil " + i + ": ");
            No atual = baldes[i];
            if (atual == null) {
                sb.append("(vazio)\n");
                continue;
            }
            while (atual != null) {
                sb.append(atual.pet.getNome() + " (" + atual.pet.getMicrochip() + ") -> ");
                atual = atual.proximo;
            }
            sb.append("null\n");
        }
        return sb.toString();
    }

    // EXIBIR: imprime o desenho dos canis
    public void exibir() {
        System.out.print(desenharCanis());
    }

    // Devolve todos os bichinhos num vetor (para organizar a fila da saída)
    public Pet[] todosOsPets() {
        Pet[] todos = new Pet[quantidade];
        int k = 0;
        for (int i = 0; i < baldes.length; i++) {
            No atual = baldes[i];
            while (atual != null) {
                todos[k] = atual.pet;
                k++;
                atual = atual.proximo;
            }
        }
        return todos;
    }

    // ---------------- ESTATÍSTICAS ----------------
    // LOTAÇÃO DA CRECHE = fator de carga: quantos bichinhos por canil, em média
    public double fatorDeCarga() {
        return (double) quantidade / baldes.length;     // (double) evita divisão inteira
    }

    // Até 0,75 é o ideal dos slides; acima de 1 tem mais bichinho que canil
    public String situacaoDaLotacao() {
        double lotacao = fatorDeCarga();
        if (lotacao <= 0.75) {
            return "tranquila";
        } else if (lotacao <= 1.0) {
            return "no limite";
        }
        return "superlotada";
    }

    private int tamanhoDaLista(No inicio) {
        int tamanho = 0;
        No atual = inicio;
        while (atual != null) {
            tamanho++;
            atual = atual.proximo;
        }
        return tamanho;
    }

    public int baldesVazios() {
        int vazios = 0;
        for (int i = 0; i < baldes.length; i++) {
            if (baldes[i] == null) {
                vazios++;
            }
        }
        return vazios;
    }

    public int maiorLista() {
        int maior = 0;
        for (int i = 0; i < baldes.length; i++) {
            int tamanho = tamanhoDaLista(baldes[i]);
            if (tamanho > maior) {
                maior = tamanho;
            }
        }
        return maior;
    }

    // Colisões (contagem simples): todo bichinho que chegou num canil já ocupado.
    // Para cada canil: tamanho - 1 (se tiver pelo menos 1 bichinho).
    public int colisoes() {
        int total = 0;
        for (int i = 0; i < baldes.length; i++) {
            int tamanho = tamanhoDaLista(baldes[i]);
            if (tamanho > 1) {
                total += tamanho - 1;
            }
        }
        return total;
    }

    // Colisões "de verdade": NOMES DIFERENTES dividindo o mesmo canil.
    // Para cada canil: (quantidade de nomes distintos) - 1.
    public int colisoesEntreNomesDiferentes() {
        int total = 0;
        for (int i = 0; i < baldes.length; i++) {
            int distintos = nomesDistintos(baldes[i]);
            if (distintos > 1) {
                total += distintos - 1;
            }
        }
        return total;
    }

    private int nomesDistintos(No inicio) {
        int distintos = 0;
        No atual = inicio;
        while (atual != null) {
            // esse nome já apareceu ANTES neste mesmo canil?
            boolean repetido = false;
            No antes = inicio;
            while (antes != atual) {
                if (antes.pet.getChaveNome().equals(atual.pet.getChaveNome())) {
                    repetido = true;
                    break;
                }
                antes = antes.proximo;
            }
            if (!repetido) {
                distintos++;
            }
            atual = atual.proximo;
        }
        return distintos;
    }

    public void mostrarEstatisticas() {
        System.out.printf("Lotacao da creche ..... %.2f bichinhos por canil (%d / %d): %s%n",
                fatorDeCarga(), quantidade, baldes.length, situacaoDaLotacao());
        System.out.println("Canis vazios .......... " + baldesVazios());
        System.out.println("Canil mais cheio ...... " + maiorLista() + " bichinhos (maior lista)");
        System.out.println("Colisoes (bichinho em canil ocupado) ... " + colisoes());
        System.out.println("Colisoes entre nomes diferentes ........ " + colisoesEntreNomesDiferentes());
    }
}
