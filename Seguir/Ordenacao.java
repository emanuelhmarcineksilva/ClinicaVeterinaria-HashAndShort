public class Ordenacao {
    private Ordenacao() {
    }

    public static ResultadoOrdenacao bubbleSort(Pet[] pets, boolean passoAPasso) {
        long comparacoes = 0;
        long trocas = 0;
        long inicio = System.nanoTime();

        for (int i = 0; i < pets.length - 1; i++) {
            boolean trocou = false;
            if (passoAPasso) {
                System.out.println("\nBubble Sort — passagem " + (i + 1));
            }
            for (int j = 0; j < pets.length - 1 - i; j++) {
                Pet esquerda = pets[j];
                Pet direita = pets[j + 1];
                comparacoes++;
                boolean foraDeOrdem = esquerda.getMicrochip() > direita.getMicrochip();
                if (passoAPasso) {
                    System.out.print("Compara " + esquerda.getMicrochip() + " e "
                            + direita.getMicrochip() + ": ");
                }
                if (foraDeOrdem) {
                    pets[j] = direita;
                    pets[j + 1] = esquerda;
                    trocas++;
                    trocou = true;
                    if (passoAPasso) {
                        System.out.println("trocam de cadeira na sala de espera.");
                    }
                } else if (passoAPasso) {
                    System.out.println("a ordem foi mantida.");
                }
            }
            if (passoAPasso) {
                System.out.println("Fim da passagem " + (i + 1) + ".");
            }
            if (!trocou) {
                break;
            }
        }
        return new ResultadoOrdenacao(comparacoes, trocas, System.nanoTime() - inicio);
    }

    public static ResultadoOrdenacao insertionSort(Pet[] pets, boolean passoAPasso) {
        long comparacoes = 0;
        long deslocamentos = 0;
        long inicio = System.nanoTime();

        for (int i = 1; i < pets.length; i++) {
            Pet chave = pets[i];
            int j = i - 1;
            if (passoAPasso) {
                System.out.println("\nInsertion Sort — etapa " + i + ", inserindo "
                        + chave.getMicrochip() + ".");
            }
            while (j >= 0) {
                Pet anterior = pets[j];
                comparacoes++;
                if (anterior.getMicrochip() > chave.getMicrochip()) {
                    pets[j + 1] = anterior;
                    deslocamentos++;
                    if (passoAPasso) {
                        System.out.println(anterior.getMicrochip() + " > "
                                + chave.getMicrochip() + ": desloca uma cadeira para a direita.");
                    }
                    j--;
                } else {
                    if (passoAPasso) {
                        System.out.println(anterior.getMicrochip() + " <= "
                                + chave.getMicrochip() + ": a ordem foi mantida.");
                    }
                    break;
                }
            }
            pets[j + 1] = chave;
        }
        return new ResultadoOrdenacao(comparacoes, deslocamentos, System.nanoTime() - inicio);
    }

    public static ResultadoOrdenacao quickSort(Pet[] pets, boolean passoAPasso) {
        long comparacoes = 0;
        long trocas = 0;
        long inicio = System.nanoTime();

        if (pets.length > 1) {
            int[] pilha = new int[pets.length * 2];
            int topo = 0;
            pilha[topo++] = 0;
            pilha[topo++] = pets.length - 1;
            int particaoNumero = 0;

            while (topo > 0) {
                int fim = pilha[--topo];
                int comeco = pilha[--topo];
                if (comeco >= fim) {
                    continue;
                }

                particaoNumero++;
                Pet pivo = pets[fim];
                int i = comeco - 1;
                if (passoAPasso) {
                    System.out.println("\nQuick Sort — particao " + particaoNumero
                            + " (pivo " + pivo.getMicrochip() + ").");
                }

                for (int j = comeco; j < fim; j++) {
                    Pet atual = pets[j];
                    comparacoes++;
                    if (passoAPasso) {
                        System.out.print("Compara " + atual.getMicrochip() + " com o pivo "
                                + pivo.getMicrochip() + ": ");
                    }
                    if (atual.getMicrochip() < pivo.getMicrochip()) {
                        i++;
                        if (i != j) {
                            trocar(pets, i, j);
                            trocas++;
                            if (passoAPasso) {
                                System.out.println("trocam de cadeira.");
                            }
                        } else if (passoAPasso) {
                            System.out.println("ja esta do lado correto; sem troca.");
                        }
                    } else if (passoAPasso) {
                        System.out.println("a ordem foi mantida.");
                    }
                }

                int posicaoPivo = i + 1;
                if (posicaoPivo != fim) {
                    trocar(pets, posicaoPivo, fim);
                    trocas++;
                    if (passoAPasso) {
                        System.out.println("O pivo troca de cadeira e fica na posicao "
                                + posicaoPivo + ".");
                    }
                } else if (passoAPasso) {
                    System.out.println("O pivo ja esta na posicao correta.");
                }

                if (posicaoPivo + 1 < fim) {
                    pilha[topo++] = posicaoPivo + 1;
                    pilha[topo++] = fim;
                }
                if (comeco < posicaoPivo - 1) {
                    pilha[topo++] = comeco;
                    pilha[topo++] = posicaoPivo - 1;
                }
            }
        }
        return new ResultadoOrdenacao(comparacoes, trocas, System.nanoTime() - inicio);
    }

    private static void trocar(Pet[] pets, int primeiro, int segundo) {
        Pet auxiliar = pets[primeiro];
        pets[primeiro] = pets[segundo];
        pets[segundo] = auxiliar;
    }

    public static Pet[] copiar(Pet[] pets) {
        Pet[] copia = new Pet[pets.length];
        System.arraycopy(pets, 0, copia, 0, pets.length);
        return copia;
    }

    public static boolean estaOrdenado(Pet[] pets) {
        for (int i = 1; i < pets.length; i++) {
            if (pets[i - 1].getMicrochip() > pets[i].getMicrochip()) {
                return false;
            }
        }
        return true;
    }

    public static void exibirResultado(String algoritmo, ResultadoOrdenacao resultado) {
        System.out.printf("%s: comparacoes = %d, trocas/deslocamentos = %d, tempo = %d ns%n",
                algoritmo, resultado.getComparacoes(),
                resultado.getTrocasOuDeslocamentos(), resultado.getTempoNanosegundos());
    }

    public static void exibirPets(Pet[] pets) {
        for (Pet pet : pets) {
            System.out.println("  " + pet);
        }
    }
}
