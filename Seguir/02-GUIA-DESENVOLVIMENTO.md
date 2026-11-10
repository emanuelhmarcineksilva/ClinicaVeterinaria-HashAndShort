# Guia de desenvolvimento — Pet Hash & Sort

> Este é o guia principal. Ele explica como construir cada parte, por que ela existe e em qual material da aula olhar. A ideia não é copiar um projeto pronto: é montar o código do grupo entendendo cada método.

## Antes de programar: combinados importantes

- O enunciado é a regra principal. Se um exemplo do material de apoio tiver algo diferente, sigam o enunciado.
- A tabela hash precisa ser implementada pelo grupo, com vetor e lista ligada.
- Para ordenar, implementem os algoritmos. Não chamem `Arrays.sort()` ou `Collections.sort()`.
- Usem o mesmo campo de ordenação em todos os testes: microchip.
- Cada algoritmo recebe uma cópia dos mesmos dados.
- Não adicionem funcionalidades além das pedidas.

---

# ETAPA 1 — Representar um pet

**Arquivo:** `Pet.java`

**Consultar:** enunciado, seção 3 e apêndice; material de apoio, que usa dados simples e precisa ser adaptado para objetos.

Cada pet tem quatro informações:
- `microchip`: número de 8 dígitos e único;
- `nome`;
- `especie`;
- `mania`.

Uma classe é o molde; cada objeto `Pet` é um registro concreto.

### Como montar

1. Criem os quatro atributos.
2. Criem um construtor que receba os quatro valores.
3. Criem getters para consultar os valores.
4. Criem um método `toString()` para exibir o pet de forma legível.
5. O microchip pode ser `int`, pois tem 8 dígitos e cabe no tipo. Também poderia ser `long`; escolham um e mantenham o mesmo tipo no projeto.

Exemplo da estrutura (adaptem e completem):

```java
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
}
```

### Normalizar o nome para a chave

A chave da tabela deve ficar em maiúsculas e sem acento. Não alterem o nome que será mostrado ao usuário; criem uma versão normalizada só para calcular a chave.

A ideia:
1. converter para maiúsculas;
2. decompor os caracteres Unicode;
3. remover marcas de acento;
4. usar o resultado como chave.

Em Java, `java.text.Normalizer` ajuda nessa tarefa. Exemplo:

```java
String chave = Normalizer.normalize(nome.toUpperCase(), Normalizer.Form.NFD)
        .replaceAll("\\p{M}", "");
```

Exemplo: `Paçoca` vira `PACOCA`.

### Teste desta etapa

Criem dois objetos `Pet` e imprimam os dados. Confiram se o microchip, nome, espécie e mania aparecem corretamente.

---

# ETAPA 2 — Ler os pets do arquivo

**Arquivo:** `ArquivoPets.java`

**Consultar:** enunciado, seção 3. A leitura do arquivo é uma dica explícita da professora.

O arquivo `dados/pets.txt` terá uma linha por pet:

```text
microchip;nome;especie;mania
```

Não é obrigatório escrever literalmente um cabeçalho. Se usarem cabeçalho, o leitor precisa ignorar a primeira linha.

### Como implementar

1. Receber o caminho do arquivo.
2. Ler as linhas com `Files.readAllLines(...)`.
3. Criar um array ou `ArrayList<Pet>` para guardar os pets lidos. O enunciado proíbe estruturas prontas para substituir a tabela hash; isso não impede usar uma lista/array como conjunto de dados para ordenação.
4. Para cada linha, separar os quatro campos por `;`.
5. Converter o primeiro campo para inteiro.
6. Criar um objeto `Pet`.
7. Retornar a coleção de pets.

Exemplo da conversão de uma linha:

```java
String[] campos = linha.split(";", 4);
int microchip = Integer.parseInt(campos[0]);
Pet pet = new Pet(microchip, campos[1], campos[2], campos[3]);
```

Tratem erros básicos: arquivo inexistente, linha com campo faltando ou microchip inválido. Não precisam criar um sistema complexo de recuperação.

### Teste desta etapa

- Ler o arquivo.
- Mostrar quantos pets foram carregados.
- Mostrar alguns registros.
- Conferir que existem pelo menos 50 e que os microchips não se repetem.

---

# ETAPA 3 — Construir a tabela hash com encadeamento

**Arquivo:** `TabelaHashPets.java`

