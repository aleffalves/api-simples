# API REST - Cadastro de Profissionais e Contatos

## Descrição
Esta é uma API REST desenvolvida em Java 17 utilizando Spring Boot 3.4.2 para o controle de cadastro de profissionais e seus respectivos contatos. A API permite a criação, consulta, atualização e remoção (lógica) de profissionais e contatos.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.4.2**
- **Lombok** (para redução de boilerplate code)
- **MapStruct** (para mapeamento de objetos DTO/Entidades)
- **Docker** (para conteinerização da aplicação)
- **PostgreSQL 14** (banco de dados relacional)
- **JUnit e Mockito** (para testes unitários e de integração)
- **Swagger** (para documentação da API)

## Estrutura do Banco de Dados

### Tabela `profissionais`
| Campo        | Tipo    | Descrição |
|-------------|--------|------------|
| id          | LONG   | Identificador único |
| nome        | VARCHAR | Nome do profissional |
| cargo       | VARCHAR | Cargo do profissional (Desenvolvedor, Designer, Suporte, Tester) |
| nascimento  | DATE   | Data de nascimento |
| created_date | DATE   | Data de criação do registro |
| excluido       | BOOLEAN | Indica se o profissional está excluido |

### Tabela `contatos`
| Campo        | Tipo    | Descrição |
|-------------|--------|------------|
| id          | LONG   | Identificador único |
| nome        | VARCHAR | Nome do contato |
| contato     | VARCHAR | Número do contato |
| created_date | DATE   | Data de criação do registro |
| profissional_id | LONG | Chave estrangeira para `profissionais` |

## Endpoints Disponíveis

### Profissionais
- `GET /profissionais` - Lista todos os profissionais.
- `GET /profissionais/{id}` - Retorna um profissional pelo ID.
- `POST /profissionais` - Cria um novo profissional.
- `PUT /profissionais/{id}` - Atualiza os dados de um profissional.
- `DELETE /profissionais/{id}` - Realiza uma exclusão lógica do profissional.

### Contatos
- `GET /contatos` - Lista todos os contatos.
- `GET /contatos/{id}` - Retorna um contato pelo ID.
- `POST /contatos` - Cria um novo contato.
- `PUT /contatos/{id}` - Atualiza os dados de um contato.
- `DELETE /contatos/{id}` - Remove um contato.

## Como Rodar o Projeto
1. Certifique-se de que o Docker e o Docker Compose estão instalados.
2. Execute o seguinte comando na raiz do projeto:
   ```sh
   docker-compose up -d
   ```
3. Compile e execute o projeto com:
   ```sh
   mvn spring-boot:run
   ```
4. A aplicação estará disponível em `http://localhost:8080`.

## Testes
Para executar os testes unitários:
```sh
mvn test
```

## Documentação com Swagger
A documentação interativa da API pode ser acessada através de:
```
http://localhost:8080/swagger-ui.html
```

## Autor
Este projeto foi desenvolvido por [Seu Nome].

