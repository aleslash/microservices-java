# microservices-java

Exercicio para estudar a construção de microsserviços.

Os protos foram clonados do projeto [microservices-demo](https://github.com/GoogleCloudPlatform/microservices-demo) e estão no [diretório protos](protos/README.md)

Os microsserviços seguem a seguinte estrutura de comunicação:
![Arquitetura dos Microsserviços](img/architecture-diagram.png)

## Tecnologias empregadas

[java](https://www.java.com/pt-BR/)

[Spring Boot](https://spring.io/projects/spring-boot)

[Docker](https://www.docker.com/)

[BloomRPC](https://github.com/uw-labs/bloomrpc)


# Como executar

Clonar o repositório, abrir um terminal e executar o compose.

`cd src`

`docker-compose up --build`

Com o compose, os microservices sobem nas portas:

| microservice | porta |
|---|---|
|AdService|9001|
|CartService|9002|
|CheckoutService|9003|
|CurrencyService | 9004 |
|EmailService|9005|
|PaymentService|9006|
|ProductCatalogService | 9007 |
|RecommendationService|9008|
|ShippingService|9009|
|redis|6379|
|prometheus|9090|
|frontend|8080|
|loadgenerator|N/A|

## Troubleshooting

Em caso do erro abaixo ao executar o loadgenerator, verificar o EOL do arquivo [loadgen.sh](src/loadgenerator/loadgen.sh). Deve ser LF e não CRLF. Erro bem comum em ambientes windows.
```apache
loadgenerator_1            | /bin/sh: 1: ./loadgen.sh: not found
src_loadgenerator_1 exited with code 127
