package Pets;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

// CRECHE DOS BICHOS - menu principal: liga todas as partes do projeto
public class Main {

    static final int M_INICIAL = 13;
    static final int[] TAMANHOS_TESTE = {13, 17, 31};   // 31 = tamanho escolhido pelo grupo

    static Scanner teclado;
    static TabelaHash creche;
    static Pet[] doArquivo;     // os bichinhos do pets.txt (a Grande Corrida usa no n = 50)

    public static void main(String[] args) {
        teclado = new Scanner(System.in);   // criado aqui: assim os testes conseguem simular o teclado
        String arquivo = localizarArquivo();
        doArquivo = ArquivoPets.lerPets(arquivo);
        creche = new TabelaHash(M_INICIAL, 1);
        for (Pet p : doArquivo) {
            if (!creche.inserir(p)) {
                System.out.println("Microchip repetido ignorado: " + p);
            }
        }
        System.out.println(creche.getQuantidade() + " bichinhos chegaram na creche (" + arquivo + ")");

        int opcao;
        do {
            mostrarMenu();
            opcao = lerInteiro("Escolha uma opcao: ");
            switch (opcao) {
                case 1: matricular(); break;
                case 2: procurar(); break;
                case 3: irParaCasa(); break;
                case 4: creche.exibir(); break;
                case 5: lotacao(); break;
                case 6: organizarFila(); break;
                case 7: passoAPasso(); break;
                case 8: grandeCorrida(); break;
                case 9: mudarCanis(); break;
                case 0: System.out.println("Creche fechada. Ate amanha, bichinhos!"); break;
                default: System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    // O IntelliJ pode rodar a partir da pasta do módulo (untitled) ou da pasta do projeto
    static String localizarArquivo() {
        if (Files.exists(Path.of("dados", "pets.txt"))) {
            return "dados/pets.txt";
        }
        return "untitled/dados/pets.txt";
    }

    static void mostrarMenu() {
        System.out.println();
        System.out.println("+==========================================+");
        System.out.println("|            CRECHE DOS BICHOS             |");
        System.out.println("+==========================================+");
        System.out.println("| 1 - Matricular bichinho                  |");
        System.out.println("| 2 - Procurar bichinhos por nome          |");
        System.out.println("| 3 - Bichinho foi para casa (microchip)   |");
        System.out.println("| 4 - Mostrar os canis (tabela hash)       |");
        System.out.println("| 5 - Lotacao da creche (estatisticas)     |");
        System.out.println("| 6 - Organizar a fila da saida            |");
        System.out.println("| 7 - Fila da saida passo a passo          |");
        System.out.println("| 8 - A Grande Corrida                     |");
        System.out.println("| 9 - Mudar os canis (M e funcao hash)     |");
        System.out.println("| 0 - Fechar a creche                      |");
        System.out.println("+==========================================+");
    }

    // Lê a linha inteira e converte para número; repete até a pessoa digitar um número
    static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String linha = teclado.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas numeros.");
            }
        }
    }

