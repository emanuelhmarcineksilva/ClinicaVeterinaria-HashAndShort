import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/*
 * =====================================================================================
 *  PROJETO FINAL: PET HASH & SORT - CÓDIGO MODELO BASE
 *  Resolução de Problemas Estruturados em Computação - Profa. Lisiane Reips
 * =====================================================================================
 *  Este código segue ESTRITAMENTE a estrutura e o estilo didático do material de apoio
 *  da professora (Tabela Hash com Encadeamento Exterior, Bubble Sort, Insertion Sort
 *  e Quick Sort), adaptado para a classe Pet exigida no Projeto Final.
 * =====================================================================================
 */
public class PetHashSortBase {

    // ---------------------------------------------------------------------------------
    // 1. CLASSE MODELO: PET
    // ---------------------------------------------------------------------------------
    public static class Pet {
        long microchip; // Identificador único (8 dígitos)
        String nome;     // Chave de busca na Tabela Hash
        String especie;  // ex: cão, gato, calopsita
        String mania;    // ex: "dorme no teclado", "rouba meias"

        public Pet(long microchip, String nome, String especie, String mania) {
            this.microchip = microchip;
            this.nome = nome;
            this.especie = especie;
            this.mania = mania;
        }

        @Override
        public String toString() {
            return "[" + microchip + "] " + nome + " (" + especie + ") - Mania: " + mania;
        }
    }

    // ---------------------------------------------------------------------------------
    // 2. ESTRUTURA DE DADOS: TABELA HASH COM ENCADEAMENTO EXTERIOR
    // ---------------------------------------------------------------------------------
    public static class TabelaHashPet {
        private LinkedList<Pet>[] tabela;
        private int capacidade;
        private int quantidade;

        @SuppressWarnings("unchecked")
        public TabelaHashPet(int capacidade) {
            this.capacidade = capacidade;
            this.quantidade = 0;
            this.tabela = new LinkedList[capacidade];
            for (int i = 0; i < capacidade; i++) {
                this.tabela[i] = new LinkedList<>();
            }
        }

        // FUNÇÃO HASH 1: Soma dos códigos ASCII (igual ao material de apoio da professora)
        public int hash1(String chave) {
            int soma = 0;
            String chaveUpper = chave.toUpperCase();
            for (int i = 0; i < chaveUpper.length(); i++) {
                soma += chaveUpper.charAt(i);
            }
            return Math.floorMod(soma, capacidade);
        }

        // FUNÇÃO HASH 2: Polinomial (estilo String.hashCode() do Java)
        public int hash2(String chave) {
            int h = 0;
            String chaveUpper = chave.toUpperCase();
            for (int i = 0; i < chaveUpper.length(); i++) {
                h = 31 * h + chaveUpper.charAt(i);
            }
            return Math.floorMod(h, capacidade);
        }

        // INSERIR PET
        public void inserir(Pet pet) {
            int indice = hash1(pet.nome);
            tabela[indice].add(pet);
            quantidade++;
        }

        // BUSCAR PETS POR NOME (retorna todos os pets que compartilham o mesmo nome/chave)
        public LinkedList<Pet> buscarPorNome(String nome) {
            int indice = hash1(nome);
            LinkedList<Pet> encontrados = new LinkedList<>();
            for (Pet p : tabela[indice]) {
                if (p.nome.equalsIgnoreCase(nome)) {
                    encontrados.add(p);
                }
            }
            return encontrados;
        }

        // REMOVER PET POR MICROCHIP
        public boolean removerPorMicrochip(long microchip, String nome) {
            int indice = hash1(nome);
            Pet petEncontrado = null;
            for (Pet p : tabela[indice]) {
                if (p.microchip == microchip) {
                    petEncontrado = p;
                    break;
                }
            }
            if (petEncontrado != null) {
                tabela[indice].remove(petEncontrado);
                quantidade--;
                return true;
            }
            return false;
        }

        // EXIBIR ESTRUTURA DA TABELA HASH
        public void exibir() {
            System.out.println("\n--- FICHÁRIO DA TABELA HASH (M = " + capacidade + ") ---");
            for (int i = 0; i < capacidade; i++) {
                System.out.print("[" + i + "] ");
                if (tabela[i].isEmpty()) {
                    System.out.println("null");
                } else {
                    for (int j = 0; j < tabela[i].size(); j++) {
                        Pet p = tabela[i].get(j);
                        System.out.print(p.nome + " (" + p.microchip + ")");
                        if (j < tabela[i].size() - 1) System.out.print(" -> ");
                    }
                    System.out.println(" -> null");
                }
            }
        }

