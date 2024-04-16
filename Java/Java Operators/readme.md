# Sistema de Gerenciamento de Notas Fiscais em Java

Este é um projeto simples em Java que demonstra como criar um sistema de gerenciamento de notas fiscais utilizando JDBC para interação com um banco de dados SQLite.

## Funcionalidades

- Criação de uma tabela de notas fiscais no banco de dados (se ainda não existir).
- Inserção de novas notas fiscais a partir dos dados escaneados do código de barras.
- Consulta de todas as notas fiscais armazenadas no banco de dados.

## Pré-requisitos

- JDK (Java Development Kit) instalado
- Apache Maven (opcional, para compilação e execução)

## Tecnologias Utilizadas

- `Java`
- `JDBC`
- `SQLite`

## Autor

- [Me Segue no Linkedin](https://www.linkedin.com/in/pedro-mota-dias/)

---

## Conceitos abordados:

### Pontos Explorados no Projeto

1. **JDBC (Java Database Connectivity)**: Utilizado para interagir com um banco de dados SQLite, incluindo a criação de tabelas, inserção de dados e consulta de registros.

2. **Variáveis de Ambiente**: São utilizadas para armazenar as credenciais do banco de dados (`DB_USERNAME` e `DB_PASSWORD`), garantindo uma forma flexível de configuração.

3. **Manipulação de Strings**: É feita a manipulação de strings para processar os dados escaneados do código de barras da nota fiscal.

4. **Entrada e Saída de Dados**: A entrada de dados é realizada através do console, onde o usuário pode escanear o código de barras da nota fiscal e inserir os detalhes necessários.

5. **Estruturas de Dados**: Utiliza-se a estrutura de dados `Map` para armazenar os detalhes da nota fiscal.

6. **Tratamento de Exceções**: São implementados blocos try-catch para lidar com exceções que podem ocorrer durante a interação com o banco de dados.

7. **Compilação e Execução do Projeto**: O Maven é utilizado para compilar e executar o projeto, proporcionando uma forma eficiente de gerenciar as dependências e o ciclo de vida do projeto.

8. **Padrões de Projeto**: Embora não esteja explicitamente mencionado, é possível identificar o padrão de projeto DAO (Data Access Object) sendo utilizado para separar as operações de acesso ao banco de dados.
