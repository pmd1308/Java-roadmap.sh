# ContaBanco

## Descrição do Projeto

O projeto ContaBanco é um sistema simples de gerenciamento de contas bancárias que utiliza conceitos de Programação Orientada a Objetos (POO) como abstração, encapsulamento, herança e polimorfismo. O sistema permite criar contas poupança e correntes, realizar operações de depósito e saque, e armazenar as informações em um banco de dados SQLite. O projeto também inclui uma interface gráfica desenvolvida com Java Swing para interagir com o usuário.

## Estrutura do Projeto

```
ContaBanco/
├── src/
│ ├── AccountActions.java
│ ├── Account.java
│ ├── SavingsAccount.java
│ ├── CheckingAccount.java
│ ├── AccountDAO.java
│ ├── DatabaseConnection.java
│ └── ContaBancoGUI.java
```

## Conteúdo dos Arquivos

### AccountActions.java

Define as operações que podem ser realizadas em uma conta bancária.

### Account.java

Classe abstrata que representa uma conta bancária geral com atributos e operações básicas.

### SavingsAccount.java

Representa uma conta poupança com uma taxa de juros.

### CheckingAccount.java

Representa uma conta corrente com um limite de cheque especial.

### AccountDAO.java

Classe que gerencia as operações de banco de dados para persistência das contas.

### DatabaseConnection.java

Gerencia a conexão com o banco de dados SQLite.

### ContaBancoGUI.java

Fornece a interface gráfica para o sistema de contas bancárias.


## Conceitos de POO Aplicados

### Abstração

- **AccountActions.java**: Define a interface para operações de conta bancária.
- **Account.java**: Classe abstrata que encapsula os atributos e comportamentos comuns de uma conta bancária.

### Encapsulamento

- Uso de modificadores de acesso (private, public) para proteger os atributos das classes e fornecer métodos públicos para acesso controlado.
- **Account.java**: Os atributos `number`, `agency`, `clientName` e `balance` são privados e acessíveis apenas por meio de métodos públicos.

### Herança

- **SavingsAccount.java** e **CheckingAccount.java** herdam da classe abstrata **Account.java**, reutilizando atributos e métodos comuns, e estendendo funcionalidades específicas.

### Polimorfismo

- Métodos sobrescritos nas classes **SavingsAccount** e **CheckingAccount** para fornecer comportamentos específicos para tipos diferentes de contas bancárias.
- Uso de uma referência de tipo **Account** para instanciar objetos de suas subclasses.