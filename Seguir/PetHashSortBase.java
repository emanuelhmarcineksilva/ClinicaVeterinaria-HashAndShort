import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class PetHashSortBase {
    private static final int CAPACIDADE_INICIAL = 13;

    public static void main(String[] args) {
        Path arquivo = Paths.get("dados", "pets.txt");
        if (!Files.exists(arquivo)) {
            arquivo = Paths.get("Seguir", "dados", "pets.txt");
        }

        final Pet[] petsIniciais;
        try {
            petsIniciais = ArquivoPets.ler(arquivo.toString());
        } catch (IOException e) {
            System.err.println("Nao foi possivel carregar os dados iniciais: " + e.getMessage());
            return;
        }

        TabelaHashPets tabela = new TabelaHashPets(CAPACIDADE_INICIAL);
        for (Pet pet : petsIniciais) {
            if (!tabela.inserir(pet)) {
                System.err.println("Microchip inicial duplicado: " + pet.getMicrochip());
                return;
            }
        }

        System.out.println("Pet Hash & Sort — " + petsIniciais.length + " pets carregados.");
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            exibirMenu();
            Integer opcao = lerInteiro(scanner, "Escolha uma opcao: ");
            if (opcao == null) {
                break;
            }

            switch (opcao) {
                case 1:
                    cadastrarPet(scanner, tabela);
                    break;
                case 2:
                    buscarPets(scanner, tabela);
                    break;
                case 3:
                    removerPet(scanner, tabela);
                    break;
                case 4:
                    tabela.exibir();
                    break;
                case 5:
                    tabela.estatisticas().exibir("Tabela atual", 1);
                    TabelaHashPets.exibirComparacao(tabela.listarTodos());
                    break;
                case 6:
                    organizarFila(tabela);
                    break;
                case 7:
                    ordenarPassoAPasso(scanner, tabela);
                    break;
                case 8:
                    GrandeCorrida.executar();
                    break;
                case 0:
                    executando = false;
                    System.out.println("Sistema encerrado.");
                    break;
                default:
                    System.out.println("Opcao invalida. Escolha um numero do menu.");
            }
        }
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n======================================");
        System.out.println("       PET HASH & SORT - CLINICA");
        System.out.println("======================================");
        System.out.println("1 - Cadastrar pet");
        System.out.println("2 - Buscar pets por nome");
        System.out.println("3 - Remover pet pelo microchip");
        System.out.println("4 - Mostrar fichario completo");
        System.out.println("5 - Estatisticas e comparacao das funcoes hash");
        System.out.println("6 - Organizar fila da sala de espera");
        System.out.println("7 - Ordenacao passo a passo");
        System.out.println("8 - A Grande Corrida");
        System.out.println("0 - Encerrar");
    }

    private static void cadastrarPet(Scanner scanner, TabelaHashPets tabela) {
        Long microchip = lerMicrochip(scanner, "Microchip (8 digitos): ");
        if (microchip == null) {
            return;
        }
        if (tabela.contemMicrochip(microchip)) {
            System.out.println("Ja existe um pet com esse microchip.");
            return;
        }

        String nome = lerTexto(scanner, "Nome: ");
        if (nome == null) return;
        String especie = lerTexto(scanner, "Especie: ");
        if (especie == null) return;
        String mania = lerTexto(scanner, "Mania: ");
        if (mania == null) return;

        try {
            Pet pet = new Pet(microchip, nome, especie, mania);
            if (tabela.inserir(pet)) {
                System.out.println("Pet cadastrado: " + pet);
            } else {
                System.out.println("Ja existe um pet com esse microchip.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Cadastro invalido: " + e.getMessage());
        }
    }

    private static void buscarPets(Scanner scanner, TabelaHashPets tabela) {
        String nome = lerTexto(scanner, "Nome para busca: ");
        if (nome == null) return;
        Pet[] encontrados = tabela.buscarPorNome(nome);
        if (encontrados.length == 0) {
            System.out.println("Nenhum pet encontrado com esse nome.");
            return;
        }
        System.out.println("Pets encontrados (" + encontrados.length + "):");
        Ordenacao.exibirPets(encontrados);
    }

    private static void removerPet(Scanner scanner, TabelaHashPets tabela) {
        Long microchip = lerMicrochip(scanner, "Microchip do pet a remover: ");
        if (microchip == null) return;
        Pet removido = tabela.removerPorMicrochip(microchip);
        if (removido == null) {
            System.out.println("Nenhum pet encontrado com esse microchip.");
        } else {
            System.out.println("Pet removido: " + removido);
        }
    }

    private static void organizarFila(TabelaHashPets tabela) {
        Pet[] pets = tabela.listarTodos();
        if (pets.length == 0) {
            System.out.println("Nao ha pets cadastrados para ordenar.");
            return;
        }

        Pet[] bubble = Ordenacao.copiar(pets);
        ResultadoOrdenacao resultadoBubble = Ordenacao.bubbleSort(bubble, false);
        Ordenacao.exibirResultado("Bubble Sort", resultadoBubble);

        Pet[] insertion = Ordenacao.copiar(pets);
        ResultadoOrdenacao resultadoInsertion = Ordenacao.insertionSort(insertion, false);
        Ordenacao.exibirResultado("Insertion Sort", resultadoInsertion);

        Pet[] quick = Ordenacao.copiar(pets);
        ResultadoOrdenacao resultadoQuick = Ordenacao.quickSort(quick, false);
        Ordenacao.exibirResultado("Quick Sort", resultadoQuick);

        System.out.println("\nFila em ordem crescente de microchip:");
        Ordenacao.exibirPets(quick);
    }

    private static void ordenarPassoAPasso(Scanner scanner, TabelaHashPets tabela) {
        Pet[] pets = tabela.listarTodos();
        if (pets.length == 0) {
            System.out.println("Nao ha pets cadastrados para ordenar.");
            return;
        }

        int limite = Math.min(10, pets.length);
        System.out.println("A demonstracao usa de 1 a " + limite + " pets.");
        Integer quantidade = lerInteiro(scanner, "Quantidade de pets: ");
        if (quantidade == null) return;
        if (quantidade < 1 || quantidade > limite) {
            System.out.println("Quantidade invalida.");
            return;
        }

        System.out.println("1 - Bubble Sort");
        System.out.println("2 - Insertion Sort");
        System.out.println("3 - Quick Sort");
        Integer algoritmo = lerInteiro(scanner, "Algoritmo: ");
        if (algoritmo == null) return;
        if (algoritmo < 1 || algoritmo > 3) {
            System.out.println("Algoritmo invalido.");
            return;
        }

        Pet[] demonstracao = new Pet[quantidade];
        System.arraycopy(pets, 0, demonstracao, 0, quantidade);
        System.out.println("\nFila antes da demonstracao:");
        Ordenacao.exibirPets(demonstracao);

        ResultadoOrdenacao resultado;
        if (algoritmo == 1) {
            resultado = Ordenacao.bubbleSort(demonstracao, true);
            System.out.println("\nBubble Sort — trocas: "
                    + resultado.getTrocasOuDeslocamentos());
        } else if (algoritmo == 2) {
            resultado = Ordenacao.insertionSort(demonstracao, true);
            System.out.println("\nInsertion Sort — deslocamentos: "
                    + resultado.getTrocasOuDeslocamentos());
        } else {
            resultado = Ordenacao.quickSort(demonstracao, true);
            System.out.println("\nQuick Sort — trocas: "
                    + resultado.getTrocasOuDeslocamentos());
        }
        System.out.println("Comparacoes: " + resultado.getComparacoes());
        System.out.println("Fila ordenada:");
        Ordenacao.exibirPets(demonstracao);
    }

    private static String lerTexto(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            if (!scanner.hasNextLine()) return null;
            String valor = scanner.nextLine().trim();
            if (!valor.isEmpty()) return valor;
            System.out.println("O campo nao pode ficar vazio.");
        }
    }

    private static Integer lerInteiro(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            if (!scanner.hasNextLine()) return null;
            String valor = scanner.nextLine().trim();
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro valido.");
            }
        }
    }

    private static Long lerMicrochip(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            if (!scanner.hasNextLine()) return null;
            String valor = scanner.nextLine().trim();
            if (!valor.matches("\\d{8}")) {
                System.out.println("O microchip deve conter exatamente 8 digitos.");
                continue;
            }
            return Long.parseLong(valor);
        }
    }
}
