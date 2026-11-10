## Material de Apoio

## Tabela Hash e Algoritmos de Ordenação em Java

Resolução de Problemas Estruturados em Computação · Profa. Lisiane Reips

## Leia antes de começar

Estes quatro programas são propositalmente simples. Eles mostram só o coração de cada estrutura, com números inteiros e frutas, para vocês entenderem a lógica linha por linha.

Eles não são a solução do projeto final. Para o projeto, vocês ainda vão precisar pensar em como guardar personagens (e não só números), como contar comparações e trocas, como remover da tabela hash, como medir o tempo e muito mais. As perguntas “Para pensar” no fim de cada seção são pistas desse caminho.

Sugestão: copiem cada programa, rodem, mudem os valores e observem o que acontece.

Coloquem System.out.println dentro dos laços para ver o algoritmo trabalhando.

## 1. Tabela hash com encadeamento

Uma tabela hash guarda dados num vetor, mas em vez de procurar posição por posição, ela calcula onde cada elemento deve ficar, usando uma função hash aplicada à chave.

Quando duas chaves diferentes caem na mesma posição, temos uma colisão. Aqui, cada posição guarda uma lista ligada: quem colide simplesmente entra na lista (isso se chama encadeamento).

## Código: TabelaHashSimples.java

```
/*
* EXEMPLO DIDÁTICO: Tabela Hash com encadeamento (chaining)
* ---------------------------------------------------------
* Ideia: em vez de procurar um elemento percorrendo tudo, calculamos
* um "endereço" (índice) a partir da própria chave, usando uma função hash.
*
* Quando duas chaves diferentes caem no mesmo índice, temos uma COLISÃO.
* Aqui tratamos colisões com ENCADEAMENTO: cada posição do vetor guarda
* uma pequena lista ligada com todos os elementos que caíram ali.
*
* Neste exemplo guardamos frutas (chave) e seus preços (valor).
*/
public class TabelaHashSimples {
// Cada "nó" da lista ligada guarda um par chave/valor
// e aponta para o próximo nó da mesma posição.
static class No {
String chave;
double valor;
No proximo;
No(String chave, double valor) {
this.chave = chave;
this.valor = valor;
```


```
}
}
private No[] tabela; // vetor de listas (os "baldes" ou buckets)
private int tamanho; // quantidade de posições do vetor (M)
public TabelaHashSimples(int tamanho) {
this.tamanho = tamanho;
this.tabela = new No[tamanho]; // todas as posições começam vazias (null)
}
// FUNÇÃO HASH: transforma a chave em um índice entre 0 e tamanho-1.
// Esta é a mais simples possível: soma os códigos dos caracteres
// e pega o resto da divisão pelo tamanho da tabela.
private int hash(String chave) {
int soma = 0;
for (int i = 0; i < chave.length(); i++) {
soma += chave.charAt(i); // charAt devolve o código do caractere
}
return soma % tamanho;
}
// INSERIR: calcula o índice e coloca o novo nó no início da lista.
public void inserir(String chave, double valor) {
int indice = hash(chave);
No novo = new No(chave, valor);
novo.proximo = tabela[indice]; // o novo aponta para quem já estava lá
tabela[indice] = novo; // e passa a ser o primeiro da lista
}
// BUSCAR: vai direto ao índice e percorre só aquela lista.
public Double buscar(String chave) {
int indice = hash(chave);
No atual = tabela[indice];
while (atual != null) {
if (atual.chave.equals(chave)) {
return atual.valor; // encontrou!
}
atual = atual.proximo;
}
return null; // não está na tabela
}
// EXIBIR: mostra cada posição e a lista que está nela.
public void exibir() {
for (int i = 0; i < tamanho; i++) {
System.out.print("[" + i + "] ");
No atual = tabela[i];
while (atual != null) {
System.out.print(atual.chave + " -> ");
atual = atual.proximo;
}
System.out.println("null");
}
}
public static void main(String[] args) {
TabelaHashSimples t = new TabelaHashSimples(5); // tabela pequena: mais colisões
t.inserir("banana", 4.50);
t.inserir("uva", 9.90);
t.inserir("manga", 6.00);
t.inserir("kiwi", 12.00);
t.inserir("pera", 7.25);
```


```
t.inserir("caju", 8.00);
t.exibir();
System.out.println("Preço da manga: " + t.buscar("manga"));
System.out.println("Preço do abacaxi: " + t.buscar("abacaxi"));
}
}
```