**Consultar nesta ordem:**
1. `8-Tabela_Hash.pdf`: conceito de chave, índice e função hash.
2. `Material de Apoio - Hash e Ordenação em Java.pdf`, seção 1: código de tabela hash com encadeamento.
3. `TabelaHashExplicada.java`: explicação linha por linha.
4. `Tabela_Hash_Hotel_Hash.pdf`: exemplo mais completo de tabela e colisões.

**Atenção:** o Hotel Hash também mostra outras estratégias. O projeto pede somente **encadeamento**. Não implementem endereçamento aberto, área de reserva ou rehashing, a menos que a professora peça.

## 3.1 Entender a estrutura

A tabela será um vetor de posições. Cada posição é um balde. Cada balde aponta para o primeiro nó de uma lista ligada.

Cada nó guarda:
- um objeto `Pet`;
- uma referência para o próximo nó.

Desenho simplificado:

```text
tabela
  |
  +-- [0] -> null
  +-- [1] -> Pet Luna -> Pet Luna -> null
  +-- [2] -> Pet Thor -> null
  +-- [3] -> Pet Bob -> Pet Mel -> null
  +-- [4] -> null
```

Duas coisas diferentes podem cair no mesmo balde: isso é uma colisão. Pets com o mesmo nome também caem no mesmo balde porque têm a mesma chave; tecnicamente, colisão de hash é quando **chaves diferentes** chegam ao mesmo índice.

## 3.2 Criar o nó

Dentro de `TabelaHashPets`, criem uma classe interna simples:

```java
private static class No {
    Pet pet;
    No proximo;

    No(Pet pet) {
        this.pet = pet;
        this.proximo = null;
    }
}
```

A classe interna evita criar outro arquivo só para um elemento que só a tabela usa.

## 3.3 Criar o vetor e o construtor

A tabela precisa guardar:
- vetor de `No`;
- quantidade total de pets cadastrados;
- tamanho do vetor (`M`);
- qual função hash está sendo usada, caso optem por permitir alternar entre as duas.

O vetor começa vazio. Em Java, posições de um vetor de objetos começam com `null`.

Exemplo da ideia:

```java
private No[] tabela;
private int quantidade;

public TabelaHashPets(int capacidade) {
    tabela = new No[capacidade];
    quantidade = 0;
}
```

Testem com capacidade 13, depois 17 e depois outro tamanho escolhido pelo grupo.

## 3.4 Implementar as duas funções hash

As duas funções recebem a chave já normalizada e devolvem um índice entre `0` e `M - 1`.

### Função 1 — soma dos códigos dos caracteres

É a função pedida no enunciado e mostrada no material de apoio:

1. começar `soma = 0`;
2. percorrer cada caractere do nome;
3. somar o código do caractere;
4. devolver `soma % M`.

Pseudocódigo:

```text
soma = 0
para cada caractere da chave:
    soma = soma + código_do_caractere
índice = soma % M
```

Em Java, `charAt(i)` acessa um caractere e o converte para seu código numérico.

### Função 2 — uma função criada pelo grupo

Uma alternativa simples é uma soma ponderada: cada caractere contribui de acordo com sua posição.

```text
valor = 0
para i de 0 até tamanho da chave - 1:
    valor = valor + código(chave[i]) * (i + 1)
índice = valor % M
```

Ela é diferente da soma simples e é fácil de explicar. O grupo pode escolher outra, mas precisa documentar a fórmula e comparar os resultados.

**Importante:** usem exatamente a mesma função na inserção e na busca. Se calcularem índices de formas diferentes, a busca não encontrará o registro.

## 3.5 Inserir um pet

Passos:
1. normalizar o nome;
2. calcular o índice com a função escolhida;
3. criar um novo nó;
4. ligar o novo nó ao início da lista que já estava no balde;
5. atualizar o início do balde;
6. aumentar a quantidade.

A inserção no início é simples:
- `novo.proximo` recebe o antigo primeiro nó;
- `tabela[indice]` passa a apontar para `novo`.

Antes de inserir, validem que o microchip ainda não está cadastrado. O enunciado exige microchips únicos. Para isso, podem percorrer os pets já guardados e procurar pelo número. Não confundam microchip único com nome único: nomes repetidos são obrigatórios.

## 3.6 Buscar por nome

A busca recebe um nome:
1. normaliza o nome;
2. calcula o índice;
3. começa no primeiro nó daquele balde;
4. percorre a lista usando `while (atual != null)`;
5. compara a chave normalizada de cada pet;
6. mostra todos os pets com aquele nome.

