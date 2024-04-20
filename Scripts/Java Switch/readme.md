Adicionar o texto de conteudo.

Extre projeto tem haver com esse conteudo que te passe? Quero fazer um curso pratico para cada tema, e preciso que você avalie. 

comente esse codigo de maneira breve e objetiva, ensinado Java. Quero apenas os comentários mais importante, e não altere os comentários:

crie um readme.md, com tags e tudo mais, para esse projeto. Ele deve ser breve e explicativo

adicione esse trecho ao readme.md. Me de com tags e tudo, mantendo a linguagem concisa e breve, adicione cada tpitulo com ###:
# Sistema de Gerenciamento de Planos em Java

Este é um projeto em Java que apresenta um sistema simples para gerenciamento de planos, utilizando JDBC para interação com um banco de dados MySQL.

## Funcionalidades

- Criação da tabela de planos no banco de dados (se ainda não existir).
- Registro de novos planos com duração especificada.
- Visualização de todos os planos registrados no banco de dados.
- Possibilidade de abrir um terminal SQL para interação direta com o banco de dados.

## Pré-requisitos

- JDK (Java Development Kit) instalado
- Banco de dados MySQL instalado e configurado localmente
- Definição das variáveis de ambiente `DB_USER`, `DB_PASS` e `DB_NAME` com as credenciais e nome do banco de dados

## Tecnologias Utilizadas

- `Java`
- `JDBC`
- `MySQL`

## Autor

- [Me Segue no Linkedin](https://www.linkedin.com/in/pedro-mota-dias/)

---

## Conceitos abordados:

### Pontos Explorados no Projeto

1. **JDBC (Java Database Connectivity)**: Utilizado para interagir com o banco de dados MySQL, incluindo a criação de tabelas, inserção de dados e consulta de registros.

2. **Variáveis de Ambiente**: São utilizadas para armazenar as credenciais do banco de dados (`DB_USER`, `DB_PASS` e `DB_NAME`), garantindo uma forma flexível de configuração e segurança.

3. **Manipulação de Strings**: É feita a manipulação de strings para exibir mensagens e processar a entrada do usuário.

4. **Estruturas de Controle**: O projeto faz uso de estruturas de controle, como `switch`, para lidar com diferentes opções selecionadas pelo usuário.

5. **Tratamento de Exceções**: Implementa blocos try-catch para lidar com exceções que podem ocorrer durante a interação com o banco de dados e execução do sistema.

6. **Utilização de Prepared Statements**: Para evitar injeção de SQL e garantir segurança, são utilizados prepared statements para executar consultas parametrizadas.

7. **ProcessBuilder**: A classe `ProcessBuilder` é usada para abrir um terminal SQL para interação direta com o banco de dados, demonstrando uma forma de execução de processos externos.

8. **Padrão de Projeto**: Embora não esteja explicitamente implementado, o código segue um estilo de organização e separação de responsabilidades que remete ao padrão de projeto MVC (Model-View-Controller).