## Saída esperada

```
[0] null
[1] kiwi -> manga -> null
[2] uva -> null
[3] null
[4] caju -> pera -> banana -> null
Preço da manga: 6.0
Preço do abacaxi: null
```

## Para pensar 🤔

- 1. Por que kiwi e manga caíram na mesma posição? Façam a conta da função hash à mão.

- 2. Testem com tamanho 7 e com tamanho 10. O que muda nas colisões?

- 3. Este exemplo não tem remoção. Como vocês removeriam um nó do meio de uma lista ligada?

- 4. E se a mesma chave for inserida duas vezes? O que este código faz? É o que vocês querem no projeto?


## 2. Bubble Sort

O Bubble Sort compara pares vizinhos e troca quando estão fora de ordem. A cada passagem completa, o maior valor ainda fora do lugar “flutua” até o final do vetor, como uma bolha.

É o mesmo exemplo dos slides: o vetor {7, 2, 12, 8, 3}.

## Código: BubbleSortSimples.java

```
/*
* EXEMPLO DIDÁTICO: Bubble Sort (ordenação por flutuação)
* -------------------------------------------------------
* Ideia: percorrer o vetor comparando VIZINHOS (posições j e j+1).
* Se estiverem fora de ordem, trocamos. Ao fim de cada passagem,
* o maior valor ainda não ordenado "flutua" até o final.
*/
public class BubbleSortSimples {
public static void bubbleSort(int[] v) {
int n = v.length;
// Laço externo: cada passagem coloca 1 elemento no lugar certo,
// por isso precisamos de no máximo n-1 passagens.
for (int i = 0; i < n - 1; i++) {
// Laço interno: compara os pares vizinhos.
// "n - 1 - i" porque os últimos i elementos já estão no lugar.
for (int j = 0; j < n - 1 - i; j++) {
if (v[j] > v[j + 1]) {
// TROCA (swap) usando uma variável auxiliar
int aux = v[j];
v[j] = v[j + 1];
v[j + 1] = aux;
}
}
}
}
// Função auxiliar só para mostrar o vetor na tela
public static void imprimir(int[] v) {
for (int x : v) System.out.print(x + " ");
System.out.println();
}
public static void main(String[] args) {
int[] numeros = {7, 2, 12, 8, 3}; // mesmo exemplo dos slides
System.out.print("Antes: ");
imprimir(numeros);
bubbleSort(numeros);
System.out.print("Depois: ");
imprimir(numeros);
}
}
```


## Saída esperada

Antes:

7 2 12 8 3

Depois: 2 3 7 8 12

## Para pensar 🤔

- 1. Onde vocês colocariam contadores de comparações e de trocas?

- 2. Se uma passagem inteira terminar sem nenhuma troca, o que isso significa? Como aproveitar isso?

- 3. Como adaptar o código para ordenar objetos (por exemplo, pelo atributo matrícula) em vez de int?


## 3. Insertion Sort

O Insertion Sort imagina o vetor dividido em duas partes: uma já ordenada (à esquerda) e outra com os elementos que faltam. A cada passo, pegamos o próximo elemento (a chave) e o deslizamos para a esquerda até encontrar o lugar certo.

Repare que, em vez de várias trocas, o código desloca os maiores uma casa para a direita e só no final coloca a chave no lugar.

## Código: InsertionSortSimples.java

```
/*
* EXEMPLO DIDÁTICO: Insertion Sort (ordenação por inserção)
* ---------------------------------------------------------
* Ideia: imaginar o vetor dividido em duas partes:
* - à esquerda, uma parte JÁ ORDENADA (começa só com v[0]);
* - à direita, os elementos que ainda vamos "inserir".
* Pegamos o próximo elemento (a "chave") e o deslizamos para a
* esquerda até encontrar sua posição correta na parte ordenada.
*/
public class InsertionSortSimples {
public static void insertionSort(int[] v) {
// Começamos em 1, pois a posição 0 sozinha já está "ordenada".
for (int i = 1; i < v.length; i++) {
int chave = v[i]; // elemento que vamos inserir
int j = i - 1; // último índice da parte ordenada
// Enquanto houver elemento à esquerda MAIOR que a chave,
// empurramos esse elemento uma casa para a direita.
while (j >= 0 && v[j] > chave) {
v[j + 1] = v[j];
j--;
}
// Quando o laço para, j+1 é o lugar certo da chave.
v[j + 1] = chave;
}
}
public static void imprimir(int[] v) {
for (int x : v) System.out.print(x + " ");
System.out.println();
}
public static void main(String[] args) {
int[] numeros = {7, 2, 12, 8, 3}; // mesmo exemplo dos slides
System.out.print("Antes: ");
imprimir(numeros);
insertionSort(numeros);
System.out.print("Depois: ");
imprimir(numeros);
}
}
```


