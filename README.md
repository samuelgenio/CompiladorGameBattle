# CompiladorGameBattle

Compilador desenvolvido para compilar linguagem própria baseada em LMS.

O desenvolvimento da linguagem foi motivado pelo desenvolvimento de um Jogo de Batalha 2D para o trabalho de conclusão de curso da Universidade do Sul de Santa Catarina (UNISUL).

# Linguagem

Baseada principalmente na linguagem LMS, que é uma linguagem com fins didáticos criada a partir de elementos da linguagem Pascal. Utilizando símbolos e estruturas da linguagem Java, foi desenvolvida a DLS (Linguagem Específica de Domínio) para o jogo. A Linguagem é fracamente tipada, onde sua principal semelhança com LMS pode ser notada na declaração de variável, que deve ser feita no início do script, porém a estrutura de execução baseada em métodos e chamadas são inspiradas na linguagem Java.

A Linguagem possui dois tipos de dados, numeral (inteiro) e literal (string), onde não é necessário identificar o tipo da variável, ela é dinamicamente identificada ao receber seu primeiro valor. Porém caso uma variável do tipo inteiro, após inicializada, receba um valor literal, o compilador irá acusar erro. A Linguagem é case-sensitive e permitirá a criação de variáveis personalizadas, assim como métodos, desde que não possuam o nome de nenhum método ou variável reservada.

# Fluxo de Compilação

![](https://github.com/samuelgenio/CompiladorGameBattle/blob/master/files/fluxocompilador.jpg)

# Fonte

![](https://github.com/samuelgenio/CompiladorGameBattle/blob/master/files/fonte.jpg)
