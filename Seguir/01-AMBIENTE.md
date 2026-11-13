# Preparando o ambiente — Pet Hash & Sort

> Objetivo desta etapa: deixar todo mundo com o mesmo projeto pronto para começar a programar. Ainda não é hora de implementar os algoritmos.

## 1. O que cada integrante precisa ter

- IntelliJ IDEA instalado.
- JDK instalado (recomendação: **JDK 21**, versão LTS).
- Acesso à pasta compartilhada/repositório do grupo.
- Os arquivos de aula enviados pela professora disponíveis para consulta.

O IntelliJ Community é suficiente. Não precisamos de versão paga, Maven, Spring, banco de dados ou plugins especiais.

## 2. Conferir se o Java está instalado

Abra o terminal do computador e execute:

```bash
java -version
javac -version
```

Os dois comandos devem mostrar uma versão instalada. O `java` executa programas e o `javac` compila arquivos `.java`.

Se aparecer que o comando não existe, falta instalar/configurar o JDK. Instale um JDK 21 e, se necessário, configure a variável `JAVA_HOME` e o `PATH` conforme o sistema operacional.

No IntelliJ, também é possível configurar o JDK em:

`File > Project Structure > Project > SDK`

Escolha o JDK instalado. Se não aparecer, use a opção para adicionar um JDK.

## 3. Criar o projeto no IntelliJ IDEA

1. Abra o IntelliJ IDEA.
2. Clique em **New Project**.
3. Escolha **Java**.
4. Dê o nome `PetHashSort`.
5. Em **Build system**, escolha `IntelliJ` (não precisamos de Maven ou Gradle).
6. Em **JDK**, selecione o JDK 21.
7. Crie o projeto.

Se a tela da sua versão for um pouco diferente, o objetivo é o mesmo: criar um projeto Java simples usando o JDK instalado.

## 4. Criar as pastas

Na raiz do projeto, criem:

```text
PetHashSort/
├── dados/
└── src/
    └── pethashsort/
```

No IntelliJ:

- Clique com o botão direito no projeto para criar a pasta `dados`.
- Dentro de `src`, crie o pacote `pethashsort` (`New > Package`).
- Dentro do pacote, serão criadas as classes Java.

O IntelliJ normalmente marca `src` como pasta de código-fonte automaticamente. Se não marcar, clique com o botão direito em `src` e escolha `Mark Directory as > Sources Root`.

## 5. Criar os arquivos iniciais

Por enquanto, criem apenas os arquivos vazios:

```text
src/pethashsort/
├── Main.java
├── Pet.java
├── TabelaHashPets.java
├── Ordenacao.java
├── ResultadoOrdenacao.java
├── GeradorPets.java
├── ArquivoPets.java
└── GrandeCorrida.java
```

Para criar: botão direito no pacote `pethashsort` → `New > Java Class`.

Se o IntelliJ criar automaticamente `package pethashsort;`, mantenham essa linha no começo de cada classe.

## 6. Criar um primeiro programa para testar o ambiente

Dentro de `Main.java`, coloquem temporariamente:

```java
package pethashsort;

public class Main {
    public static void main(String[] args) {
        System.out.println("Pet Hash & Sort iniciado!");
    }
}
```

Clique no triângulo verde ao lado do `main` e escolha **Run 'Main.main()'**.

Se aparecer `Pet Hash & Sort iniciado!` no console, o ambiente está funcionando.

Depois desse teste, podem manter essa mensagem ou substituir pelo menu quando ele for desenvolvido.

## 7. Criar o arquivo de dados

Dentro da pasta `dados`, criem `pets.txt`.

O formato será uma linha por pet, com os campos separados por ponto e vírgula:

```text
60218841;Luna;gata;dorme em cima do teclado
31057720;Thor;cão;late para o aspirador
48812903;Pipoca;cão;pula quando ouve passeio
```

A primeira parte do desenvolvimento deve incluir os 20 pets do apêndice e pelo menos mais 30 criados pelo grupo. Confiram:

- microchip com exatamente 8 dígitos;
- nenhum microchip repetido;
- nome, espécie e mania preenchidos;
- nomes repetidos de propósito.

Atenção: o exemplo acima é só para mostrar o formato. Transcrevam os 20 registros completos do apêndice do enunciado e acrescentem os 30 do grupo.

## 8. Como ler o arquivo sem complicar

A aplicação será executada com a pasta raiz do projeto como diretório de trabalho. Assim, o caminho pode ser:

```java
Path caminho = Path.of("dados", "pets.txt");
```

A leitura pode ser feita com `Files.readAllLines(caminho)`. Cada linha é separada usando `split(";")`.

Exemplo de ideia (a implementação completa fica para a etapa de leitura de dados):

```java
String[] campos = linha.split(";", 4);
```

O `4` limita a divisão a quatro partes, evitando separar a mania caso ela tenha ponto e vírgula no texto. Mesmo assim, evitem usar `;` dentro dos campos.

## 9. Como o grupo vai compartilhar o projeto

Escolham **um lugar oficial** para manter o código atualizado:

- GitHub privado/público, se todos já souberem usar; ou
- uma pasta compartilhada, se for o caminho mais rápido para o grupo.

Se usarem Git:

1. Uma pessoa cria o repositório.
2. Todos clonam o mesmo projeto.
3. Cada integrante trabalha em uma parte/branch ou combina quem edita cada arquivo.
4. Antes de começar, atualizem a cópia local.
5. Não editem o mesmo arquivo ao mesmo tempo sem combinar.

Se Git for novidade para todos e o prazo estiver muito apertado, não gastem horas aprendendo uma ferramenta nova. Usem uma pasta compartilhada com cópias de segurança e combinem claramente quem está editando cada arquivo.

**Regra importante:** nunca deixem versões diferentes do projeto espalhadas no WhatsApp sem saber qual é a mais atual.

## 10. Conferência antes de começar a desenvolver

- [ ] `java -version` funciona.
- [ ] `javac -version` funciona.
- [ ] O IntelliJ reconhece o JDK.
- [ ] O projeto `PetHashSort` abre sem erro.
- [ ] A pasta `dados` existe.
- [ ] O pacote `pethashsort` existe.
- [ ] Os oito arquivos Java iniciais foram criados.
- [ ] `Main.java` executa e mostra a mensagem de teste.
- [ ] Os quatro integrantes conseguem abrir e executar o mesmo projeto.

**Quando tudo estiver marcado, o ambiente está pronto.** A próxima etapa é implementar `Pet.java` e começar a tabela hash, seguindo o `02-GUIA-DESENVOLVIMENTO.md`.
