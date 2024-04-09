# Rinha de backend - 2024 - Q1 versão java puro


## Instruções para subir aplicação usando docker
Existe um script para executar o passo a passo para subir todos os serviços, execute no powershell:

    ./init-docker-compose-test.ps1

## Bibliotecas utilizadas

* HikariCp
* Logback
* Jackson
* Postgresql
* slf4j

## Váriaveis de ambiente

* APP_PORT -> default 8080
* DATABASE_URL -> default jdbc:postgresql://localhost:5432/crebito
* DATABASE_PASSWORD -> default java_puro_db
* DATABASE_USERNAME -> default java_puro_db
* MAX_POOL_SIZE -> default 10

## Anotações pessoais
Aqui trago algumas opiniões pessoais sobre o desenvolvimento com as ferramentas utilizadas

- Tentei utilizar o logger nativo, mas é muito feio
- Optei por utilizar o HttpServer nativo do java para ver como ele se comporta tomando fogo (passa mal)
- Utilizei o padrão de projeto cadeia de responsabilidades para encadear as validações do corpo da requisição
- Para criar e gerenciar uma pool de conexões com banco de dados, utilizei o hikariCP
- Tentei seguir a separação padrão de responsabilidades de pacotes que gosto, ex: controller -> service -> repository -> domain
- Foi bom relembrar o quanto o spring é bom, simples e prático comparando com isso aqui
- Para logs utilizei o logback
- Com toda a certeza, o handle e as validações foram as partes mais chatas de fazer
- Por algum motivo ainda não descoberto, a aplicação começa a engasgar depois de algumas requisições
- Aprendi uma estrutura chamada try with resources, é um try catch mas que procura finalizar o que é declarado como resource em sua chamada, ex:
```java
static String readFirstLineFromFile(String path) throws IOException {
    try (FileReader fr = new FileReader(path);
    BufferedReader br = new BufferedReader(fr)) {
    return br.readLine();
    }
}
```

## Links

[Try-with-resources](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html)

[HikariCP](https://github.com/brettwooldridge/HikariCP)

[Rinha de backend - 2024 - q1](https://github.com/zanfranceschi/rinha-de-backend-2024-q1)

[HttpServer - Java](https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpServer.html)

[Padrão de projeto: corrente de responsabilidades (Chain of Responsibility)](https://refactoring.guru/design-patterns/chain-of-responsibility/java/example)