## Saída esperada

Antes:

7 2 12 8 3

Depois: 2 3 7 8 12

## Para pensar 🤔

- 1. Quantas vezes o while roda se o vetor já estiver ordenado? E se estiver em ordem inversa?

- 2. Por que o while precisa testar j >= 0 antes de v[j] > chave? O que aconteceria se a ordem fosse invertida?

- 3. No projeto, o que vocês vão contar como “troca” no Insertion Sort?


## 4. Quick Sort

O Quick Sort usa divisão e conquista: escolhe um pivot (aqui, o último elemento), leva os menores para a esquerda e os maiores para a direita, coloca o pivot na sua posição definitiva e repete o processo, recursivamente, em cada lado.

É o mesmo exemplo dos slides: o vetor {7, 1, 3, 5, 2, 6, 4}, com pivot 4 na primeira partição.

## Código: QuickSortSimples.java

```
/*
* EXEMPLO DIDÁTICO: Quick Sort (divisão e conquista)
* --------------------------------------------------
* Ideia:
* 1. Escolher um PIVOT (aqui: o último elemento).
* 2. PARTICIONAR: levar os menores que o pivot para a esquerda,
* os maiores para a direita, e colocar o pivot no meio,
* na sua posição definitiva.
* 3. Repetir (recursão) para a parte da esquerda e para a da direita.
*/
public class QuickSortSimples {
public static void quickSort(int[] v, int inicio, int fim) {
// Caso base: com 0 ou 1 elemento não há nada a ordenar.
if (inicio < fim) {
int posPivot = particionar(v, inicio, fim);
quickSort(v, inicio, posPivot - 1); // ordena os menores
quickSort(v, posPivot + 1, fim); // ordena os maiores
}
}
// Organiza o trecho v[inicio..fim] em torno do pivot
// e devolve a posição final do pivot.
public static int particionar(int[] v, int inicio, int fim) {
int pivot = v[fim]; // pivot = último elemento
int i = inicio - 1; // fim da "partição dos menores"
for (int j = inicio; j < fim; j++) {
if (v[j] < pivot) {
i++; // abre espaço na partição dos menores
trocar(v, i, j); // e leva v[j] para lá
}
}
// A próxima posição disponível (i+1) é o lugar certo do pivot.
trocar(v, i + 1, fim);
return i + 1;
}
public static void trocar(int[] v, int a, int b) {
int aux = v[a];
v[a] = v[b];
v[b] = aux;
}
public static void imprimir(int[] v) {
for (int x : v) System.out.print(x + " ");
System.out.println();
}
```


```
public static void main(String[] args) {
int[] numeros = {7, 1, 3, 5, 2, 6, 4}; // mesmo exemplo dos slides
System.out.print("Antes: ");
imprimir(numeros);
quickSort(numeros, 0, numeros.length - 1);
System.out.print("Depois: ");
imprimir(numeros);
}
}
```

## Saída esperada

Antes:

Depois: 1 2 3 4 5 6 7

7 1 3 5 2 6 4

## Para pensar 🤔

- 1. Coloquem um println dentro de particionar e comparem a saída com os slides, passo a passo.

- 2. O que acontece com as partições se o vetor já estiver ordenado e o pivot for sempre o último elemento?

- 3. Quantas chamadas recursivas “empilhadas” isso gera com 10.000 elementos? O Java aguenta?

- 4. Que outras escolhas de pivot vocês conhecem? Por que elas ajudam?

## Como compilar e rodar

No terminal, dentro da pasta dos arquivos:

```
javac BubbleSortSimples.java
java BubbleSortSimples
```

Ou criem um projeto na IDE de vocês (Eclipse, IntelliJ, NetBeans, VS Code) e colem cada classe num arquivo com o mesmo nome da classe.
