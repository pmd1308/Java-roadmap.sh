# Spring Boot MongoDB API 

## Introdução

Este projeto é uma aplicação Spring Boot que demonstra a criação de uma API RESTful integrada com um banco de dados NoSQL MongoDB. O foco é implementar funcionalidades de login de usuários, com ênfase na segurança, testes e melhores práticas de desenvolvimento.
PS: Por algum motivo não consigo commitar, então decidi compacta-lo.

## Resumo

O projeto oferece uma API RESTful para criação de sessões. A segurança é gerida através de autenticação JWT e a comunicação com o banco de dados MongoDB é realizada utilizando o Spring Data MongoDB. A aplicação segue princípios SOLID e inclui testes unitários e integração para garantir a qualidade do código.

### Funcionalidades

- **Autenticação JWT**: Proteção da API com autenticação baseada em tokens JWT.
- **Persistência em MongoDB**: Armazenamento e recuperação de dados de usuários em um banco de dados MongoDB.
- **Testes**: Testes unitários e de integração para verificar a funcionalidade dos componentes da aplicação.
- **Pen Testes**: Criado escripts que exploram a vulnerabilidade do aplicativo.

## Tecnologias Usadas

- **[Spring Boot](https://spring.io/projects/spring-boot)**: Framework para criação de aplicações Java baseadas em Spring.
- **[MongoDB](https://www.mongodb.com/)**: Banco de dados NoSQL para armazenamento de documentos JSON.
- **[Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb)**: Framework para integração com MongoDB e abstração de operações CRUD.
- **[Spring Security](https://spring.io/projects/spring-security)**: Framework para implementação de segurança, incluindo autenticação e autorização.
- **[JUnit](https://junit.org/junit5/)**: Framework de teste para Java, usado para criar e executar testes automatizados.
- **[MockMvc](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#test-webmvc)**: Utilitário para testar controllers e endpoints em uma aplicação Spring Boot.

### Técnicas de Invasão Estudadas

- **[Escaneamento de Portas](https://www.rapid7.com/fundamentals/port-scanning/)**: Técnica utilizada para identificar portas abertas em um servidor, revelando possíveis pontos de entrada para ataques.
- **[Exploração de Configurações Inseguras](https://owasp.org/www-community/vulnerabilities/Unrestricted_Access_to_Resource_By_Resource_Identifier)**: Identificação de configurações que permitem o acesso não autorizado a recursos, como o MongoDB sem autenticação.
- **[Exfiltração de Dados](https://www.owasp.org/index.php/Data_Exfiltration)**: Técnica de extrair dados de um sistema para um servidor externo sem a devida autorização, potencialmente comprometendo a integridade e confidencialidade dos dados.
- **[Testes de Segurança](https://owasp.org/www-community/Testing)**: Prática de avaliar a segurança de sistemas para identificar vulnerabilidades e verificar a eficácia das medidas de proteção implementadas.

```mermaid
classDiagram
    class User {
        +String id
        +String username
        +String password
        +String email
        +String getId()
        +String getUsername()
        +String getPassword()
        +String getEmail()
        +void setId(String id)
        +void setUsername(String username)
        +void setPassword(String password)
        +void setEmail(String email)
    }

    class UserDTO {
        +String username
        +String email
        +String getUsername()
        +String getEmail()
        +void setUsername(String username)
        +void setEmail(String email)
    }

    class UserController {
        +UserService userService
        +ResponseEntity<UserDTO> createUser(UserDTO userDTO)
        +ResponseEntity<UserDTO> getUserById(String id)
        +ResponseEntity<List<UserDTO>> getAllUsers()
        +ResponseEntity<UserDTO> updateUser(String id, UserDTO userDTO)
        +ResponseEntity<Void> deleteUser(String id)
    }

    class UserService {
        +UserRepository userRepository
        +UserDTO createUser(UserDTO userDTO)
        +UserDTO getUserById(String id)
        +List<UserDTO> getAllUsers()
        +UserDTO updateUser(String id, UserDTO userDTO)
        +void deleteUser(String id)
    }

    class UserServiceImpl {
        +UserRepository userRepository
        +UserDTO createUser(UserDTO userDTO)
        +UserDTO getUserById(String id)
        +List<UserDTO> getAllUsers()
        +UserDTO updateUser(String id, UserDTO userDTO)
        +void deleteUser(String id)
    }

    class UserRepository {
        +User findById(String id)
        +List<User> findAll()
        +User save(User user)
        +void deleteById(String id)
    }

    class JwtUtil {
        +String generateToken(Authentication authentication)
        +Claims getClaimsFromToken(String token)
        +boolean validateToken(String token, UserDetails userDetails)
    }

    class JwtAuthenticationFilter {
        +JwtUtil jwtUtil
        +doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    }

    class JwtEntryPoint {
        +void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
    }

    class WebSecurityConfig {
        +JwtUtil jwtUtil
        +configure(HttpSecurity http)
    }

    UserController --> UserService : uses
    UserServiceImpl --> UserRepository : uses
    UserService --> UserServiceImpl : implements
    JwtAuthenticationFilter --> JwtUtil : uses
    WebSecurityConfig --> JwtUtil : configures
    WebSecurityConfig --> JwtAuthenticationFilter : adds filter
    JwtAuthenticationFilter --> UserController : applies security
    JwtUtil --> UserController : supports authentication

    User : "1" --> "0..*" UserDTO : data transfer
```