# Instruções para desenvolvimento — Pet Hash & Sort
## Cenário A: Clínica Veterinária

> **Objetivo deste documento:** orientar a IA da IDE na implementação da atividade final Pet Hash & Sort, usando o código-base e o guia de desenvolvimento que já estão disponíveis no projeto.

---

## 1. Antes de alterar qualquer código: analisar os materiais existentes

Antes de implementar, faça uma análise do projeto e dos materiais fornecidos. Esta etapa é obrigatória.

1. Localize e leia integralmente:
   - o **código-base** disponibilizado para a atividade;
   - o **guia de desenvolvimento** disponibilizado pelo professor;
   - os arquivos Java, arquivos de dados e demais materiais já existentes no projeto.
2. Identifique a estrutura atual: classes, responsabilidades, métodos implementados, convenções de nomes, estilo de código e funcionalidades que já estão prontas.
3. Compare o código-base e o guia com os requisitos deste documento.
4. Aproveite e complete o que já existe. **Não substitua o projeto inteiro por uma implementação nova** e não remova trechos funcionais sem necessidade.
5. Siga prioritariamente os conceitos, técnicas e padrões apresentados pelo professor no código-base e no guia. Não introduza bibliotecas, estruturas prontas ou técnicas avançadas que não sejam necessárias ou que contrariem o material da disciplina.
6. Se houver conflito entre este documento e uma orientação explícita do professor presente no guia, respeite o material do professor e registre a divergência para análise.
7. Antes de começar a editar, apresente um resumo curto do que encontrou e um plano de implementação organizado por etapas.

Não presuma que uma classe, método ou funcionalidade esteja ausente antes de verificar o projeto.

---

## 2. Contexto da atividade e cenário escolhido

O projeto deve ser um programa de console em **Java**, chamado **Pet Hash & Sort**, ambientado em uma **clínica veterinária**.

A clínica precisa organizar os cadastros dos pacientes e a fila da sala de espera:

- **Fichário da recepção:** representa a tabela hash.
- **Chave da tabela hash:** nome do pet, convertido para maiúsculas e sem acentos.
- **Pets com o mesmo nome:** possuem a mesma chave e devem ser armazenados no mesmo balde.
- **Colisões entre nomes diferentes:** devem ser resolvidas por encadeamento, usando uma lista ligada em cada balde.
- **Fila da sala de espera:** vetor de pets que será ordenado pelo número do microchip.
- **Troca de cadeira:** representa uma troca (`swap`) realizada por um algoritmo de ordenação.

Atenção: pets com nomes iguais não devem ser tratados como cadastros duplicados. Por exemplo, podem existir várias Lunas, cada uma com microchip, espécie e mania próprios.

---

## 3. Regras e restrições obrigatórias

- Linguagem: Java.
- Interface: console/terminal.
- A tabela hash deve ser implementada pelo grupo.
- Não utilizar `HashMap`, `Hashtable`, `HashSet` ou outra estrutura pronta do Java para substituir a tabela hash solicitada.
- Não utilizar `Arrays.sort`, `Collections.sort` ou qualquer método de ordenação pronta.
- As colisões da tabela hash devem ser tratadas por **encadeamento** (lista ligada em cada posição).
- Os três algoritmos devem ser implementados manualmente: Bubble Sort, Insertion Sort e Quick Sort.
- Não usar bibliotecas externas para realizar as estruturas ou algoritmos centrais da atividade.
- Não alterar os dados de origem durante os testes de ordenação: cada algoritmo deve receber sua própria cópia dos mesmos dados.
- Manter o código organizado, legível e comentado de forma útil.
- Não adicionar funcionalidades que desviem dos objetivos avaliados.

Se o código-base já utilizar alguma estrutura auxiliar, verifique se ela é compatível com as restrições antes de mantê-la.

---

## 4. Modelo dos dados

Cada paciente deve possuir, no mínimo, os seguintes atributos:

| Atributo | Regra |
|---|---|
| Microchip | Número inteiro de 8 dígitos, único para cada pet |
| Nome | Nome do animal; pode se repetir |
| Espécie | Espécie do animal |
| Mania | Característica ou comportamento engraçado do animal |

