# Currency Calculator

O projeto `CurrencyCalculator` é uma aplicação Java que permite a conversão entre diferentes moedas utilizando valores atualizados via API. O sistema também suporta o registro de operações em um banco de dados SQLite local para rastreamento histórico. O objetivo é aplicação dos conceitos de array,

## Funcionalidades

- **Carregamento de Moedas via API**: Carrega os valores mais recentes do Bitcoin e Ethereum em USD, EUR e GBP.
- **Conversão de Moedas**: Permite ao usuário converter valores entre as moedas suportadas.
- **Histórico de Operações**: Registra cada operação de conversão em um banco de dados e permite visualizar o histórico.
- **Gestão de Histórico**: Possibilidade de limpar o histórico de operações.

## Tecnologias Utilizadas

- Java
- SQLite (para o armazenamento de dados locais)
- Unirest (para chamadas HTTP)
- API CoinGecko (para preços de moedas)

## Como Utilizar

### Pré-requisitos

Certifique-se de ter o Java instalado em sua máquina. Além disso, é necessário ter acesso à internet para as requisições à API do CoinGecko.

### Execução

1. Clone o repositório para seu ambiente local.
2. Abra um terminal e navegue até o diretório do projeto.
3. Compile o projeto com o comando `javac CurrencyCalculator.java`.
4. Execute o programa com `java CurrencyCalculator`.

### Menu de Opções

- **1. Listar moedas e seus valores**: Mostra os valores atualizados das moedas.
- **2. Converter moeda**: Realiza uma conversão entre duas moedas escolhidas.
- **3. Ver histórico de operações**: Exibe todas as operações de conversão realizadas.
- **4. Limpar histórico de operações**: Apaga o histórico de operações.
- **5. Sair**: Fecha o programa.


## Autor

- [Me Segue no Linkedin](https://www.linkedin.com/in/pedro-mota-dias/)

---

## Conceitos aplicados:

### Utilização de Arrays Multidimensionais

O projeto utiliza arrays bidimensionais para armazenar os valores de moeda para Bitcoin e Ethereum em diferentes moedas. Isso demonstra compreensão do conceito de arrays multidimensionais.

### Acesso aos Elementos do Array

O código acessa os elementos dos arrays multidimensionais usando índices, como visto na função `loadCurrenciesFromAPI()`, onde os valores do Bitcoin e Ethereum são atribuídos aos arrays multidimensionais `bitcoinValues` e `ethereumValues`.

### Manipulação dos Dados do Array

O projeto realiza operações como conversão de moeda, listagem de valores de moeda e registro de operações, todas elas envolvendo a manipulação dos dados armazenados vs arrays multidimensionais.

### Estruturação do Código

O código está estruturado de forma apropriada, com métodos separados para diferentes funcionalidades, o que melhora a organização e a legibilidade do código.

### Boas Práticas de Programação

O código segue boas práticas de programação, como o uso de comentários para explicar partes importantes do código e o tratamento de exceções.


<i>Powered by CoinGecko API.
