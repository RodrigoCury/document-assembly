# Document Assembly

Como eu gosto de complicar minha vida, ao invés de fazer um simples document Assembler, fiz uma Cloud application para geração de Documentos baseados em Arvores de dados

### Ferramentas

- Docker
- LocalStack (Simular em Docker AWS S3)
- Kafka
- Spring Boot
- Apache POI
- PostgreSQL
- MongoDB
- Flyway

### Detalhes

São dua aplicações RESTful com Spring Boot Web

- API de gerenciamento de documentos (Onde a estrutura de documentos é salva e criada)
- API de Processamento de documentos e gerenciamento do S3 (Onde os documentos são processados, ou para o console ou para .docx e enviados ao S3)

A primeira API se comunica com a segunda através de mensageria via Kafka para iniciar Jobs(processar dados, e limpar arquivo), além disso é através dela que criamos a estrutura de documentos.

A segunda é reponsável por fornecer o processamento e o acesso aos arquivos gerados pela primeira, é aqui em que vemos e fazemos download dos documentos gerados pelas entidades criadas na primeira API.

### Para Rodar

Acesse a pasta do reposítório pelo terminal 

```bash
docker-compose build
docker-compose up -d
```

Para ver os logs

```bash
# API de Ferenciamento
# localhost:2346/api/da
docker logs -f looplex-api

# API de processamento
# localhost:2348/api/dp
docker logs -f looplex-document-processor-api
```

Para facilitar a interação Adicionei na base repositório um JSON que pode ser importado no Postman com todos os requests já estruturados.