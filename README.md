# Mercado API

API REST de um mercado, feita pra praticar Spring Boot de verdade — não só aquele CRUD de tutorial.

A ideia surgiu enquanto eu estudava e queria algo que fizesse sentido: um cliente faz um pedido, o estoque baixa, o status vai mudando, e por aí vai.

## O que tem aqui

- Cadastro de clientes
- Cadastro de produtos (com categoria e estoque)
- Criação de pedidos com vários itens
- Baixa automática de estoque
- Controle de status (PENDENTE → PAGO → ENVIADO → ENTREGUE)
- Tratamento de erros com mensagens claras
- Testes unitários com JUnit e Mockito

## Tecnologias

- Java 21
- Spring Boot 3
- Spring Data JPA
- MySQL
- Gradle
- Lombok
- JUnit 5 + Mockito

## Rodando aí na sua máquina

Você vai precisar de Java 21 e MySQL instalados.

```bash
# clona o repositório
git clone https://github.com/Lucasmalves148/loja-mercado-api.git
cd loja-mercado-api/mercado

# cria o banco
mysql -u root -p -e "CREATE DATABASE mercado_db;"

# ajusta o application.properties com seu usuário e senha do MySQL
# depois roda
./gradlew bootRun
```

A API sobe em `http://localhost:8080`.

## Endpoints

### Clientes

| Método | Rota | O que faz |
|--------|------|-----------|
| POST | `/clientes` | Cria um cliente |
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por ID |
| GET | `/clientes/email?email=` | Busca por email |
| PUT | `/clientes/{id}` | Atualiza |
| DELETE | `/clientes/{id}` | Deleta |

### Produtos

| Método | Rota | O que faz |
|--------|------|-----------|
| POST | `/produtos` | Cria um produto |
| GET | `/produtos` | Lista todos |
| GET | `/produtos/{id}` | Busca por ID |
| GET | `/produtos/nome?nome=` | Busca por nome |
| GET | `/produtos/sem-estoque` | Lista os que estão zerados |
| PUT | `/produtos/preco/{id}` | Altera o preço |
| PUT | `/produtos/quantidade/{id}` | Altera o estoque |
| DELETE | `/produtos/{id}` | Deleta |

### Pedidos

| Método | Rota | O que faz |
|--------|------|-----------|
| POST | `/pedido` | Cria um pedido |
| GET | `/pedido` | Lista todos |
| GET | `/pedido/{id}` | Busca por ID |
| GET | `/pedido/status/{cod}` | Busca por status |
| GET | `/pedido/total` | Quantidade total de pedidos |
| GET | `/pedido/total-pedido/{id}` | Valor total de um pedido |
| GET | `/pedido/clientes/{id}` | Pedidos de um cliente |
| PUT | `/pedido/status/{id}` | Atualiza o status |
| DELETE | `/pedido/{id}` | Deleta |

## Exemplo de criação de pedido

```json
{
  "clienteId": 1,
  "itens": [
    { "idProduto": 1, "quantidade": 2 },
    { "idProduto": 3, "quantidade": 1 }
  ]
}
```

A resposta vem com o pedido montado, o cliente, os itens e o total.

## Testes

```bash
./gradlew test
```

Os testes cobrem os serviços de cliente, produto e pedido — tanto os caminhos felizes quanto as exceções (cliente inexistente, estoque insuficiente, etc).

## Estrutura

```
src/main/java/lojamercado/mercado/
├── controller/
├── dto/
│   ├── request/
│   └── response/
├── entity/
├── enumerate/
├── exceptions/
├── handler/
├── map/
├── repository/
└── service/
```

## O que ainda quero fazer

- [ ] Autenticação com JWT
- [ ] Docker + docker-compose
- [ ] Documentação com Swagger
- [ ] Paginação nas listagens