Não parem no primeiro resultado: podem existir várias Lunas.

Se nenhum for encontrado, mostrem uma mensagem clara.

## 3.7 Remover por microchip

A remoção é pelo microchip, não pelo nome. Como o índice da hash é calculado pelo nome, primeiro será necessário localizar o pet.

Uma maneira direta:
1. percorrer os baldes;
2. em cada balde, percorrer sua lista;
3. comparar o microchip;
4. quando encontrar, retirar o nó da lista.

Há três situações que precisam funcionar:
- remover o primeiro nó;
- remover um nó do meio/final;
- microchip não encontrado.

Para remover do meio, mantenham uma referência ao nó anterior. Quando encontrar o alvo, façam o nó anterior apontar para o próximo do alvo. Se o alvo for o primeiro, atualizem o início do balde.

Depois de remover, diminuam `quantidade` uma vez.

## 3.8 Exibir a tabela inteira

Percorram todos os índices de `0` até `M - 1`. Para cada balde:
- imprimam o índice;
- percorram a lista;
- mostrem os pets;
- se estiver vazio, indiquem que está vazio.

A professora quer ver a tabela balde por balde, não apenas a quantidade de pets.

## 3.9 Calcular estatísticas

O enunciado pede quatro estatísticas:

**Fator de carga**

```text
fator de carga = quantidade de pets / M
```

É um valor decimal. Exemplo: 50 pets em 13 baldes → `50 / 13`.

**Balde vazio**

Conte quantas posições do vetor estão com `null`.

**Maior lista**

Percorra cada lista e descubra o maior número de nós em um único balde.

**Número de colisões**

Para manter uma definição clara e consistente, contem quantos elementos excedentes existem nos baldes: para cada balde, `tamanhoDaLista - 1`, quando a lista tiver mais de um elemento. Somem esses excedentes.

Exemplo: listas com tamanhos 3, 2 e 1 geram `(3 - 1) + (2 - 1) = 3` colisões entre chaves que compartilham baldes. Expliquem no relatório qual definição de contagem foi usada.

## 3.10 Comparar M e as funções hash

Façam os testes com:
- M = 13;
- M = 17;
- mais um tamanho escolhido pelo grupo.

Para cada combinação, registrem:
- baldes vazios;
- tamanho da maior lista;
- colisões;
- fator de carga.

Para comparar as duas funções, usem exatamente os mesmos nomes e o mesmo M. Assim a comparação é justa.

### Testes mínimos da tabela

- Inserir dois pets com nome Luna e microchips diferentes.
- Buscar Luna e conferir se aparecem os dois.
- Inserir nomes diferentes que caiam no mesmo balde.
- Exibir a tabela.
- Remover um pet pelo microchip.
- Tentar remover um microchip inexistente.
- Conferir estatísticas.
- Repetir com M 13, M 17 e o terceiro tamanho.

---

# ETAPA 4 — Criar os resultados dos algoritmos

**Arquivo:** `ResultadoOrdenacao.java` (ou classe interna em `Ordenacao.java`).

Cada execução precisa devolver:
- vetor de pets ordenado;
- número de comparações;
- número de trocas ou deslocamentos;
- tempo gasto.

Usem `long` para contadores e tempo, pois os valores podem crescer.

Uma classe de resultado pode guardar esses valores. O tempo pode ser calculado fora do algoritmo com `System.nanoTime()`.

Não misturem os contadores de execuções diferentes. Cada chamada começa com contadores zerados.

---

# ETAPA 5 — Implementar os três algoritmos

**Arquivo:** `Ordenacao.java`

**Consultar:**
- Bubble: `10-Bubble Sort.pptx` + seção 2 do Material de Apoio.
- Insertion: `9-Insertion Sort.pptx` + seção 3 do Material de Apoio.
- Quick: `11-Quick Sort.pptx` + seção 4 do Material de Apoio.

Os exemplos das aulas usam números. No projeto, a comparação será feita pelo `microchip` do objeto `Pet`.

## 5.1 Preparar uma cópia para cada algoritmo

Não executem os três algoritmos sobre o mesmo vetor em sequência. O primeiro deixaria os dados ordenados e prejudicaria a comparação.

Antes de cada execução, criem uma cópia independente do mesmo conjunto original. Se usarem `ArrayList`, `new ArrayList<>(original)` faz uma cópia da lista; se usarem vetor, `clone()` copia o vetor.

A cópia pode conter referências aos mesmos objetos `Pet`, porque os algoritmos só vão trocar as posições, não alterar os dados internos dos pets.