    static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return teclado.nextLine().trim();
    }

    static void matricular() {
        int microchip = lerInteiro("Microchip (8 digitos): ");
        if (microchip < 10_000_000 || microchip > 99_999_999) {
            System.out.println("O microchip precisa ter exatamente 8 digitos.");
            return;
        }
        String nome = lerTexto("Nome: ");
        String especie = lerTexto("Especie: ");
        String mania = lerTexto("Mania: ");
        if (nome.isEmpty()) {
            System.out.println("O nome nao pode ficar vazio.");
            return;
        }
        Pet novo = new Pet(microchip, nome, especie, mania);
        if (creche.inserir(novo)) {
            System.out.println("Bichinho matriculado! Chave " + novo.getChaveNome()
                    + " -> canil " + creche.canilDoNome(nome));
        } else {
            System.out.println("Ja existe um bichinho com esse microchip.");
        }
    }

    static void procurar() {
        String nome = lerTexto("Nome do bichinho: ");
        Pet[] achados = creche.buscarPorNome(nome);
        System.out.println("Chave " + Pet.normalizar(nome) + " -> canil " + creche.canilDoNome(nome));
        if (achados.length == 0) {
            System.out.println("Nenhum bichinho com esse nome na creche.");
        } else {
            System.out.println(achados.length + " bichinho(s) encontrado(s):");
            for (Pet p : achados) {
                System.out.println("  " + p);
            }
        }
    }

    static void irParaCasa() {
        int microchip = lerInteiro("Microchip do bichinho que foi para casa: ");
        Pet saiu = creche.removerPorMicrochip(microchip);
        if (saiu == null) {
            System.out.println("Nenhum bichinho com esse microchip.");
        } else {
            System.out.println("Tchau, " + saiu.getNome() + "! Saiu do canil "
                    + creche.canilDoNome(saiu.getNome()) + ".");
        }
    }

    static void lotacao() {
        System.out.println("--- Lotacao da creche ---");
        creche.mostrarEstatisticas();

        // Os MESMOS bichinhos em creches de tamanhos e funções diferentes (comparação justa)
        Pet[] todos = creche.todosOsPets();
        System.out.println("\n--- Comparando tamanhos (M) e funcoes hash com os mesmos "
                + todos.length + " bichinhos ---");
        System.out.printf("%-7s %4s %8s %7s %6s %9s %11s%n",
                "Funcao", "M", "Lotacao", "Vazios", "Maior", "Colisoes", "Nomes dif.");
        for (int f = 1; f <= 2; f++) {
            for (int m : TAMANHOS_TESTE) {
                TabelaHash teste = new TabelaHash(m, f);
                for (Pet p : todos) {
                    teste.inserir(p);
                }
                System.out.printf("%-7d %4d %8.2f %7d %6d %9d %11d%n", f, m,
                        teste.fatorDeCarga(), teste.baldesVazios(), teste.maiorLista(),
                        teste.colisoes(), teste.colisoesEntreNomesDiferentes());
            }
        }
    }

    // Com dados já ordenados, o Quick Sort (pivô = último) faz 10.000 chamadas
    // "empilhadas" e pode dar StackOverflowError. Solução: rodar a corrida numa
    // thread separada com uma pilha grande (256 MB).
    // A tarefa da thread "() -> ..." roda a corrida com os bichinhos do arquivo (n = 50).
    static void grandeCorrida() {
        Thread corrida = new Thread(null, () -> GrandeCorrida.executar(doArquivo),
                "corrida", 256L * 1024 * 1024);
        corrida.start();
        try {
            corrida.join();          // espera a corrida acabar antes de voltar ao menu
        } catch (InterruptedException e) {
            System.out.println("Corrida interrompida.");
        }
    }

    // Opção 9: muda o número de canis (M) e a função hash; todo bichinho troca de canil (rehashing)
    static void mudarCanis() {
        int m = lerInteiro("Quantos canis (M)? ");
        if (m < 1) {
            System.out.println("O numero de canis (M) precisa ser pelo menos 1.");
            return;
        }
        int funcao = lerInteiro("Funcao hash (1 = soma dos codigos, 2 = ponderada do grupo): ");
        if (funcao != 1 && funcao != 2) {
            System.out.println("Funcao invalida (use 1 ou 2).");
            return;
        }
        creche.reorganizar(m, funcao);
        System.out.printf("Creche reorganizada: %d canis, funcao hash %d. Lotacao: %.2f (%s)%n",
                m, funcao, creche.fatorDeCarga(), creche.situacaoDaLotacao());
    }

    static void organizarFila() {
        Pet[] bichinhos = creche.todosOsPets();
        if (bichinhos.length < 2) {
            System.out.println("Poucos bichinhos para organizar a fila.");
            return;
        }
        Pet[] fila = null;
        for (int a = 0; a < 3; a++) {
            Pet[] copia = Ordenacao.copiar(bichinhos);      // a mesma fila para os três
            long inicio = System.nanoTime();
            ResultadoOrdenacao r = Ordenacao.ordenar(a, copia, false);
            long fim = System.nanoTime();
            String movimento = "trocas";
            if (a == 1) {
                movimento = "deslocamentos";
            }
            System.out.printf("%-15s comparacoes: %5d | %s: %5d | tempo: %.3f ms%n",
                    r.algoritmo, r.comparacoes, movimento, r.trocas, (fim - inicio) / 1_000_000.0);
            fila = copia;
        }
        System.out.println("\nFila da saida (menor microchip vai para casa primeiro):");
        for (int i = 0; i < fila.length; i++) {
            System.out.println("  " + (i + 1) + "o: " + fila[i]);
        }
    }

    static void passoAPasso() {
        Pet[] todos = creche.todosOsPets();
        int qtd = lerInteiro("Quantos bichinhos na fila (2 a 10)? ");
        if (qtd < 2 || qtd > 10 || qtd > todos.length) {
            System.out.println("Escolha entre 2 e 10 bichinhos.");
            return;
        }
        System.out.println("1 - Bubble Sort | 2 - Insertion Sort | 3 - Quick Sort");
        int escolha = lerInteiro("Algoritmo: ");
        if (escolha < 1 || escolha > 3) {
            System.out.println("Algoritmo invalido.");
            return;
        }
        Pet[] fila = new Pet[qtd];
        for (int i = 0; i < qtd; i++) {
            fila[i] = todos[i];
        }
        System.out.println("Fila ANTES de organizar:");
        Ordenacao.mostrarFila(fila);
        ResultadoOrdenacao r = Ordenacao.ordenar(escolha - 1, fila, true);
        System.out.println("\nFIM do " + r.algoritmo + ": " + r.comparacoes
                + " comparacoes e " + r.trocas + " trocas/deslocamentos.");
    }
}
