# Pet Hash & Sort — visão geral do projeto

> Guia rápido para os quatro integrantes. Leiam este arquivo antes de começar a programar.

## 1. O que vamos construir?

Um programa de console em **Java** que organiza pets. Ele precisa juntar três assuntos vistos nas aulas:

1. **Tabela Hash própria:** guarda pets usando o nome como chave e trata colisões com listas ligadas.
2. **Ordenação:** implementa Bubble Sort, Insertion Sort e Quick Sort para ordenar pelo microchip.
3. **Grande Corrida:** compara os três algoritmos com diferentes quantidades e situações de dados.

O programa também precisa ter um menu para acessar as funções.

**Importante:** o trabalho não pede interface gráfica, banco de dados, login, cadastro online ou outras funções extras. Não vamos inventar trabalho.

## 2. Escolha do cenário

O enunciado oferece três cenários. As estruturas e os algoritmos são os mesmos; só muda a história usada nas mensagens do programa.

| Cenário | Como contar a história |
|---|---|
| Clínica veterinária | Pets são pacientes; a hash é o fichário; a ordenação organiza a sala de espera. |
| Creche dos bichos | Pets ficam nos canis; cada balde é um canil; a ordenação organiza a fila da saída. |
| Concurso de fofura | Pets são competidores; a hash guarda inscrições; a ordenação organiza o desfile. |

**Decisão do grupo:** ______________________________

Até essa decisão ser tomada, usem no código nomes neutros, como `Pet`, `TabelaHashPets` e `Ordenacao`. Assim não será necessário reescrever a lógica depois.

## 3. O que o programa precisa fazer

### Dados dos pets
- Começar com os 20 pets do apêndice do enunciado.
- Criar pelo menos mais 30 pets, chegando a no mínimo 50.
- Repetir nomes de propósito (Luna, Thor, Bob etc.).
- Cada pet tem número de 8 dígitos, único, nome, espécie e mania.
- Ler os dados de um arquivo de texto no início do programa.
- Gerar dados aleatórios para 1.000 e 10.000 pets, com números únicos.

### Tabela hash
- Chave: nome em maiúsculas e sem acento.
- Colisões: encadeamento, ou seja, uma lista ligada em cada posição.
- Inserir pet.
- Buscar pelo nome e mostrar todos os pets com aquele nome.
- Remover pelo microchip.
- Exibir todos os baldes.
- Mostrar fator de carga, baldes vazios, maior lista e colisões.
- Testar M = 13, M = 17 e mais um tamanho escolhido pelo grupo.
- Comparar duas funções hash: soma dos códigos dos caracteres e outra criada pelo grupo.

### Ordenação
- Bubble Sort, Insertion Sort e Quick Sort.
- Ordenar pelo microchip (ou inscrição, se escolherem o cenário C).
- Cada algoritmo recebe uma cópia dos mesmos dados.
- Contar comparações e trocas; no Insertion Sort, contar deslocamentos.
- Modo passo a passo com até 10 pets, narrando as operações.

### Grande Corrida
Testar os três algoritmos com:
- 50, 1.000 e 10.000 pets;
- dados aleatórios, já ordenados e em ordem inversa;
- tempo com `System.nanoTime()`;
- comparações e trocas/deslocamentos.

São 27 combinações de algoritmo, situação e quantidade. Guardem os resultados para as tabelas do relatório.

### Menu
O menu deve permitir cadastrar, buscar, remover, mostrar a tabela, exibir estatísticas, ordenar, executar o passo a passo e rodar a Grande Corrida.

## 4. Arquitetura simples sugerida

Não precisamos de frameworks ou banco de dados. Uma organização pequena já resolve:

```text
PetHashSort/
├── dados/
│   └── pets.txt
├── src/
│   └── pethashsort/
│       ├── Main.java
│       ├── Pet.java
│       ├── TabelaHashPets.java
│       ├── Ordenacao.java
│       ├── ResultadoOrdenacao.java
│       ├── GeradorPets.java
│       ├── ArquivoPets.java
│       └── GrandeCorrida.java
└── README.md
```

