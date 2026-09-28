# API Gestão de Entregas

API REST em **Spring Boot** para a gestão de motoristas e entregas de uma empresa de logística, com autenticação **JWT** e controle de acesso por papéis (**Spring Security**).

Projeto desenvolvido como atividade prática do curso Técnico em Desenvolvimento de Sistemas.

## Sumário

- [Funcionalidades](#funcionalidades)
- [Tecnologias](#tecnologias)
- [Como executar](#como-executar)
- [Endpoints](#endpoints)
- [Autenticação e autorização](#autenticação-e-autorização)
- [Padrão de respostas de erro](#padrão-de-respostas-de-erro)
- [Modelo de dados](#modelo-de-dados)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Próximos passos](#próximos-passos)

## Funcionalidades

- Cadastro e login de usuários, com senha criptografada em BCrypt
- Autenticação stateless com token JWT (validade de 1 hora)
- Autorização por papel: `ROLE_ADMIN` e `ROLE_USER`
- CRUD parcial de motoristas (criar, listar, buscar por id, buscar por nome e deletar)
- Validação dos dados de entrada com Bean Validation
- Respostas de erro padronizadas em JSON, incluindo 401 e 403 gerados pelo Security
- Documentação interativa com Swagger UI
- Banco H2 em memória, populado com um administrador na inicialização

## Tecnologias

| Tecnologia | Uso |
|---|---|
| Java 21 | Linguagem |
| Spring Boot 4.1.1 | Framework base |
| Spring Security | Autenticação e autorização |
| JJWT 0.12.6 | Geração e validação do token JWT |
| Spring Data JPA / Hibernate | Persistência |
| H2 Database | Banco de dados em memória |
| MapStruct | Conversão entre entidades e DTOs |
| Bean Validation | Validação dos DTOs |
| Springdoc OpenAPI 3.1.0 | Swagger UI |
| Lombok | Redução de código repetitivo |
| Maven | Build e dependências |

## Como executar

### Pré-requisitos

- JDK 21
- Maven 3.9+ (ou o `mvnw` do projeto, se estiver disponível)

### Passos

```bash
git clone https://github.com/EricHafemann/Entrega-API.git
cd Entrega-API
mvn spring-boot:run
```

A aplicação sobe em **http://localhost:8181**.

| Recurso | URL |
|---|---|
| Swagger UI | http://localhost:8181/swagger-ui.html |
| Console do H2 | http://localhost:8181/h2-console |

Dados de conexão no console do H2:

- **JDBC URL:** `jdbc:h2:mem:entregadb`
- **User:** `sa`
- **Password:** (vazio)

> O banco é em memória: os dados são recriados a cada reinicialização.

## Endpoints

### Autenticação (públicos)

| Método | Rota | Descrição | Sucesso |
|---|---|---|---|
| POST | `/api/auth/register` | Cadastra um usuário com `ROLE_USER` | 201 |
| POST | `/api/auth/login` | Autentica e devolve o token JWT | 200 |

### Motoristas (exigem token)

| Método | Rota | Descrição | Permissão | Sucesso |
|---|---|---|---|---|
| POST | `/api/motoristas` | Cria um motorista | `ADMIN` | 201 |
| GET | `/api/motoristas` | Lista todos os motoristas | `USER`, `ADMIN` | 200 |
| GET | `/api/motoristas/{id}` | Busca um motorista por id | `USER`, `ADMIN` | 200 |
| GET | `/api/motoristas/busca/{nome}` | Busca motoristas pelo nome | `USER`, `ADMIN` | 200 |
| DELETE | `/api/motoristas/{id}` | Remove um motorista | `ADMIN` | 204 |

## Autenticação e autorização

### Usuário administrador inicial

Criado automaticamente pelo `data.sql` para testes em ambiente de desenvolvimento:

| Username | Senha | Role |
|---|---|---|
| `Admin` | `123@Mudar` | `ROLE_ADMIN` |

Novos usuários criados em `/api/auth/register` recebem sempre `ROLE_USER`.

### Fluxo de uso

**1. Fazer login**

```bash
curl -X POST http://localhost:8181/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "Admin", "password": "123@Mudar"}'
```

Resposta:

```json
{ "token": "eyJhbGciOiJIUzM4NCJ9..." }
```

**2. Enviar o token nas requisições protegidas**

```bash
curl http://localhost:8181/api/motoristas \
  -H "Authorization: Bearer <token>"
```

No Swagger UI, use o botão **Authorize** e informe apenas o token, sem o prefixo `Bearer`.

### Como funciona

1. No login, o `AuthenticationManager` valida usuário e senha (BCrypt) e o `JwtService` gera o token.
2. A cada requisição, o `JwtAuthenticationFilter` lê o header `Authorization`, valida a assinatura e a expiração do token e carrega o usuário do banco.
3. O `SecurityConfig` compara a rota e o método HTTP com o papel do usuário.

Como o papel é lido do banco a cada requisição, alterar a role de um usuário vale imediatamente, sem esperar o token expirar.

## Padrão de respostas de erro

Todos os erros, inclusive os do Spring Security, seguem o mesmo formato:

```json
{
  "mensagem": "Credenciais inválidas !",
  "code": 401,
  "path": "/api/auth/login",
  "dateTime": "2026-09-28T00:18:53.698"
}
```

| Status | Situação |
|---|---|
| 400 | Dados inválidos, corpo ausente ou JSON malformado |
| 401 | Token ausente, inválido ou expirado; credenciais incorretas |
| 403 | Usuário autenticado sem permissão para o recurso |
| 404 | Recurso não encontrado |
| 409 | Registro duplicado (username ou CNH já existentes) |
| 500 | Erro interno inesperado |

## Modelo de dados

```
tb_usuario                tb_motorista              tb_entrega
-----------               ------------              ----------
id (PK)                   id (PK)                   id (PK)
username (único)          nome                      descricao
password (BCrypt)         cnh (única)               status
role                                                motorista_id (FK)

tb_motorista 1 ────────< tb_entrega
```

Status possíveis de uma entrega: `EM_PREPARACAO`, `ESPERANDO_PAGAMENTO`, `A_CAMINHO`, `ENTREGUE`, `CANCELADO`.

## Estrutura do projeto

```
src/main/java/br/com/ctw/apientregas
├── config         # SecurityConfig, JwtAuthenticationFilter, OpenApiConfig
│   └── service    # JwtService
├── controller     # AuthController, MotoristaController
├── dto            # Objetos de entrada (request) e saída (response)
├── entities       # Entidades JPA e enums (Role, Status)
├── exception      # Exceções de negócio e ErroResponse
├── handler        # GlobalHandlerException (@RestControllerAdvice)
├── mapper         # Mappers do MapStruct
├── repository     # Interfaces Spring Data JPA
└── service        # AuthService, CustomUserDetailService, MotoristaService
```

## Autor

**Eric Hafemann** — [@EricHafemann](https://github.com/EricHafemann)