        // ESTATÍSTICAS EXIGIDAS NO PROJETO
        public void exibirEstatísticas() {
            double fatorDeCarga = (double) quantidade / capacidade;
            int baldesVazios = 0;
            int maiorLista = 0;
            int colisoes = 0;

            for (int i = 0; i < capacidade; i++) {
                int tam = tabela[i].size();
                if (tam == 0) {
                    baldesVazios++;
                } else {
                    if (tam > 1) {
                        colisoes += (tam - 1);
                    }
                    if (tam > maiorLista) {
                        maiorLista = tam;
                    }
                }
            }

            System.out.println("\n--- ESTATÍSTICAS DA TABELA HASH ---");
            System.out.println("Capacidade (M): " + capacidade);
            System.out.println("Total de Pets cadastrados: " + quantidade);
            System.out.printf("Fator de Carga (FC): %.2f\n", fatorDeCarga);
            System.out.println("Baldes vazios: " + baldesVazios);
            System.out.println("Tamanho da maior lista: " + maiorLista);
            System.out.println("Número de colisões: " + colisoes);
        }
    }

    // ---------------------------------------------------------------------------------
    // 3. ALGORITMOS DE ORDENAÇÃO POR MICROCHIP (ESTILO MATERIAL DE APOIO)
    // ---------------------------------------------------------------------------------