## 5.2 Bubble Sort

**Ideia:** comparar elementos vizinhos. Se estiverem fora de ordem, trocar. Ao final de cada passagem, o maior valor ainda não organizado vai para o fim.

Passos:
1. laço externo para as passagens;
2. laço interno comparando `j` e `j + 1`;
3. contar uma comparação sempre que comparar dois microchips;
4. se o da esquerda for maior, fazer a troca e contar uma troca;
5. reduzir o limite do laço interno a cada passagem, porque o final já está organizado.

Pseudocódigo:

```text
para i de 0 até n - 2:
    para j de 0 até n - 2 - i:
        comparações++
        se vetor[j].microchip > vetor[j+1].microchip:
            trocar vetor[j] com vetor[j+1]
            trocas++
```

O material de apoio também mostra uma otimização: se uma passagem termina sem nenhuma troca, o vetor já está ordenado e o algoritmo pode parar. É uma otimização simples e compatível com o algoritmo.

## 5.3 Insertion Sort

**Ideia:** considerar a parte esquerda do vetor como ordenada e inserir nela o próximo elemento.

Passos:
1. começar no índice 1;
2. guardar o pet atual em uma variável `chave`;
3. olhar para os elementos anteriores;
4. enquanto o anterior tiver microchip maior que a chave, deslocá-lo uma posição para a direita;
5. colocar a chave na posição que sobrou.

Pseudocódigo:

```text
para i de 1 até n - 1:
    chave = vetor[i]
    j = i - 1

    enquanto j >= 0:
        comparações++
        se vetor[j].microchip > chave.microchip:
            vetor[j + 1] = vetor[j]
            deslocamentos++
            j--
        senão:
            parar o enquanto

    vetor[j + 1] = chave
```

**Contagem:** contem uma comparação sempre que dois microchips são comparados. O teste `j >= 0` sozinho não é comparação entre pets. Contem como deslocamento cada elemento movido uma posição para a direita. Não chamem deslocamento de troca: o Insertion Sort normalmente trabalha deslocando elementos.

## 5.4 Quick Sort

**Ideia:** dividir e conquistar. Escolher um pivô, colocar os menores de um lado e os maiores do outro, e repetir nas partições.

O slide usa o último elemento como pivô. Para acompanhar o material:
1. escolham o último elemento do trecho como pivô;
2. percorram os elementos do trecho;
3. comparem cada microchip com o pivô;
4. mantenham uma posição para o próximo elemento menor;
5. quando encontrarem um menor, troquem para a região da esquerda;
6. no final, coloquem o pivô na posição correta;
7. repitam para as regiões à esquerda e à direita do pivô.

A função de partição deve devolver a posição final do pivô. Na versão recursiva, o Quick Sort chamaria a ordenação para as duas partes que restaram.

**Cuidado com 10.000 pets:** como o último elemento é o pivô, dados já ordenados ou em ordem inversa podem gerar partições muito desequilibradas. Além de ficar lento, a versão recursiva pode atingir profundidade demais e causar `StackOverflowError`. Para evitar esse problema sem trocar a lógica ensinada nos slides, vocês podem implementar o mesmo processo de partição usando uma pilha explícita de índices (`int[]`): guardem os limites `inicio` e `fim` de cada trecho pendente, retirem um trecho da pilha, particionem e adicionem os subtrechos que ainda tiverem pelo menos dois elementos. Assim, não dependem da pilha de chamadas do Java. A comparação continua sendo feita com o último elemento do trecho como pivô.

**Contagem:**
- uma comparação cada vez que um microchip é comparado com o pivô;
- uma troca quando duas posições diferentes realmente trocam de lugar;
- contem apenas trocas entre posições diferentes, para não registrar uma troca de um elemento consigo mesmo.

A possível lentidão do Quick Sort nos dados ordenados/invertidos é um comportamento importante para observar e discutir na pergunta 9 do relatório.

## 5.5 Conferir o resultado de cada algoritmo

Depois de ordenar:
- imprimam os microchips em ordem crescente em um teste pequeno;
- confiram que nenhum pet sumiu;
- confiram que nenhum pet apareceu duplicado por erro de troca;
- comparem o primeiro e o último microchip.

Não usem uma função de ordenação pronta para validar o resultado. Façam uma verificação simples: percorram o vetor e confirmem que cada microchip é menor ou igual ao próximo.

---

# ETAPA 6 — Modo passo a passo

