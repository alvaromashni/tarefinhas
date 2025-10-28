# Tarefinhas — API de tarefas to-do

Projeto de estudos para praticar CRUD com foco em boas práticas, organização em camadas e evolução contínua. Mantém a simplicidade do MVC enquanto prepara terreno para melhorias (DTOs, validação, testes, etc.).



> **Status:** em desenvolvimento contínuo - estou finalizando o back-end, depois planejo implementar um front-end e fazer deploy (estimo até dezembro/2025)



## Índice

- [Tecnologias](#tecnologias)

- [Requisitos](#requisitos)

- [Como rodar localmente](#como-rodar-localmente)

- [Configuração de banco](#configuração-de-banco)

- [Arquitetura e pastas](#arquitetura-e-pastas)

- [Modelos](#modelos)

- [Endpoints da API](#endpoints-da-api)

- [Exemplos de requisição](#exemplos-de-requisição)

- [Erros comuns](#erros-comuns)

- [Testes](#testes)

- [Roadmap](#roadmap)

- [Contribuição](#contribuição)

- [Autor](#autor)

- [Licença](#licença)

  
---



## Tecnologias



-  **Java** 21+

-  **Spring Boot** (Web, Validation, Data JPA, Security, SQL driver, JWT)

-  **Maven** (wrapper incluso: `mvnw`/`mvnw.cmd`)

- Banco: **H2**/ **PostgreSQL** / **MySQL** - eu utilizei o PostgreSQL.



\* Se autenticação/JWT ainda não estiver ativa, ignore o header `Authorization` nos exemplos.

---  

## Requisitos

- Java 21+

- Git

- (Opcional) Docker e Docker Compose

- (Opcional) PostgreSQL ou MySQL locais



---



## Como rodar localmente



1.  **Clonar o repositório**

```bash

git clone https://github.com/alvaromashni/tarefinhas.git

cd tarefinhas

```



2.  **(Opcional) Configurar o banco** em `src/main/resources/application.properties`. Exemplos abaixo.



3.  **Subir a aplicação**

```bash

./mvnw spring-boot:run

# Windows:

mvnw.cmd spring-boot:run

```

A API iniciará em **http://localhost:8080**.

---

## Configuração de banco



### Opção A — H2 (rápido para desenvolvimento)

```properties

spring.datasource.url=jdbc:h2:mem:tarefinhas;DB_CLOSE_DELAY=-1;MODE=PostgreSQL

spring.datasource.driverClassName=org.h2.Driver

spring.datasource.username=sa

spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update

spring.h2.console.enabled=true

logging.level.org.hibernate.SQL=DEBUG

```



Acesse o console H2 em **/h2-console**.



### Opção B — PostgreSQL

```properties

spring.datasource.url=jdbc:postgresql://localhost:5432/tarefinhas

spring.datasource.username=postgres

spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

```



### Opção C — MySQL

```properties

spring.datasource.url=jdbc:mysql://localhost:3306/tarefinhas?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC

spring.datasource.username=root

spring.datasource.password=secret

spring.jpa.hibernate.ddl-auto=update

```



> Em produção: prefira migrações com **Flyway/Liquibase** e `ddl-auto=validate`.



---



## Arquitetura e pastas



Organização utilizada (MVC + DTOs + camadas claras):
```
src/main/java/...

└── dev/mashni/tarefinhas

├─ controller/ # REST Controllers (AuthController, TaskController)

├─ service/ # Regras de negócio (AuthService, TaskService)

├─ repository/ # Spring Data JPA (UserRepository, TaskRepository)

├─ model/ # Entidades JPA (User, Task)

├─ dto/ # Data Transfer Objects

├─ exception/ # Exceptions + handlers (GlobalExceptionHandler)

├─ config/ # CORS, Swagger/OpenAPI, Security

└─ TarefinhasApplication.java

```

Benefícios: responsabilidades separadas, testes mais simples, manutenção previsível.

---
## Modelos

### User
```json
{

"id":  "string-uuid",

"name":  "string",

"email":  "string",

"password":  "hashed",

"isDeleted":  false

}

```



### Task

```json

{

"id":  "string-uuid",

"taskTitle":  "string",

"taskDescription":  "string",

"status":  "PENDING|DONE",

"createdAt":  "2025-10-28T15:00:00Z",

"updatedAt":  "2025-10-28T15:00:00Z"

}

```  

> Campos de auditoria (`createdAt`, `updatedAt`) e `status` podem ser adicionados se ainda não existirem.

---  

## Endpoints da API

### Autenticação

| Método | Rota | Corpo (JSON) | Descrição |

|:------:|------------------|-----------------------------------------------|--------------------|

| POST | `/auth/register` | `{ "name", "email", "password" }` | Cria usuário |

| POST | `/auth/login` | `{ "email", "password" }` | Autentica usuário |


> **JWT :** rotas de `/tasks` exigem `Authorization: Bearer <token>`.

### Tarefas

| Método | Rota | Corpo (JSON) | Descrição |

|:------:|----------------------|------------------------------------|---------------------------|

| POST | `/tasks/createtask` | `{ "taskTitle", "taskDescription" }` | Cria tarefa |

| GET | `/tasks/getTask{id}` | — | Busca tarefa por `id` |

| PUT | `/tasks/putTask` | `{ "taskId", "taskTitle?", "taskDescription?" }` | Atualiza título/descrição |



> Sugestões REST usuais (implemente conforme necessidade): `GET /tasks`, `DELETE /tasks/{id}`, filtros/paginação.

--- 

## Exemplos de requisição
### Registro

```bash
curl  -X  POST  http://localhost:8080/auth/register  -H  "Content-Type: application/json"  -d  '{ "name":"Ana", "email":"ana@email.com", "password":"123456" }'

```
### Login

```bash

curl  -X  POST  http://localhost:8080/auth/login  -H  "Content-Type: application/json"  -d  '{ "email":"ana@email.com", "password":"123456" }'

```

**Resposta:**

```json

{ "token":  "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." }

```

### Criar tarefa (autenticado)

```bash

curl  -X  POST  http://localhost:8080/tasks/createtask  -H  "Content-Type: application/json"  -H  "Authorization: Bearer <SEU_TOKEN>"  -d  '{ "taskTitle":"Estudar Patterns", "taskDescription":"Revisar Strategy e Template Method" }'

```

### Buscar tarefa por ID (autenticado)

```bash

curl  "http://localhost:8080/tasks/getTask123"  -H  "Authorization: Bearer <SEU_TOKEN>"

```

### Atualizar tarefa (autenticado)

```bash

curl  -X  PUT  http://localhost:8080/tasks/putTask  -H  "Content-Type: application/json"  -H  "Authorization: Bearer <SEU_TOKEN>"  -d  '{ "taskId":"123", "taskTitle":"Estudar Patterns (rev)" }'

```

---  

## Erros comuns

-  **400** — validação de payload (e.g., e-mail inválido, campos obrigatórios ausentes)

-  **401/403** — credenciais ausentes/inválidas ou acesso negado

-  **404** — recurso não encontrado (tarefa inexistente)

-  **409** — conflitos (e.g., e-mail já cadastrado)

-  **500** — erro interno (ver logs)

---
## Testes (ainda não implementei)

-  **Unitários**: serviços e validações

-  **Integração**: repositórios (H2), controllers (MockMvc)

- Sugestões: `JUnit 5`, `Spring Boot Test`, `Testcontainers` (PostgreSQL)

 
---

## Roadmap


- [ ] Documentação **OpenAPI/Swagger**

- [ ] Autenticação JWT + roles (Admin/User)

- [ ] CRUD completo de tarefas (listar, concluir, filtros)

- [ ] Paginação & ordenação

- [ ] Auditoria (`createdAt`, `updatedAt`)

- [ ] Testes (unitários/integr.)

- [ ] CI (GitHub Actions)

- [ ] Dockerfile + `docker-compose` para DB

---


## Contribuição

1. Faça um fork

2. Crie uma branch: `git checkout -b feature/minha-feature`

3. Commit: `git commit -m "feat: minha feature"`

4. Push: `git push origin feature/minha-feature`

5. Abra um Pull Request

Padrão de commits sugerido: **Conventional Commits**.

---

## Autor

-  **Álvaro Mashni** — [@alvaromashni](https://github.com/alvaromashni)
---
## Licença
**MIT**