    // 3.1 BUBBLE SORT
    public static void bubbleSort(Pet[] v) {
        int n = v.length;
        long comparacoes = 0;
        long trocas = 0;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                comparacoes++;
                if (v[j].microchip > v[j + 1].microchip) {
                    Pet aux = v[j];
                    v[j] = v[j + 1];
                    v[j + 1] = aux;
                    trocas++;
                }
            }
        }
        System.out.println("Bubble Sort concluído -> Comparações: " + comparacoes + " | Trocas: " + trocas);
    }

    // 3.2 INSERTION SORT
    public static void insertionSort(Pet[] v) {
        int n = v.length;
        long comparacoes = 0;
        long trocas = 0; // Deslocamentos

        for (int i = 1; i < n; i++) {
            Pet chave = v[i];
            int j = i - 1;

            while (j >= 0) {
                comparacoes++;
                if (v[j].microchip > chave.microchip) {
                    v[j + 1] = v[j];
                    trocas++;
                    j--;
                } else {
                    break;
                }
            }
            v[j + 1] = chave;
        }
        System.out.println("Insertion Sort concluído -> Comparações: " + comparacoes + " | Deslocamentos: " + trocas);
    }

    // 3.3 QUICK SORT
    public static class MetricasQuick {
        long comparacoes = 0;
        long trocas = 0;
    }

    public static void quickSort(Pet[] v, int inicio, int fim, MetricasQuick m) {
        if (inicio < fim) {
            int posPivot = particionar(v, inicio, fim, m);
            quickSort(v, inicio, posPivot - 1, m);
            quickSort(v, posPivot + 1, fim, m);
        }
    }

    private static int particionar(Pet[] v, int inicio, int fim, MetricasQuick m) {
        Pet pivot = v[fim]; // Pivot = último elemento
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            m.comparacoes++;
            if (v[j].microchip < pivot.microchip) {
                i++;
                trocar(v, i, j);
                m.trocas++;
            }
        }
        trocar(v, i + 1, fim);
        m.trocas++;
        return i + 1;
    }

    private static void trocar(Pet[] v, int a, int b) {
        Pet aux = v[a];
        v[a] = v[b];
        v[b] = aux;
    }

    // Função Auxiliar para imprimir vetor de Pets
    public static void imprimirVetor(Pet[] v) {
        for (Pet p : v) {
            System.out.println("  " + p);
        }
    }

    // ---------------------------------------------------------------------------------
    // 4. MENU PRINCIPAL DE CONSOLE (MODELO CONFORME EXIGIDO NO PROJETO)
    // ---------------------------------------------------------------------------------
    public static void main(String[] args) {
        // Tabela Hash com M = 13 (conforme especificado na Parte 1 do projeto)
        TabelaHashPet tabela = new TabelaHashPet(13);

        // Turminha inicial do Apêndice do projeto para testes
        Pet[] dadosIniciais = {
            new Pet(60218841L, "Luna", "gata", "dorme em cima do teclado"),
            new Pet(31057720L, "Thor", "cão", "late para o aspirador"),
            new Pet(48812903L, "Pipoca", "cão", "pula quando ouve a palavra passeio"),
            new Pet(22091187L, "Mel", "gata", "derruba copos da mesa, um por um"),
            new Pet(70334512L, "Zeus", "cão", "tem medo do gato do vizinho"),
            new Pet(13870255L, "Luna", "cachorra", "rouba meias"),
            new Pet(39021746L, "Bob", "cão", "come a ração do irmão"),
            new Pet(84460013L, "Bob", "gato", "dorme 20 horas por day")
        };

        // Inserir dados iniciais na tabela hash
        for (Pet p : dadosIniciais) {
            tabela.inserir(p);
        }

        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n==========================================");
            System.out.println("    ♥ PET HASH & SORT - CLINICA VET ♥");
            System.out.println("==========================================");
            System.out.println("1 - Cadastrar pet");
            System.out.println("2 - Buscar pets por nome");
            System.out.println("3 - Remover pet (microchip)");
            System.out.println("4 - Mostrar fichário (tabela hash)");
            System.out.println("5 - Estatísticas da tabela");
            System.out.println("6 - Organizar sala de espera (Ordenar por Microchip)");
            System.out.println("0 - Fechar a clínica");
            System.out.println("==========================================");
            System.out.print("Escolha uma opção: ");

            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir quebra de linha
            } else {
                scanner.nextLine();
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Microchip (8 dígitos): ");
                    long chip = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Espécie: ");
                    String esp = scanner.nextLine();
                    System.out.print("Mania: ");
                    String mania = scanner.nextLine();

                    Pet novo = new Pet(chip, nome, esp, mania);
                    tabela.inserir(novo);
                    System.out.println("-> Pet cadastrado com sucesso!");
                    break;

                case 2:
                    System.out.print("Digite o nome do pet para buscar: ");
                    String buscaNome = scanner.nextLine();
                    LinkedList<Pet> achados = tabela.buscarPorNome(buscaNome);
                    if (achados.isEmpty()) {
                        System.out.println("Nenhum pet encontrado com o nome " + buscaNome);
                    } else {
                        System.out.println("Pets encontrados (" + achados.size() + "):");
                        for (Pet p : achados) {
                            System.out.println("  " + p);
                        }
                    }
                    break;

                case 3:
                    System.out.print("Digite o nome do pet: ");
                    String rNome = scanner.nextLine();
                    System.out.print("Digite o microchip do pet a remover: ");
                    long rChip = scanner.nextLong();
                    scanner.nextLine();

                    if (tabela.removerPorMicrochip(rChip, rNome)) {
                        System.out.println("-> Pet removido com sucesso!");
                    } else {
                        System.out.println("-> Pet não encontrado.");
                    }
                    break;

                case 4:
                    tabela.exibir();
                    break;

                case 5:
                    tabela.exibirEstatísticas();
                    break;

                case 6:
                    // Criar cópia dos dados iniciais para testar os algoritmos de ordenação
                    Pet[] copiaBubble = Arrays.copyOf(dadosIniciais, dadosIniciais.length);
                    Pet[] copiaInsertion = Arrays.copyOf(dadosIniciais, dadosIniciais.length);
                    Pet[] copiaQuick = Arrays.copyOf(dadosIniciais, dadosIniciais.length);

                    System.out.println("\n--- TESTANDO ALGORITMOS DE ORDENAÇÃO ---");

                    System.out.println("\n1. Executando Bubble Sort...");
                    bubbleSort(copiaBubble);

                    System.out.println("\n2. Executando Insertion Sort...");
                    insertionSort(copiaInsertion);

                    System.out.println("\n3. Executando Quick Sort...");
                    MetricasQuick mq = new MetricasQuick();
                    quickSort(copiaQuick, 0, copiaQuick.length - 1, mq);
                    System.out.println("Quick Sort concluído -> Comparações: " + mq.comparacoes + " | Trocas: " + mq.trocas);

                    System.out.println("\n--- VETOR ORDENADO (POR MICROCHIP) ---");
                    imprimirVetor(copiaQuick);
                    break;

                case 0:
                    System.out.println("Saindo do sistema. Até logo!");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }
}