# API Evento

# Tecnologias

## Backend
- Java (Versão 17)
- Maven
- Lombok
- Spring Boot
- JPA/Hibernate
- Spring Security
- MockMvc e JUnit
- QueryDsl
- Bancos de dados PostgresSQL
- Flyway
- Banco de dados H2 (Testes integrado)
- Swagger (Documentação)
- Jacoco
- Docker

## Sobre o projeto

Esta aplicação consiste que o usuário logado pode cadastrar qual tipo de evento que ele pretende ir, após o cadastro ele pode visualizar, mostrar seus eventos paginados, alterar ou até mesmo deletar. Para realizar esse cadastro de evento é necessário informar o cep, esse 
cep informado é validado no webservice chamado ViaCep.

![Cadastro de Evento](https://github.com/zGabrielZ/assets/blob/main/API%20Evento/api-cadastro-evento.png)


## Modelo conceitual
![Modelo conceitual](https://github.com/zGabrielZ/assets/blob/main/API%20Evento/modelo-conceitual-evento.png)

# Como executar o projeto

## Backend 

Pré requisito: Java 17 e Docker

```
# clonar o projeto evento api
git clone https://github.com/zGabrielZ/api-evento.git

# clonar o projeto que está com o script do docker yaml do banco de dados postgressql, isso é o ambiente dev
git clone https://github.com/zGabrielZ/configs.git

# entrar na pasta do projeto que consiste o script o docker yaml
 cd '.\Config API Evento\postgres-dev\'

# executar o script docker yaml
docker-compose up -d

# apos isso, entrar na pasta backend do projeto api evento
cd backend

# rodar a aplicação
./mvnw spring-boot:run
```

Após executar o projeto, entra no navegador e digite http://localhost:8080/swagger-ui/index.html#/. Vai ser mostrado a documentação da API.

![Documentação](https://github.com/zGabrielZ/assets/blob/main/API%20Evento/doc-api-evento.png)

# Autor

Gabriel Ferreira

https://www.linkedin.com/in/gabriel-ferreira-4b817717b/