O microchip é o identificador individual do paciente e o campo usado para ordenar a fila. O nome é a chave da tabela hash.

### 4.1. Normalização do nome

Para calcular a posição na tabela hash, normalize o nome:

1. Converter para maiúsculas.
2. Remover acentos.
3. Usar o nome normalizado como chave.

Exemplos:

- `Luna` → `LUNA`
- `Paçoca` → `PACOCA`
- `Thor` → `THOR`

A normalização deve ser aplicada de maneira consistente na inserção e na busca. Não é necessário modificar a forma original do nome exibida ao usuário.

### 4.2. Dados iniciais obrigatórios

Utilize os 20 registros fornecidos no apêndice da atividade como ponto de partida. Preserve os microchips, nomes, espécies e manias:

| Microchip | Nome | Espécie | Mania |
|---|---|---|---|
| 60218841 | Luna | gata | dorme em cima do teclado |
| 31057720 | Thor | cão | late para o aspirador |
| 48812903 | Pipoca | cão | pula quando ouve a palavra “passeio” |
| 22091187 | Mel | gata | derruba copos da mesa, um por um |
| 70334512 | Zeus | cão | tem medo do gato do vizinho |
| 51200468 | Amora | gata | mia para a geladeira |
| 13870255 | Luna | cachorra | rouba meias |
| 39021746 | Bob | cão | come a ração do irmão |
| 84460013 | Bob | gato | dorme 20 horas por dia |
| 27719384 | Lola | cachorra | adora tomar banho |
| 65502291 | Paçoca | gato | caça o ponteiro laser |
| 44183067 | Frida | cachorra | usa laço e se acha |
| 90027758 | Simba | gato | acha que é leão |
| 18345602 | Nina | coelha | rói o fio do carregador |
| 76690431 | Pandora | gata | abre portas sozinha |
| 33208179 | Chico | papagaio | imita o toque do celular |
| 57741820 | Thor | gato | tem nome de cão, mas é gato |
| 81196354 | Fred | hamster | corre a noite inteira na rodinha |
| 29573006 | Luna | calopsita | assobia quando alguém chega |
| 62015547 | Toddy | cão | só come se for na mão |

Além desses 20, criar **pelo menos 30 pets novos**, totalizando no mínimo **50 registros**. Os nomes repetidos devem ser intencionais para permitir observar o comportamento da tabela hash.

Regras dos dados:
- Todos os microchips devem ter exatamente 8 dígitos.
- Nenhum microchip pode se repetir.
- Cada registro deve ter nome, espécie e mania preenchidos.
- Não remover os registros do apêndice.
- Os dados podem ficar em arquivo de texto, preferencialmente seguindo o formato `microchip;nome;especie;mania`, conforme a orientação da atividade.
- Se o projeto já possuir um formato de arquivo ou mecanismo de leitura, aproveite-o quando atender aos requisitos.

---

## 5. Parte 1 — Implementar a tabela hash

### 5.1. Estrutura

Criar ou completar uma tabela hash própria, com:

- Um vetor de baldes.
- Uma lista ligada independente em cada posição para armazenar os registros que caírem naquele balde.
- Chave baseada no nome normalizado do pet.
- Capacidade configurável, para permitir testar diferentes valores de `M`.

Não usar uma coleção pronta do Java para substituir o vetor de baldes ou as listas ligadas exigidas. Se necessário, implementar os nós da lista ligada com uma classe própria.

### 5.2. Operações obrigatórias

Implementar e testar:

1. **Inserir pet:** calcular o índice pela função hash e inserir o registro no balde correspondente.
2. **Buscar por nome:** receber um nome, normalizá-lo e exibir todos os pets com aquele nome. Não retornar apenas o primeiro.
3. **Remover por microchip:** localizar e remover somente o registro cujo microchip corresponda ao informado. Não remover todos os pets com o mesmo nome.
4. **Exibir tabela inteira:** mostrar todos os baldes, inclusive os vazios, e os registros armazenados em cada um.

### 5.3. Duas funções hash

Implementar duas funções hash diferentes:

- **Função 1 — soma dos códigos dos caracteres:** somar os códigos dos caracteres do nome normalizado e calcular o índice a partir da capacidade `M`.
- **Função 2 — função criada pelo grupo:** implementar uma segunda estratégia de cálculo de índice, diferente da primeira, adequada ao conteúdo estudado e explicável no relatório.

