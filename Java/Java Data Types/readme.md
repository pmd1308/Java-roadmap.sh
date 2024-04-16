# Projeto CRUD Java com JDBC

Este é um projeto simples de CRUD (Create, Read, Update, Delete) em Java, utilizando JDBC para gerenciar dados em um banco de dados MySQL.

## Configuração do Ambiente

### Pré-Requisitos

- Java JDK 8 ou superior
- MySQL Server
- IDE de sua preferência (recomendamos IntelliJ IDEA ou Eclipse)

### Configuração do Banco de Dados

1. Crie um banco de dados MySQL chamado `seubanco`.
2. Crie uma tabela `usuarios` com a seguinte estrutura:

   ```sql
   CREATE TABLE usuarios (
       id INT AUTO_INCREMENT PRIMARY KEY,
       nome VARCHAR(255),
       idade TINYINT,
       ativo BOOLEAN,
       genero CHAR(1)
   );

## Variáveis de Ambiente

Configure as seguintes variáveis de ambiente no seu sistema:

- `JDBC_URL` - URL de conexão com o banco de dados (ex: jdbc:mysql://localhost:3306/seubanco)
- `JDBC_USER` - Usuário do banco de dados
- `JDBC_PASSWORD` - Senha do banco de dados

## Funcionalidades

- **Inserir Dados**: Insere novos usuários na tabela `usuarios`.
- **Consultar Dados**: Mostra todos os usuários registrados na tabela.

## Autor

- [Me Segue no Linkedin](https://www.linkedin.com/in/pedro-mota-dias/)

---

## Conceitos aplicados:

### Conexão com Banco de Dados

O projeto mostra como estabelecer uma conexão com um banco de dados MySQL utilizando JDBC. Isso é essencial para muitas aplicações Java que precisam armazenar e recuperar dados de um banco de dados.

### Operações CRUD

O projeto implementa operações básicas de CRUD (Create, Read, Update, Delete) para uma tabela de usuários no banco de dados. Ele mostra como inserir dados na tabela e como consultar e exibir esses dados.

### Segurança

O uso de consultas preparadas (PreparedStatement) ajuda a prevenir ataques de injeção de SQL, o que é uma prática recomendada para garantir a segurança das operações de banco de dados.

### Configuração Externa

O uso de variáveis de ambiente para armazenar as credenciais do banco de dados é uma prática comum para separar as configurações do código fonte, facilitando a manutenção e a segurança.

### Tipos de Dados

A escolha dos tipos de dados apropriados para cada coluna da tabela, como byte para idade e char para gênero, demonstra consideração pela eficiência de armazenamento e pelo design do banco de dados.
