# Quarkus/REST/Kafka

## Derleme & Çalıştırma

Gerekli Kafka, Zookeeper ve MySQL sürümlerini docker üzerinde çalıştır:
```shell script
docker-compose up --build
```

Quarkus projesini dev modunda çalıştır:
```shell script
mvn quarkus:dev
```

Postman collection: `quarkus-rest-kafka.postman_collection.json` 