A segunda função deve ser realmente diferente da soma simples. Documente sua lógica no código e não use `hashCode()` como substituto sem autorização expressa do professor.

Permitir comparar as duas funções usando os mesmos dados e os mesmos tamanhos de tabela.

### 5.4. Estatísticas obrigatórias

Exibir:

- **Fator de carga:** quantidade de registros armazenados dividida pela quantidade de baldes (`N / M`).
- **Quantidade de baldes vazios:** posições sem nenhum registro.
- **Tamanho da maior lista:** maior quantidade de registros encontrada em um único balde.
- **Número de colisões:** definir e aplicar uma regra consistente. Para esta atividade, contar cada registro inserido em um balde que já possuía pelo menos um registro como uma colisão. Explicar essa convenção no código ou na apresentação, caso o guia do professor estabeleça outra definição.

### 5.5. Capacidades que devem ser testadas

Testar a tabela com:

- `M = 13`
- `M = 17`
- Mais um tamanho escolhido pelo grupo.

Executar os testes com as duas funções hash e comparar as estatísticas. A capacidade deve ser configurável, sem precisar alterar manualmente a implementação.

---

## 6. Parte 2 — Implementar os algoritmos de ordenação

A fila da sala de espera deve ser organizada pelo **microchip em ordem crescente**.

Implementar manualmente:

1. Bubble Sort.
2. Insertion Sort.
3. Quick Sort.

### 6.1. Comparação justa

Cada algoritmo deve receber uma cópia independente do mesmo conjunto de pets. Assim, nenhum algoritmo será beneficiado por receber dados já ordenados pelo algoritmo anterior.

A ordenação deve comparar os microchips, e não os nomes.

### 6.2. Contadores

Cada algoritmo deve contabilizar:

- Número de comparações.
- Número de trocas (`swap`) ou deslocamentos, conforme o funcionamento do algoritmo.

No Insertion Sort, contabilizar os deslocamentos realizados, conforme a atividade. Deixar claro no resultado o que foi contado como deslocamento.

Não misturar comparações com trocas/deslocamentos. Os contadores devem ser reiniciados a cada execução.

### 6.3. Modo passo a passo

Criar um modo de demonstração para ordenar até 10 pets.

Nesse modo, narrar as operações na linguagem do cenário da clínica:

- Comparação entre os microchips dos pacientes.
- Quando houver troca, informar que os pets trocaram de cadeira na sala de espera.
- Quando não houver troca, informar que a ordem foi mantida.
- Identificar o algoritmo e, quando aplicável, a passagem/etapa.
- Ao final, exibir o total de comparações e trocas/deslocamentos.

A narração deve refletir as operações reais executadas pelo algoritmo. Não inventar trocas nem exibir mensagens que não correspondam ao estado do vetor.

Para conjuntos maiores que 10, não usar a narração detalhada de cada operação.

---

## 7. Parte 3 — A Grande Corrida

Implementar uma funcionalidade para gerar conjuntos de:

- 1.000 pets.
- 10.000 pets.

O gerador deve combinar nomes, espécies e manias e produzir microchips únicos de 8 dígitos. Evitar duplicidade de microchips mesmo em conjuntos grandes.

A atividade também exige medições com 50 pets. Portanto, a Grande Corrida deve permitir executar os testes com:

- 50 registros;
- 1.000 registros;
- 10.000 registros.

### 7.1. Situações de entrada

Para cada quantidade, preparar três situações:

1. **Aleatório:** microchips em ordem embaralhada.
2. **Já ordenado:** microchips em ordem crescente.
3. **Ordem inversa:** microchips em ordem decrescente.

As três situações devem representar os mesmos conjuntos de registros, mudando apenas a disposição dos elementos no vetor. Garanta que a preparação dos dados não utilize métodos de ordenação prontos.

### 7.2. Medições

Para cada combinação de algoritmo, situação e quantidade, registrar:

- Tempo de execução usando `System.nanoTime()`.
- Número de comparações.
- Número de trocas ou deslocamentos.

Executar os três algoritmos sobre cópias equivalentes dos dados.

