# Divisão do grupo — 4 integrantes

> A divisão organiza quem começa cada parte. Ela não significa que cada pessoa só vai aprender aquilo. O enunciado exige que todos saibam explicar e mexer em qualquer parte do código.

## 1. Como dividir sem deixar alguém sabendo só uma coisa

Cada integrante assume uma responsabilidade inicial, mas o trabalho terá duas rodadas de troca:

1. **Construção:** cada pessoa começa sua parte e explica o raciocínio aos outros.
2. **Troca:** cada integrante pega o código de outra pessoa, faz uma pequena alteração e explica como funciona.
3. **Revisão geral:** os quatro percorrem o projeto e simulam perguntas da professora.

Assim o tempo não é desperdiçado com quatro pessoas implementando exatamente a mesma coisa desde o início, mas ninguém fica preso a um único assunto.

## 2. Responsabilidades iniciais

| Integrante | Responsabilidade inicial | O que deve entregar primeiro |
|---|---|---|
| Integrante 1 | Modelo dos pets + arquivo de dados + leitura | `Pet.java`, `ArquivoPets.java` e `pets.txt` com pelo menos 50 registros. |
| Integrante 2 | Tabela hash — estrutura e operações | `TabelaHashPets.java`: nó, vetor, funções hash, inserir, buscar e remover. |
| Integrante 3 | Tabela hash — exibição, estatísticas e testes | Completar/revisar `TabelaHashPets.java`: exibir baldes, estatísticas e comparação de M/funções. |
| Integrante 4 | Algoritmos de ordenação | `Ordenacao.java` e `ResultadoOrdenacao.java`: três sorts, contadores e modo passo a passo. |

A tabela hash foi dividida entre duas pessoas porque é uma parte central e tem bastante requisito. A pessoa 3 deve trabalhar junto com a pessoa 2 na mesma classe, combinando antes quem edita cada trecho para evitar conflito.

## 3. Segunda rodada: todos passam por outra parte

Quando as primeiras versões estiverem funcionando, façam esta troca:

| Quem começou em... | Deve estudar e alterar também... |
|---|---|
| Integrante 1 | Uma função de ordenação e sua contagem. |
| Integrante 2 | Gerador de pets e preparação dos cenários da Grande Corrida. |
| Integrante 3 | Leitura do arquivo e integração do cadastro/remoção com o menu. |
| Integrante 4 | Funções hash e estatísticas da tabela. |

Cada pessoa deve fazer uma alteração real, por menor que seja, e explicar o trecho aos demais. Só ler o código não conta como prática.

## 4. Partes que serão feitas em conjunto

Depois das partes individuais:

- **Gerador e Grande Corrida:** integrantes 1 e 4 começam; os outros dois revisam os cenários e a medição.
- **Menu e integração:** os quatro conferem as opções; uma pessoa integra, mas todos testam.
- **Relatório:** cada integrante prepara as respostas das perguntas relacionadas à parte que estudou; depois todos conferem os resultados.
- **Apresentação:** cada um explica uma parte diferente, mas todos devem conseguir responder perguntas sobre o projeto inteiro.

## 5. Regra para uma entrega ser considerada pronta

A pessoa responsável só marca sua parte como pronta quando:
- compila;
- executa;
- tem pelo menos um teste;
- consegue explicar o que cada método faz;
- outra pessoa do grupo consegue fazer uma alteração simples nela.

## 6. Roteiro rápido de estudo em grupo

Em cada encontro, tentem seguir esta ordem:

1. **10 min:** responsável explica o que vai implementar e qual material da aula está usando.
2. **20–30 min:** implementação em conjunto ou revisão do código.
3. **10 min:** outra pessoa explica o mesmo trecho com as próprias palavras.
4. **5 min:** registrar o que está pronto e o que falta.

Não transformem isso em reunião longa. O objetivo é programar e testar.

## 7. Perguntas que todos precisam conseguir responder

### Sobre os pets e os dados
- Quais são os campos de um pet?
- Por que o microchip não pode se repetir?
- Por que o nome é normalizado antes de virar chave?

### Sobre a tabela hash
- O que é chave, índice, balde e função hash?
- O que é colisão?
- Como o encadeamento resolve colisões?
- Por que a busca pelo nome precisa percorrer a lista do balde?
- Como remover um nó do início e do meio?
- Como calcular fator de carga e maior lista?
- Qual é a diferença entre as duas funções hash?

### Sobre ordenação
- O que Bubble compara?
- Como Insertion insere o elemento na parte ordenada?
- O que é pivô no Quick Sort?
- Qual a diferença entre troca e deslocamento?
- Por que cada algoritmo recebe uma cópia dos mesmos dados?

### Sobre a Grande Corrida
- Como o tempo é medido?
- Por que não narrar cada comparação durante a medição?
- Por que o Quick Sort com último pivô pode piorar em dados ordenados?
- O que os resultados mostram sobre O(n²) e O(n log n)?

Se alguém não souber responder, voltem ao arquivo correspondente e ao material indicado no `00-VISAO-GERAL.md`.