| Arquivo | Responsabilidade |
|---|---|
| `Pet.java` | Representar um pet e seus quatro dados. |
| `TabelaHashPets.java` | Vetor de baldes, nós encadeados, hash, operações e estatísticas. |
| `Ordenacao.java` | Os três algoritmos e suas contagens. |
| `ResultadoOrdenacao.java` | Guardar comparações, trocas/deslocamentos e tempo. |
| `GeradorPets.java` | Criar pets aleatórios e números únicos. |
| `ArquivoPets.java` | Ler o arquivo inicial de pets. |
| `GrandeCorrida.java` | Preparar os cenários, executar os algoritmos e exibir resultados. |
| `Main.java` | Menu e ligação entre as partes. |

**Não criem classes extras só por organização.** Se o grupo preferir, `ResultadoOrdenacao` pode ser uma classe interna de `Ordenacao`; o importante é manter o código compreensível.

## 5. Onde consultar cada material enviado

| Quando estiver fazendo... | Consultem primeiro | Para quê |
|---|---|---|
| Entender o que é hash, chave, índice e função hash | `8-Tabela_Hash.pdf` | Conceitos e exemplos visuais das aulas. |
| Criar a tabela com lista ligada | `Material de Apoio - Hash e Ordenação em Java.pdf`, seção 1 | Exemplo simples de encadeamento, inserir, buscar e exibir. |
| Entender o código de hash linha por linha | `TabelaHashExplicada.java` | Explicação de Java, nós, funções hash e listas. |
| Entender colisões e encadeamento | `Tabela_Hash_Hotel_Hash.pdf` | Analogia do hotel e implementação comentada. Atenção: o projeto pede encadeamento; não implementem as outras estratégias desse material. |
| Implementar Bubble Sort | `10-Bubble Sort.pptx` e seção 2 do Material de Apoio | Comparação de vizinhos e trocas. |
| Implementar Insertion Sort | `9-Insertion Sort.pptx` e seção 3 do Material de Apoio | Inserção na parte já ordenada e deslocamentos. |
| Implementar Quick Sort | `11-Quick Sort.pptx` e seção 4 do Material de Apoio | Pivô, partição e recursão. |
| Conferir o que é obrigatório | `Projeto Final - Pet Hash Sort Turma A Manhã.pdf` | O enunciado é a fonte principal dos requisitos. |

O material de apoio usa exemplos didáticos (frutas e números). Ele **não é a solução pronta do projeto**: será necessário adaptar para objetos `Pet`, contadores e requisitos do enunciado.

## 6. Regras para não sair do escopo

- Não usar `HashMap`, `Hashtable`, `HashSet` ou outra tabela pronta para substituir a tabela hash construída pelo grupo.
- Não usar `Arrays.sort()` nem `Collections.sort()` para fazer a ordenação.
- Não implementar endereçamento aberto: o enunciado pede encadeamento.
- Não criar interface gráfica.
- Não adicionar cadastro com senha, banco de dados, relatórios automáticos ou outras funções que não foram pedidas.
- Usar arrays e listas ligadas simples onde fizer sentido. A lista ligada da tabela hash deve ser implementada pelo grupo.

## 7. Entregas finais

1. Código-fonte `.java` organizado e comentado, junto com o arquivo de dados.
2. Relatório PDF de 3 a 6 páginas, com cenário, decisões, tabelas da Grande Corrida e respostas às nove perguntas do enunciado.
3. Apresentação de 5 a 8 minutos, com o programa funcionando ao vivo.

A professora também deixou claro que **todos os integrantes precisam saber explicar e mexer em qualquer parte do código**. Por isso, a divisão do trabalho é só um ponto de partida, não uma separação definitiva de conhecimento.

## 8. Ordem recomendada de desenvolvimento

1. Preparar o ambiente e criar a estrutura de pastas.
2. Criar a classe `Pet` e o arquivo com os dados.
3. Implementar e testar a tabela hash.
4. Implementar e testar os três algoritmos de ordenação.
5. Criar o modo passo a passo.
6. Criar o gerador e a Grande Corrida.
7. Integrar tudo no menu.
8. Fazer os testes finais, relatório e apresentação.

Não tentem fazer tudo de uma vez. Primeiro deixem o projeto compilando e executem uma parte pequena por vez.
