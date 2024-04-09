# Rinha de backend - 2024 - Q1 versão java puro

## Váriaveis de ambiente

* APP_PORT
* DATABASE_URL
* DATABASE_PASSWORD
* DATABASE_USERNAME
* MAX_POOL_SIZE

## Anotações pessoais
Aqui trago algumas opiniões pessoais sobre o desenvolvimento com as ferramentas utilizadas

- Tentei utilizar o logger nativo, mas é muito feio
- Utilizei o padrão de projeto cadeia de responsabilidades para encadear as validações do corpo da requisição
- Para criar e gerenciar uma pool de conexões com banco de dados, utilizei o hikariCP
- Tentei seguir a separação padrão de responsabilidades de pacotes que gosto, ex: controller -> service -> repository -> domain
- Foi bom relembrar o quanto o spring é bom, simples e prático comparando com isso aqui
- Para logs utilizei o logback
- Com toda a certeza, o handle e as validações foram as partes mais chatas de fazer
- Por algum motivo ainda não descoberto, a aplicação começa a engasgar depois de algumas requisições

## Links