Ao final, exibir os resultados de forma organizada e permitir que sejam transcritos para as tabelas do relatório.

A Grande Corrida deve cobrir todas as combinações:

| Algoritmo | Situação |
|---|---|
| Bubble Sort | Aleatório |
| Bubble Sort | Já ordenado |
| Bubble Sort | Ordem inversa |
| Insertion Sort | Aleatório |
| Insertion Sort | Já ordenado |
| Insertion Sort | Ordem inversa |
| Quick Sort | Aleatório |
| Quick Sort | Já ordenado |
| Quick Sort | Ordem inversa |

Repetir essa matriz para `n = 50`, `n = 1.000` e `n = 10.000`.

A medição de tempo deve considerar apenas a ordenação, não a geração dos dados, a cópia dos vetores ou a impressão de mensagens no console. O modo de narração passo a passo não deve ser usado durante a medição.

Atenção especial ao Quick Sort: testar as três situações e observar se a escolha do pivô causa comportamento muito diferente em vetores já ordenados ou inversamente ordenados. Não esconder resultados inesperados; eles fazem parte da análise.

---

## 8. Parte 4 — Menu do programa

Criar ou adaptar um menu de console para o cenário da clínica veterinária. Ele deve oferecer, no mínimo, as opções abaixo:

1. Cadastrar pet.
2. Buscar pets por nome.
3. Remover pet pelo microchip.
4. Mostrar fichário completo (tabela hash).
5. Mostrar estatísticas da tabela hash.
6. Organizar a fila da sala de espera.
7. Executar ordenação passo a passo.
8. Executar A Grande Corrida.
0. Encerrar o programa.

Os textos podem ser personalizados, mas todas as funcionalidades devem estar acessíveis pelo menu.

### Regras de interação

- Validar as entradas do usuário.
- Não encerrar inesperadamente quando o usuário digitar um valor inválido.
- Informar quando um pet não for encontrado.
- Na busca por nome, exibir todos os resultados correspondentes.
- Na remoção, confirmar qual pet foi removido, identificando-o pelo microchip.
- Após ordenar, exibir a fila em ordem crescente de microchip.
- Não permitir microchips duplicados.
- Manter o menu em execução até o usuário escolher a opção de encerramento.

---

## 9. Testes e critérios de aceite

Antes de considerar a atividade concluída, executar testes que comprovem os itens abaixo.

### 9.1. Tabela hash

- [ ] A tabela é implementada manualmente.
- [ ] A colisão é tratada por encadeamento.
- [ ] A chave é o nome em maiúsculas e sem acentos.
- [ ] A inserção aceita pets com nomes repetidos.
- [ ] A busca retorna todos os pets com o nome informado.
- [ ] A remoção por microchip remove somente o registro correto.
- [ ] A exibição mostra todos os baldes, inclusive os vazios.
- [ ] As quatro estatísticas são calculadas corretamente.
- [ ] As duas funções hash estão implementadas e podem ser comparadas.
- [ ] Foram testados `M = 13`, `M = 17` e um terceiro tamanho.

### 9.2. Ordenação

- [ ] Bubble Sort ordena corretamente por microchip crescente.
- [ ] Insertion Sort ordena corretamente por microchip crescente.
- [ ] Quick Sort ordena corretamente por microchip crescente.
- [ ] Cada algoritmo recebe uma cópia dos mesmos dados.
- [ ] Comparações e trocas/deslocamentos são contabilizados.
- [ ] O modo passo a passo funciona com até 10 pets.
- [ ] A narração corresponde às operações reais.

### 9.3. Grande Corrida

- [ ] O gerador cria 1.000 pets.
- [ ] O gerador cria 10.000 pets.
- [ ] Todos os microchips gerados são únicos e têm 8 dígitos.
- [ ] São testadas as quantidades 50, 1.000 e 10.000.
- [ ] São testadas as três situações: aleatória, crescente e decrescente.
- [ ] São registrados tempo, comparações e trocas/deslocamentos.
- [ ] As medições não incluem geração, cópia ou impressão no console.
- [ ] Os resultados são apresentados de forma organizada.

### 9.4. Programa

