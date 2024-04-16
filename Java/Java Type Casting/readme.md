# Conversor de Câmbio em Java

Este é um projeto em Java que implementa um conversor de câmbio simples, permitindo aos usuários converter valores entre diferentes moedas.

## Funcionalidades

- Mostrar as moedas disponíveis para conversão.
- Solicitar a entrada das moedas de origem e destino.
- Processar a conversão utilizando uma API de taxa de câmbio.
- Exibir o valor convertido.

## Pré-requisitos

- JDK (Java Development Kit) instalado.
- Variáveis de ambiente configuradas para armazenar as credenciais da API de taxa de câmbio.

## Tecnologias Utilizadas

- `Java`
- `HttpClient`
- `Json`
- `URI`

## Autor

- [Me Segue no Linkedin](https://www.linkedin.com/in/pedro-mota-dias/)

---

## Comentários Importantes do Código

### Pontos Essenciais do Projeto

1. **URL da API e Mapa de Moedas**: Definidas constantes para a URL da API de taxa de câmbio e um mapa de moedas com suas siglas e nomes.

2. **Interatividade com o Usuário**: Implementa a interação com o usuário por meio de entrada do terminal, solicitando as moedas de origem e destino.

3. **Obtenção da Taxa de Câmbio**: Utiliza a API de taxa de câmbio para obter a taxa entre as moedas selecionadas.

4. **Tratamento de Exceções**: Realiza tratamento de exceções para lidar com erros durante o acesso à API e conversão de valores.

5. **Boas Práticas de Programação**: Utiliza blocos try-with-resources para garantir a correta liberação de recursos e tornar o código mais limpo e seguro.

6. **Mensagens de Feedback**: Fornece mensagens informativas ao usuário para orientá-lo durante o processo de conversão.

7. **Padrão de Projeto**: Embora não explicitamente mencionado, o código segue um estilo organizacional e de separação de responsabilidades que favorece a manutenção e extensibilidade do sistema.
