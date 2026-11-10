# Checklist de desenvolvimento — Pet Hash & Sort

Marquem conforme o grupo realmente concluir e testar. Não marquem só porque o código foi escrito.

## 1. Ambiente
- [ ] Os quatro integrantes conseguem executar o projeto.
- [ ] JDK configurado no IntelliJ.
- [ ] Estrutura de pastas criada.
- [ ] `Main.java` executa sem erro.
- [ ] Todos usam a mesma versão atual do projeto.

## 2. Dados e classe Pet
- [ ] Classe `Pet` criada.
- [ ] Campos: microchip, nome, espécie e mania.
- [ ] Nome normalizado para maiúsculas e sem acento na chave.
- [ ] 20 pets do apêndice incluídos.
- [ ] Pelo menos 30 pets novos incluídos.
- [ ] Total mínimo de 50 pets.
- [ ] Microchips têm 8 dígitos.
- [ ] Microchips não se repetem.
- [ ] Nomes repetidos de propósito.
- [ ] Arquivo de texto é lido no início do programa.

## 3. Tabela hash
- [ ] Vetor de baldes implementado pelo grupo.
- [ ] Nó com `Pet` e referência para o próximo.
- [ ] Colisões resolvidas por encadeamento.
- [ ] Função hash 1: soma dos códigos dos caracteres.
- [ ] Função hash 2: função criada pelo grupo.
- [ ] Inserir.
- [ ] Buscar por nome e mostrar todos os resultados.
- [ ] Remover por microchip.
- [ ] Exibir tabela balde por balde.
- [ ] Fator de carga.
- [ ] Quantidade de baldes vazios.
- [ ] Tamanho da maior lista.
- [ ] Número de colisões.
- [ ] Teste M = 13.
- [ ] Teste M = 17.
- [ ] Teste com terceiro tamanho.
- [ ] Comparação das funções hash com os mesmos dados.

## 4. Ordenação
- [ ] Bubble Sort implementado.
- [ ] Insertion Sort implementado.
- [ ] Quick Sort implementado.
- [ ] Ordenação por microchip.
- [ ] Cópias independentes dos mesmos dados.
- [ ] Contagem de comparações.
- [ ] Contagem de trocas.
- [ ] Contagem de deslocamentos no Insertion Sort.
- [ ] Modo passo a passo para até 10 pets.
- [ ] Saída de cada algoritmo conferida em ordem crescente.

## 5. Grande Corrida
- [ ] Gerador de 1.000 pets.
- [ ] Gerador de 10.000 pets.
- [ ] Microchips únicos nos dados gerados.
- [ ] Situação aleatória.
- [ ] Situação já ordenada.
- [ ] Situação em ordem inversa.
- [ ] Medição com `System.nanoTime()`.
- [ ] Bubble executado em todas as combinações.
- [ ] Insertion executado em todas as combinações.
- [ ] Quick executado em todas as combinações.
- [ ] Tabela de tempos preenchida.
- [ ] Tabela de comparações preenchida.
- [ ] Tabela de trocas/deslocamentos preenchida.

## 6. Menu
- [ ] Cadastrar pet.
- [ ] Buscar pets por nome.
- [ ] Remover por microchip.
- [ ] Exibir tabela.
- [ ] Exibir estatísticas.
- [ ] Organizar/ordenar pets.
- [ ] Modo passo a passo.
- [ ] Grande Corrida.
- [ ] Sair.
- [ ] Opções inválidas tratadas.

## 7. Relatório
- [ ] Cenário escolhido descrito.
- [ ] Decisões do grupo explicadas.
- [ ] Tabela de tempos.
- [ ] Tabela de comparações.
- [ ] Tabela de trocas/deslocamentos.
- [ ] Resposta às nove perguntas com dados reais.
- [ ] PDF com 3 a 6 páginas.

## 8. Apresentação e domínio
- [ ] Programa funciona ao vivo.
- [ ] Apresentação entre 5 e 8 minutos.
- [ ] Todos sabem explicar a tabela hash.
- [ ] Todos sabem explicar os três sorts.
- [ ] Todos sabem explicar a Grande Corrida.
- [ ] Todos conseguem fazer uma alteração simples em qualquer arquivo.
- [ ] Todos conseguem responder perguntas sobre o projeto inteiro.

## 9. Entrega
- [ ] Código-fonte `.java` organizado e comentado.
- [ ] Arquivo de dados incluído.
- [ ] Relatório PDF incluído.
- [ ] Arquivos finais conferidos em outro computador, se possível.