- [ ] O menu disponibiliza todas as operações exigidas.
- [ ] As entradas inválidas são tratadas.
- [ ] O programa não encerra por causa de uma entrada inválida.
- [ ] O projeto compila e pode ser executado pelo console.
- [ ] Os dados iniciais totalizam pelo menos 50 pets.

---

## 10. Relatório: informações que o programa deve permitir obter

O relatório final deve ter de 3 a 6 páginas e conter o cenário escolhido, as decisões do grupo, as tabelas da Grande Corrida e as respostas às perguntas propostas.

O programa deve produzir dados suficientes para responder às seguintes questões, sem inventar resultados:

1. Por que todas as Lunas sempre caem no mesmo balde, independentemente do tamanho `M`? Isso é uma colisão?
2. Qual exemplo de colisão entre nomes diferentes apareceu na tabela?
3. Qual tamanho de tabela gerou menos colisões? Por que tamanhos primos costumam funcionar melhor?
4. Qual função hash espalhou melhor os nomes? Quais resultados sustentam a conclusão?
5. O que acontece com o fator de carga ao cadastrar 10.000 pets em uma tabela com `M = 17`? Como isso afeta a busca? O que poderia ser feito para resolver?
6. Por que a ordenação é feita pelo microchip, e não pelo nome?
7. Qual algoritmo venceu em cada situação da Grande Corrida? Os resultados combinam com as complexidades estudadas (`O(n²)` e `O(n log n)`)?
8. O Insertion Sort se comportou de forma diferente com os dados já ordenados? Por quê?
9. Aconteceu algo inesperado com o Quick Sort em alguma situação? O que ocorreu e por quê?

**Importante:** não preencher respostas do relatório com números inventados. Os valores devem vir da execução real do programa.

---

## 11. Entregáveis previstos pela atividade

A atividade solicita:

- Código-fonte `.java` organizado e comentado, acompanhado do arquivo de dados.
- Relatório em PDF de 3 a 6 páginas.
- Apresentação de 5 a 8 minutos, com o programa funcionando ao vivo.

A implementação deve facilitar a demonstração das operações da tabela hash, da ordenação passo a passo e da Grande Corrida.

---

## 12. Orientações de qualidade para a IA da IDE

Durante todo o desenvolvimento:

1. Trabalhe em etapas pequenas e verificáveis.
2. Preserve a organização e o estilo do código-base sempre que possível.
3. Não crie uma arquitetura excessivamente complexa para uma atividade introdutória de estruturas de dados e algoritmos.
4. Prefira nomes de classes, métodos e variáveis claros e coerentes com o padrão já usado no projeto.
5. Comente principalmente regras importantes, decisões de implementação e trechos que possam gerar dúvidas na apresentação.
6. Não esconda erros com blocos `catch` vazios ou mensagens genéricas.
7. Não simule resultados de algoritmos: os contadores, tempos e narrações devem vir da execução real.
8. Não use ordenação pronta nem tabela hash pronta, mesmo que isso simplifique a implementação.
9. Após cada etapa, compile e execute testes antes de avançar.
10. Ao concluir, faça uma revisão dos requisitos e informe claramente:
    - o que foi implementado;
    - quais arquivos foram criados ou modificados;
    - quais testes foram executados e seus resultados;
    - quais requisitos ainda não foram atendidos;
    - como executar o programa.

### Ordem sugerida de desenvolvimento

1. Analisar o código-base e o guia do professor.
2. Entender e ajustar o modelo de dados e a leitura dos 50 registros.
3. Implementar ou completar a lista ligada e a tabela hash.
4. Implementar as duas funções hash e as estatísticas.
5. Implementar e testar as operações de cadastro, busca, remoção e exibição.
6. Implementar os três algoritmos de ordenação e seus contadores.
7. Implementar o modo passo a passo.
8. Implementar o gerador e a Grande Corrida.
9. Integrar todas as funcionalidades no menu.
10. Executar os testes finais e revisar o projeto em relação ao guia e aos requisitos.

**Prioridade máxima:** entregar uma implementação correta, compreensível e compatível com o que foi ensinado pelo professor. Não priorize sofisticação ou quantidade de código; priorize atender integralmente à atividade e permitir que os integrantes expliquem como cada parte funciona.