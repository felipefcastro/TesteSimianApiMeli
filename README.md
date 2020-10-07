# Meli Test

REST API criada para atender o teste aplicado pelo Mercado Livre.

# Tecnologias utilizadas
* Java 8
* Spring Boot
* Maven
* Lombok
* Elastic Beanstalk - AWS
* RDS Mysql Database - AWS

# Endpoints criados 
* /simian - POST
* /stats - GET

# Como funciona
Aplicação disponível em: 
* GET - http://simianapiml-env.eba-r96pyhb2.us-east-1.elasticbeanstalk.com:8080/simian-api/stats
* POST - http://simianapiml-env.eba-r96pyhb2.us-east-1.elasticbeanstalk.com:8080/simian-api/simian

Para rodar localmente, favor realizar os comandos abaixo:
* mvn clean install
* mvn spring-boot:run
* Para acessar o verbo GET, utilize a URL http://localhost:8080/simian-api/stats
* Para acessar o verbo POST, utilize a URL http://localhost:8080/simian-api/simian