**Arquivo:** pode ficar em `Ordenacao.java`, com uma opção que ativa a narração.

**Consultar:** seção de ordenação do enunciado e os slides do algoritmo escolhido.

O modo passo a passo deve trabalhar com até 10 pets. A cada comparação/troca relevante, mostrem uma mensagem coerente com o cenário escolhido.

Exemplo de estilo:
```text
Bubble Sort — passagem 1
Luna (60218841) > Thor (31057720)? SIM — trocam de lugar!
Mel (22091187) > Bob (39021746)? NÃO — continuam nas posições.
Fim da passagem 1 — comparações: 4 | trocas: 2
```

Como fazer sem duplicar todo o algoritmo:
1. recebam um parâmetro `passoAPasso` (boolean);
2. nos pontos em que ocorre comparação/troca, verifiquem esse parâmetro;
3. se estiver ativado, imprimam a mensagem;
4. se estiver desativado, não imprimam cada operação.

O modo narrado é só para até 10 pets. Para 50, 1.000 e 10.000, deixem a saída resumida, senão o console ficará enorme e a medição será prejudicada.

---

# ETAPA 7 — Gerar pets para a Grande Corrida

**Arquivo:** `GeradorPets.java`

**Consultar:** enunciado, seção 3.

O programa precisa gerar 1.000 e 10.000 pets aleatórios, combinando nomes, espécies e manias, com microchips únicos.

Uma implementação simples:
1. criar pequenos arrays de nomes, espécies e manias;
2. usar `Random` para escolher um item de cada array;
3. gerar microchips únicos;
4. criar os objetos `Pet`;
5. devolver a coleção.

Para garantir unicidade sem complicar:
- gerar uma sequência de 10.000 números de 8 dígitos sem repetição;
- embaralhar a ordem desses números com Fisher-Yates;
- associar um número a cada pet gerado.

Fisher-Yates é uma troca simples de posições em um vetor. Não é um algoritmo de ordenação e não substitui os sorts do trabalho.

Nomes repetidos devem continuar aparecendo. O objetivo é gerar colisões e testar a tabela.

---

# ETAPA 8 — Montar a Grande Corrida

**Arquivo:** `GrandeCorrida.java`

**Consultar:** enunciado, Parte 3.

A corrida precisa testar cada algoritmo em três quantidades e três situações.

### Preparar os três tipos de entrada

Para cada quantidade (50, 1.000 e 10.000), criem uma base de pets com microchips únicos.

A partir da mesma base, montem:
- **Aleatório:** ordem embaralhada;
- **Já ordenado:** microchips em ordem crescente;
- **Ordem inversa:** microchips em ordem decrescente.

Não usem `Arrays.sort()` para montar o vetor crescente. Podem gerar uma sequência crescente de microchips e depois embaralhar uma cópia para o cenário aleatório. A ordem inversa pode ser construída percorrendo a sequência crescente de trás para frente.

### Executar cada algoritmo

Para cada combinação:
1. preparar uma cópia da entrada;
2. iniciar o cronômetro com `System.nanoTime()`;
3. executar o algoritmo;
4. parar o cronômetro;
5. guardar o tempo, comparações e trocas/deslocamentos;
6. confirmar que a saída ficou ordenada.

Cálculo:

```java
long inicio = System.nanoTime();

// executar o algoritmo

long fim = System.nanoTime();
long tempoGasto = fim - inicio;
```

O tempo fica em nanossegundos. Se quiserem mostrar milissegundos, dividam por `1_000_000.0`, mas guardem a unidade no cabeçalho da tabela.

**Não incluam impressão passo a passo dentro da medição.** A saída no console pode deixar o tempo muito maior e injusto.

### Quantas execuções?

O enunciado pede os resultados dos três algoritmos, três situações e três quantidades. Isso gera 27 resultados por métrica.

Guardem:
- tabela de tempo;
- tabela de comparações;
- tabela de trocas/deslocamentos.

Se houver muita variação de tempo entre execuções, podem repetir a medição e usar uma média, mas não compliquem antes de ter uma primeira versão correta. O requisito mínimo é medir e registrar os resultados pedidos.

---

# ETAPA 9 — Criar o menu

**Arquivo:** `Main.java`

**Consultar:** enunciado, Parte 4.

O menu é a entrada para as funções. Usem `Scanner` para ler a opção.

Estrutura sugerida:

```text
1 - Cadastrar pet
2 - Buscar pets por nome
3 - Remover pet por microchip
4 - Exibir tabela hash
5 - Exibir estatísticas
6 - Ordenar pets
7 - Modo passo a passo
8 - A Grande Corrida
0 - Sair
```

Como implementar:
1. carregar os pets do arquivo quando o programa iniciar;
2. inserir os pets carregados na tabela hash;
3. mostrar o menu dentro de um `while`;
4. ler a opção;
5. usar `switch` para chamar o método correto;
6. encerrar quando a pessoa escolher 0.

Evitem colocar toda a lógica dentro do `main`. O `main` deve coordenar as classes, não conter o algoritmo inteiro.

### Atenção ao Scanner

Se usarem `nextInt()` e depois `nextLine()`, pode sobrar uma quebra de linha e a leitura de texto parecer ser pulada. Uma forma simples é consumir a quebra de linha depois de ler o número, ou ler tudo com `nextLine()` e converter quando necessário.

---

# ETAPA 10 — Integração e testes

Façam a integração por etapas. Não esperem todas as partes ficarem prontas para testar pela primeira vez.

## Testes obrigatórios da tabela
- [ ] Inserção de pet.
- [ ] Nomes repetidos no mesmo balde.
- [ ] Colisão entre nomes diferentes.
- [ ] Busca que mostra todos os pets com o nome.
- [ ] Busca de nome inexistente.
- [ ] Remoção do primeiro nó.
- [ ] Remoção de nó no meio/fim.
- [ ] Remoção de microchip inexistente.
- [ ] Exibição balde por balde.
- [ ] Fator de carga.
- [ ] Baldes vazios.
- [ ] Maior lista.
- [ ] Número de colisões.
- [ ] Testes M = 13, M = 17 e terceiro tamanho.
- [ ] Comparação das duas funções hash.

## Testes obrigatórios da ordenação
- [ ] Bubble Sort crescente.
- [ ] Insertion Sort crescente.
- [ ] Quick Sort crescente.
- [ ] Comparações contabilizadas.
- [ ] Trocas contabilizadas.
- [ ] Deslocamentos do Insertion contabilizados.
- [ ] Cada algoritmo recebe cópia dos mesmos dados.
- [ ] Modo passo a passo com até 10 pets.

## Testes da Grande Corrida
- [ ] 50 pets nas três situações.
- [ ] 1.000 pets nas três situações.
- [ ] 10.000 pets nas três situações.
- [ ] Três algoritmos executados.
- [ ] Tempo medido com `System.nanoTime()`.
- [ ] Tabelas de tempo, comparações e trocas/deslocamentos preenchidas.

## Testes do menu
- [ ] Todas as opções chamam a função certa.
- [ ] Opções inválidas não encerram o programa.
- [ ] Sair encerra corretamente.

---

# ETAPA 11 — Relatório e apresentação

O relatório tem de 3 a 6 páginas. Não escrevam respostas genéricas: a professora pede que usem os números que o programa de vocês mostrou.

Respondam às nove perguntas do enunciado:
1. Por que todas as Lunas caem no mesmo balde, qualquer que seja M? Isso é colisão?
2. Exemplo real de colisão entre nomes diferentes.
3. Qual M gerou menos colisões? Por que tamanhos primos costumam funcionar melhor?
4. Qual função hash espalhou melhor os nomes? Como comprovaram?
5. O que acontece com o fator de carga com 10.000 pets e M = 17? Como afeta a busca? O que fariam?
6. Por que ordenar por microchip e não por nome?
7. Qual algoritmo venceu em cada situação? Os resultados combinam com O(n²) e O(n log n)?
8. Como o Insertion Sort se comportou com dados já ordenados? Por quê?
9. Aconteceu algo estranho com o Quick Sort? O que e por quê?

A apresentação dura de 5 a 8 minutos e deve mostrar o programa funcionando. Todos precisam conseguir explicar qualquer parte.

---

# O que NÃO fazer agora

- Não começar pela tela/menu antes de testar as estruturas.
- Não tentar implementar hash, sorts e corrida tudo no mesmo arquivo.
- Não usar biblioteca pronta para fazer a tabela ou ordenar.
- Não gastar tempo com interface gráfica.
- Não escrever o relatório antes de ter os resultados medidos.
- Não deixar uma pessoa como única que entende uma parte.

**Próximo passo imediato:** concluam o `01-AMBIENTE.md`, depois façam juntos a classe `Pet.java` e a leitura dos dados. Em seguida, comecem a tabela hash.
