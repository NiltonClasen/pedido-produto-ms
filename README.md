### Projeto de Microserviços

Este repositório contém dois microserviços: `msProduto` e `msPedido`. O objetivo deste repositório é entender sobre microserviços e a comunicação entre eles.

## Visão Geral dos Microserviços

### msProduto

`msProduto` é responsável por gerenciar produtos, incluindo seus preços e níveis de estoque.

#### Funcionalidades

- Criar, ler, atualizar e deletar produtos
- Atualizar o estoque de produtos
- Gerenciar preços de produtos

#### Tecnologias

- Java
- Spring Boot
- Maven
- MongoDB

#### Endpoints

- `GET /api/produtos`: Lista todos os produtos
- `POST /api/produtos`: Cria um novo produto
- `GET /api/produtos/{id}`: Obtém um produto pelo ID
- `PUT /api/produtos/{id}`: Atualiza um produto pelo ID
- `DELETE /api/produtos/{id}`: Deleta um produto pelo ID
- `PUT /api/produtos/estoque/{id}`: Atualiza o estoque de um produto pelo ID

### msPedido

`msPedido` é responsável por gerenciar pedidos, incluindo o valor total e os itens do pedido.

#### Funcionalidades

- Criar, ler, atualizar e deletar pedidos
- Calcular o valor total do pedido
- Gerenciar itens do pedido

#### Tecnologias

- Java
- Spring Boot
- Maven
- MySQL

#### Endpoints

- `GET /api/pedidos`: Lista todos os pedidos
- `POST /api/pedidos`: Cria um novo pedido
- `GET /api/pedidos/{id}`: Obtém um pedido pelo ID
- `PUT /api/pedidos/{id}`: Atualiza um pedido pelo ID
- `DELETE /api/pedidos/{id}`: Deleta um pedido pelo ID

## Executando a Aplicação

### Pré-requisitos

- Docker
- Docker Compose

### Passos

1. Clone o repositório:
   ```sh
   git clone <repository-url>
   cd <repository-directory>
   ```

2. Construa e execute os contêineres Docker:
   ```sh
   docker-compose up --build
   ```

3. Acesse as aplicações:
   - `msProduto`: `http://localhost:8080`
   - `msPedido`: `http://localhost:8081`


## Documentação

A documentação da API está disponível via Swagger:
- `msProduto`: `http://localhost:8080/swagger-ui.html`
- `msPedido`: `http://localhost:8081/swagger-ui.html`
